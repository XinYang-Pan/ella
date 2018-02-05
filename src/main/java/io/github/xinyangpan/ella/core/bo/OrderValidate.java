package io.github.xinyangpan.ella.core.bo;

import java.math.BigDecimal;

import org.springframework.util.Assert;

public class OrderValidate {
	
	public void common(Order order) {
		Assert.notNull(order, "Order can not be null.");
		Assert.isTrue(order.getId() != 0, "Id can not be 0.");
		Assert.isTrue(order.getStatus() != Status.PLACING, "Status must be PLACING.");
		Assert.isTrue(order.getFilledQuantity().compareTo(BigDecimal.ZERO) == 0, "Filled Quantity must be 0.");
		Assert.notNull(order.getOrderType(), "Order Type can not be null.");
		Assert.notNull(order.getSide(), "Side can not be null.");
		Assert.isTrue(order.getExecutions().isEmpty(), "Executions must be empty.");
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
	
	public void market(Order order) {
		if (order.getMaxAmount() == null) {
			checkQtyAndTotalQtyBothNotNullAndGreaterThanZeroAndSameValue(order);
		} else {
			checkQtyAndTotalQtyBothNullOrBothNotNullAndGreaterThanZeroAndSameValue(order);
		}
	}

	public void limit(Order order) {
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
