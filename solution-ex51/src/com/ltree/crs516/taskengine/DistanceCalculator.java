package com.ltree.crs516.taskengine;

import static com.ltree.crs516.data.DataConstants.DATA_DIR;
import static java.lang.Math.abs;
import static java.lang.Math.max;
import static java.lang.Math.min;
import static java.lang.Math.sqrt;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ltree.crs516.data.DataService;
import com.ltree.crs516.data.FileBasedDataService;
import com.ltree.crs516.data.RecordNotFoundException;
import com.ltree.crs516.domain.Station;

public final class DistanceCalculator {

	//The files are in ascending order based on how long it takes to 
	//process them. Some might have fewer stations with large amounts of data 
	//and others might have very many stations with small amounts of data.
	public final static String[] dataFile = new String[] {
		"CTDO7805.gz",
		"OSDO5711.gz",
		"CTDO1009.gz",
		"OSDO7101.gz",
		"GLDS7503.gz",
		"CTDO7113.gz",
		"XBTO5101.gz",
		"SURF_ALL.gz" 
	};

	private static final Logger logger 
							= LoggerFactory.getLogger(DistanceCalculator.class);
	private DataService dataService;
	private double maximumDistance;


	/**
	 * Finds the maximum of the distances between stations in the dataService.
	 */
	
	//The first action() method is just a naive double loop.
		/**
		 * Finds the maximum of the distances between the stations that are in 
		 * the dataService.
		 */	
	public void action() {
		double maxDistance = 0;
		// Loop through all the stations.
		for (Station station1 : dataService) {
			// Loop through all the stations.
			for (Station station2 : dataService) {
				maxDistance = max(maxDistance,
						calculateDistance(station1, station2, 
							DistanceUnits.KILOMETERS));
			}
		}
		maximumDistance = maxDistance;
	}

	public static void main(String[] args) {
		DistanceCalculator calculator = new DistanceCalculator();
		
//TODO 1: 
//On the next non-comment line you choose which data file to use. Run the code 
//using dataFile[0], dataFile[1], ... until you find the greatest index for which 
//the run takes less than a minute. This will depend on the hardware you are
//using. There is a main() method in this class and instructions in there on how 
//to run it. You will know when it is done because it will print a message
//"maximum distance is ..."		
		
//To abandon a run click the red button on the console tab. With the naive 
//approach you won't get very far!
		
		calculator.setFile(new File(DATA_DIR+dataFile[0]));
		calculator.action();
		logger.info("maximum distance is {}",calculator.maximumDistance);
	}


	
//TODO 2: For now just note that you will come back here later to write an 
//optimized action2().
	
