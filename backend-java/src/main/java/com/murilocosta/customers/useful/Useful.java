package com.murilocosta.customers.useful;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Useful {

	public static final String CUSTOMERS = "CUSTOMERS";	
	public static final String CUSTOMERS_UPDATE = "CUSTOMERS::UPDATE";
	
	public static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	
	
	//Errors
	public static final String ER_0001 = "Error loading data!";
	public static final String ER_0002 = "Error saving record!";
	public static final String ER_0003 = "Error updating record!";
	public static final String ER_0004 = "Customer not found!";
	public static final String ER_0005 = "Invalid Null Object!";
	public static final String ER_0006 = "Birth Date - Required Field!";
	public static final String ER_0007 = "City - Required Field!";
	public static final String ER_0008 = "State - Required Field!";
	public static final String ER_0009 = "FirstName - Required Field!";
	public static final String ER_0010 = "LastName - Required Field!";
	public static final String ER_0011 = "E-mail- Required Field!";
	public static final String ER_0012 = "E-mail- Already in use!";
	
	public static Integer getAge(String date) throws ParseException {
		Calendar currentDate = Calendar.getInstance();
		Calendar birthDate = Calendar.getInstance();
		birthDate.setTime(sdf.parse(date));
		
		int age = currentDate.get(Calendar.YEAR) - birthDate.get(Calendar.YEAR);

	    if (currentDate.get(Calendar.MONTH) < birthDate.get(Calendar.MONTH)) {
	    	age--;  
	    } else { 
	        if (currentDate.get(Calendar.MONTH) == birthDate.get(Calendar.MONTH) && 
	        		currentDate.get(Calendar.DAY_OF_MONTH) < birthDate.get(Calendar.DAY_OF_MONTH)) {
	        	age--; 
	        }
	    }
		return age;
	}
}
