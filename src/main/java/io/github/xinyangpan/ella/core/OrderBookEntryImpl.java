package io.github.xinyangpan.ella.core;

import java.math.BigDecimal;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import com.google.common.base.Strings;

import io.github.xinyangpan.ella.core.bo.Action;
import io.github.xinyangpan.ella.core.bo.Execution;
import io.github.xinyangpan.ella.core.bo.ExecutionResult;
import io.github.xinyangpan.ella.core.bo.Order;
import io.github.xinyangpan.ella.core.bo.OrderType;
import io.github.xinyangpan.ella.core.bo.ScaleConfig;
import io.github.xinyangpan.ella.core.bo.Side;
import io.github.xinyangpan.ella.core.bo.Status;

class OrderBookEntryImpl extends OrderBookEntry {
	private static final Logger LOGGER = LoggerFactory.getLogger(OrderBookEntryImpl.class);

	private final Map<Long, Order> allOrderIndex;
	private final OrderBookListener orderBookListener;
	private final ScaleConfig scaleConfig;

	OrderBookEntryImpl(Map<Long, Order> allOrderIndex, OrderBookListener orderBookListener, ScaleConfig scaleConfig, BigDecimal price) {
		this.allOrderIndex = allOrderIndex;
		this.orderBookListener = orderBookListener;
		this.scaleConfig = scaleConfig;
		this.price = price;
	}

	public void make(Order order) {
		LOGGER.info("Make Order: {}", order);
		Assert.isTrue(order.getOrderType() == OrderType.LIMIT, "Can place limit order only into Order Book.");
		orders.add(order);
		totalQuantity = totalQuantity.add(order.getQuantity());
		allOrderIndex.put(order.getId(), order);
		order.setStatus(Status.LIVE);
		order.setAction(Action.PLACED);
	}

	public ExecutionResult take(Order input) {
		LOGGER.info("Take Order: {}", input);
		Order order = null;
		ExecutionResult executionResult = new ExecutionResult();
		while ((order = orders.peekFirst()) != null) {
			if (order.isExpired()) {
				Order cancelOrder = this.cancelOrder(order);
				executionResult.addCanceledOrder(cancelOrder);
				continue;
			}
			BigDecimal inputQuantity = input.getFillableQuantity(price, scaleConfig.getQuantityScale());
			BigDecimal fillingQty = order.getQuantity().min(inputQuantity);
			if (fillingQty.signum() > 0) {
				totalQuantity = totalQuantity.subtract(fillingQty);
				Execution execution = new Execution(price, fillingQty, scaleConfig.getAmountScale(), order, input);
				executionResult.addExecution(execution);
				input.fill(execution);
				order.fill(execution);
				if (order.getQuantity().signum() > 0) {
					// still have quantity
//					orders.addFirst(order);
				} else {
					// no more quantity, remove from all order
					allOrderIndex.remove(order.getId());
					orders.remove(order);
					Assert.isTrue(order.getFilledQuantity().compareTo(order.getTotalQuantity()) == 0, "Maker Order should be full filled.");
					order.complete(Action.EXECUTED);
				}
				orderBookListener.onExecution(execution);
				orderBookListener.onOrder(order);
				if (input.getFillableQuantity(price, scaleConfig.getQuantityScale()).signum() <= 0) {
					break;
				}
			}
		}
		LOGGER.info("Take Order Result: {}", input);
		return executionResult;
	}

	public Order cancelOrder(Order input) {
		LOGGER.info("Cancel Order: {}", input);
		Assert.notNull(input, "Order can not be null.");
		Assert.isTrue(input.getOrderType() == OrderType.LIMIT, "Order must be limit.");
		Order order = allOrderIndex.remove(input.getId());
		if (order != null && orders.remove(order)) {
			totalQuantity = totalQuantity.subtract(order.getQuantity());
			order.complete(Action.CANCELED);
			return order;
		} else {
			LOGGER.warn("Cancel order failed for {}. ref = {}", input, order);
			order.setAction(Action.CANCEL_FAILED);
			return input;
		}
	}

	public String toOrderBoardStr(int priceLength, int qtyLength, Side side) {
		String priceStr = Strings.padStart(String.valueOf(this.price), priceLength, ' ');
		String qtyStr = Strings.padStart(String.valueOf(this.totalQuantity), qtyLength, ' ');
		String space = Strings.padStart("", priceLength, ' ');
		switch (side) {
		case BUY:
			return String.format("%s %s %s", priceStr, qtyStr, space);
		case SELL:
			return String.format("%s %s %s", space, qtyStr, priceStr);
		default:
			throw new IllegalArgumentException(String.valueOf(side));
		}
	}

}
