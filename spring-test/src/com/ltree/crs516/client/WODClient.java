package com.ltree.crs516.client;

import java.awt.Cursor;

/**
 * This abstract methods of this class are expected by the  the controller.  
 * Alternative swing views for WOD09 will extend this class. 
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
	public abstract void setLocators(int currentStation, int totalNumberOfStations);

	/**
	 * Client view should display the message.
	 * @param message
	 */
	public abstract void sendMessage(String message);

	public abstract void setTitle(String string);

	public abstract void setCursor(Cursor workcursor);

}
