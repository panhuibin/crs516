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

%<br/>public enum TaskEngineLocator {<br/>&#160;&#160;INSTANCE;<br/><br/>&#160;&#160;&#160;&#160;public TaskEngine getTaskEngine() throws RemoteException,<br/>&#160;&#160;&#160;&#160;&#160;&#160;NotBoundException, AccessException {<br/>&#160;&#160;&#160;&#160;Registry registry;<br/>&#160;&#160;&#160;&#160;registry = LocateRegistry.getRegistry(TaskEngine.hostName);<br/>&#160;&#160;&#160;&#160;TaskEngine engine = (TaskEngine) registry.lookup(TaskEngine.bindName);<br/>&#160;&#160;&#160;&#160;return engine;<br/>&#160;&#160;}<br/>}<br/><br/>
