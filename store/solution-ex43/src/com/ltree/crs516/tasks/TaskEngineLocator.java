package com.ltree.crs516.tasks;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ltree.crs516.taskengine.TaskEngine;

public enum TaskEngineLocator {
	INSTANCE;

	private final static Logger logger = LoggerFactory
			.getLogger(TaskEngineLocator.class);

	private Registry registry;
	
	public TaskEngine getTaskEngine() {
		TaskEngine engine = null;	
		try {
			if (registry == null) {
				registry = LocateRegistry.getRegistry("localhost");
			}
			engine = (TaskEngine) registry.lookup(TaskEngine.bindName);
		} catch (RemoteException e) {
			logger.error("Failed to get engine", e);
		} catch (NotBoundException e) {
			logger.error("Failed to get engine with bind name {}",
					TaskEngine.bindName, e);
		}
		logger.info("engine located ");
		return engine;
	}
	
}

