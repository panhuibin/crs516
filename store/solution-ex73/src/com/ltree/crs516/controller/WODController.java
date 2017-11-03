package com.ltree.crs516.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ltree.crs516.client.WODClient;
import com.ltree.crs516.data.DataService;
import com.ltree.crs516.data.FileBasedDataService;
import com.ltree.crs516.data.RecordNotFoundException;
import com.ltree.crs516.domain.IStation;
import com.ltree.crs516.util.WODThreadPools;

/**
 * The controller that orchestrates communication between the other components.
 * This controller is file-centric.
 */
public class WODController extends Observable {

	/**
	 * The station being viewed. A value of zero means that no station is being
	 * viewed.
	 */
	private volatile int currentStation = 0;

	private DataService dataService;

	/**
	 * A File object representing the data file being displayed.
	 */
	private volatile File dbFile;
	
	/**
	 * Name of the data file being displayed
	 */
	private String fileName;

	Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * WODClient is the interface type of the top level JFrame that houses the 
	 * program. The interface prevents WODController from knowledge of the 
	 * presentation technology (Swing in this case).
	 */
	private WODClient owningFrame;
	
	public WODController(WODClient owner) {
		owningFrame = owner;
		WODThreadPools.getDaemonThreadService().submit(new DataFileMonitor(dbFile));
	}
	
	public WODController() {
		this(null);
	}
	
	public void setOwningFrame(WODClient owningFrame) {
		this.owningFrame = owningFrame;
	}
	
	/**
	 * Instructs the data layer to read in the station 
	 * and then invokes the observers (tabs) which will
	 * refresh their displays.
	 */
	private void display() {
		lookingUpMessage();
		try {
			IStation station = readCurrentStation();
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
		}
		fileName = dbFile.getName();
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

	private IStation readCurrentStation() throws RecordNotFoundException,
			IOException {
		if (dataService.size() == 0)
			return null;
		if (currentStation > dataService.size()) {
			currentStation = dataService.size();
		}
		if (currentStation == 0) {
			currentStation = 1;
		}
		IStation station = null;		
		station = dataService.read(currentStation - 1); //long method call
		return station;
	}

	public int getCurrentIndex() {
		return currentStation;
	}

	private void lookingUpMessage() {
		owningFrame.sendMessage("<html><font color='red'>" + "looking up data.");
	}

	/**
	 * Moves to the next station if there is one.
	 * This method runs in the GUI Thread so it must
	 * finish quickly!
	 */
	public void next() {
		WODThreadPools.getThreadService().submit(new Runnable(){

			@Override
			public void run() {
				currentStation = (currentStation < dataService.size()) ? ++currentStation
						: dataService.size();
				display();
			}});
	}

	/**
	 * Moves to the previous station if there is one.
	 * This method runs in the GUI Thread!
	 *
	 */
	public void previous() {
		WODThreadPools.getThreadService().submit(new Runnable(){

			@Override
			public void run() {
				currentStation = (currentStation > 0) ? --currentStation : 0;
				display();
				
			}});
	}


	public void setCurrentStation(final int newStation) {

		WODThreadPools.getThreadService().submit(new Runnable(){

			@Override
			public void run() {

				if(newStation >=1){
				currentStation = (newStation < dataService.size()) ? newStation
						: dataService.size();
				}
				else{
					currentStation = 1;
				}
				display();
				
			}});	
	}

	public void setDataService(DataService dataService) {
		this.dataService = dataService;
	}

	/**
	 * 
	 * @param theFileStr
	 */
	public void loadFile(String theFileStr) {
		/*Just set the file. A worker thread will do the actual loading
		 * as this code is potentially running in the senders(GUI) thread.*/
		dbFile = new File(theFileStr);
		logger.info("Loading "+ dbFile);
	}
	
	/**
	 * Runnable that monitors the data file chosen. Whenever it changes a new
	 * set of data is loaded.
	 *
	 */
	private class DataFileMonitor implements Runnable {

		/**
		 * File to be read.
		 */
		private File fs;

		private DataFileMonitor(File fs) {
			this.fs = fs;
		}

		public void run() {
			while (true) {
				WODThreadPools.delay(1);
				if (dbFile != null && !dbFile.equals(fs)) {
					fs = dbFile;
					owningFrame.setCursor(WODClient.workCursor);
					if(dataService == null){
						dataService = new FileBasedDataService(fs);
					}
					else{
						dataService.load(fs);
					}
					logger.info("About to read file " + fs.getName());
					while (dataService.isLoading()) {
						WODThreadPools.delay(0.1f);
					}
					currentStation = 1;
					display();
					owningFrame.sendMessage("All stations are loaded!");
					logger.info("All stations from " + fs.getName()
							+ " are loaded!");
					owningFrame.setCursor(WODClient.normalCursor);
				}
			}
		}
	}
	
	/**
	 * Returns the index of the station being examined.
	 * @return an int, the index of the station.
	 */
	public int getCurrentStation() {
		return currentStation;
	}

	private IStation readCurrentStation(boolean noProxy)
			throws RecordNotFoundException, IOException {
		IStation station = null;
		station = dataService.read(currentStation - 1, true); //long call
		return station;
	}
	
	public void getDetails() {
		WODThreadPools.getThreadService().submit(new Runnable() {
			@Override
			public void run() {
				try {
					IStation station = readCurrentStation(true);
					if (station == null) {
						return;
					}
					owningFrame.setLocators(currentStation, dataService.size());
					setChanged();
					notifyObservers(station);
					owningFrame.sendMessage("Ready");
				} catch (IOException e) {
					logger.warn("Could not read station{}", currentStation, e);
					owningFrame.sendMessage(e.getMessage());
					return;
				} catch (RecordNotFoundException e) {
					logger.warn("Could not find the record", e);
					owningFrame.sendMessage(e.getMessage());
				}
				fileName = dbFile.getName();
				if (dataService.isLoading()) {
					owningFrame.setTitle("Ocean data from file " + fileName + ": record "
							+ (currentStation));
					owningFrame.sendMessage("Still reading more data ...");
				} else {
					owningFrame.setTitle("Ocean data from file " + fileName + ": record "
							+ (currentStation) + " out of " + dataService.size());
					owningFrame.sendMessage("Ready");
				}
			}

		});
	}
	
	public void setObservers(List<Observer> observers){
		for (Observer observer : observers) {
			addObserver(observer);
		}
	}	
}
