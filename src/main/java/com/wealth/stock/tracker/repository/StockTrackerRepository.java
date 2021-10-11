package com.wealth.stock.tracker.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wealth.stock.tracker.entity.StockTracker;
import com.wealth.stock.tracker.entity.StockTrackerId;

public interface StockTrackerRepository extends JpaRepository<StockTracker, StockTrackerId>{
	
	public List<StockTracker> findByStock(String symbol);

}
