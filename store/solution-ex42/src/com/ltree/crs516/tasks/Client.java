package com.ltree.crs516.tasks;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ltree.crs516.taskengine.Command;
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

//TODO 1: Create an instance of CompositeCommandImpl. 
			
			CompositeCommandImpl cci = new CompositeCommandImpl();

//TODO 2: Create an instance of each of CommandImpl1, CommandImpl1,CommandImpl3, and add them to the 
//CompositeCommandImpl
			
			Command c1 = new CommandImpl1();
			Command c2 = new CommandImpl2();
			Command c3 = new CommandImpl3();
			cci.addCommand(c1);
			cci.addCommand(c2);
			cci.addCommand(c3);

			logger.info("commandImpl created ");
			
//TODO 3: Submit the CompositeCommandImpl to the TaskEngine engine.
			
			engine.submitTask(cci);

			logger.info("command submitted to task engine");			
		} catch (RemoteException e) {
			logger.error("Failed to get engine", e);
		} catch (NotBoundException e) {
			logger.error("Nothing bound under the name {}", TaskEngine.bindName, e);
		}
	}
}
