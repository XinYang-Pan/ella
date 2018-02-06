package io.github.xinyangpan.ella.core;

import java.math.BigDecimal;
import java.util.Deque;
import java.util.LinkedList;
import java.util.stream.Collectors;

import io.github.xinyangpan.ella.core.bo.Order;

public class OrderBookEntry {

	protected BigDecimal price;
	protected BigDecimal totalQuantity = BigDecimal.ZERO;
	protected Deque<Order> orders = new LinkedList<>();

	public OrderBookEntry() {
	}

	public OrderBookEntry copy() {
		OrderBookEntry copy = new OrderBookEntry();
		copy.price = this.price;
		copy.totalQuantity = this.totalQuantity;
		copy.orders.addAll(this.orders.stream().map(Order::copy).collect(Collectors.toList()));
		return copy;
	}
	
	@Override
	public String toString() {
		return String.format("OrderBookEntry [price=%s, totalQuantity=%s, orders=%s]", price, totalQuantity, orders);
	}

	public BigDecimal getPrice() {
		return price;
	}

	public BigDecimal getTotalQuantity() {
		return totalQuantity;
	}

	public Deque<Order> getOrders() {
		return orders;
	}

}