package com.ltree.crs516.client;

import java.awt.Cursor;


/**
 * This methods of this interface are expected by the controller.  
 * Various alternative swing views for WOD09 will extend this class. 
 * @author crs516 development team
 *
 */
public interface WODClient{

	public static final Cursor workCursor = new Cursor(Cursor.WAIT_CURSOR);
	public static final	Cursor normalCursor = new Cursor(Cursor.DEFAULT_CURSOR);

	/**
	 * Instructs view to display the current station and the total number of available stations
	 * @param currentStation, an int, the record number of the current station. It is a number 
	 * between 1 and totalNumberOfStations inclusive.
	 * @param totalNumberOfStations, an int, the total number of stations available.
	 */
	public void setLocators(int currentStation, int totalNumberOfStations);

	/**
	 * Client view should display the message.
	 * @param message
	 */
	public void sendMessage(String message);

	public void setTitle(String string);

}
