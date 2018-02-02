package io.github.xinyangpan.ella.core;

import java.math.BigDecimal;
import java.util.Deque;
import java.util.LinkedList;

import org.springframework.util.Assert;

import com.google.common.base.Strings;

import io.github.xinyangpan.ella.core.bo.Execution;
import io.github.xinyangpan.ella.core.bo.Order;
import io.github.xinyangpan.ella.core.bo.OrderType;
import io.github.xinyangpan.ella.core.bo.Side;

public class OrderBookEntry {
	private BigDecimal price;
	private BigDecimal totalQuantity = BigDecimal.ZERO;
	private Deque<Order> orders = new LinkedList<>();

	public void place(Order order) {
		Assert.isTrue(order.getOrderType() == OrderType.LIMIT, "Can place limit order only into Order Book.");
		orders.add(order);
		totalQuantity = totalQuantity.add(order.getQuantity());
	}

	public void take(Order input) {
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
					orders.addFirst(order);
				}
				if (input.getFillableQuantity(price).signum() <= 0) {
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
