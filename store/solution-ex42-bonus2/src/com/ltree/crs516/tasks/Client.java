package com.ltree.crs516.tasks;

import java.rmi.RemoteException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ltree.crs516.taskengine.Command;
import com.ltree.crs516.taskengine.TaskEngine;

public class Client {

	private TaskEngineLocator locator = TaskEngineLocator.INSTANCE;
	private final Logger logger = LoggerFactory.getLogger(Client.class);

	/**
	 * Looks up task engine, instantiates CommandImpl and submits it 
	 * to task engine.
	 */
	public void prepareAndSubmitCommand() {
		
		try {
			
//{{Marker 1 	
		
		// Find the engine in the registry.
			TaskEngine engine = locator.getTaskEngine();

//}}End marker 1


			CompositeCommandImpl cci = new CompositeCommandImpl();
			Command c1 = new CommandBytesInProfileImpl();
			Command c2 = new CommandCountryImpl();
			Command c3 = new CommandCruiseNumberImpl();
			cci.addCommand(c1);
			cci.addCommand(c2);
			cci.addCommand(c3);
			logger.info("commandImpl created ");
			engine.submitTask(cci);
			logger.info("command submitted to task engine");			
		} catch (RemoteException e) {
			logger.error("Failed to get engine", e);
		}
	}
}
