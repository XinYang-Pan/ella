package io.github.xinyangpan.ella;

import io.github.xinyangpan.ella.core.bo.Order;

public interface OrderBookService {

	Order placeOrder(Order order);

	Order cancelOrder(long orderId);

}
