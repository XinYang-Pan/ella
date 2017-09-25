package io.github.xinyangpan;

import io.github.xinyangpan.ella.core.OrderBook;
import io.github.xinyangpan.ella.core.bo.Order;
import io.github.xinyangpan.ella.core.bo.OrderType;
import io.github.xinyangpan.ella.core.bo.Side;

public class Ella {

	public static void main(String[] args) {
		OrderBook orderBook = new OrderBook();
		// BID
		System.out.println(orderBook.placeOrder(newOrder(Side.BUY, 1000, 120.57)));
		System.out.println(orderBook.toOrderBoardStr());
		System.out.println(orderBook.placeOrder(newOrder(Side.BUY, 1000, 120.55)));
		System.out.println(orderBook.toOrderBoardStr());
		System.out.println(orderBook.placeOrder(newOrder(Side.BUY, 1000, 120.56)));
		System.out.println(orderBook.toOrderBoardStr());
		// ASK
		System.out.println(orderBook.placeOrder(newOrder(Side.SELL, 1000, 120.56)));
		System.out.println(orderBook.toOrderBoardStr());
		System.out.println(orderBook.placeOrder(newOrder(Side.SELL, 1000, 120.53)));
		System.out.println(orderBook.toOrderBoardStr());
		System.out.println(orderBook.placeOrder(newOrder(Side.SELL, 1000, 120.52)));
		System.out.println(orderBook.toOrderBoardStr());
		// 
		System.out.println(orderBook.toOrderBoardStr());
	}

	public static Order newOrder(Side side, long quantity, double price) {
		Order order = new Order();
		order.setId(1);
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
		order.setId(1);
		order.setOrderType(OrderType.MARKET);
		order.setQuantity(quantity);
		order.setFilledQuantity(0);
		order.setTotalQuantity(quantity);
		order.setSide(side);
		order.setOrderTs(System.currentTimeMillis());
		return order;
	}

}
