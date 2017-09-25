package io.github.xinyangpan.ella;

import io.github.xinyangpan.ella.core.order.Order;

public interface OrderBookService {

	Order placeOrder(Order order);

	Order cancelOrder(long orderId);

}
