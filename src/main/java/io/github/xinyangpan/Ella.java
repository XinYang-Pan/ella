package io.github.xinyangpan;

import java.math.BigDecimal;

import io.github.xinyangpan.ella.core.OrderBookImpl;
import io.github.xinyangpan.ella.core.bo.Order;
import io.github.xinyangpan.ella.core.bo.OrderType;
import io.github.xinyangpan.ella.core.bo.Side;

public class Ella {

	public static void main(String[] args) {
		OrderBookImpl orderBook = new OrderBookImpl();
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
		order.setQuantity(new BigDecimal(String.valueOf(quantity)));
		order.setTotalQuantity(new BigDecimal(String.valueOf(quantity)));
		order.setPrice(new BigDecimal(String.valueOf(price)));
		order.setSide(side);
		order.setOrderTs(System.currentTimeMillis());
		return order;
	}

	public static Order newOrder(Side side, long quantity) {
		Order order = new Order();
		order.setId(1);
		order.setOrderType(OrderType.MARKET);
		order.setQuantity(new BigDecimal(String.valueOf(quantity)));
		order.setTotalQuantity(new BigDecimal(String.valueOf(quantity)));
		order.setSide(side);
		order.setOrderTs(System.currentTimeMillis());
		return order;
	}

}
