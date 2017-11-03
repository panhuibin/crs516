package com.ltree.crs516.tasks;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ltree.crs516.data.DataService;
import com.ltree.crs516.domain.Station;
import com.ltree.crs516.taskengine.DataServiceLocator;
import com.ltree.crs516.taskengine.TaskEngine;

public class Client {

@	private TaskEngineLocator locator = TaskEngineLocator.INSTANCE;
%<br/>private TaskEngineLocator locator = TaskEngineLocator.INSTANCE;<br/><br/>

	private final Logger logger = LoggerFactory.getLogger(Client.class);

	/**
	 * Looks up task engine, instantiates CommandImpl and submits it 
	 * to task engine.
	 */
	public void prepareAndSubmitCommand() {

		try {
			
//{{Marker 1 	
			
			// Find the engine in the registry.
$			Registry registry;
$			registry = LocateRegistry.getRegistry(TaskEngine.hostName);
$			TaskEngine engine = (TaskEngine) registry.lookup(TaskEngine.bindName);
@			TaskEngine engine = locator.getTaskEngine();
	
//}}End marker 1
%<br/>//{{Marker 1<br/>&#160;&#160;TaskEngine engine = locator.getTaskEngine();<br/>//}}End marker 1<br/><br/>

			logger.info("engine located ");
			CompositeCommandImpl cci = new CompositeCommandImpl();
			
$			CommandImpl1 c1 = new CommandImpl1();
$			CommandImpl2 c2 = new CommandImpl2();
$			CommandImpl3 c3 = new CommandImpl3();
$			cci.addCommand(c1);
$			cci.addCommand(c2);
$			cci.addCommand(c3);

$//You will replace the above command objects with lambda expressions
@			cci.addCommand(()->{
@				DataService dataService = DataServiceLocator.INSTANCE.getDataService();
@				for (Station station : dataService) {
@					System.out.println(station.getBytesInProfile());
@				}
@			});
@			cci.addCommand(()->{
@				DataService dataService = DataServiceLocator.INSTANCE.getDataService();
@				for (Station station : dataService) {
@					System.out.println(station.getCountry());
@				}
@			});
@			cci.addCommand(()->{
@				DataService dataService = DataServiceLocator.INSTANCE.getDataService();
@				for (Station station : dataService) {
@					System.out.println(station.getCruiseNumber());
@				}
@			});
@
$
$
$
$
$
$
$
$
$
$
			
			engine.submitTask(cci);
			logger.info("command submitted to task engine");			
		} catch (RemoteException e) {
			logger.error("Failed to get engine", e);
		} catch (NotBoundException e) {
			logger.error("Nothing bound under the name {}", TaskEngine.bindName, e);
		}
	}
	
}
