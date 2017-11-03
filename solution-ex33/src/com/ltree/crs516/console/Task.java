package com.ltree.crs516.console;

import java.io.File;
import java.io.IOException;

import static com.ltree.crs516.data.DataConstants.DATA_DIR;
import com.ltree.crs516.data.DataService;
import com.ltree.crs516.data.FileBasedDataService;
import com.ltree.crs516.domain.Station;

public final class Task {

	private File file = new File(DATA_DIR + "OSDO5711.gz");

	private void execute() {

//TODO 1: Note: the code below will need to be in a try/catch block
// but we shall first ignore that.		
		
		try(DataService dataService = createDataService()){
		
		//{{Marker 1	
	

			//TODO 2: 
			//Write a foreach loop going through all the stations of 
			//dataService and printing the number of bytes in the profile
			//to the console. 

			for (Station station : dataService) {
				System.out.println(station.getBytesInProfile());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}



	}

	public static void main(String[] args) {
		new Task().execute();
	}

	private DataService createDataService() {
		DataService dataService = new FileBasedDataService(null);
		dataService.load(file);
		while (dataService.isLoading()) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return dataService;
	}
	
}
