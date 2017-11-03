package com.ltree.crs516.tasks;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ltree.crs516.data.DataService;
import com.ltree.crs516.domain.Station;
import com.ltree.crs516.taskengine.Command;
import com.ltree.crs516.taskengine.DataServiceLocator;

 @SuppressWarnings("serial")
public final class CommandCruiseNumberImpl  implements Command{
	
	private final Logger logger = LoggerFactory.getLogger(CommandCruiseNumberImpl.class);

	/* Will be called from run() of CommandImpl. */
	@Override
	public void run() {
		logger.info("run() called ");
		
//Obtain a DataService from the DataServiceLocator and in a variable called dataService.
		
		DataService dataService = DataServiceLocator.INSTANCE.getDataService();

//Loop through the all the stations in the dataService and for 
//each station print the value of the cruiseNumber property of the station.

		for (Station station : dataService) {
			System.out.println(station.getCruiseNumber());
		}
	}
}

