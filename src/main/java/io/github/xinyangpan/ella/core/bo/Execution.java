package io.github.xinyangpan.ella.core.bo;

import java.math.BigDecimal;

public class Execution {
	private BigDecimal price;
	private BigDecimal quantity;

	public Execution() {
	}

	public Execution(BigDecimal price, BigDecimal quantity) {
		super();
		this.price = price;
		this.quantity = quantity;
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

}
