package io.github.xinyangpan;

import static io.github.xinyangpan.OrderUtils.market;
import static io.github.xinyangpan.ella.core.test.AssertJ.assertThat;

import org.junit.Test;

import io.github.xinyangpan.ella.core.OrderBookImpl;
import io.github.xinyangpan.ella.core.bo.Action;
import io.github.xinyangpan.ella.core.bo.OrderResult;
import io.github.xinyangpan.ella.core.bo.Side;
import io.github.xinyangpan.ella.core.bo.Status;

public class OrderBookMarketTest {

	@Test
	public void sell500() {
		OrderBookImpl orderBook = NoneMatchBook.bookSample1();
		// 
		assertThat(orderBook)
			.askDepthIs(3)
			.askVolumeIs(120.61, 1000)
			.askVolumeIs(120.59, 1000)
			.askVolumeIs(120.58, 1000)
			.bidDepthIs(3)
			.bidVolumeIs(120.57, 1000)
			.bidVolumeIs(120.56, 1000)
			.bidVolumeIs(120.55, 1000);
		OrderResult orderResult = orderBook.placeOrder(market(Side.SELL, 500));
		System.out.println(orderResult);
		// Order
		assertThat(orderResult).is(Status.FILLED, Action.EXECUTED).quantityIs(0).filledQuantityIs(500)
			.executionSizeIs(1)
			.hasExecution(1, 120.57, 500, 60285);
		// Order Book
		assertThat(orderBook)
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
	public void sell1500() {
		OrderBookImpl orderBook = NoneMatchBook.bookSample1();
		// 
		assertThat(orderBook)
			.askDepthIs(3)
			.askVolumeIs(120.61, 1000)
			.askVolumeIs(120.59, 1000)
			.askVolumeIs(120.58, 1000)
			.bidDepthIs(3)
			.bidVolumeIs(120.57, 1000)
			.bidVolumeIs(120.56, 1000)
			.bidVolumeIs(120.55, 1000);
		OrderResult orderResult = orderBook.placeOrder(market(Side.SELL, 1500));
		System.out.println(orderResult);
		// Order
		assertThat(orderResult).is(Status.FILLED, Action.EXECUTED).quantityIs(0).filledQuantityIs(1500)
			.executionSizeIs(2)
			.hasExecution(1, 120.57, 1000, 120570)
			.hasExecution(2, 120.56, 500, 60280);
		// Order Book
		assertThat(orderBook)
			.askDepthIs(3)
			.askVolumeIs(120.61, 1000)
			.askVolumeIs(120.59, 1000)
			.askVolumeIs(120.58, 1000)
			.bidDepthIs(2)
			.bidVolumeIs(120.56, 500)
			.bidVolumeIs(120.55, 1000);
	}

	@Test
	public void sell5000() {
		OrderBookImpl orderBook = NoneMatchBook.bookSample1();
		// 
		assertThat(orderBook)
			.askDepthIs(3)
			.askVolumeIs(120.61, 1000)
			.askVolumeIs(120.59, 1000)
			.askVolumeIs(120.58, 1000)
			.bidDepthIs(3)
			.bidVolumeIs(120.57, 1000)
			.bidVolumeIs(120.56, 1000)
			.bidVolumeIs(120.55, 1000);
		OrderResult orderResult = orderBook.placeOrder(market(Side.SELL, 5000));
		System.out.println(orderResult);
		// Order
		assertThat(orderResult).is(Status.PARTIAL_FILLED, Action.EXECUTED).quantityIs(2000).filledQuantityIs(3000)
			.executionSizeIs(3)
			.hasExecution(1, 120.57, 1000, 120570)
			.hasExecution(2, 120.56, 1000, 120560)
			.hasExecution(3, 120.55, 1000, 120550);
		// Order Book
		assertThat(orderBook)
			.askDepthIs(3)
			.askVolumeIs(120.61, 1000)
			.askVolumeIs(120.59, 1000)
			.askVolumeIs(120.58, 1000)
			.bidDepthIs(0);
	}

	@Test
	public void buy500() {
		OrderBookImpl orderBook = NoneMatchBook.bookSample1();
		// 
		assertThat(orderBook)
			.askDepthIs(3)
			.askVolumeIs(120.61, 1000)
			.askVolumeIs(120.59, 1000)
			.askVolumeIs(120.58, 1000)
			.bidDepthIs(3)
			.bidVolumeIs(120.57, 1000)
			.bidVolumeIs(120.56, 1000)
			.bidVolumeIs(120.55, 1000);
		OrderResult orderResult = orderBook.placeOrder(market(Side.BUY, 500));
		System.out.println(orderResult);
		// Order
		assertThat(orderResult).is(Status.FILLED, Action.EXECUTED).quantityIs(0).filledQuantityIs(500)
			.executionSizeIs(1)
			.hasExecution(1, 120.58, 500, 60290);
		// Order Book
		assertThat(orderBook)
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
	public void buy1500() {
		OrderBookImpl orderBook = NoneMatchBook.bookSample1();
		// 
		assertThat(orderBook)
			.askDepthIs(3)
			.askVolumeIs(120.61, 1000)
			.askVolumeIs(120.59, 1000)
			.askVolumeIs(120.58, 1000)
			.bidDepthIs(3)
			.bidVolumeIs(120.57, 1000)
			.bidVolumeIs(120.56, 1000)
			.bidVolumeIs(120.55, 1000);
		OrderResult orderResult = orderBook.placeOrder(market(Side.BUY, 1500));
		System.out.println(orderResult);
		// Order
		assertThat(orderResult).is(Status.FILLED, Action.EXECUTED).quantityIs(0).filledQuantityIs(1500)
			.executionSizeIs(2)
			.hasExecution(1, 120.58, 1000, 120580)
			.hasExecution(2, 120.59, 500, 60295);
		// Order Book
		assertThat(orderBook)
			.askDepthIs(2)
			.askVolumeIs(120.61, 1000)
			.askVolumeIs(120.59, 500)
			.bidDepthIs(3)
			.bidVolumeIs(120.57, 1000)
			.bidVolumeIs(120.56, 1000)
			.bidVolumeIs(120.55, 1000);
	}

	@Test
	public void buy5000() {
		OrderBookImpl orderBook = NoneMatchBook.bookSample1();
		// 
		assertThat(orderBook)
			.askDepthIs(3)
			.askVolumeIs(120.61, 1000)
			.askVolumeIs(120.59, 1000)
			.askVolumeIs(120.58, 1000)
			.bidDepthIs(3)
			.bidVolumeIs(120.57, 1000)
			.bidVolumeIs(120.56, 1000)
			.bidVolumeIs(120.55, 1000);
		OrderResult orderResult = orderBook.placeOrder(market(Side.BUY, 5000));
		System.out.println(orderResult);
		// Order
		assertThat(orderResult).is(Status.PARTIAL_FILLED, Action.EXECUTED).quantityIs(2000).filledQuantityIs(3000)
			.executionSizeIs(3)
			.hasExecution(1, 120.58, 1000, 120580)
			.hasExecution(2, 120.59, 1000, 120590)
			.hasExecution(3, 120.61, 1000, 120610);
		// Order Book
		assertThat(orderBook)
			.askDepthIs(0)
			.bidDepthIs(3)
			.bidVolumeIs(120.57, 1000)
			.bidVolumeIs(120.56, 1000)
			.bidVolumeIs(120.55, 1000);
		orderResult = orderBook.placeOrder(market(Side.BUY, 5000));
		System.out.println(orderResult);
		// Order
		assertThat(orderResult).is(Status.NONE_FILLED, Action.EXECUTED).quantityIs(5000).filledQuantityIs(0);
	}

}
