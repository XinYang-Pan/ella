package io.github.xinyangpan;

import static io.github.xinyangpan.OrderUtils.newOrder;

import org.junit.Test;

import io.github.xinyangpan.ella.core.OrderBookImpl;
import io.github.xinyangpan.ella.core.bo.Side;

public class OrderBookTest {

	@Test
	public void limitSell500() {
		OrderBookImpl orderBook = NoneMatchBook.bookSample1();
		System.out.println(orderBook.toOrderBoardStr());
		System.out.println(orderBook.placeOrder(newOrder(Side.SELL, 500, 120.01)));
		System.out.println(orderBook.toOrderBoardStr());
	}

	@Test
	public void limitSell5000() {
		OrderBookImpl orderBook = NoneMatchBook.bookSample1();
		System.out.println(orderBook.toOrderBoardStr());
		System.out.println(orderBook.placeOrder(newOrder(Side.SELL, 5000, 120.01)));
		System.out.println(orderBook.toOrderBoardStr());
	}

	@Test
	public void marketSell500() {
		OrderBookImpl orderBook = NoneMatchBook.bookSample1();
		System.out.println(orderBook.toOrderBoardStr());
		System.out.println(orderBook.placeOrder(newOrder(Side.SELL, 500)));
		System.out.println(orderBook.toOrderBoardStr());
	}

	@Test
	public void marketSell5000() {
		OrderBookImpl orderBook = NoneMatchBook.bookSample1();
		System.out.println(orderBook.toOrderBoardStr());
		System.out.println(orderBook.placeOrder(newOrder(Side.SELL, 5000)));
		System.out.println(orderBook.toOrderBoardStr());
	}

	@Test
	public void limitBuy500() {
		OrderBookImpl orderBook = NoneMatchBook.bookSample1();
		System.out.println(orderBook.toOrderBoardStr());
		System.out.println(orderBook.placeOrder(newOrder(Side.BUY, 500, 130.01)));
		System.out.println(orderBook.toOrderBoardStr());
	}

	@Test
	public void limitBuy5000() {
		OrderBookImpl orderBook = NoneMatchBook.bookSample1();
		System.out.println(orderBook.toOrderBoardStr());
		System.out.println(orderBook.placeOrder(newOrder(Side.BUY, 5000, 130.01)));
		System.out.println(orderBook.toOrderBoardStr());
	}

	@Test
	public void marketBuy500() {
		OrderBookImpl orderBook = NoneMatchBook.bookSample1();
		System.out.println(orderBook.toOrderBoardStr());
		System.out.println(orderBook.placeOrder(newOrder(Side.BUY, 500)));
		System.out.println(orderBook.toOrderBoardStr());
	}

	@Test
	public void marketBuy5000() {
		OrderBookImpl orderBook = NoneMatchBook.bookSample1();
		System.out.println(orderBook.toOrderBoardStr());
		System.out.println(orderBook.placeOrder(newOrder(Side.BUY, 5000)));
		System.out.println(orderBook.toOrderBoardStr());
	}

}
