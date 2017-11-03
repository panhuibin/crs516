package com.ltree.crs516.taskengine;

import static com.ltree.crs516.data.DataConstants.DATA_DIR;
import static java.lang.Math.abs;
import static java.lang.Math.max;
import static java.lang.Math.min;
import static java.lang.Math.sqrt;

import java.io.File;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ltree.crs516.data.DataService;
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
	
/*	
	public void action2() {
		double maxDistance = 0;
		try {
			for (int i = 0; i < dataService.size(); i++) {
			//Use the read() method of dataService to get the ith station.


				for (int j = i + 1; j < dataService.size(); j++) {
			//Use the read() method of dataService to get the jth station.




			//Adjust maxDistance to be the maximum of maxDistance and the 
			//distance between the stations.



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
*/



//TODO 3: In the bonus, the method action3() goes here.





//TODO 4: In the bonus, the method action4() goes here.



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
	@SuppressWarnings("unused")//Used in the bonus.
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
	
	@SuppressWarnings("unused")//Used in bonus.
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
