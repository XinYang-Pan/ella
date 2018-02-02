package io.github.xinyangpan;

import static io.github.xinyangpan.OrderUtils.newOrder;

import org.junit.Test;

import io.github.xinyangpan.ella.core.OrderBookImpl;
import io.github.xinyangpan.ella.core.bo.Side;

public class OrderBookTest2 {

	@Test
	public void several() {
		OrderBookImpl orderBook = NoneMatchBook.bookSample1();
		System.out.println(orderBook.toOrderBoardStr());
		System.out.println(orderBook.placeOrder(newOrder(Side.SELL, 500, 120.01)));
		System.out.println(orderBook.toOrderBoardStr());
		System.out.println(orderBook.placeOrder(newOrder(Side.SELL, 500, 120.01)));
		System.out.println(orderBook.toOrderBoardStr());
		System.out.println(orderBook.placeOrder(newOrder(Side.SELL, 500, 120.01)));
		System.out.println(orderBook.toOrderBoardStr());
		System.out.println(orderBook.placeOrder(newOrder(Side.SELL, 500, 120.58)));
		System.out.println(orderBook.toOrderBoardStr());
		System.out.println(orderBook.placeOrder(newOrder(Side.SELL, 500, 120.57)));
		System.out.println(orderBook.toOrderBoardStr());
		System.out.println(orderBook.placeOrder(newOrder(Side.BUY, 1000, 120.57)));
		System.out.println(orderBook.toOrderBoardStr());
	}

}
