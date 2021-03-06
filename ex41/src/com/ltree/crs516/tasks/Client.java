package com.ltree.crs516.tasks;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import com.ltree.crs516.taskengine.TaskEngineImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



import com.ltree.crs516.taskengine.TaskEngine;

public class Client {


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
			logger.info("engine info:"+engine.getClass());

//TODO 1: Create an instance of CommandImpl. 
//Since this command is relatively simple we will not bother to use a separate Receiver.
			CommandImpl command = new CommandImpl();
			
			


//TODO 2: Submit the CommandImpl object you just created to the 
//TaskEngine called engine. 
			engine.submitTask(command);


		} catch (RemoteException e) {
			logger.error("Failed to get engine", e);
		} catch (NotBoundException e) {
			logger.error("Nothing bound under the name {}", TaskEngine.bindName, e);
		}
	}
}
