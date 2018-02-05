package io.github.xinyangpan.ella.core;

import io.github.xinyangpan.ella.core.bo.Execution;
import io.github.xinyangpan.ella.core.bo.Order;

public interface OrderBookListener {
	
	void onExecution(Execution execution);

	void onOrder(Order order);
	
}
