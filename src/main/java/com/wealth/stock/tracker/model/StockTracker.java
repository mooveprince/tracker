package com.wealth.stock.tracker.model;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

@Entity
@IdClass(StockTrackerId.class)
public class StockTracker {

	@Id
	private byte quarter;
	
	@Id
	private String stock;
	
	@Id
	private Date date;
	
	private String open;
	
	private String high;
	
	private String low;
	
	private String close;
	
	private long volume;
	
	private float percentChangePrice;
	
	private double percentChangeVolumeOverLastWk;
	
	private long previousWeeksVolume;
	
	private String nextWeeksOpen;
	
	private String nextWeeksClose;
	
	private float percentChangeNextWeeksPrice;
	
	private short daysToNextDividend;
	
	private float percentReturnNextDividend;
	
	private java.util.Date requestDate;

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

	public String getOpen() {
		return open;
	}

	public void setOpen(String open) {
		this.open = open;
	}

	public String getHigh() {
		return high;
	}

	public void setHigh(String high) {
		this.high = high;
	}

	public String getLow() {
		return low;
	}

	public void setLow(String low) {
		this.low = low;
	}

	public String getClose() {
		return close;
	}

	public void setClose(String close) {
		this.close = close;
	}

	public long getVolume() {
		return volume;
	}

	public void setVolume(long volume) {
		this.volume = volume;
	}

	public float getPercentChangePrice() {
		return percentChangePrice;
	}

	public void setPercentChangePrice(float percentChangePrice) {
		this.percentChangePrice = percentChangePrice;
	}

	public double getPercentChangeVolumeOverLastWk() {
		return percentChangeVolumeOverLastWk;
	}

	public void setPercentChangeVolumeOverLastWk(double percentChangeVolumeOverLastWk) {
		this.percentChangeVolumeOverLastWk = percentChangeVolumeOverLastWk;
	}

	public long getPreviousWeeksVolume() {
		return previousWeeksVolume;
	}

	public void setPreviousWeeksVolume(long previousWeeksVolume) {
		this.previousWeeksVolume = previousWeeksVolume;
	}

	public String getNextWeeksOpen() {
		return nextWeeksOpen;
	}

	public void setNextWeeksOpen(String nextWeeksOpen) {
		this.nextWeeksOpen = nextWeeksOpen;
	}

	public String getNextWeeksClose() {
		return nextWeeksClose;
	}

	public void setNextWeeksClose(String nextWeeksClose) {
		this.nextWeeksClose = nextWeeksClose;
	}

	public float getPercentChangeNextWeeksPrice() {
		return percentChangeNextWeeksPrice;
	}

	public void setPercentChangeNextWeeksPrice(float percentChangeNextWeeksPrice) {
		this.percentChangeNextWeeksPrice = percentChangeNextWeeksPrice;
	}

	public short getDaysToNextDividend() {
		return daysToNextDividend;
	}

	public void setDaysToNextDividend(short daysToNextDividend) {
		this.daysToNextDividend = daysToNextDividend;
	}

	public float getPercentReturnNextDividend() {
		return percentReturnNextDividend;
	}

	public void setPercentReturnNextDividend(float percentReturnNextDividend) {
		this.percentReturnNextDividend = percentReturnNextDividend;
	}

	public java.util.Date getRequestDate() {
		return requestDate;
	}

	public void setRequestDate(java.util.Date requestDate) {
		this.requestDate = requestDate;
	}

	

	
}
