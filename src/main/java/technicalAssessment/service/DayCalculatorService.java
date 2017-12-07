package technicalAssessment.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import technicalAssessment.exception.UserException;

@Service
public class DayCalculatorService {
	
	private static Map<Integer, Integer> monthDayMap = new HashMap<Integer, Integer>();
	private static String date1, date2;
	private static int day1, day2, mon1, mon2, year1, year2;
	private static int days=0,noOfDays;
	
	/***
	 * 
	 * This is main service method which will accept input in DD MM YYYY, DD MM YYYY format
	 * Output would be DD MM YYYY, DD MM YYYY, difference
	 */
	public String calculateDays(String input){
		return input+", "+noOfDays();
	}
	
	/**
	 * 
	 * This method will start a loop from first year till the second mentioned
	 * This method will also update number of days in February according to the year
	 * Output would be the difference
	 * 
	 */
	public static int noOfDays(){
    	
        for(int i=year1; i<=year2;i++){
        	if(isLeapYear(i)){
        		monthDayMap.put(2, 29);
        	}
        	else{
        		monthDayMap.put(2, 28);
        	}
        	noOfDays = (i<year2)?getDays(mon1, 12, false, noOfDays):getDays(mon1, mon2, true, noOfDays);
        	mon1 = 1;
        }
        
        return noOfDays-1;
    }

	/**
	 * 
	 * This method will take two months as input and will calculate number of days in betwen those months
	 * There is a boolean parameter as checkYear to check if current year and second year mentioned in input are same
	 * This is just to take final month's days and add them to total number
	 * Output of this method is number of days in an year
	 * 
	 */
	public static int getDays(int mon1, int mon2, Boolean checkYear, int noOfDays) {
		for(int j=mon1;j<=mon2;j++){
			if(j==mon2 && checkYear){
				days = day2;
			}
			else{
				days = monthDayMap.get(j);
			}
			noOfDays = noOfDays+(days-day1)+1;
			day1 = 1;
		}
		return noOfDays;
	}
	
	/**
	 * 
	 * This method is used to validate format of input received.
	 * If correct it will instantiate days, months and years.
	 * Else appropriate exceptions will be thrown.
	 * 
	 */
	public void validateDates(String input) throws UserException{
		String regex="([0][0-9]|[1-2][0-9]|[3][0-1])[ ]([0][0-9]|[1][0-2])[ ]([1][9][0-9][0-9]|[2][0][0][0-9]|[2][0][1][0])[,][ ]"
				+ "([0][0-9]|[1-2][0-9]|[3][0-1])[ ]([0][0-9]|[1][0-2])[ ]([1][9][0-9][0-9]|[2][0][0][0-9]|[2][0][1][0])";
		if(!(input.matches(regex))){
        	throw new UserException("Dates should be in format: \"DD MM YYYY, DD MM YYYY\" and in between 01 01 1900 and 31 12 2010");
        }
		date1 = input.substring(0,input.indexOf(','));
		date2 = input.substring(input.indexOf(',')+2, input.length());
		day1 = Integer.parseInt(date1.substring(0, 2));
        mon1 = Integer.parseInt(date1.substring(3, 5));
        year1 = Integer.parseInt(date1.substring(6, 10));
        
        day2 = Integer.parseInt(date2.substring(0, 2));
        mon2 = Integer.parseInt(date2.substring(3, 5));
        year2 = Integer.parseInt(date2.substring(6, 10));
        
        noOfDays=0;
        
        if(year1>year2){
    		throw new UserException("Latter year can't be less than first one");
    	}
    	if(year1==year2){
    		if(mon1>mon2){
    			throw new UserException("Invalid months mentioned");
    		}
    		if((mon1==mon2) && (day1>day2)){
    			throw new UserException("Invalid day mentioned");
    		}
    	}
	}
	
	/**
	 * A static block to initiate number of days in months
	 */
	static {
    	monthDayMap.put(1, 31);
    	monthDayMap.put(2, 28);
    	monthDayMap.put(3, 31);
    	monthDayMap.put(4, 30);
    	monthDayMap.put(5, 31);
    	monthDayMap.put(6, 30);
    	monthDayMap.put(7, 31);
    	monthDayMap.put(8, 31);
    	monthDayMap.put(9, 30);
    	monthDayMap.put(10, 31);
    	monthDayMap.put(11, 30);
    	monthDayMap.put(12, 31);
    }
    
	/**
	 * 
	 * A static method to check whether an year is a leap year or not
	 * 
	 */
    public static boolean isLeapYear(int year){
    	if(year%400==0){
    		return true;
    	}
    	if(year%100==0){
    		return false;
    	}
    	if(year%4==0){
    		return true;
    	}
    	return false;
    }
}
