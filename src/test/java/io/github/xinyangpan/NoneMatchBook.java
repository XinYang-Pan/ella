package io.github.xinyangpan;

import static io.github.xinyangpan.OrderUtils.limit;

import io.github.xinyangpan.ella.core.OrderBookImpl;
import io.github.xinyangpan.ella.core.bo.Side;

public class NoneMatchBook {
	//        1000  120.61
	//        1000  120.59
	//        1000  120.58
	// 120.57 1000        
	// 120.56 1000        
	// 120.55 1000        
	public static OrderBookImpl bookSample1() {
		OrderBookImpl orderBook = new OrderBookImpl();
		orderBook.setExecutionListener(System.out::println);
		// ASK
		orderBook.placeOrder(limit(Side.SELL, 1000, 120.59));
		orderBook.placeOrder(limit(Side.SELL, 1000, 120.61));
		orderBook.placeOrder(limit(Side.SELL, 1000, 120.58));
		// BID
		orderBook.placeOrder(limit(Side.BUY, 1000, 120.57));
		orderBook.placeOrder(limit(Side.BUY, 1000, 120.55));
		orderBook.placeOrder(limit(Side.BUY, 1000, 120.56));
		return orderBook;
	}

}
