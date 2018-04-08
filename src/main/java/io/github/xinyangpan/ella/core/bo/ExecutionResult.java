package io.github.xinyangpan.ella.core.bo;

import java.util.ArrayList;
import java.util.List;

public class ExecutionResult {
	private List<Order> cancelledOrders = new ArrayList<>();
	private List<Execution> executions = new ArrayList<>();

	public void addCanceledOrder(Order order) {
		this.cancelledOrders.add(order);
	}

	public void addExecution(Execution execution) {
		this.executions.add(execution);
	}

	@Override
	public String toString() {
		return String.format("ExecutionResult [cancelledOrders=%s, executions=%s]", cancelledOrders, executions);
	}

	public List<Order> getCancelledOrders() {
		return cancelledOrders;
	}

	public void setCancelledOrders(List<Order> cancelledOrders) {
		this.cancelledOrders = cancelledOrders;
	}

	public List<Execution> getExecutions() {
		return executions;
	}

	public void setExecutions(List<Execution> executions) {
		this.executions = executions;
	}

}