	public void action2() {
		double maxDistance = 0;
		try {
			for (int i = 0; i < dataService.size(); i++) {
				Station station1 = dataService.read(i);
				for (int j = i + 1; j < dataService.size(); j++) {
					Station station2 = dataService.read(j);
					maxDistance = max(
							maxDistance,
							calculateDistance(station1, station2,
									DistanceUnits.KILOMETERS));
				}
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (RecordNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		maximumDistance = maxDistance;
	}



//TODO 3: In the bonus, the method action3() goes here.

	/*
 	* How it works:
 	* Key idea is to minimize thrashing. For the larger data sets when you find
	* the distance say between station 5 and station 5000 you are making the 
 	* dataService scroll from 5 to 5000. This might involve a disk read as the 
 	* stations are probably not both in the cache. The same is true when
 	* processing a large result set in JDBC. Jumping around will force many 
 	* fetches.
 	* New approach: Suppose the cache is of size 100. Extract the latitudes 
 	* and longitudes of stations 1-100 into a List<double[]> called 
 	* stationBatch. You don't need whole stations objects, just the locations.  
 	* Now calculate the distances between stations station 1 and station j where    
 	* station 1 is in stationBatch and station j is obtained by    
 	* dataService.read(j) j = 2, 3, ..., dataService.size().The j's are   
 	* consecutive so dataService will not thrash. For each j, find the distance  
	* between station j and all the stations in stationBatch. This does not  
 	* involve a disk read as the locations are in the List<double[]>.
 	* Then do a consecutive read of stations 101 to 200 putting their latitudes 
 	* and longitudes in stationBatch. Do a consecutive read 
	* j = 102, 103, ..., dataService.size(). For each one find the distances 
 	* between it and all the stations in stationBatch. Keep doing this until all 
	* stations are done.
 	* 
 	* */
	public void action3() {
		double maxDistance = 0;
		List<double[]> stationBatch;
		try{
			for (int i = 0; i < dataService.size(); 
									i += FileBasedDataService.MANAGEABLE_SIZE) {
				stationBatch = new ArrayList<>();
			
				//The size of the data set might not be a multiple of 
				//MANAGEABLE_SIZE.
				int endPoint = min(dataService.size(), i
						+ FileBasedDataService.MANAGEABLE_SIZE);
				//Read the next batch extracting latitudes and longitudes.
				for (int k = i; k < endPoint; k++) {//Note the consecutive read.
					Station station1 = dataService.read(k);
					double latitude1 = Double.parseDouble(station1
							.getLatitudeString());
					double longitude1 = Double.parseDouble(station1
							.getLongitudeString());
					stationBatch.add(new double[] { latitude1, longitude1 });
				}
			
				//Calculate distances from stations in stationBatch to stations
				//with index greater than beginning point of batch. i is where 
				//stationBatch starts.
				//Distances to stations with index <= beginning of stationBatch 
				//have already been done.
				for (int j = i + 1; j < dataService.size(); j++) {
					Station station2 = dataService.read(j);//Index greater than 
														//where batch starts.
					double latitude2 = Double.parseDouble(station2
							.getLatitudeString());
					double longitude2 = Double.parseDouble(station2
							.getLongitudeString());
					for (int batchCursor = 0; 
							batchCursor < stationBatch.size(); 
								batchCursor++) {//Stations in batch
						double latitude1 = stationBatch.get(batchCursor)[0];
						double longitude1 = stationBatch.get(batchCursor)[1];
						maxDistance = max(
								maxDistance,
								calculateDistance(latitude1, longitude1,
										latitude2, longitude2));
					}
				}
				maximumDistance =  sqrt(maxDistance)
											*DistanceUnits.KILOMETERS.factor;
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (RecordNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}




//TODO 4: In the bonus, the method action4() goes here.

/**
 * Key idea: The Stations are massive objects but we only need the latitudes 
 * and longitudes. The large data sets have tens of thousands of stations which  
 * won't fit in memory but tens of thousands of doubles are trivial. Just read 
 * all of the stations in sequence storing the latitudes and longitudes and
 * work with them.
 */
	public void action4() {
		double maxDistance = 0;
		double[][] stationLocations = new double[dataService.size()][];
		// Extract locations from the Stations.
		try {
			for (int i = 0; i < dataService.size(); i++) {
				Station station = dataService.read(i);//Stations read only once
					//and in sequence.
				double latitude = 
						Double.parseDouble(station.getLatitudeString());
				double longitude = 
					Double.parseDouble(station.getLongitudeString());
				stationLocations[i] = new double[] { latitude, longitude }; 
					//double[] of length 2 is much smaller than a whole Station.
			}
		} catch (RecordNotFoundException | IOException e) {
			e.printStackTrace();
		}

		for (int i = 0; i < dataService.size(); i++) {
			for (int j = i + 1; j < dataService.size(); j++) {
				maxDistance = max(
						maxDistance,
						calculateDistance(stationLocations[i],
								stationLocations[j]));
			}
		}
		maximumDistance = sqrt(maxDistance)*DistanceUnits.KILOMETERS.factor;
	}


	/**
	 * Helper method to calculate the euclidean distance between two stations.
	 * Assumes a flat earth!
	 * 
	 * @param station1
	 *            , the first station.
	 * @param station2
	 *            , the second station.
	 * @param units
	 *            , units in which distance is to be returned
	 * @return a double, the distance between the two stations.
	 */
	private double calculateDistance(Station station1, Station station2,
			DistanceUnits units) {
		double latitude1 = Double.parseDouble(station1.getLatitudeString());
		double longitude1 = Double.parseDouble(station1.getLongitudeString());
		double latitude2 = Double.parseDouble(station2.getLatitudeString());
		double longitude2 = Double.parseDouble(station2.getLongitudeString());
		double longitudeDifference = abs(longitude1 - longitude2);
		double lattitudeDifference = abs(latitude1 - latitude2);
		// In case they crossed the Greenwich meridian.
		lattitudeDifference = min(lattitudeDifference,
				360 - lattitudeDifference);
		//This could be improved. We are running the expensive sqrt() method. 
		//It would be faster to compare the squares of the distances and only 
		//sqrt() the final answer.
		double distance = sqrt((lattitudeDifference)
				* (lattitudeDifference) + (longitudeDifference)
				* (longitudeDifference));
		//This too can be improved to avoid the unnecessary multiplication below  
		//by units.factor. We really only need to do it to the final answer. The  
		//stations that are max distance apart don't change because of  
		//the units.
		return distance * units.factor;
	}
	
	/**
	 * Represents units of distance.
	 * 
	 * @author crs516 development team
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
	
	
	/**
	 * Calculates the distance between two stations given their respective
	 * longitudes and latitudes
	 * 
	 * @param latitude1
	 *            , a double, the latitude of the first station.
	 * @param longitude1
	 *            , a double, the longitude of the first station.
	 * @param latitude2
	 *            , a double, the latitude of the second station.
	 * @param longitude2
	 *            , a double, the longitude of the second station.
	 * @param units
	 *            , the units distance is to be reported in.
	 * @return
	 */
	private double calculateDistance(double latitude1, double longitude1,
			double latitude2, double longitude2) {
		double longitudeDifference = abs(longitude1 - longitude2);
		double lattitudeDifference = abs(latitude1 - latitude2);
		// In case they crossed the Greenwich meridian.
		lattitudeDifference = min(lattitudeDifference,
				360 - lattitudeDifference);
		double squareDistance =
				(lattitudeDifference)
				* (lattitudeDifference) + (longitudeDifference)
				* (longitudeDifference);
		return squareDistance;
	}
	
	private double calculateDistance(double[] location1, double[] location2) {
		double lattitudeDifference = abs(location1[0] - location2[0]);
		double longitudeDifference = abs(location1[1] - location2[1]);
		// In case they crossed the Greenwich meridian.
		lattitudeDifference = min(lattitudeDifference,
				360 - lattitudeDifference);
		double squareDistance = (lattitudeDifference)
				* (lattitudeDifference) + (longitudeDifference)
				* (longitudeDifference);
		return squareDistance;
	}

}
