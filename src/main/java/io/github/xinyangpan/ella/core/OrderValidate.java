package io.github.xinyangpan.ella.core;

import java.math.BigDecimal;
import java.util.Map;

import org.springframework.util.Assert;

import io.github.xinyangpan.ella.core.bo.Order;
import io.github.xinyangpan.ella.core.bo.OrderType;
import io.github.xinyangpan.ella.core.bo.Status;

public class OrderValidate {
	private final Map<Long, Order> allOrderIndex;

	public OrderValidate(Map<Long, Order> allOrderIndex) {
		this.allOrderIndex = allOrderIndex;
	}

	public void place(Order order) {
		common(order);
		marketOrLimit(order);
		Assert.isTrue(order.getStatus() != Status.PLACING, "Status must be PLACING.");
		Assert.isTrue(order.getVersion() == 1, "Version has to be 1.");
		Assert.isTrue(order.getFilledQuantity().compareTo(BigDecimal.ZERO) == 0, "Filled Quantity must be 0.");
		Assert.isTrue(order.getExecutions().isEmpty(), "Executions must be empty.");
	}

	private void marketOrLimit(Order order) {
		switch (order.getOrderType()) {
		case MARKET:
			this.market(order);
			break;
		case LIMIT:
			this.limit(order);
			break;
		default:
			throw new IllegalArgumentException("order type not supported.");
		}
	}

	public void cancel(Order input) {
		common(input);
		Assert.isTrue(input.getStatus() != Status.CANCELLING, "Status must be PLACING.");
		Assert.isTrue(input.getOrderType() == OrderType.LIMIT, "Must be Limit Order.");
		// check version
		Order order = allOrderIndex.get(input.getId());
		Assert.notNull(order, "Order is not found.");
		Assert.isTrue(input.getVersion() - order.getVersion() == 1, "Version Not Match.");

	}

	private void common(Order input) {
		Assert.notNull(input, "Order can not be null.");
		Assert.isTrue(input.getId() != 0, "Id can not be 0.");
		Assert.notNull(input.getOrderType(), "Order Type can not be null.");
		Assert.notNull(input.getSide(), "Side can not be null.");
	}

	private void market(Order order) {
		Assert.isTrue(order.getOrderType() == OrderType.MARKET, "Must be Market Order.");
		if (order.getMaxAmount() == null) {
			checkQtyAndTotalQtyBothNotNullAndGreaterThanZeroAndSameValue(order);
		} else {
			checkQtyAndTotalQtyBothNullOrBothNotNullAndGreaterThanZeroAndSameValue(order);
		}
	}

	private void limit(Order order) {
		Assert.isTrue(order.getOrderType() == OrderType.LIMIT, "Must be Limit Order.");
		checkQtyAndTotalQtyBothNotNullAndGreaterThanZeroAndSameValue(order);
		// 
		Assert.notNull(order.getPrice(), "Price can not be null.");
		Assert.isTrue(order.getPrice().compareTo(BigDecimal.ZERO) > 0, "Price must be greater than 0.");
		Assert.isNull(order.getMaxAmount(), "Max Amount must be null.");
	}

	private void checkQtyAndTotalQtyBothNotNullAndGreaterThanZeroAndSameValue(Order order) {
		Assert.notNull(order.getQuantity(), "Quantity can not be null.");
		Assert.isTrue(order.getQuantity().compareTo(BigDecimal.ZERO) > 0, "Quantity must be greater than 0.");
		Assert.notNull(order.getTotalQuantity(), "Total Quantity can not be null.");
		Assert.isTrue(order.getTotalQuantity().compareTo(BigDecimal.ZERO) > 0, "Total Quantity must be greater than 0.");
		Assert.isTrue(order.getQuantity().compareTo(order.getTotalQuantity()) == 0, "Quantity must be the same as Total Quantity.");
	}

	private void checkQtyAndTotalQtyBothNullOrBothNotNullAndGreaterThanZeroAndSameValue(Order order) {
		if (order.getQuantity() == null && order.getTotalQuantity() == null) {
			return;
		}
		checkQtyAndTotalQtyBothNotNullAndGreaterThanZeroAndSameValue(order);
	}

}
