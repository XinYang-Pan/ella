package io.github.xinyangpan.ella.core.bo;

import java.util.ArrayList;
import java.util.List;

public class OrderResult {
	private final Order order;
	private final List<Order> cancelledOrders = new ArrayList<>();
	private final List<Execution> executions = new ArrayList<>();
	
	public OrderResult(Order order) {
		super();
		this.order = order;
	}

	@Override
	public String toString() {
		return String.format("OrderResult [order=%s, cancelledOrders=%s, executions=%s]", order, cancelledOrders, executions);
	}

	public Order getOrder() {
		return order;
	}

	public List<Order> getCancelledOrders() {
		return cancelledOrders;
	}

	public List<Execution> getExecutions() {
		return executions;
	}

}
