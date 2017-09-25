package io.github.xinyangpan.ella.core;

import java.util.Comparator;
import java.util.Map.Entry;
import java.util.NavigableMap;
import java.util.TreeMap;

import org.springframework.util.Assert;

import io.github.xinyangpan.ella.OrderBookService;
import io.github.xinyangpan.ella.core.bo.Order;
import io.github.xinyangpan.ella.core.bo.OrderType;
import io.github.xinyangpan.ella.core.bo.Side;

public class OrderBook implements OrderBookService {
	private NavigableMap<Double, OrderBookEntry> bidMap = new TreeMap<>(Comparator.reverseOrder());
	private NavigableMap<Double, OrderBookEntry> askMap = new TreeMap<>();

	@Override
	public Order placeOrder(Order order) {
		switch (order.getOrderType()) {
		case MARKET:
		case LIMIT:
			return this.marketAndLimitOrder(order);
		case STOP:
//			return null;
		default:
			throw new IllegalArgumentException(String.format("Order Type not supported.", order.getOrderType()));
		}
	}

	private Order marketAndLimitOrder(Order order) {
		NavigableMap<Double, OrderBookEntry> oppositeSideMap = this.otherSideBook(order.getSide());
		// 
		Entry<Double, OrderBookEntry> firstEntry = null;
		while ((firstEntry = oppositeSideMap.firstEntry()) != null) {
			if (this.isEntryPriceBetterOrSameThanOrderPrice(order, firstEntry)) {
				OrderBookEntry orderBookEntry = firstEntry.getValue();
				orderBookEntry.take(order);
				// 
				long totalQuantity = orderBookEntry.getTotalQuantity();
				Assert.isTrue(totalQuantity >= 0 , "Internal Error: totalQuantity");
				if (totalQuantity == 0) {
					oppositeSideMap.remove(firstEntry.getKey());
				}
				// 
				long quantity = order.getQuantity();
				Assert.isTrue(quantity >= 0 , "Internal Error: quantity");
				if (quantity == 0) {
					// Order is full filled.
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
		if (order.getOrderType() == OrderType.LIMIT) {
			doPlace(order);
		}
		return order;
	}

	private void doPlace(Order order) {
		NavigableMap<Double, OrderBookEntry> sameSideMap = this.sameSideBook(order.getSide());
		OrderBookEntry orderBookEntry = sameSideMap.get(order.getPrice());
		if (orderBookEntry == null) {
			orderBookEntry = new OrderBookEntry();
			orderBookEntry.setPrice(order.getPrice());
			sameSideMap.put(order.getPrice(), orderBookEntry);
		}
		orderBookEntry.place(order);
	}

	private boolean isEntryPriceBetterOrSameThanOrderPrice(Order order, Entry<Double, OrderBookEntry> firstEntry) {
		switch (order.getOrderType()) {
		case MARKET:
			return true;
		case LIMIT:
			Side side = order.getSide();
			return side.isFirstPriceBetterOrSame(order.getPrice(), firstEntry.getKey());
		default:
			throw new IllegalArgumentException(String.format("Order Type not supported.", order.getOrderType()));
		}
	}

	private NavigableMap<Double, OrderBookEntry> otherSideBook(Side side) {
		switch (side) {
		case SELL:
			return this.bidMap;
		case BUY:
			return this.askMap;
		default:
			throw new IllegalArgumentException(side.name());
		}
	}

	private NavigableMap<Double, OrderBookEntry> sameSideBook(Side side) {
		switch (side) {
		case SELL:
			return this.askMap;
		case BUY:
			return this.bidMap;
		default:
			throw new IllegalArgumentException(side.name());
		}
	}

	@Override
	public Order cancelOrder(long orderId) {
		return null;
	}

	@Override
	public String toString() {
		return String.format("OrderBook [bidMap=%s, askMap=%s]", bidMap, askMap);
	}
	
	public String toOrderBoardStr() {
		int priceLength = 7;
		int qtyLength = 8;
		StringBuilder sb = new StringBuilder();
		for (Entry<Double, OrderBookEntry> e : askMap.entrySet()) {
			sb.insert(0, System.lineSeparator()).insert(0, e.getValue().toOrderBoardStr(priceLength, qtyLength, Side.SELL));
		}
		for (Entry<Double, OrderBookEntry> e : bidMap.entrySet()) {
			sb.append(e.getValue().toOrderBoardStr(priceLength, qtyLength, Side.BUY)).append(System.lineSeparator());
		}
		sb.insert(0, System.lineSeparator()).insert(0, "****** Order Board ******");
		sb.append("*************************");
		return sb.toString();
	}
	
}
