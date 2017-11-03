package com.ltree.crs516.controller;

import static com.ltree.crs516.data.DataConstants.DATA_DIR;
import static org.mockito.Mockito.mock;

import java.io.File;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;

import com.ltree.crs516.client.WODClient;
import com.ltree.crs516.data.FileBasedDataService;
import com.ltree.crs516.domain.Station;

public class WODControllerTest implements Observer{

	private WODController testSubject;
	private File largerFile = new File(DATA_DIR + "OSDO7101.gz");
	private FileBasedDataService dataService;
	private Random random = new Random();
	
	//We don't need a GUI so we use a mock for the owning frame
	private WODClient mockOwningFrame;
	
	@Before
	public void setup(){
		mockOwningFrame = mock(WODClient.class); 
		testSubject = new WODController(mockOwningFrame);
		dataService = new FileBasedDataService(largerFile);
		testSubject.setDataService(dataService);
		testSubject.addObserver(this);
	}
	
	@Test
	public void testSetCurrentStation() throws InterruptedException {
		while(dataService.isLoading()){
			System.out.println("Waiting for data to load");
			Thread.sleep(1000);
		}
		int maxIndex = dataService.size();
		double now = System.nanoTime();
		
		// Ask for stations
		for(int i=0; i<30_000;i++){
		testSubject.setCurrentStation(random.nextInt(maxIndex));
		}
		
		double timeTaken = System.nanoTime()-now;
		System.out.println("This took "+ timeTaken +" nanos");
	}

	@Override
	/*
	 * This code will run every time the Observable (WODController) changes stations
	 */
	public void update(Observable o, Object arg) {
		Station currentStation = (Station)arg;
		System.out.println("Current station is "+currentStation.getStationNumber());
	}

}
