package com.ltree.crs516.controller;

import java.io.IOException;
import java.util.Observable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ltree.crs516.client.WODClient;
import com.ltree.crs516.data.DataService;
import com.ltree.crs516.data.RecordNotFoundException;
import com.ltree.crs516.domain.Station;

/**
 * The controller in an MVC pattern. More on MVC later. 
 * The classes of the com.ltree.crs516.client package form the view. 
 * The classes of com.ltree.crs516.domain and com.ltree.crs516.data
 * form the model. Communication between the controller and the tabs 
 * that form the view follows an Observer/Observable pattern.
 */

//TODO 1: Note that WODController is an Observable.

public final class WODController extends Observable {

	private Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * Reference to an object that implements the 
	 * com.ltree.crs516.data.DataService interface.
	 */
	private DataService dataService;

	/**
	 * Name of the data file being displayed.
	 */
	private String fileName;

	/**
	 * WODClient is the interface type of the top level JFrame that houses the 
	 * program. The interface prevents WODController from knowledge of the 
	 * presentation technology (Swing in this case).
	 */
	private WODClient owningFrame; 
	
	public WODController(WODClient owner) {
		owningFrame = owner;
	}

	/**
	 * Convenience method to give feedback to the owning frame that
	 * data is being looked up.
	 */
	private void lookingUpMessage() {
		owningFrame.sendMessage("<html><font color='red'>" + "looking up data.");
	}

	/**
	 * The station being viewed. A value of zero means that no station is being
	 * viewed.
	 */
	private volatile int currentStation = 0;
	
	/**
	 * Helper method that will request the dataService for the station 
	 * object identified by the int currentStation. 
	 * @return a Station, the current station
	 * @throws RecordNotFoundException if there is no such station.
	 * @throws IOException if there are communication problems
	 */
	private Station readCurrentStation() throws RecordNotFoundException,
			IOException {
		if (dataService.size() == 0){ //Means there is no data.
			return null;
		}	
		//Validation that the number currentStation makes sense.
		if (currentStation > dataService.size()) {
			currentStation = dataService.size();
		}
		//Client calls the first station station 1 
		//but dataService calls it station 0.				
		if (currentStation == 0) {
			currentStation = 1;
		}
		Station station = null;
		station = dataService.read(currentStation - 1); 
		return station;
	}

	/**
	 * Helper method that instructs the data layer to read in the station 
	 * and then invokes the observers (tabs) which will
	 * refresh their displays.
	 */
	private void display() {
		lookingUpMessage();
	//Feedback to owning frame, the top level JFrame that holds the program.
	//This makes the little red message "loading data" appear on the GUI.	
		try {
			Station station = readCurrentStation();
			if(station == null){return;}
			owningFrame.setLocators(currentStation, dataService.size());
			setChanged();
			notifyObservers(station);
		} catch (IOException e) {
			logger.warn("Could not read station{}", currentStation,e);
			owningFrame.sendMessage(e.getMessage());
			return;
		} catch (RecordNotFoundException e) {
			logger.warn("Could not find the record",e);
			owningFrame.sendMessage(e.getMessage());
			return;
		}
		if (dataService.isLoading()) {
			owningFrame.setTitle("Ocean data from file " + fileName + ": record "
					+ (currentStation));
			owningFrame.sendMessage("Still reading more data ...");
		} else {
			owningFrame.setTitle("Ocean data from file " + fileName + ": record "
					+ (currentStation) + " out of " + dataService.size());
			owningFrame.sendMessage("");
		}
	}


	public int getCurrentIndex() {
		return currentStation;
	}

	/**
	 * Moves to the next station if there is one.
	 */
	public void next() {
		 /* This method runs in the GUI Thread so it must finish quickly!*/
		currentStation = (currentStation < dataService.size()) ? ++currentStation
				: dataService.size();
		display();
	}

	/**
	 * Moves to the previous station if there is one.
	 */
	public void previous() {
		 /* This method runs in the GUI Thread so it must finish quickly!*/		
		currentStation = (currentStation > 0) ? --currentStation : 0;
		display();
	}

	public void setCurrentStation(int newStation) {
		 /* This method runs in the GUI Thread so it must finish quickly!*/		
		if(newStation >=1){
		currentStation = (newStation < dataService.size()) ? newStation
				: dataService.size();
		}
		else{
			currentStation = 1;
		}
		display();
	}

	public void setDataService(DataService dataService) {
		this.dataService = dataService;
	}
}
