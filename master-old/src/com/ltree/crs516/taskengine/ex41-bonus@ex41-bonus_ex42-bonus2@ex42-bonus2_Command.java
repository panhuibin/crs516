package com.ltree.crs516.taskengine;

import java.io.File;
import java.io.Serializable;

import com.ltree.crs516.data.DataService;
import com.ltree.crs516.data.FileBasedDataService;

public interface Command extends Runnable, Serializable{

	/**
	 * Convenience method to get the data service.
	 * 
	 * @return
	 */
	default DataService createDataService(File file) {
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
