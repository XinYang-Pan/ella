package io.github.xinyangpan.ella.core.bo;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Execution {
	private BigDecimal price;
	private BigDecimal quantity;
	private BigDecimal amount;

	public Execution() {
	}

	public Execution(BigDecimal price, BigDecimal quantity) {
		super();
		this.price = price;
		this.quantity = quantity;
		this.amount = this.price.multiply(this.quantity).setScale(Const.AMOUNT_SCALE, RoundingMode.DOWN);
	}

	@Override
	public String toString() {
		return String.format("Execution [price=%s, quantity=%s]", price, quantity);
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

}
