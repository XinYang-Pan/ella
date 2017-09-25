package io.github.xinyangpan;

import java.util.concurrent.atomic.AtomicLong;

import io.github.xinyangpan.ella.core.bo.Order;
import io.github.xinyangpan.ella.core.bo.OrderType;
import io.github.xinyangpan.ella.core.bo.Side;

public class OrderUtils {
	private static AtomicLong id = new AtomicLong(1);

	public static Order newOrder(Side side, long quantity, double price) {
		Order order = new Order();
		order.setId(id.getAndIncrement());
		order.setOrderType(OrderType.LIMIT);
		order.setQuantity(quantity);
		order.setFilledQuantity(0);
		order.setTotalQuantity(quantity);
		order.setPrice(price);
		order.setSide(side);
		order.setOrderTs(System.currentTimeMillis());
		return order;
	}

	public static Order newOrder(Side side, long quantity) {
		Order order = new Order();
		order.setId(id.getAndIncrement());
		order.setOrderType(OrderType.MARKET);
		order.setQuantity(quantity);
		order.setFilledQuantity(0);
		order.setTotalQuantity(quantity);
		order.setSide(side);
		order.setOrderTs(System.currentTimeMillis());
		return order;
	}

}
