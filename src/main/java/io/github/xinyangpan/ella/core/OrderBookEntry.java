package io.github.xinyangpan.ella.core;

import java.util.Deque;
import java.util.LinkedList;

import org.springframework.util.Assert;

import com.google.common.base.Strings;

import io.github.xinyangpan.ella.core.order.Execution;
import io.github.xinyangpan.ella.core.order.Order;
import io.github.xinyangpan.ella.core.order.OrderType;
import io.github.xinyangpan.ella.core.order.Side;

public class OrderBookEntry {
	private double price;
	private long totalQuantity;
	private Deque<Order> orders = new LinkedList<>();

	public synchronized void place(Order order) {
		Assert.isTrue(order.getOrderType() != OrderType.MARKET, "Can not place market order into Order Book.");
		orders.add(order);
		totalQuantity = totalQuantity + order.getQuantity();
	}

	public synchronized void take(Order input) {
		Order order = null;
		while ((order = orders.pollFirst()) != null) {
			long fillingQty = Long.min(order.getQuantity(), input.getQuantity());
			if (fillingQty > 0) {
				totalQuantity = totalQuantity - fillingQty;
				Execution execution = new Execution(price, fillingQty);
				input.fill(execution);
				order.fill(execution);
				if (order.getQuantity() > 0) {
					orders.addFirst(order);
				}
				if (input.getQuantity() <= 0) {
					break;
				}
			}
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

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public long getTotalQuantity() {
		return totalQuantity;
	}

	public void setTotalQuantity(long totalQuantity) {
		this.totalQuantity = totalQuantity;
	}

	public Deque<Order> getOrders() {
		return orders;
	}

	public void setOrders(Deque<Order> orders) {
		this.orders = orders;
	}

}
