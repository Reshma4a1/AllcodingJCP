package com.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtil{
	
	/**
	 * Descriptin : method designed to get future date
	 * @param numberOfDaysFromToday
	 * @return
	 */
	public static String getFutureDate(int numberOfDaysFromToday){
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy");  
		LocalDateTime now = LocalDateTime.now(); 
		return dtf.format(now.plusDays(numberOfDaysFromToday));
	}
	
	/**
	 * Description : method designed to get current date
	 * @return
	 */
	public static String currentDate(){
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy");  
		LocalDateTime now = LocalDateTime.now(); 
		return dtf.format(now);  
	}
}
