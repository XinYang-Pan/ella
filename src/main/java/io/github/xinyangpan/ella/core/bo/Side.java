package io.github.xinyangpan.ella.core.bo;

import java.math.BigDecimal;

public enum Side {
	BUY, SELL;

	public boolean isFirstPriceBetterOrSame(BigDecimal firstPrice, BigDecimal secondPrice) {
		switch (this) {
		case BUY:
			return firstPrice.compareTo(secondPrice) >= 0;
		case SELL:
			return firstPrice.compareTo(secondPrice) <= 0;
		default:
			throw new IllegalArgumentException(this.name());
		}
	}
	
}