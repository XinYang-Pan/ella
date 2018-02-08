package io.github.xinyangpan.ella;

import java.math.BigDecimal;
import java.util.List;
import java.util.NavigableMap;

import io.github.xinyangpan.ella.core.OrderBookEntry;
import io.github.xinyangpan.ella.core.bo.Order;
import io.github.xinyangpan.ella.core.bo.Side;

public interface OrderBook {

	Order placeOrder(Order order);

	Order cancel(Order order);
	
	Order modifyOrder(Order order);

	Order getCopy(long orderId);

	List<Order> snapshot();

	NavigableMap<BigDecimal, OrderBookEntry> getSideBook(Side side);


}
