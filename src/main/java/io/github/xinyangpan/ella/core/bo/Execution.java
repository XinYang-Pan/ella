package io.github.xinyangpan.ella.core.bo;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

public class Execution {
	private final BigDecimal price;
	private final BigDecimal quantity;
	private final BigDecimal amount;
	private final long makerId;
	private final long takerId;
	private final Order makerOrder;
	private final Order takerOrder;

	public Execution(BigDecimal price, BigDecimal quantity, int amountScale, long makerId, long takerId) {
		this.price = price;
		this.quantity = quantity;
		this.amount = this.price.multiply(this.quantity).setScale(amountScale, RoundingMode.DOWN);
		this.makerId = makerId;
		this.takerId = takerId;
		this.makerOrder = null;
		this.takerOrder = null;
	}

	public Execution(BigDecimal price, BigDecimal quantity, int amountScale, Order makerOrder, Order takerOrder) {
		this.price = price;
		this.quantity = quantity;
		this.amount = this.price.multiply(this.quantity).setScale(amountScale, RoundingMode.DOWN);
		this.makerOrder = Objects.requireNonNull(makerOrder);
		this.takerOrder = Objects.requireNonNull(takerOrder);
		this.makerId = makerOrder.getId();
		this.takerId = takerOrder.getId();
	}

	@Override
	public String toString() {
		return String.format("Execution [price=%s, quantity=%s, amount=%s, makerId=%s, takerId=%s]", price, quantity, amount, makerId, takerId);
	}

	public BigDecimal getPrice() {
		return price;
	}

	public BigDecimal getQuantity() {
		return quantity;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public long getMakerId() {
		return makerId;
	}

	public long getTakerId() {
		return takerId;
	}

	public Order getMakerOrder() {
		return makerOrder;
	}

	public Order getTakerOrder() {
		return takerOrder;
	}

}
