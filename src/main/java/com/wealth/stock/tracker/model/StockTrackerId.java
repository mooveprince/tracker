package com.wealth.stock.tracker.model;

import java.io.Serializable;
import java.sql.Date;

public class StockTrackerId implements Serializable {
	
	private byte quarter;
	private String stock;
	private String date;
	
	
	public byte getQuarter() {
		return quarter;
	}
	public void setQuarter(byte quarter) {
		this.quarter = quarter;
	}
	public String getStock() {
		return stock;
	}
	public void setStock(String stock) {
		this.stock = stock;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	
	
	

}
