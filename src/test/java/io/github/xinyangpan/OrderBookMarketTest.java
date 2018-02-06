package io.github.xinyangpan;

import static io.github.xinyangpan.OrderUtils.market;

import org.junit.Test;

import io.github.xinyangpan.ella.core.OrderBookImpl;
import io.github.xinyangpan.ella.core.bo.Order;
import io.github.xinyangpan.ella.core.bo.Side;
import io.github.xinyangpan.ella.core.test.OrderBookAssert;

public class OrderBookMarketTest {

	@Test
	public void marketSell500() {
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
		Order order = orderBook.placeOrder(market(Side.SELL, 500));
		System.out.println(order);
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
	public void marketSell5000() {
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
		Order order = orderBook.placeOrder(market(Side.SELL, 5000));
		System.out.println(order);
		// 
		OrderBookAssert.assertThat(orderBook)
			.askDepthIs(3)
			.askVolumeIs(120.61, 1000)
			.askVolumeIs(120.59, 1000)
			.askVolumeIs(120.58, 1000)
			.bidDepthIs(0);
	}

	@Test
	public void marketBuy500() {
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
		Order order = orderBook.placeOrder(market(Side.BUY, 500));
		System.out.println(order);
		// 
		OrderBookAssert.assertThat(orderBook)
			.askDepthIs(3)
			.askVolumeIs(120.61, 1000)
			.askVolumeIs(120.59, 1000)
			.askVolumeIs(120.58, 500)
			.bidDepthIs(3)
			.bidVolumeIs(120.57, 1000)
			.bidVolumeIs(120.56, 1000)
			.bidVolumeIs(120.55, 1000);
	}

	@Test
	public void marketBuy5000() {
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
		Order order = orderBook.placeOrder(market(Side.BUY, 5000));
		System.out.println(order);
		// 
		OrderBookAssert.assertThat(orderBook)
			.askDepthIs(0)
			.bidDepthIs(3)
			.bidVolumeIs(120.57, 1000)
			.bidVolumeIs(120.56, 1000)
			.bidVolumeIs(120.55, 1000);
	}

}
