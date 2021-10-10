package com.wealth.stock.tracker.batch;

import java.util.List;

import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.wealth.stock.tracker.entity.StockTracker;
import com.wealth.stock.tracker.repository.StockTrackerRepository;

@Component
public class Writer implements ItemWriter<StockTracker>{
	
	@Autowired
	private StockTrackerRepository stockTrackerRepository;

	@Override
	public void write(List<? extends StockTracker> items) throws Exception {
		// TODO Auto-generated method stub
		stockTrackerRepository.saveAll(items);
	}

}
