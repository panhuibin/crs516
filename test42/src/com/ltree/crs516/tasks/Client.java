package com.ltree.crs516.tasks;

import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ltree.crs516.data.DataService;
import com.ltree.crs516.domain.Station;
import com.ltree.crs516.taskengine.DataServiceLocator;
import com.ltree.crs516.taskengine.TaskEngine;

public class Client {

//	private TaskEngineLocator locator = TaskEngineLocator.INSTANCE;
	private TaskEngineFactory taskEngineFactory;
	private CompositeCommandFactory compositeFommandFactory;

	public void setCompositeCommandFactory(
			CompositeCommandFactory compositeFommandFactory) {
		this.compositeFommandFactory = compositeFommandFactory;
	}

	public void setTaskEngineFactory(TaskEngineFactory taskEngineFactory) {
		this.taskEngineFactory = taskEngineFactory;
	}

	private final Logger logger = LoggerFactory.getLogger(Client.class);
	
	/**
	 * Looks up task engine, instantiates CommandImpl and submits it 
	 * to task engine.
	 */
	public void prepareAndSubmitCommand() {
		try {
//			TaskEngine engine = locator.getTaskEngine();
			TaskEngine engine = taskEngineFactory.getEngine();
			
//			CompositeCommandImpl cci = new CompositeCommandImpl();
			CompositeCommandImpl cci = compositeFommandFactory.getCommand();
			cci.addCommand(()->{
				DataService dataService = DataServiceLocator.INSTANCE.getDataService();
				for (Station station : dataService) {
					System.out.println(station.getBytesInProfile());
				}
			});
			cci.addCommand(()->{
				DataService dataService = DataServiceLocator.INSTANCE.getDataService();
				for (Station station : dataService) {
					System.out.println(station.getCountry());
				}
			});
			cci.addCommand(()->{
				DataService dataService = DataServiceLocator.INSTANCE.getDataService();
				for (Station station : dataService) {
					System.out.println(station.getCruiseNumber());
				}
			});
			
			logger.info("commandImpl created and populated");
			engine.submitTask(cci);
			logger.info("command submitted to task engine");			
		} catch (RemoteException e) {
			logger.error("Failed to get engine", e);
		} catch (EngineNotAvailableException e) {
			e.printStackTrace();
		}
	}

	public static class TaskEngineFactory{
		private TaskEngine engine;
		
		public void setEngine(TaskEngine engine) {
			this.engine = engine;
		}

		public TaskEngine getEngine() throws EngineNotAvailableException{
			try {
				if(engine == null){
					TaskEngineLocator locator = TaskEngineLocator.INSTANCE;
					engine = locator.getTaskEngine();
				}
				return engine;
			} catch (Exception e) {
				EngineNotAvailableException ex = new EngineNotAvailableException();
				ex.initCause(e);
				throw ex;
			} 
		}		                           
	}

	public static class CompositeCommandFactory{
		private CompositeCommandImpl command;
		
		public void setCommand(CompositeCommandImpl command) {
			this.command = command;
		}

		public CompositeCommandImpl getCommand() {
			CompositeCommandImpl commandToReturn = command;
				if(commandToReturn == null){
					commandToReturn =  new CompositeCommandImpl();
				}
				command = null;
				return commandToReturn;
		}		                           
	}
	
	@SuppressWarnings("serial")
	public static class EngineNotAvailableException extends Exception{}
}

