package com.ltree.crs516.taskengine;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The invoker in a command design pattern.
 * 
 * @author crs516 development team.
 * 
 */
 @SuppressWarnings("serial")
public final class TaskEngineImpl extends UnicastRemoteObject implements
		TaskEngine {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * Submit the command object to the workerPool thread pool for execution.
	 */
	@Override
	public synchronized void submitTask(Command command) {

		// TODO 1: call the run method
		command.run();
	}

	// ==== The rest of the code is communication. 

	public TaskEngineImpl() throws RemoteException {

		try {
			// Bind a proxy in rmiregistry for client to look up.
			Naming.rebind(bindName, this);
			logger.info("Task engine is registered in rmiRegistry");
		} catch (MalformedURLException e) {
			logger.error("Bind name is malformed. ", e);
		}
	}

	/*
	 * Shuts down the task engine gracefully.
	 */
	public void stop() throws RemoteException {
		logger.info("Task engine says bye!.");
		System.exit(0);
	}
}
