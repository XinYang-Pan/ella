package io.github.xinyangpan.ella.core;

import java.math.BigDecimal;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import com.google.common.base.Strings;

import io.github.xinyangpan.ella.core.bo.Execution;
import io.github.xinyangpan.ella.core.bo.Order;
import io.github.xinyangpan.ella.core.bo.OrderType;
import io.github.xinyangpan.ella.core.bo.Side;

class OrderBookEntry {
	private static final Logger LOGGER = LoggerFactory.getLogger(OrderBookEntry.class);
	
	private final Map<Long, Order> allOrderIndex;
	private BigDecimal price;
	private BigDecimal totalQuantity = BigDecimal.ZERO;
	private Deque<Order> orders = new LinkedList<>();

	OrderBookEntry(Map<Long, Order> allOrderIndex) {
		this.allOrderIndex = allOrderIndex;
	}

	public void make(Order order) {
		LOGGER.info("Make Order: {}", order);
		Assert.isTrue(order.getOrderType() == OrderType.LIMIT, "Can place limit order only into Order Book.");
		orders.add(order);
		totalQuantity = totalQuantity.add(order.getQuantity());
		allOrderIndex.put(order.getId(), order);
	}

	public void take(Order input) {
		LOGGER.info("Take Order: {}", input);
		Order order = null;
		while ((order = orders.pollFirst()) != null) {
			BigDecimal inputQuantity = input.getFillableQuantity(price);
			BigDecimal fillingQty = order.getQuantity().min(inputQuantity);
			if (fillingQty.signum() > 0) {
				totalQuantity = totalQuantity.subtract(fillingQty);
				Execution execution = new Execution(price, fillingQty);
				input.fill(execution);
				order.fill(execution);
				if (order.getQuantity().signum() > 0) {
					// still have quantity
					orders.addFirst(order);
				} else {
					// no more quantity, remove from all order
					allOrderIndex.remove(order.getId());
				}
				if (input.getFillableQuantity(price).signum() <= 0) {
					break;
				}
			}
		}
		LOGGER.info("Take Order Result: {}", input);
	}
	
	public void cancelOrder(Order input) {
		LOGGER.info("Cancel Order: {}", input);
		Assert.notNull(input, "Order can not be null.");
		Assert.isTrue(input.getOrderType() == OrderType.LIMIT, "Order must be limit.");
		Order order = allOrderIndex.remove(input.getId());
		if (orders.remove(order)) {
			totalQuantity.subtract(order.getQuantity());
		} else {
			LOGGER.warn("No order found for {}", input);
		}
	}

	@Override
	public String toString() {
		return String.format("OrderBookEntry [price=%s, totalQuantity=%s, orders=%s]", price, totalQuantity, orders);
	}

	public String toOrderBoardStr(int priceLength, int qtyLength, Side side) {
		String priceStr = Strings.padStart(String.valueOf(this.price), priceLength, ' ');
		String qtyStr = Strings.padStart(String.valueOf(this.totalQuantity), qtyLength, ' ');
		String space = Strings.padStart("", priceLength, ' ');
		switch (side) {
		case BUY:
			return String.format("%s %s %s", priceStr, qtyStr, space);
		case SELL:
			return String.format("%s %s %s", space, qtyStr, priceStr);
		default:
			throw new IllegalArgumentException(String.valueOf(side));
		}
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public BigDecimal getTotalQuantity() {
		return totalQuantity;
	}

	public void setTotalQuantity(BigDecimal totalQuantity) {
		this.totalQuantity = totalQuantity;
	}

	public Deque<Order> getOrders() {
		return orders;
	}

	public void setOrders(Deque<Order> orders) {
		this.orders = orders;
	}

}
