package io.github.xinyangpan.ella.core.bo;

import java.util.ArrayList;
import java.util.List;

public class OrderResult {
	private final Order order;
	private final List<Execution> executions = new ArrayList<>();
	
	public OrderResult(Order order) {
		super();
		this.order = order;
	}

	@Override
	public String toString() {
		return String.format("OrderResult [order=%s, executions=%s]", order, executions);
	}

	public Order getOrder() {
		return order;
	}

	public List<Execution> getExecutions() {
		return executions;
	}

}
