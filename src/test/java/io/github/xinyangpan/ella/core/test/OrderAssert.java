package io.github.xinyangpan.ella.core.test;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

import org.assertj.core.api.AbstractAssert;

import io.github.xinyangpan.ella.core.EllaUtils;
import io.github.xinyangpan.ella.core.bo.Action;
import io.github.xinyangpan.ella.core.bo.Execution;
import io.github.xinyangpan.ella.core.bo.Order;
import io.github.xinyangpan.ella.core.bo.OrderResult;
import io.github.xinyangpan.ella.core.bo.Status;

public class OrderAssert extends AbstractAssert<OrderAssert, OrderResult> {

	public OrderAssert(OrderResult actual) {
		super(actual, OrderAssert.class);
	}

//	public OrderAssert statusIs(Status status) {
//		isNotNull();
//		if (!Objects.equals(actual.getStatus(), status)) {
//			failWithMessage("Expected order's status to be <%s> but was <%s>", status, actual.getStatus());
//		}
//		return this;
//	}
	
	private Order actualOrder() {
		return actual.getOrder();
	}

	public OrderAssert is(Status status, Action action) {
		isNotNull();
		if (!Objects.equals(actualOrder().getStatus(), status)) {
			failWithMessage("Expected order's status to be <%s> but was <%s>", status, actualOrder().getStatus());
		}
		if (!Objects.equals(actualOrder().getAction(), action)) {
			failWithMessage("Expected order's action to be <%s> but was <%s>", action, actualOrder().getAction());
		}
		return this;
	}

	public OrderAssert versionIs(int version) {
		isNotNull();
		if (actualOrder().getVersion() != version) {
			failWithMessage("Expected order's version to be <%s> but was <%s>", version, actualOrder().getVersion());
		}
		return this;
	}

	public OrderAssert quantityIs(double quantity) {
		isNotNull();
		BigDecimal quantityBd = new BigDecimal(Double.toString(quantity));
		if (actualOrder().getQuantity().compareTo(quantityBd) != 0) {
			failWithMessage("Expected order's quantity to be <%s> but was <%s>", quantity, actualOrder().getQuantity());
		}
		return this;
	}

	public OrderAssert filledQuantityIs(double filledQuantity) {
		isNotNull();
		BigDecimal filledQuantityBd = new BigDecimal(Double.toString(filledQuantity));
		if (actualOrder().getFilledQuantity().compareTo(filledQuantityBd) != 0) {
			failWithMessage("Expected order's filledQuantity to be <%s> but was <%s>", filledQuantity, actualOrder().getFilledQuantity());
		}
		return this;
	}

	public OrderAssert executionSizeIs(int size) {
		isNotNull();
		List<Execution> executions = actual.getExecutions();
		if (executions.size() != size) {
			failWithMessage("Expected execution price to be <%s> but was <%s>", size, executions.size());
		}
		return this;
	}
	
	// starting with 1
	public OrderAssert hasExecution(int index, double price, double quantity, double amount) {
		isNotNull();
		List<Execution> executions = actual.getExecutions();
		if (executions.size() < index - 1) {
			failWithMessage("Order's execution not found. index is <%s> but size is <%s>", index, executions.size());
		}
		Execution execution = executions.get(index-1);
		BigDecimal priceBd = EllaUtils.convert(price);
		if (execution.getPrice().compareTo(priceBd) != 0) {
			failWithMessage("Expected execution price to be <%s> but was <%s>", priceBd, execution.getPrice());
		}
		BigDecimal quantityBd = EllaUtils.convert(quantity);
		if (execution.getQuantity().compareTo(quantityBd) != 0) {
			failWithMessage("Expected execution price to be <%s> but was <%s>", quantityBd, execution.getQuantity());
		}
		BigDecimal amountBd = EllaUtils.convert(amount);
		if (execution.getAmount().compareTo(amountBd) != 0) {
			failWithMessage("Expected execution price to be <%s> but was <%s>", amountBd, execution.getAmount());
		}
		return this;
	}
	
}
