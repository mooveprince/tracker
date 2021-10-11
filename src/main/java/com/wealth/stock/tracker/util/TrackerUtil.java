package com.wealth.stock.tracker.util;

import org.springframework.stereotype.Component;

import com.wealth.stock.tracker.constant.ErrorEnum;
import com.wealth.stock.tracker.model.StockTrackerError;

@Component
public class TrackerUtil {

	/*
	 * To check whether the given string is numeric
	 */
	public static boolean isNumeric(String str) {
		try {
			Double.parseDouble(str);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
	
	
	public static boolean isStringNullOrEmpty(String... elements) {
		
		boolean isStringNullOrEmpty = true;
		
		for (String element : elements) {
			
			if (element != null && !element.trim().isEmpty()) {
				return false;
			}
			
		}
		
		return isStringNullOrEmpty;
		
	}
	
	public static StockTrackerError getError(ErrorEnum errorEnum) {
		
		StockTrackerError error = new StockTrackerError();
		
		error.setErrorCode(errorEnum.getKey());
		error.setErrorDescription(errorEnum.getValue());
		
		
		return error;
		
	}

}
