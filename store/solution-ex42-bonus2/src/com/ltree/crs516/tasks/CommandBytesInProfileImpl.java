package com.ltree.crs516.tasks;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ltree.crs516.data.DataService;
import com.ltree.crs516.domain.Station;
import com.ltree.crs516.taskengine.Command;
import com.ltree.crs516.taskengine.DataServiceLocator;

 @SuppressWarnings("serial")
public final class CommandBytesInProfileImpl implements Command {
	
	private final Logger logger = LoggerFactory.getLogger(CommandBytesInProfileImpl.class);

	/* Will be called from run() of CompositeCommandImpl. */
	@Override
	public void run() {
		logger.info("run() called ");

//Obtain a DataService from the DataServiceLocator.
		
		DataService dataService = DataServiceLocator.INSTANCE.getDataService();

//Loop through the all the stations in the dataService and for 
//each station print the number of bytes in the profile to the console.

		for (Station station : dataService) {
			System.out.println(station.getBytesInProfile());
		}
	}

}
