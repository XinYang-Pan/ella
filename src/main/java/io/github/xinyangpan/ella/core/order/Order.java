package io.github.xinyangpan.ella.core.order;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.Assert;

public class Order {
	private long id;
	private long quantity;
	private long filledQuantity;
	private long totalQuantity;
	private double price;
	private Side side;
	private OrderType orderType;
	private long orderTs;
	private List<Execution> executions = new ArrayList<>();
	
	public synchronized void fill(Execution execution) {
		long quantity = execution.getQuantity();
		Assert.isTrue(quantity <= this.quantity, "Target quantity is greater than remaining one.");
		this.quantity = this.quantity - quantity;
		this.filledQuantity = this.filledQuantity + quantity;
		executions.add(execution);
	}
	
	@Override
	public String toString() {
		return String.format("Order [id=%s, quantity=%s, filledQuantity=%s, totalQuantity=%s, price=%s, side=%s, orderType=%s, orderTs=%s, executions=%s]", id, quantity, filledQuantity, totalQuantity, price, side, orderType, orderTs, executions);
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getQuantity() {
		return quantity;
	}

	public void setQuantity(long quantity) {
		this.quantity = quantity;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
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

	public long getFilledQuantity() {
		return filledQuantity;
	}

	public void setFilledQuantity(long filledQuantity) {
		this.filledQuantity = filledQuantity;
	}

	public long getTotalQuantity() {
		return totalQuantity;
	}

	public void setTotalQuantity(long totalQuantity) {
		this.totalQuantity = totalQuantity;
	}

	public List<Execution> getExecutions() {
		return executions;
	}

	public void setExecutions(List<Execution> executions) {
		this.executions = executions;
	}

}
