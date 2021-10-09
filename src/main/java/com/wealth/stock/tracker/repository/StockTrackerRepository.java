package com.wealth.stock.tracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wealth.stock.tracker.model.StockTracker;
import com.wealth.stock.tracker.model.StockTrackerId;

public interface StockTrackerRepository extends JpaRepository<StockTracker, StockTrackerId>{

}
