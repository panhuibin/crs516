package com.ltree.crs516.tasks;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

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
			CompositeCommandImpl cci = new CompositeCommandImpl();

//TODO 1: Add three lambda expressions to the CompositeCommandImpe cci that will print messages to the console
// like "Running command 1", "Running command 2", "Running command 3" 

@			cci.addCommand(()->System.out.println("Running command 1"));
@			cci.addCommand(()->System.out.println("Running command 2"));
@			cci.addCommand(()->System.out.println("Running command 3"));			
$
$
$
%TODO 1:<br />cci.addCommand(()->System.out.println("Running command 1"));<br />cci.addCommand(()->System.out.println("Running command 2"));<br />cci.addCommand(()->System.out.println("Running command 3"));<br /><br />			

			logger.info("commandImpl created ");		

//TODO 2: Submit the CompositeCommandImpl to the TaskEngine engine.
			
@			engine.submitTask(cci);
%TODO 2:<br/>engine.submitTask(cci);<br/><br/>

			logger.info("command submitted to task engine");			
		} catch (RemoteException e) {
			logger.error("Failed to get engine", e);
		} catch (NotBoundException e) {
			logger.error("Nothing bound under the name {}", TaskEngine.bindName, e);
		}
	}
}
