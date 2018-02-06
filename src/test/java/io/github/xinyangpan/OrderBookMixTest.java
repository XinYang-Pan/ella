package io.github.xinyangpan;

import static io.github.xinyangpan.OrderUtils.limit;
import static io.github.xinyangpan.OrderUtils.market;

import org.junit.Test;

import io.github.xinyangpan.ella.core.OrderBookImpl;
import io.github.xinyangpan.ella.core.bo.Order;
import io.github.xinyangpan.ella.core.bo.Side;
import io.github.xinyangpan.ella.core.bo.Status;

public class OrderBookMixTest {

	@Test
	public void several() {
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
		Order order = orderBook.placeOrder(limit(Side.BUY, 1000, 120.57));
		System.out.println(order);
		System.out.println(orderBook.toOrderBoardStr());
		System.out.println(orderBook.snapshot());
		order = order.copy();
		order.versionPlus();
		order.setStatus(Status.CANCELLING);
		System.out.println(orderBook.cancel(order));
		System.out.println(orderBook.snapshot());
	}

}
