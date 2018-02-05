package io.github.xinyangpan.ella.core.bo;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Execution {
	private final BigDecimal price;
	private final BigDecimal quantity;
	private final BigDecimal amount;
	private final long makerId;
	private final long takerId;

	public Execution(BigDecimal price, BigDecimal quantity, long makerId, long takerId) {
		this.price = price;
		this.quantity = quantity;
		this.amount = this.price.multiply(this.quantity).setScale(Const.AMOUNT_SCALE, RoundingMode.DOWN);
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

}
