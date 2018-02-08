package io.github.xinyangpan.ella.core;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NavigableMap;
import java.util.TreeMap;
import java.util.stream.Collectors;

import org.springframework.util.Assert;

import com.google.common.collect.Maps;

import io.github.xinyangpan.ella.OrderBook;
import io.github.xinyangpan.ella.core.bo.Action;
import io.github.xinyangpan.ella.core.bo.Order;
import io.github.xinyangpan.ella.core.bo.OrderType;
import io.github.xinyangpan.ella.core.bo.ScaleConfig;
import io.github.xinyangpan.ella.core.bo.Side;
import io.github.xinyangpan.ella.core.bo.Status;

public class OrderBookImpl implements OrderBook {
	private Map<Long, Order> allOrderIndex = Maps.newHashMap();
	private OrderBookListener orderBookListener;
	private ScaleConfig scaleConfig;
	
	private NavigableMap<BigDecimal, OrderBookEntryImpl> bidMap = new TreeMap<>(Comparator.reverseOrder());
	private NavigableMap<BigDecimal, OrderBookEntryImpl> askMap = new TreeMap<>();
	
	private OrderValidate orderValidate = new OrderValidate(allOrderIndex);

	@Override
	public Order placeOrder(Order order) {
		orderValidate.place(order);
		order.setOrderTs(System.currentTimeMillis());
		order.versionPlus();
		switch (order.getOrderType()) {
		case MARKET:
		case LIMIT:
			return this.marketAndLimitOrder(order);
		default:
			throw new IllegalArgumentException(String.format("Order Type not supported.", order.getOrderType()));
		}
	}
	
	@Override
	public Order modifyOrder(Order order) {
		orderValidate.modify(order);
		order.setAction(Action.CANCELING);
		order = this.cancel(order);
		if (order != null && order.getAction() == Action.CANCELED) {
			order.setStatus(Status.LIVE);
			order.setAction(Action.PLACING);
			this.placeOrder(order);
			order.setAction(Action.MODIFIED);
			return order;
		}
		order.setAction(Action.MODIFY_FAILED);
		return order;
	}

	private Order marketAndLimitOrder(Order order) {
		NavigableMap<BigDecimal, OrderBookEntryImpl> oppositeSideMap = this.otherSideBook(order.getSide());
		// 
		Entry<BigDecimal, OrderBookEntryImpl> firstEntry = null;
		while ((firstEntry = oppositeSideMap.firstEntry()) != null) {
			if (this.isEntryPriceBetterOrSameThanOrderPrice(order, firstEntry)) {
				OrderBookEntryImpl orderBookEntry = firstEntry.getValue();
				orderBookEntry.take(order);
				// 
				BigDecimal totalQuantity = orderBookEntry.getTotalQuantity();
				Assert.isTrue(totalQuantity.signum() >= 0 , "Internal Error: totalQuantity");
				if (totalQuantity.signum() == 0) {
					oppositeSideMap.remove(firstEntry.getKey());
				}
				// 
				BigDecimal fillableQuantity = order.getFillableQuantity(orderBookEntry.getPrice(), scaleConfig.getQuantityScale());
				Assert.isTrue(fillableQuantity.signum() >= 0 , "Internal Error: quantity");
				if (fillableQuantity.signum() == 0) {
					// Order is full filled.
					order.complete(Action.EXECUTED);
					return order;
				}
			} else {
				// Order (Limit) has unfilled quantity.
				Assert.isTrue(order.getOrderType() == OrderType.LIMIT , "Internal Error: quantity");
				doPlace(order);
				return order;
			}
		}
		// No Book Entries are left.
		switch (order.getOrderType()) {
		case MARKET:
			order.complete(Action.EXECUTED);
			break;
		case LIMIT:
			doPlace(order);
			break;
		}
		return order;
	}

