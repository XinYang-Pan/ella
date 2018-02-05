package io.github.xinyangpan.ella.core.bo;

public class ScaleConfig {

	private int amountScale;
	private int quantityScale;
	
	public ScaleConfig(int amountScale, int quantityScale) {
		super();
		this.amountScale = amountScale;
		this.quantityScale = quantityScale;
	}

	@Override
	public String toString() {
		return String.format("ScaleConfig [amountScale=%s, quantityScale=%s]", amountScale, quantityScale);
	}

	public int getAmountScale() {
		return amountScale;
	}

	public void setAmountScale(int amountScale) {
		this.amountScale = amountScale;
	}

	public int getQuantityScale() {
		return quantityScale;
	}

	public void setQuantityScale(int quantityScale) {
		this.quantityScale = quantityScale;
	}

}
