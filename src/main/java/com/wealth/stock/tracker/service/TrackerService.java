package com.wealth.stock.tracker.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wealth.stock.tracker.entity.StockTracker;
import com.wealth.stock.tracker.repository.StockTrackerRepository;

/**
 * @author moove
 * 
 * Service layer for search/ add apis - searchBySymbol now 
 */
@Service
public class TrackerService {
	
	private static final Logger logger = LoggerFactory.getLogger(TrackerService.class);
	
	@Autowired
	StockTrackerRepository stockTrackerRepo;
	
	public List<StockTracker> searchBySymbol(String symbol) {
		
		logger.info("Search the stock tracker for the symbol {} ", symbol);
		
		List<StockTracker> stockTrackerList = stockTrackerRepo.findByStock(symbol);
		
		logger.info("Search has been completed for the symbol {} ", symbol);
		
		return stockTrackerList;
		
	}
	
	
	public void addTrackerRecord (StockTracker stockTracker) {
		
		logger.info("Adding new tracker record");
		
		stockTrackerRepo.save(stockTracker);
		
		logger.info("Successfully added");
		
	}

}