	private void doPlace(Order order) {
		NavigableMap<BigDecimal, OrderBookEntryImpl> sameSideMap = this.sameSideBook(order.getSide());
		OrderBookEntryImpl orderBookEntry = sameSideMap.get(order.getPrice());
		if (orderBookEntry == null) {
			orderBookEntry = new OrderBookEntryImpl(allOrderIndex, orderBookListener, scaleConfig, order.getPrice());
			sameSideMap.put(order.getPrice(), orderBookEntry);
		}
		orderBookEntry.make(order);
	}

	private boolean isEntryPriceBetterOrSameThanOrderPrice(Order order, Entry<BigDecimal, OrderBookEntryImpl> firstEntry) {
		BigDecimal price = order.getPrice();
		if (price == null) {
			return true;
		}
		Side side = order.getSide();
		return side.isFirstPriceBetterOrSame(price, firstEntry.getKey());
	}

	private NavigableMap<BigDecimal, OrderBookEntryImpl> otherSideBook(Side side) {
		switch (side) {
		case SELL:
			return this.bidMap;
		case BUY:
			return this.askMap;
		default:
			throw new IllegalArgumentException(side.name());
		}
	}

	private NavigableMap<BigDecimal, OrderBookEntryImpl> sameSideBook(Side side) {
		switch (side) {
		case SELL:
			return this.askMap;
		case BUY:
			return this.bidMap;
		default:
			throw new IllegalArgumentException(side.name());
		}
	}

	public NavigableMap<BigDecimal, OrderBookEntry> getSideBook(Side side) {
		NavigableMap<BigDecimal, OrderBookEntryImpl> sideBook = this.sameSideBook(side);
		NavigableMap<BigDecimal, OrderBookEntry> result = Maps.newTreeMap(sideBook.comparator());
		for (Entry<BigDecimal, OrderBookEntryImpl> e : sideBook.entrySet()) {
			result.put(e.getKey(), e.getValue().copy());
		}
		return result;
	}
	
	@Override
	public Order cancel(Order order) {
		orderValidate.cancel(order);
		order.versionPlus();
		NavigableMap<BigDecimal, OrderBookEntryImpl> sameSideBook = this.sameSideBook(order.getSide());
		OrderBookEntryImpl orderBookEntry = sameSideBook.get(order.getPrice());
		return orderBookEntry.cancelOrder(order);
	}
	
	@Override
	public List<Order> snapshot() {
		return allOrderIndex.values().stream().map(order -> order.copy()).collect(Collectors.toList());
	}

	@Override
	public Order getCopy(long orderId) {
		return allOrderIndex.get(orderId).copy();
	}
	
	@Override
	public String toString() {
		return String.format("OrderBook [bidMap=%s, askMap=%s]", bidMap, askMap);
	}
	
	public String toOrderBoardStr() {
		int priceLength = 7;
		int qtyLength = 8;
		StringBuilder sb = new StringBuilder();
		for (Entry<BigDecimal, OrderBookEntryImpl> e : askMap.entrySet()) {
			sb.insert(0, System.lineSeparator()).insert(0, e.getValue().toOrderBoardStr(priceLength, qtyLength, Side.SELL));
		}
		for (Entry<BigDecimal, OrderBookEntryImpl> e : bidMap.entrySet()) {
			sb.append(e.getValue().toOrderBoardStr(priceLength, qtyLength, Side.BUY)).append(System.lineSeparator());
		}
		sb.insert(0, System.lineSeparator()).insert(0, "****** Order Board ******");
		sb.append("*************************");
		return sb.toString();
	}

	public OrderBookListener getOrderBookListener() {
		return orderBookListener;
	}

	public void setOrderBookListener(OrderBookListener orderBookListener) {
		this.orderBookListener = orderBookListener;
	}

	public ScaleConfig getScaleConfig() {
		return scaleConfig;
	}

	public void setScaleConfig(ScaleConfig scaleConfig) {
		this.scaleConfig = scaleConfig;
	}
	
}
