package io.github.xinyangpan.ella.core.bo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.util.Assert;

public class Order {
	private long id;
	private BigDecimal quantity;
	private BigDecimal filledQuantity;
	private BigDecimal totalQuantity;
	// worst price for Market Order
	private BigDecimal price;
	private Side side;
	private OrderType orderType;
	private long orderTs;
	// for Market Order
	private BigDecimal maxAmount;
	private List<Execution> executions = new ArrayList<>();
	
	public void fill(Execution execution) {
		BigDecimal quantity = execution.getQuantity();
		Assert.isTrue(quantity.compareTo(this.quantity) <= 0, "Target quantity is greater than remaining one.");
		this.quantity = this.quantity.subtract(quantity);
		this.filledQuantity = this.filledQuantity.add(quantity);
		executions.add(execution);
	}
	
	@Override
	public String toString() {
		return String.format("Order [id=%s, quantity=%s, filledQuantity=%s, totalQuantity=%s, price=%s, side=%s, orderType=%s, orderTs=%s, maxAmount=%s, executions=%s]", id, quantity, filledQuantity, totalQuantity, price, side, orderType, orderTs, maxAmount, executions);
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

}
