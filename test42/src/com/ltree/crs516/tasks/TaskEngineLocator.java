package com.ltree.crs516.tasks;

import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import com.ltree.crs516.taskengine.TaskEngine;

public enum TaskEngineLocator {
	INSTANCE;

	public TaskEngine getTaskEngine() throws RemoteException,
			NotBoundException, AccessException {
		Registry registry;
		registry = LocateRegistry.getRegistry(TaskEngine.hostName);
		TaskEngine engine = (TaskEngine) registry.lookup(TaskEngine.bindName);
		return engine;
	}
}

