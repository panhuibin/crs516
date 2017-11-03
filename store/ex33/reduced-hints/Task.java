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
//TODO 1,2: The method createDataService() below will get you a DataService. 
//Loop through all the stations of the DataService printing the 
//number of bytes to the console.






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
