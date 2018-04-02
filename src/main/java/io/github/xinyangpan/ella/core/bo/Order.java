package io.github.xinyangpan.ella.core.bo;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.springframework.util.Assert;

public class Order {
	private long id;
	private BigDecimal quantity; // Left Quantity
	private BigDecimal filledQuantity = BigDecimal.ZERO;
	private BigDecimal totalQuantity; // quantity + filledQuantity = totalQuantity
	// worst price for Market Order
	private BigDecimal price;
	private Side side;
	private Status status;
	private Action action;
	private OrderType orderType;
	private long orderTs;
	// for Market Order
	private BigDecimal maxAmount;

	public Order copy() {
		Order copy = new Order();
		this.copyTo(copy);
		return copy;
	}

	public void copyTo(Order copy) {
		copy.id = this.id;
		copy.quantity = this.quantity;
		copy.filledQuantity = this.filledQuantity;
		copy.totalQuantity = this.totalQuantity;
		copy.price = this.price;
		copy.side = this.side;
		copy.status = this.status;
		copy.action = this.action;
		copy.orderType = this.orderType;
		copy.orderTs = this.orderTs;
		copy.maxAmount = this.maxAmount;
	}
	
	public void fill(Execution execution) {
		if (this.maxAmount != null) {
			this.maxAmount = this.maxAmount.subtract(execution.getAmount());
			Assert.isTrue(this.maxAmount.signum() >= 0, "Max Amount is exceeded.");
		}
		BigDecimal quantity = execution.getQuantity();
		Assert.isTrue(quantity.compareTo(this.quantity) <= 0, "Target quantity is greater than remaining one.");
		this.quantity = this.quantity.subtract(quantity);
		this.filledQuantity = this.filledQuantity.add(quantity);
	}
	
	public BigDecimal getFillableQuantity(BigDecimal price, int quantityScale) {
		if (maxAmount == null) {
			return quantity;
		}
		BigDecimal maxQuantity = this.maxAmount.divide(price, quantityScale, RoundingMode.FLOOR);
		if (quantity == null) {
			return maxQuantity;
		}
		return maxQuantity.min(quantity);
	}

	public void complete(Action action) {
		this.action = action;
		if (filledQuantity.compareTo(BigDecimal.ZERO) == 0) {
			this.status = Status.NONE_FILLED;
			return;
		}
		if (this.totalQuantity == null) {
			// No total quantity, max amount executing
			this.status = Status.FILLED;
		} else if (totalQuantity != null && totalQuantity.compareTo(filledQuantity) == 0) {
			this.status = Status.FILLED;
		} else {
			this.status = Status.PARTIAL_FILLED;
		}
	}

	@Override
	public String toString() {
		return String.format("Order [id=%s, quantity=%s, filledQuantity=%s, totalQuantity=%s, price=%s, side=%s, status=%s, action=%s, orderType=%s, orderTs=%s, maxAmount=%s]", id, quantity, filledQuantity, totalQuantity, price, side, status, action, orderType, orderTs, maxAmount);
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public BigDecimal getQuantity() {
		return quantity;
	}

	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public OrderType getOrderType() {
		return orderType;
	}

	public void setOrderType(OrderType orderType) {
		this.orderType = orderType;
	}

	public long getOrderTs() {
		return orderTs;
	}

	public void setOrderTs(long orderTs) {
		this.orderTs = orderTs;
	}

	public Side getSide() {
		return side;
	}

	public void setSide(Side side) {
		this.side = side;
	}

	public BigDecimal getFilledQuantity() {
		return filledQuantity;
	}

	public void setFilledQuantity(BigDecimal filledQuantity) {
		this.filledQuantity = filledQuantity;
	}

	public BigDecimal getTotalQuantity() {
		return totalQuantity;
	}

	public void setTotalQuantity(BigDecimal totalQuantity) {
		this.totalQuantity = totalQuantity;
	}

	public BigDecimal getMaxAmount() {
		return maxAmount;
	}

	public void setMaxAmount(BigDecimal maxAmount) {
		this.maxAmount = maxAmount;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Action getAction() {
		return action;
	}

	public void setAction(Action action) {
		this.action = action;
	}

}
