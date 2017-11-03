package com.ltree.crs516.taskengine;

import static java.lang.Math.abs;
import static java.lang.Math.max;
import static java.lang.Math.min;
import static java.lang.Math.sqrt;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ltree.crs516.data.DataService;
import com.ltree.crs516.data.FileBasedDataService;
import com.ltree.crs516.data.RecordNotFoundException;
import com.ltree.crs516.domain.Station;

/**
 * Finds the maximum of the distances between stations in the dataService.
 * Key idea: The Stations are massive objects but we only need the latitudes and 
 * longitudes. The large data sets have tens of thousands of stations which 
 * won't fit in memory but tens of thousands of doubles are trivial. Just 
 * read all of the stations in sequence storing the latitudes and longitudes and
 * work with them.
 */
public final class DistanceCalculator {

	//The files are in ascending order based on how long it takes to process 
	//them. Some might have fewer stations with large amounts of data and others 
	//might have very many stations with small amounts of data.
	public final static String[] dataFile = new String[] {
		"CTDO7805.gz",
		"OSDO7101.gz",		
		"OSDO5711.gz",
		"CTDO1009.gz",
		"GLDS7503.gz",
		"CTDO7113.gz",
		"XBTO5101.gz",
		"SURF_ALL.gz" 
	};
	
	private DataService dataService;
	private double maximumDistance;
	private static final Logger logger 
							= LoggerFactory.getLogger(DistanceCalculator.class);

	//The way the methods action4() and action5() work is
	
	//Step 1: 
	//Read the locations of all the stations into a double[][] called 
	//stationLocations. A pair of doubles, the latitude and longitude are much 
	//smaller than a whole Station. All of them will fit in memory are the 
	//same time.
	
	//Step 2: 
	//Go through the double[][] stationLocations and calculate the maximum 
	//distance between any two stations. Step 2 is simple and all three methods 
	//use the helper method below. Step 1 is the expensive part one and you will 
	//work on that.	
	
	
	/**
	 * Helper method to calculate the maximum distance between any two stations 
	 * located at latitudes and longitudes stored in an array.
	 * 
	 * @param stationLocations a double[][] where each double[] is of the form
	 * {latitude, longitude}.
	 * @return the maximum of the distances between the locations.
	 */
	private double calculateMaximumDistance(double[][] stationLocations) {
		double maxSquaredDistance = 0;
		for (int i = 1; i < dataService.size(); i++) {
			for (int j = i + 1; j < dataService.size(); j++) {
				maxSquaredDistance = max(
						maxSquaredDistance,
						calculateDistance(stationLocations[i],
								stationLocations[j]));
			}
		}
		return sqrt(maxSquaredDistance)*DistanceUnits.KILOMETERS.factor;
	}

//======= action4(): Already works. It uses a single thread for step 1 =========	
	
	public void action4() {
		double[][] stationLocations = new double[dataService.size()][];
		//Step 1: Extract latitude and longitudes from the stations
		try {
			for (int i = 1; i < dataService.size(); i++) {
				Station station = dataService.read(i);
				double latitude 
							= Double.parseDouble(station.getLatitudeString());
				double longitude 
							= Double.parseDouble(station.getLongitudeString());
				stationLocations[i] = new double[] { latitude, longitude };
			}
		} catch (RecordNotFoundException | IOException e) {
			e.printStackTrace();
		}
		
		logger.info("Step 1 is done!");

		//Step 2: Now that all the locations are in the array find the 
		//maximum distance using the helper method.
		maximumDistance = calculateMaximumDistance(stationLocations);
		
		logger.info("Step 2 is done!");

	}

//============== action5(): Using a countDownLatch for step 1 ==================
	
