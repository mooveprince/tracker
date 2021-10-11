package com.wealth.stock.tracker.model;

import java.util.List;

import com.wealth.stock.tracker.entity.StockTracker;

public class StockTrackerSearchResponse {
	
	public SearchInputRequest inputRequest;
	public List<StockTracker> stockTracker;
	public StockTrackerError stockTrackerError;
	
	
	public List<StockTracker> getStockTracker() {
		return stockTracker;
	}
	public void setStockTracker(List<StockTracker> stockTracker) {
		this.stockTracker = stockTracker;
	}
	public StockTrackerError getStockTrackerError() {
		return stockTrackerError;
	}
	public void setStockTrackerError(StockTrackerError stockTrackerError) {
		this.stockTrackerError = stockTrackerError;
	}
	public SearchInputRequest getInputRequest() {
		return inputRequest;
	}
	public void setInputRequest(SearchInputRequest inputRequest) {
		this.inputRequest = inputRequest;
	}
	
	

}
