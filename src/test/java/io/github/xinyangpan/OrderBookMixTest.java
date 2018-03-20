package io.github.xinyangpan;

import static io.github.xinyangpan.OrderUtils.limit;
import static io.github.xinyangpan.ella.core.test.AssertJ.assertThat;

import org.junit.Test;

import io.github.xinyangpan.ella.core.OrderBookImpl;
import io.github.xinyangpan.ella.core.bo.Action;
import io.github.xinyangpan.ella.core.bo.Order;
import io.github.xinyangpan.ella.core.bo.OrderResult;
import io.github.xinyangpan.ella.core.bo.Side;

public class OrderBookMixTest {

	@Test
	public void several() {
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
		System.out.println(orderBook.placeOrder(limit(Side.SELL, 1500, 120.01)));
		System.out.println(orderBook.placeOrder(limit(Side.SELL, 500, 120.01)));
		System.out.println(orderBook.placeOrder(limit(Side.SELL, 500, 120.01)));
		System.out.println(orderBook.placeOrder(limit(Side.SELL, 500, 120.58)));
		System.out.println(orderBook.placeOrder(limit(Side.SELL, 500, 120.57)));
		// 
		assertThat(orderBook)
			.askDepthIs(4)
			.askVolumeIs(120.61, 1000)
			.askVolumeIs(120.59, 1000)
			.askVolumeIs(120.58, 1500)
			.askVolumeIs(120.57, 500)
			.bidDepthIs(1)
			.bidVolumeIs(120.57, 0)
			.bidVolumeIs(120.56, 0)
			.bidVolumeIs(120.55, 500);
	}

	@Test
	public void several1() {
		OrderBookImpl orderBook = NoneMatchBook.bookSample1();
		System.out.println(orderBook.toOrderBoardStr());
		System.out.println(orderBook.placeOrder(limit(Side.SELL, 1500, 120.01)));
//		System.out.println(orderBook.placeOrder(market(Side.SELL, 15000)));
//		System.out.println(orderBook.placeOrder(market(Side.SELL, 15000)));
		System.out.println(orderBook.toOrderBoardStr());
		System.out.println(orderBook.placeOrder(limit(Side.SELL, 500, 120.01)));
		System.out.println(orderBook.toOrderBoardStr());
		System.out.println(orderBook.placeOrder(limit(Side.SELL, 500, 120.01)));
		System.out.println(orderBook.toOrderBoardStr());
		System.out.println(orderBook.placeOrder(limit(Side.SELL, 500, 120.58)));
		System.out.println(orderBook.toOrderBoardStr());
		System.out.println(orderBook.placeOrder(limit(Side.SELL, 500, 120.57)));
		System.out.println(orderBook.toOrderBoardStr());
		OrderResult orderResult = orderBook.placeOrder(limit(Side.BUY, 1000, 120.57));
		System.out.println(orderResult);
		System.out.println(orderBook.toOrderBoardStr());
		System.out.println(orderBook.snapshot());
		Order order = orderResult.getOrder().copy();
		order.versionPlus();
		order.setAction(Action.CANCELING);
		System.out.println(orderBook.cancel(order));
		System.out.println(orderBook.snapshot());
	}
}
