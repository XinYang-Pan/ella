package io.github.xinyangpan.ella.core.bo;

import java.util.ArrayList;
import java.util.List;

public class OrderResult {
	private Order order;
	private final List<Execution> executions = new ArrayList<>();

	@Override
	public String toString() {
		return String.format("OrderResult [order=%s, executions=%s]", order, executions);
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public List<Execution> getExecutions() {
		return executions;
	}

}
