package com.ltree.crs516.data;

import java.io.Closeable;
import java.io.IOException;
import java.util.List;

import com.ltree.crs516.domain.Station;

/**
 * This interface declares the methods that the clients use to request data. The
 * intent is that users of this software will use these methods in their own
 * classes to access data. They will then apply their logic to the data and have
 * some means of displaying their results. On the other hand if the WOD data
 * changes in format the researcher's software does not need to change but
 * instead the researcher would write a new implementation of this class.
 * 
 * @author crs516 development team
 */

public interface DataService extends Iterable<Station>, Closeable {

	/**
	 * Reads a record from the database and returns it in the form of a Station
	 * object.
	 * 
	 * @param recNo
	 *            The record number of the desired record.
	 * @return A Station object.
	 * @throws RecordNotFoundException
	 *             if recNo does not correspond to a Station in the database.
	 * @throws IOException
	 */
	public Station read(int recNo) throws RecordNotFoundException, IOException;

//TODO 1: Add a public method called read that takes two arguments 
//(int currentStation, boolean noProxy) and returns an IStation. It should be 
//able to throw RecordNotFoundException, IOException.
	
	
	
	/**
	 * Returns the current size of the database.
	 * 
	 * @return an int, the number of records in the database.
	 */
	public int size();

	/**
	 * Triggers the reading of the WOD09 data file.
	 */
	public void load();

	/**
	 * Indicates if the file is loaded.
	 * 
	 * @return true if the data is being loaded and false otherwise.
	 */
	public boolean isLoading();

	/**
	 * For example a file based DataService will load the file
	 * @param details
	 */
	public void load(Object details);

	/**
	 * Returns the available cruises
	 * 
	 * @return
	 */
	public List<Integer> getCruiseNumbers();

	/**
	 * Returns the Station at that index in the cruise with the 
	 * given cruiseNumber.
	 * @param cruiseNumber, an int that identifies the cruise.
	 * @param index, an int, the index of the station in the cruise.
	 * @return
	 */
	public Station read(Integer cruiseNumber, int index);

	/**
	 * Returns the number of station in the cruise.
	 * @param cruiseNumber, an int that identifies the cruise.
	 * @return an int, the number of stations that comprise the cruise.
	 */
	public Integer getCruiseSize(Integer cruiseNumber);


	

}
