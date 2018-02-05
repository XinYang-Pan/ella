package io.github.xinyangpan.ella.core.bo;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import org.springframework.util.Assert;

public class Order {
	private long id;
	private BigDecimal quantity;
	private BigDecimal filledQuantity = BigDecimal.ZERO;
	private BigDecimal totalQuantity;
	// worst price for Market Order
	private BigDecimal price;
	private Side side;
	private Status status;
	private OrderType orderType;
	private long orderTs;
	private int version = 1;
	// for Market Order
	private BigDecimal maxAmount;
	private List<Execution> executions = new ArrayList<>();

	public Order copy() {
		Order clone = new Order();
		clone.id = this.id;
		clone.quantity = this.quantity;
		clone.filledQuantity = this.filledQuantity;
		clone.totalQuantity = this.totalQuantity;
		clone.price = this.price;
		clone.side = this.side;
		clone.status = this.status;
		clone.orderType = this.orderType;
		clone.orderTs = this.orderTs;
		clone.version = this.version;
		clone.maxAmount = this.maxAmount;
		clone.executions.addAll(this.executions);
		return clone;
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
		executions.add(execution);
	}
	
	public BigDecimal getFillableQuantity(BigDecimal price) {
		if (maxAmount == null) {
			return quantity;
		}
		BigDecimal maxQuantity = this.maxAmount.divide(price, Const.QUANTITY_SCALE, RoundingMode.FLOOR);
		if (quantity == null) {
			return maxQuantity;
		}
		return maxQuantity.min(quantity);
	}

	public void complete() {
		if (filledQuantity.compareTo(BigDecimal.ZERO) == 0) {
			this.status = Status.FAILED;
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

	public void versionPlus() {
		this.version ++;
	}
	
	@Override
	public String toString() {
		return String.format("Order [id=%s, quantity=%s, filledQuantity=%s, totalQuantity=%s, price=%s, side=%s, status=%s, orderType=%s, orderTs=%s, version=%s, maxAmount=%s, executions=%s]", id, quantity, filledQuantity, totalQuantity, price, side, status, orderType, orderTs, version, maxAmount, executions);
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

	public List<Execution> getExecutions() {
		return executions;
	}

	public void setExecutions(List<Execution> executions) {
		this.executions = executions;
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

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

}