	private CountDownLatch countDownLatch;
	public void action5() {
		double[][] stationLocations = new double[dataService.size()][];

		//Step 1: Extract latitude and longitudes from the stations.

//TODO 1: First scroll down to //{{Marker 1 and see how the inner class 
//SingleStationReader reads a station and puts the location into the 
//stationLocations array. Then come back to this point.
		
		//This time you will create a task that reads a single station and puts
		//the latitude and longitude into the array. Submit such a task for each 
		//station to a thread pool. All threads will be reading stations 
		//currently in the cache otherwise there will be a lot of file i/o so 
		//we need to know the cache size.
		int cacheSize = FileBasedDataService.MANAGEABLE_SIZE;

//TODO 2: Create a newFixedThreadPool of size 4
		ExecutorService threadPool = null;//Edit this.
		
		//You will use a countdown latch to make sure all stations in the cache 
		//are read before moving to the next batch.
		
		//At any time the cache holds data for stations number i where
		//i goes from the value cursor to cursor+cacheSize except perhaps the
		//last one. The last one might end at dataService.size() since the 
		//cacheSize might not divide dataService.size() exactly.
		int cursor = 1;
		while (cursor < dataService.size()) {
			// The int endpoint points at the end value of i in the cache.
			int endPoint = min(cursor + cacheSize, dataService.size());
			logger.info("cursor is {} and endpoint is {}", cursor, endPoint);

//TODO 3: Create a CountDownLatch with the beginning count starting at 
//(endPoint - cursor).			
			countDownLatch = null;

			logger.info("Countdown is at {}", countDownLatch.getCount());			
			//Now you will submit jobs to the thread pool
			for (int stationNumber = cursor; stationNumber < endPoint; stationNumber++) {

//TODO 4: Create a 	SingleStationReader. Pass the array stationLocations and 
//the int stationNumber into the constructor.		

				SingleStationReader task = null;

//TODO 5: Submit the singleStationReader to the pool.
				

				
				
			}

//TODO 6: Call await() on the countDownLatch. You will need a try block and 
//catch InterruptedException. 			









//TODO 7: Increment the cursor by adding cacheSize to it.			
			
			
		}//Main thread goes back to top of loop and submits the next set 
		 //of stations.
		
		logger.info("Step 1 is done!");
		
		//Step 2: Now that all the locations are in the array find the 
		//maximum distance.
		
		maximumDistance = calculateMaximumDistance(stationLocations);
		
		logger.info("Step 2 is done!");		
	}

	
//{{Marker 1	
	/**
	 * This Runnable has the logic to read and extract the longitude and 
	 * latitude from a single station and put them in the stationLocations array.
	 * @author crs516 Development Team.
	 *
	 */
	private final class SingleStationReader implements Runnable{
		private double[][] stationLocations;
		private int stationNumber;
		
		private SingleStationReader(double[][] stationLocations,	
															int stationNumber) {
			this.stationLocations = stationLocations;
			this.stationNumber = stationNumber;
		}

		@Override
		public void run() {
		//Executed in separate thread. Reads a single station, puts the location 
		//in the array and then calls countDown().	
			try {
				Station station = dataService.read(stationNumber);
				double latitude 
							= Double.parseDouble(station.getLatitudeString());
				double longitude 
							= Double.parseDouble(station.getLongitudeString());
				stationLocations[stationNumber] 
										= new double[] { latitude, longitude };
				countDownLatch.countDown();
			} catch (NumberFormatException | RecordNotFoundException
					| IOException e) {
				e.printStackTrace();
			}
		}
	}
//{{End Marker 1
	

//======================== No more TODOs after this ============================	

	/**
	 * Calculates the distance between two locations each given as a double[]
	 */
	private double calculateDistance(double[] location1, double[] location2) {
		double lattitudeDifference = abs(location1[0] - location2[0]);
		double longitudeDifference = abs(location1[1] - location2[1]);
		// In case they crossed the Greenwich meridian.
		lattitudeDifference = min(lattitudeDifference,
				360 - lattitudeDifference);
		double squareDistance 
			= lattitudeDifference* lattitudeDifference 
			  + longitudeDifference	* longitudeDifference;
		return squareDistance;
	}

	/**
	 * Each degree of longitude/latitude becomes this many kilometers or miles
	 * on the ground.
	 * @author crs 516 Development Team.
	 *
	 */
	private enum DistanceUnits {
		KILOMETERS(40075 / 360.0), MILES(24901 / 360.0);
		private final double factor;
	
		private DistanceUnits(double factor) {
			this.factor = factor;
		}
	}
	
	public void close() {
		try {
			dataService.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public double getMaximumDistance() {
		return maximumDistance;
	}
	
	public void setFile(File file) {
		dataService = DataServiceFactory.createFileBasedDataService(file);
	}
	

}
