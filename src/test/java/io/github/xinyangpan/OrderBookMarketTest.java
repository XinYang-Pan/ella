package io.github.xinyangpan;

import static io.github.xinyangpan.OrderUtils.limit;
import static io.github.xinyangpan.OrderUtils.market;

import org.junit.Test;

import io.github.xinyangpan.ella.core.OrderBookImpl;
import io.github.xinyangpan.ella.core.bo.Side;
import io.github.xinyangpan.ella.core.test.OrderBookAssert;

public class OrderBookMarketTest {

	@Test
	public void limitSell500() {
		OrderBookImpl orderBook = NoneMatchBook.bookSample1();
		// 
		OrderBookAssert.assertThat(orderBook)
			.askDepthIs(3)
			.askVolumeIs(120.61, 1000)
			.askVolumeIs(120.59, 1000)
			.askVolumeIs(120.58, 1000)
			.bidDepthIs(3)
			.bidVolumeIs(120.57, 1000)
			.bidVolumeIs(120.56, 1000)
			.bidVolumeIs(120.55, 1000);
		System.out.println(orderBook.placeOrder(limit(Side.SELL, 500, 120.01)));
		// 
		OrderBookAssert.assertThat(orderBook)
			.askDepthIs(3)
			.askVolumeIs(120.61, 1000)
			.askVolumeIs(120.59, 1000)
			.askVolumeIs(120.58, 1000)
			.bidDepthIs(3)
			.bidVolumeIs(120.57, 500)
			.bidVolumeIs(120.56, 1000)
			.bidVolumeIs(120.55, 1000);
	}

	@Test
	public void limitSell5000() {
		OrderBookImpl orderBook = NoneMatchBook.bookSample1();
		// 
		OrderBookAssert.assertThat(orderBook)
			.askDepthIs(3)
			.askVolumeIs(120.61, 1000)
			.askVolumeIs(120.59, 1000)
			.askVolumeIs(120.58, 1000)
			.bidDepthIs(3)
			.bidVolumeIs(120.57, 1000)
			.bidVolumeIs(120.56, 1000)
			.bidVolumeIs(120.55, 1000);
		System.out.println(orderBook.placeOrder(limit(Side.SELL, 5000, 120.01)));
		// 
		OrderBookAssert.assertThat(orderBook)
			.askDepthIs(4)
			.askVolumeIs(120.61, 1000)
			.askVolumeIs(120.59, 1000)
			.askVolumeIs(120.58, 1000)
			.askVolumeIs(120.01, 2000)
			.bidDepthIs(0);
	}

	@Test
	public void marketSell500() {
		OrderBookImpl orderBook = NoneMatchBook.bookSample1();
		System.out.println(orderBook.toOrderBoardStr());
		System.out.println(orderBook.placeOrder(market(Side.SELL, 500)));
		System.out.println(orderBook.toOrderBoardStr());
	}

	@Test
	public void marketSell5000() {
		OrderBookImpl orderBook = NoneMatchBook.bookSample1();
		System.out.println(orderBook.toOrderBoardStr());
		System.out.println(orderBook.placeOrder(market(Side.SELL, 5000)));
		System.out.println(orderBook.toOrderBoardStr());
	}

	@Test
	public void limitBuy500() {
		OrderBookImpl orderBook = NoneMatchBook.bookSample1();
		System.out.println(orderBook.toOrderBoardStr());
		System.out.println(orderBook.placeOrder(limit(Side.BUY, 500, 130.01)));
		System.out.println(orderBook.toOrderBoardStr());
	}

	@Test
	public void limitBuy5000() {
		OrderBookImpl orderBook = NoneMatchBook.bookSample1();
		System.out.println(orderBook.toOrderBoardStr());
		System.out.println(orderBook.placeOrder(limit(Side.BUY, 5000, 130.01)));
		System.out.println(orderBook.toOrderBoardStr());
	}

	@Test
	public void marketBuy500() {
		OrderBookImpl orderBook = NoneMatchBook.bookSample1();
		System.out.println(orderBook.toOrderBoardStr());
		System.out.println(orderBook.placeOrder(market(Side.BUY, 500)));
		System.out.println(orderBook.toOrderBoardStr());
	}

	@Test
	public void marketBuy5000() {
		OrderBookImpl orderBook = NoneMatchBook.bookSample1();
		System.out.println(orderBook.toOrderBoardStr());
		System.out.println(orderBook.placeOrder(market(Side.BUY, 5000)));
		System.out.println(orderBook.toOrderBoardStr());
	}

}
