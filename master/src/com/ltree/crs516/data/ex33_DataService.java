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

//TODO 1: Change the declaration of this interface so that it extends
//the two interfaces Iterable<Station> and Closeable.

@public interface DataService extends Iterable<Station>, Closeable {
$public interface DataService{
%TODO 1: 	public interface DataService extends Iterable<Station>, Closeable {

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
	Station read(int recNo) throws RecordNotFoundException, IOException;

	/**
	 * Returns the current size of the database.
	 * 
	 * @return an int, the number of records in the database.
	 */
	int size();

	/**
	 * Triggers the reading of the WOD09 data file.
	 */
	void load();

	/**
	 * Indicates if the file is loaded.
	 * 
	 * @return true if the data is being loaded and false otherwise.
	 */
	boolean isLoading();

	/**
	 * For example a file based DataService will load the file.
	 * @param details
	 */
	void load(Object details);

	/**
	 * Returns the available cruises.
	 * 
	 * @return
	 */
	List<Integer> getCruiseNumbers();

	/**
	 * Returns the Station at that index in the cruise with the 
	 * given cruiseNumber.
	 * @param cruiseNumber, an int that identifies the cruise.
	 * @param index, an int, the index of the station in the cruise.
	 * @return
	 */
	Station read(Integer cruiseNumber, int index);

	/**
	 * Returns the number of stations in the cruise.
	 * @param cruiseNumber, an int that identifies the cruise.
	 * @return an int, the number of stations that comprise the cruise.
	 */
	Integer getCruiseSize(Integer cruiseNumber);

}