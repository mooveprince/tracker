package com.wealth.stock.tracker.util;

import org.springframework.stereotype.Component;

@Component
public class TrackerUtil {
	
	/*
	 * To check whether the given string is numeric
	 */
	public static boolean isNumeric(String str) { 
		  try {  
		    Double.parseDouble(str);  
		    return true;
		  } catch(NumberFormatException e){  
		    return false;  
		  }  
		}

}
