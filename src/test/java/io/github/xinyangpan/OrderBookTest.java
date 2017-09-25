package io.github.xinyangpan;

import static io.github.xinyangpan.OrderUtils.newOrder;

import org.junit.Test;

import io.github.xinyangpan.ella.core.OrderBook;
import io.github.xinyangpan.ella.core.order.Side;

public class OrderBookTest {

	@Test
	public void limitSell500() {
		OrderBook orderBook = NoneMatchBook.bookSample1();
		System.out.println(orderBook.toOrderBoardStr());
		System.out.println(orderBook.placeOrder(newOrder(Side.SELL, 500, 120.01)));
		System.out.println(orderBook.toOrderBoardStr());
	}

	@Test
	public void limitSell5000() {
		OrderBook orderBook = NoneMatchBook.bookSample1();
		System.out.println(orderBook.toOrderBoardStr());
		System.out.println(orderBook.placeOrder(newOrder(Side.SELL, 5000, 120.01)));
		System.out.println(orderBook.toOrderBoardStr());
	}

	@Test
	public void marketSell500() {
		OrderBook orderBook = NoneMatchBook.bookSample1();
		System.out.println(orderBook.toOrderBoardStr());
		System.out.println(orderBook.placeOrder(newOrder(Side.SELL, 500)));
		System.out.println(orderBook.toOrderBoardStr());
	}

	@Test
	public void marketSell5000() {
		OrderBook orderBook = NoneMatchBook.bookSample1();
		System.out.println(orderBook.toOrderBoardStr());
		System.out.println(orderBook.placeOrder(newOrder(Side.SELL, 5000)));
		System.out.println(orderBook.toOrderBoardStr());
	}

	@Test
	public void limitBuy500() {
		OrderBook orderBook = NoneMatchBook.bookSample1();
		System.out.println(orderBook.toOrderBoardStr());
		System.out.println(orderBook.placeOrder(newOrder(Side.BUY, 500, 130.01)));
		System.out.println(orderBook.toOrderBoardStr());
	}

	@Test
	public void limitBuy5000() {
		OrderBook orderBook = NoneMatchBook.bookSample1();
		System.out.println(orderBook.toOrderBoardStr());
		System.out.println(orderBook.placeOrder(newOrder(Side.BUY, 5000, 130.01)));
		System.out.println(orderBook.toOrderBoardStr());
	}

	@Test
	public void marketBuy500() {
		OrderBook orderBook = NoneMatchBook.bookSample1();
		System.out.println(orderBook.toOrderBoardStr());
		System.out.println(orderBook.placeOrder(newOrder(Side.BUY, 500)));
		System.out.println(orderBook.toOrderBoardStr());
	}

	@Test
	public void marketBuy5000() {
		OrderBook orderBook = NoneMatchBook.bookSample1();
		System.out.println(orderBook.toOrderBoardStr());
		System.out.println(orderBook.placeOrder(newOrder(Side.BUY, 5000)));
		System.out.println(orderBook.toOrderBoardStr());
	}

}
