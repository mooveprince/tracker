package com.wealth.stock.tracker.batch;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import com.wealth.stock.tracker.model.StockTracker;
import com.wealth.stock.tracker.model.StockTrackerInput;
import com.wealth.stock.tracker.util.TrackerUtil;

@Component
public class Processor implements ItemProcessor<StockTrackerInput, StockTracker>{
	
	DateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");

	@Override
	public StockTracker process(StockTrackerInput stockTrackerInput) throws Exception {
		// TODO Auto-generated method stub
		
		StockTracker stockTracker = new StockTracker();
		
		stockTracker.setQuarter(stockTrackerInput.getQuarter());
		stockTracker.setStock(stockTrackerInput.getStock());
		
		//converting string into sql date 
		java.util.Date date=sdf.parse(stockTrackerInput.getDate());
	    java.sql.Date sqlDate = new java.sql.Date(date.getTime());
	    
		stockTracker.setDate(sqlDate);
		
		stockTracker.setOpen(stockTrackerInput.getOpen());
		stockTracker.setHigh(stockTrackerInput.getHigh());
		stockTracker.setLow(stockTrackerInput.getLow());
		stockTracker.setClose(stockTrackerInput.getClose());
		
		if (TrackerUtil.isNumeric(stockTrackerInput.getVolume())) {
			stockTracker.setVolume(Long.parseLong(stockTrackerInput.getVolume()));
		}
		
		if (TrackerUtil.isNumeric(stockTrackerInput.getPercentChangePrice())) {
			stockTracker.setPercentChangePrice(Float.parseFloat(stockTrackerInput.getPercentChangePrice()));
		}
		
		if (TrackerUtil.isNumeric(stockTrackerInput.getPercentChangeVolumeOverLastWk())) {
			stockTracker.setPercentChangeVolumeOverLastWk(Double.parseDouble(stockTrackerInput.getPercentChangeVolumeOverLastWk()));
		}
		
		if (TrackerUtil.isNumeric(stockTrackerInput.getPreviousWeeksVolume())) {
			stockTracker.setPreviousWeeksVolume(Long.parseLong(stockTrackerInput.getPreviousWeeksVolume()));
		}
		
		stockTracker.setNextWeeksOpen(stockTrackerInput.getNextWeeksOpen());
		stockTracker.setNextWeeksClose(stockTrackerInput.getNextWeeksClose());
		
		if (TrackerUtil.isNumeric(stockTrackerInput.getPercentChangeNextWeeksPrice())) {
			stockTracker.setPercentChangeNextWeeksPrice(Float.parseFloat(stockTrackerInput.getPercentChangeNextWeeksPrice()));
		}
		
		if (TrackerUtil.isNumeric(stockTrackerInput.getDaysToNextDividend())) {
			stockTracker.setDaysToNextDividend(Short.parseShort(stockTrackerInput.getDaysToNextDividend()));
		}
		
		if (TrackerUtil.isNumeric(stockTrackerInput.getPercentReturnNextDividend())) {
			stockTracker.setPercentReturnNextDividend(Float.parseFloat(stockTrackerInput.getPercentReturnNextDividend()));
		}
		
		
		return stockTracker;
	}

}
