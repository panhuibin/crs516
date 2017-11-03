package com.ltree.crs516.taskengine;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ltree.crs516.data.DataService;
import com.ltree.crs516.data.FileBasedDataService;

final class DataServiceFactory {
	
	private static final Logger logger = LoggerFactory.getLogger(DistanceCalculator.class);

	static DataService createFileBasedDataService(File file) {
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

}
