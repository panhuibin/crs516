package com.ltree.crs516.data;

import java.io.IOException;

import com.ltree.crs516.domain.Station;

// TODO 1: Examine this file so that you are aware of the methods of the 
// interface. To test the controller we don't need a working implementation.
// We shall create mock objects based on the interface.

/**
 * This interface declares the methods that the GUI uses
 * to request data. 
 * 
 * @author crs516 development team.
 */
/* The intent is that analysts using this software will use these 
 * methods in their own classes to access data. They will then 
 * apply their logic to the data and have some means of displaying their
 * results. On the other hand if the WOD data changes in 
 * format the researcher's software does not need to change
 * but instead the researcher would write/revise the implementation
 * of the DataService interface.
 */
public interface DataService {

    /**
     * Reads a record from the database and returns it in the form of a Station object.
     *
     * @param recNo The record number of the desired record.
     * @return A Station object, the one that corresponds to recNo.
     * @throws RecordNotFoundException if there is no station that corresponds
     *  to recNo in the database.
     * @throws IOException 
     */
    Station read(int recNo) throws RecordNotFoundException, IOException;
    
    /**
     * Returns the current size of the database.
     * @return an int, the number of records in the database.
     */
    int size();

 // TODO 2: Note that we are file-centric in the way we are looking at the data.
 // Later we will realize that really the analysts see stations which have id's
 // called accession numbers. 
 // A collection of Stations from one voyage/buoy or even a sensor attached to 
 // a large sea animal like an elephant seal is a cruise and it has 
 // an id, a cruise number. The gzip file is just a container for distributing a 
 // number of cruises and has no meaning in the application domain. 
 // We, the IT team are not subject experts so we will get to that realization 
 // later.
 //   
 // Frequent visits from the ultimate users of the software really help here.   
 // We shall also want to move the data to a relational database at which point
 // there will be no files to talk about but just cruises and stations.
 //   
 // Lesson: Your first design will not usually be the best so you should 
 // be prepared to refactor code.
    
	/**
	 * Triggers the reading of the WOD09 data file. 
	 */
	void load();
	
	/**
	 * Indicates if the file is loaded.
	 * @return true if the data is being loaded and false otherwise. 
	 */
	boolean isLoading();

}
