package io.github.xinyangpan.ella.core;

import java.math.BigDecimal;

public class EllaUtils {

	public static BigDecimal convert(double d) {
		return new BigDecimal(Double.toString(d));
	}

}
