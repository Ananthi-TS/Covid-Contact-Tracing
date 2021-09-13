package com.covid.main;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Utility {

	
	/**
	 * This method is used to check if the date value passed is non negative and is not greater than the current date
	 * 
	 * @param hash- date value
	 * @return Returns true if date is valid, false otherwise
	 */
	public static boolean checkDate(int dateVal) {
		
		if(dateVal<0) {
			return false;
		}else {
			if(dateVal>getCurrentDate()) {
				return false;
			}
			else {
				return true;
			}
		}
	}
	
	/**
	 * This method returns the number of days from January 1,2020.
	 * 
	 * @return Returns the number of days from January 1,2020.
	 */
	public static long getCurrentDate() {
		LocalDate d1=LocalDate.now();
		LocalDate d2=LocalDate.parse("2020-01-01");
		long days = ChronoUnit.DAYS.between(d2, d1);
		return days;
	}
	
	
	
	
}
