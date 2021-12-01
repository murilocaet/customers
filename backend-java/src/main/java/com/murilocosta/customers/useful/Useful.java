package com.murilocosta.customers.useful;

import java.text.SimpleDateFormat;

public class Useful {

	public static final String CUSTOMERS = "CUSTOMERS";	
	public static final String CUSTOMERS_UPDATE = "CUSTOMERS::UPDATE";
	
	public static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	
	
	//Errors
	public static final String ER_0001 = "Error loading data!";
	public static final String ER_0002 = "Error saving record!";
	public static final String ER_0003 = "Error updating record!";
	public static final String ER_0004 = "Customer not found!";
	public static final String ER_0005 = "Invalid Null Object!";
	public static final String ER_0006 = "Age - Required Field!";
	public static final String ER_0007 = "City - Required Field!";
	public static final String ER_0008 = "State - Required Field!";
	public static final String ER_0009 = "FirstName - Required Field!";
	public static final String ER_0010 = "LastName - Required Field!";
	public static final String ER_0011 = "E-mail- Required Field!";
	public static final String ER_0012 = "E-mail- Already in use!";
		
}
