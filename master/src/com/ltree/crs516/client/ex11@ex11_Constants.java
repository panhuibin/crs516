package com.ltree.crs516.client;

public final class Constants {

	/**
	 * This class is not meant to be instantiated.
	 */
	private Constants(){}
	
	/**
	 * Key for the list of cruise numbers passed to the JSP for display
	 */
	public static final String CRUISE_NUMBERS = "cruise_numbers";
	
	/**
	 * Cursor so that user can page through cruise numbers
	 */
	public static final String CURSOR_VALUE = "cursor_value";
	
	/**
	 * Number of cruise numbers per page when viewed by user
	 */
	public static final int SHORT_LIST_SIZE = 10;
	
	/**
	 * The cruise number of the current cruise the user is looking at
	 */
	public static final String CRUISE_NUMBER = "cruiseNumber";
	
	/**
	 * Key for the station object passed to the jsp
	 */
	public static final String STATION = "station";
	
	/**
	 * The station number within a cruise
	 */
	public static final String STATION_NUMBER = "station_number";

}
