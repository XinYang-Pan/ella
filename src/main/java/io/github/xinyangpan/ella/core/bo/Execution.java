package io.github.xinyangpan.ella.core.bo;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Execution {
	private BigDecimal price;
	private BigDecimal quantity;
	private BigDecimal amount;
	private long makerId;
	private long takerId;

	public Execution() {
	}

	public Execution(BigDecimal price, BigDecimal quantity) {
		super();
		this.price = price;
		this.quantity = quantity;
		this.amount = this.price.multiply(this.quantity).setScale(Const.AMOUNT_SCALE, RoundingMode.DOWN);
	}

	public Execution(BigDecimal price, BigDecimal quantity, long makerId, long takerId) {
		this(price, quantity);
		this.makerId = makerId;
		this.takerId = takerId;
	}

	@Override
	public String toString() {
		return String.format("Execution [price=%s, quantity=%s, amount=%s, makerId=%s, takerId=%s]", price, quantity, amount, makerId, takerId);
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public BigDecimal getQuantity() {
		return quantity;
	}

	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public long getMakerId() {
		return makerId;
	}

	public void setMakerId(long makerId) {
		this.makerId = makerId;
	}

	public long getTakerId() {
		return takerId;
	}

	public void setTakerId(long takerId) {
		this.takerId = takerId;
	}

}
