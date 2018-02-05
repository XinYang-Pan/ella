package io.github.xinyangpan.ella;

import io.github.xinyangpan.ella.core.bo.Order;

public interface OrderBook {

	Order placeOrder(Order order);

	Order cancel(Order order);

}
