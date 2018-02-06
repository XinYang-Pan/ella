package io.github.xinyangpan.ella.core.test;

import org.assertj.core.api.AbstractAssert;

import io.github.xinyangpan.ella.core.bo.Order;

public class OrderAssert extends AbstractAssert<OrderAssert, Order> {


	public OrderAssert(Order actual) {
		super(actual, OrderAssert.class);
	}

	public static OrderAssert assertThat(Order actual) {
		return new OrderAssert(actual);
	}
	
}
