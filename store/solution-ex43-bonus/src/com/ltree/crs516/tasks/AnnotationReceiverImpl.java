package com.ltree.crs516.tasks;

import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ltree.crs516.data.DataService;
import com.ltree.crs516.domain.Station;
import com.ltree.crs516.taskengine.DataServiceLocator;
import com.ltree.crs516.taskengine.Receiver;

 @SuppressWarnings("serial")
public final class AnnotationReceiverImpl implements Serializable, Receiver {

	private final Logger logger = LoggerFactory.getLogger(AnnotationReceiverImpl.class);
	

	@Action(1)
	public void bytesInProfile() {
		logger.info("bytesInProfile action called ");
		System.out.println("Number of bytes in profiles of stations");
		DataService dataService = DataServiceLocator.INSTANCE.getDataService();
		for (Station station : dataService) {
			System.out.println(station.getBytesInProfile());
		}
	}
	
//TODO 1: Add a method appropriately annotated that will loop through all
//the stations and print out the country property of each one.
	
	@Action(2)
	public void country() {
		logger.info("country action called ");
		System.out.println("Country property of stations");
		DataService dataService = DataServiceLocator.INSTANCE.getDataService();
		for (Station station : dataService) {
			System.out.println(station.getCountry());
		}
	}
//TODO 2: Add a method appropriately annotated that will loop through all
//the stations and print out the cruise number property of each one.
	@Action(3)
	public void cruiseNumber() {
		logger.info("cruiseNumber action called ");
		System.out.println("Cruise Number property of stations");
		DataService dataService = DataServiceLocator.INSTANCE.getDataService();
		for (Station station : dataService) {
			System.out.println(station.getCruiseNumber());
		}
	}

}
