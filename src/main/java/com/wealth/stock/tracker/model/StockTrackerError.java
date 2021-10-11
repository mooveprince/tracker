package com.wealth.stock.tracker.model;

/**
 * @author moove
 * 
 * Error object for Stock Tracker API
 */
public class StockTrackerError {
	
	public short errorCode;
	public String errorDescription;
	
	public short getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(short errorCode) {
		this.errorCode = errorCode;
	}
	public String getErrorDescription() {
		return errorDescription;
	}
	public void setErrorDescription(String errorDescription) {
		this.errorDescription = errorDescription;
	}
	
	

}
