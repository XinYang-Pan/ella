package io.github.xinyangpan.ella.core.test;

import java.math.BigDecimal;

import org.assertj.core.api.AbstractAssert;

import io.github.xinyangpan.ella.OrderBook;

public class OrderBookAssert extends AbstractAssert<OrderBookAssert, OrderBook> {

	public OrderBookAssert(OrderBook actual) {
		super(actual, OrderBookAssert.class);
	}

	public static OrderBookAssert assertThat(OrderBook actual) {
		return new OrderBookAssert(actual);
	}

	public OrderBookAssert bidDepth(int depth) {
		// check that actual TolkienCharacter we want to make assertions on is not null.
		isNotNull();

		// check condition
//		if (!Objects.equals(actual.getName(), name)) {
//			failWithMessage("Expected character's name to be <%s> but was <%s>", name, actual.getName());
//		}

		// return the current assertion for method chaining
		return this;
	}

	public OrderBookAssert bidVolume(BigDecimal price, BigDecimal volume) {
		// check that actual TolkienCharacter we want to make assertions on is not null.
		isNotNull();

		// check condition
//		if (!Objects.equals(actual.getName(), name)) {
//			failWithMessage("Expected character's name to be <%s> but was <%s>", name, actual.getName());
//		}

		// return the current assertion for method chaining
		return this;
	}

	public OrderBookAssert askDepth(int depth) {
		// check that actual TolkienCharacter we want to make assertions on is not null.
		isNotNull();

		// check condition
//		if (!Objects.equals(actual.getName(), name)) {
//			failWithMessage("Expected character's name to be <%s> but was <%s>", name, actual.getName());
//		}

		// return the current assertion for method chaining
		return this;
	}

	public OrderBookAssert askVolume(BigDecimal price, BigDecimal volume) {
		// check that actual TolkienCharacter we want to make assertions on is not null.
		isNotNull();

		// check condition
//		if (!Objects.equals(actual.getName(), name)) {
//			failWithMessage("Expected character's name to be <%s> but was <%s>", name, actual.getName());
//		}

		// return the current assertion for method chaining
		return this;
	}

}
