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

public class DistanceCalculator {

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

	//The files are in ascending order based on how long it takes to process 
	//them. Some might have fewer stations with large amounts of data and others 
	//might have very many stations with small amounts of data.
	public static String[] dataFile = new String[] {
		"CTDO7805.gz",
		"OSDO7101.gz",		
		"OSDO5711.gz",
		"CTDO1009.gz",
		"GLDS7503.gz",
		"CTDO7113.gz",
		"XBTO5101.gz",
		"SURF_ALL.gz" 
	};
	
	private static final Logger logger 
							= LoggerFactory.getLogger(DistanceCalculator.class);
	private DataService dataService;
	private File file;
	private double maximumDistance;

	public static void main(String[] args) {
		DistanceCalculator calculator = new DistanceCalculator();
		calculator.setFile(new File(DATA_DIR+DistanceCalculator.dataFile[7]));
		calculator.action();
		logger.info("maximum distance is {}",calculator.maximumDistance);
	}

	/**
	 * Finds the maximum of the distances between stations in the dataService.
	 */
	public void action() {
		double maxDistance = 0;
		// Loop through all the stations.
		for (Station station1 : dataService) {
			// Loop through all the stations.
			for (Station station2 : dataService) {
				maxDistance = max(
						maxDistance,
						calculateDistance(station1, station2,
								DistanceUnits.KILOMETERS));
			}
		}
		maximumDistance = maxDistance;
	}

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

	public void action3() {
		double maxDistance = 0;
		List<double[]> stationBatch;
		try {
			for (int i = 0; i < dataService.size(); i 
									+= FileBasedDataService.MANAGEABLE_SIZE) {
				stationBatch = new ArrayList<>();
				int endPoint = min(dataService.size(), i
						+ FileBasedDataService.MANAGEABLE_SIZE);
				//Read the next batch.
				for (int k = i; k < endPoint; k++) {
					Station station1 = dataService.read(k);
					double latitude1 = Double.parseDouble(station1
							.getLatitudeString());
					double longitude1 = Double.parseDouble(station1
							.getLongitudeString());
					stationBatch.add(new double[] { latitude1, longitude1 });
				}
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
				maximumDistance = Math.sqrt(maxDistance)
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

	public void action4() {
		double maxDistance = 0;
		double[][] stationLocations = new double[dataService.size()][];
		// Extract latitude and longitudes from the stations.
		int numberOfStations = dataService.size();
		try {
			for (int i = 0; i < numberOfStations; i++) {
				Station station = dataService.read(i);//Stations read only 
					//once and in sequence.
				double latitude = Double.parseDouble(station
						.getLatitudeString());
				double longitude = Double.parseDouble(station
						.getLongitudeString());
				stationLocations[i] = new double[] { latitude, longitude };
					//double[] of length 2 is much smaller than a whole Station.
			}
			dataService.close(); //Won't need it amy more.
		} catch (RecordNotFoundException | IOException e) {
			e.printStackTrace();
		}
		
		for (int i = 0; i < numberOfStations; i++) {
			for (int j = i + 1; j < numberOfStations; j++) {
				maxDistance = max(
						maxDistance,
						calculateDistance(stationLocations[i],
								stationLocations[j]));
			}
		}
		maximumDistance = Math.sqrt(maxDistance)
											*DistanceUnits.KILOMETERS.factor;
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
		double distance = sqrt(
				lattitudeDifference	* lattitudeDifference 
				+ longitudeDifference * longitudeDifference
		);
		return distance * units.factor;
	}

	public void close() {
		try {
			dataService.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Convenience method to get the data service.
	 * 
	 * @return
	 */
	private DataService createDataService() {
		logger.info("file is {}", file);
		DataService dataService = new FileBasedDataService(file);
		while (dataService.isLoading()) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		logger.info("Data service has size {}", dataService.size());
		return dataService;
	}

	public double getMaximumDistance() {
		return maximumDistance;
	}

	public void setFile(File file) {
		this.file = file;
		dataService = createDataService();
	}
}
