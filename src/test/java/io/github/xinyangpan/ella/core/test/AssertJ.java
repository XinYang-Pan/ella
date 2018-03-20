package io.github.xinyangpan.ella.core.test;

import org.assertj.core.api.Assertions;

import io.github.xinyangpan.ella.OrderBook;
import io.github.xinyangpan.ella.core.bo.OrderResult;

public class AssertJ extends Assertions {

	public static OrderAssert assertThat(OrderResult actual) {
		return new OrderAssert(actual);
	}

	public static OrderBookAssert assertThat(OrderBook actual) {
		return new OrderBookAssert(actual);
	}

}
