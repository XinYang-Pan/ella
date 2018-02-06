package io.github.xinyangpan;

import static io.github.xinyangpan.OrderUtils.limit;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import io.github.xinyangpan.ella.core.OrderBookImpl;
import io.github.xinyangpan.ella.core.bo.Order;
import io.github.xinyangpan.ella.core.bo.Side;
import io.github.xinyangpan.ella.core.bo.Status;
import io.github.xinyangpan.ella.core.test.OrderBookAssert;

public class OrderBookLimitTest {

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
		Order order = orderBook.placeOrder(limit(Side.SELL, 500, 120.01));
		System.out.println(order);
		// Order
		assertThat(order.getStatus()).isEqualTo(Status.FILLED);
		assertThat(order.getQuantity()).isEqualByComparingTo("0");
		assertThat(order.getFilledQuantity()).isEqualByComparingTo("500");
		assertThat(order.getVersion()).isEqualTo(2);
		// Order Book
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
		Order order = orderBook.placeOrder(limit(Side.SELL, 5000, 120.01));
		System.out.println(order);
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
	public void limitBuy500() {
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
		Order order = orderBook.placeOrder(limit(Side.BUY, 500, 130.01));
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
	public void limitBuy5000() {
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
		Order order = orderBook.placeOrder(limit(Side.BUY, 5000, 130.01));
		System.out.println(order);
		// 
		OrderBookAssert.assertThat(orderBook)
			.askDepthIs(0)
			.bidDepthIs(4)
			.bidVolumeIs(130.01, 2000)
			.bidVolumeIs(120.57, 1000)
			.bidVolumeIs(120.56, 1000)
			.bidVolumeIs(120.55, 1000);
	}

}
