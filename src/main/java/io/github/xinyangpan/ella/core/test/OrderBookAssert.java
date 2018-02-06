package io.github.xinyangpan.ella.core.test;

import java.math.BigDecimal;
import java.util.NavigableMap;

import org.assertj.core.api.AbstractAssert;

import io.github.xinyangpan.ella.OrderBook;
import io.github.xinyangpan.ella.core.OrderBookEntry;
import io.github.xinyangpan.ella.core.bo.Side;

public class OrderBookAssert extends AbstractAssert<OrderBookAssert, OrderBook> {

	public OrderBookAssert(OrderBook actual) {
		super(actual, OrderBookAssert.class);
	}

	public static OrderBookAssert assertThat(OrderBook actual) {
		return new OrderBookAssert(actual);
	}

	public OrderBookAssert bidDepthIs(int depth) {
		return this.depthIs(Side.BUY, depth);
	}

	public OrderBookAssert bidVolumeIs(BigDecimal price, BigDecimal volume) {
		return this.volumeIs(Side.BUY, price, volume);
	}

	public OrderBookAssert askDepthIs(int depth) {
		return this.depthIs(Side.SELL, depth);
	}

	public OrderBookAssert askVolumeIs(BigDecimal price, BigDecimal volume) {
		return this.volumeIs(Side.SELL, price, volume);
	}

	public OrderBookAssert depthIs(Side side, int depth) {
		isNotNull();

		NavigableMap<BigDecimal, OrderBookEntry> sideBook = actual.getSideBook(side);

		// check condition
		if (sideBook.size() != depth) {
			failWithMessage("Expected Order Book Depth for <%s> to be <%s> but was <%s>", side, depth, sideBook.size());
		}
		return this;
	}

	public OrderBookAssert volumeIs(Side side, BigDecimal price, BigDecimal volume) {
		isNotNull();

		NavigableMap<BigDecimal, OrderBookEntry> sideBook = actual.getSideBook(side);
		OrderBookEntry orderBookEntry = sideBook.get(price);

		// check condition
		if (orderBookEntry.getTotalQuantity().compareTo(volume) != 0) {
			failWithMessage("Expected Order Book Volume for <%s-%s> to be <%s> but was <%s>", side, price, volume, orderBookEntry.getTotalQuantity());
		}
		return this;
	}

}
