package io.github.xinyangpan.ella.core.order;

public class Execution {
	private double price;
	private long quantity;

	public Execution() {
	}

	public Execution(double price, long quantity) {
		super();
		this.price = price;
		this.quantity = quantity;
	}

	@Override
	public String toString() {
		return String.format("Execution [price=%s, quantity=%s]", price, quantity);
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public long getQuantity() {
		return quantity;
	}

	public void setQuantity(long quantity) {
		this.quantity = quantity;
	}

}
