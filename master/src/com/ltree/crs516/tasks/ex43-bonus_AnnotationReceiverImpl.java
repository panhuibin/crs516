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
	
@	@Action(2)
@	public void country() {
@		logger.info("country action called ");
@		System.out.println("Country property of stations");
@		DataService dataService = DataServiceLocator.INSTANCE.getDataService();
@		for (Station station : dataService) {
@			System.out.println(station.getCountry());
@		}
@	}
$
$
$
$
$
$
$
$
$
%TODO 1:<br/>&#160;&#160;@Action(2)<br/>&#160;&#160;public void country() {<br/>&#160;&#160;&#160;&#160;logger.info("country action called ");<br/>&#160;&#160;&#160;&#160;System.out.println("Country property of stations");<br/>&#160;&#160;&#160;&#160;DataService dataService = DataServiceLocator.INSTANCE.getDataService();<br/>&#160;&#160;&#160;&#160;for (Station station : dataService) {<br/>&#160;&#160;&#160;&#160;&#160;&#160;System.out.println(station.getCountry());<br/>&#160;&#160;&#160;&#160;}<br/>&#160;&#160;}<br/><br/>
//TODO 2: Add a method appropriately annotated that will loop through all
//the stations and print out the cruise number property of each one.
@	@Action(3)
@	public void cruiseNumber() {
@		logger.info("cruiseNumber action called ");
@		System.out.println("Cruise Number property of stations");
@		DataService dataService = DataServiceLocator.INSTANCE.getDataService();
@		for (Station station : dataService) {
@			System.out.println(station.getCruiseNumber());
@		}
@	}
$
$
$
$
$
$
$
$
%TODO 2:<br/>&#160;&#160;@Action(3)<br/>&#160;&#160;public void cruiseNumber() {<br/>&#160;&#160;&#160;&#160;logger.info("cruise number action called ");<br/>&#160;&#160;&#160;&#160;System.out.println("Cruise Numbers of stations");<br/>&#160;&#160;&#160;&#160;&#160;&#160;DataService dataService = DataServiceLocator.INSTANCE.getDataService();<br/>&#160;&#160;&#160;&#160;&#160;&#160;for (Station station : dataService) {<br/>&#160;&#160;&#160;&#160;&#160;&#160;System.out.println(station.getCruiseNumber());<br/>&#160;&#160;&#160;&#160;}<br/>&#160;&#160;}<br/><br/>

}
