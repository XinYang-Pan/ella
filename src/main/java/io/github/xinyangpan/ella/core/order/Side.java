package io.github.xinyangpan.ella.core.order;

public enum Side {
	BUY, SELL;

	public boolean isFirstPriceBetterOrSame(double firstPrice, double secondPrice) {
		switch (this) {
		case BUY:
			return firstPrice >= secondPrice;
		case SELL:
			return firstPrice <= secondPrice;
		default:
			throw new IllegalArgumentException(this.name());
		}
	}
	
}