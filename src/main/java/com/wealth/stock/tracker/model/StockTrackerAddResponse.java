package com.wealth.stock.tracker.model;

public class StockTrackerAddResponse {
	
	public AddInputRequest inputRequest;
	public String addStockTrackerStatus;
	public StockTrackerError stockTrackerError;
	
	
	public AddInputRequest getInputRequest() {
		return inputRequest;
	}
	public void setInputRequest(AddInputRequest inputRequest) {
		this.inputRequest = inputRequest;
	}
	public String getAddStockTrackerStatus() {
		return addStockTrackerStatus;
	}
	public void setAddStockTrackerStatus(String addStockTrackerStatus) {
		this.addStockTrackerStatus = addStockTrackerStatus;
	}
	public StockTrackerError getStockTrackerError() {
		return stockTrackerError;
	}
	public void setStockTrackerError(StockTrackerError stockTrackerError) {
		this.stockTrackerError = stockTrackerError;
	}
	
	

}
