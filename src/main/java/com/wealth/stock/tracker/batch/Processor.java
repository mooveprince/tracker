package com.wealth.stock.tracker.batch;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import com.wealth.stock.tracker.model.StockTracker;

@Component
public class Processor implements ItemProcessor<StockTracker, StockTracker>{

	@Override
	public StockTracker process(StockTracker item) throws Exception {
		// TODO Auto-generated method stub
		return item;
	}

}
