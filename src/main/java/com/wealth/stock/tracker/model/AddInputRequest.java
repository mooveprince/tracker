package com.wealth.stock.tracker.model;

import com.wealth.stock.tracker.entity.StockTracker;

public class AddInputRequest {
	
	public StockTracker stockTracker;

	public StockTracker getStockTracker() {
		return stockTracker;
	}

	public void setStockTracker(StockTracker stockTracker) {
		this.stockTracker = stockTracker;
	}
	
	

}
