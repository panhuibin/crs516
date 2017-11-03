package com.ltree.crs516.taskengine;

import java.io.File;

import com.ltree.crs516.data.DataConstants;
import com.ltree.crs516.data.DataService;
import com.ltree.crs516.data.FileBasedDataService;

public enum DataServiceLocator {
	INSTANCE;
	
	private File file = new File(DataConstants.DATA_DIR+"OSDO5711.gz");

	public DataService getDataService() {
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
