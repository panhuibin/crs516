package com.ltree.crs516.tasks;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ltree.crs516.data.DataService;
import com.ltree.crs516.domain.Station;
import com.ltree.crs516.taskengine.Command;
import com.ltree.crs516.taskengine.DataServiceLocator;

 @SuppressWarnings("serial")
public final class CommandCountryImpl  implements Command {
	
	private final Logger logger = LoggerFactory.getLogger(CommandCountryImpl.class);

	/* Will be called from run() of CommandImpl. */
	@Override
	public void run() {
		logger.info("run() called ");

//Obtain a DataService from the DataServiceLocator and in a variable called dataService.
		
		DataService dataService = DataServiceLocator.INSTANCE.getDataService();

//Loop through the all the stations in the dataService and for 
//each station print the value of the country property of the station.
//The method getCountry() gives you the value of the country property.

		for (Station station : dataService) {
			System.out.println(station.getCountry());
		}
	}
}

