package com.wealth.stock.tracker.entity;

import java.io.Serializable;
import java.sql.Date;

public class StockTrackerId implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private byte quarter;
	private String stock;
	private Date date;
	
	
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
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}

	
	
	

}
