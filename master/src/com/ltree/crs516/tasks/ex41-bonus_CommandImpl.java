package com.ltree.crs516.tasks;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ltree.crs516.data.DataConstants;
import com.ltree.crs516.data.DataService;
import com.ltree.crs516.domain.Station;
import com.ltree.crs516.taskengine.Command;

 @SuppressWarnings("serial")
public final class CommandImpl implements Command {

	private final Logger logger = LoggerFactory.getLogger(CommandImpl.class);
	
	private final File file = new File(DataConstants.DATA_DIR+"OSDO5711.gz");

	/**
	 * Will be called by task engine
	 */
	@Override
	public void run() {
		logger.info("execute called ");

//TODO 1: There is a helper method createDataService() inherited from Command. Call it 
//giving it the File file above as an input argument to obtain a DataService object.
$
@		DataService dataService = createDataService(file);
&		DataService dataService = null; //Edit this line.
^		DataService dataService = null;
%TODO 1:		DataService dataService = createDataService(file);<br/><br/>

//TODO 2: Loop through the stations of the dataService. (Use a foreach loop).
$//For each station, print something, e.g., the number of bytes in the profile of that station
$//to the console.
^//For each station, print a property of your choice to the console.
//This is where the actual work gets done.		
@		for (Station station : dataService) {
@			System.out.println(station.getBytesInProfile());
@		}
$
$
$
$
%TODO 2:<br/>for (Station station : dataService) {<br/>&#160;&#160;&#160;&#160;System.out.println(station.getBytesInProfile());<br/>}<br/><br/>

	}
	
}

