package com.ltree.crs516.tasks;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ltree.crs516.taskengine.TaskEngine;
import com.ltree.crs516.tasks.CommandImpl;

/**
 * Creates and submits CommandImpl to the task engine.
 * @author crs516 development team
 *
 */
public final class Client {

	private final Logger logger = LoggerFactory.getLogger(Client.class);

	/**
	 * Looks up task engine, instantiates CommandImpl and submits it 
	 * to task engine.
	 */
	public void prepareAndSubmitCommand() {
		Registry registry;
		try {
			// Find the engine in the registry.
			registry = LocateRegistry.getRegistry(TaskEngine.hostName);
			TaskEngine engine = (TaskEngine) registry.lookup(TaskEngine.bindName);
			logger.info("engine located ");
			
			// Create the CommandImpl.
			
//TODO 1: Create an instance of CommandImpl. 
//Since this command is relatively simple we will not bother to use a separate Receiver.
			
			CommandImpl task = new CommandImpl();

			logger.info("commandImpl created ");
			
			// Submit the CommandImpl.
			
//TODO 2: There is a method called submitTask on the TaskEngine interface to 
//submit tasks. Use it to submit the CommandImpl object you just created to the 
//TaskEngine called engine. 
			engine.submitTask(task);

			logger.info("command submitted to task engine");			
		} catch (RemoteException e) {
			logger.error("Failed to get engine", e);
		} catch (NotBoundException e) {
			logger.error("Nothing bound under the name {}", TaskEngine.bindName, e);
		}
	}
}
