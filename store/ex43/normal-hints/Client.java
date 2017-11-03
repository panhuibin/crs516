package com.ltree.crs516.tasks;

import java.rmi.RemoteException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ltree.crs516.taskengine.Command;
import com.ltree.crs516.taskengine.Receiver;
import com.ltree.crs516.taskengine.TaskEngine;
import com.ltree.crs516.tasks.NamingPatternCommandImpl;
import com.ltree.crs516.tasks.NamingPatternReceiverImpl;

/**
 * Creates and submits CommandImpl to the task engine.
 * 
 * @author crs516 development team
 * 
 */
public final class Client {

	private final Logger logger = LoggerFactory.getLogger(Client.class);

	/**
	 * Looks up task engine, instantiates CommandImpl and submits it to task
	 * engine.
	 */
	public void prepareAndSubmitCommand() {

		
			
//TODO 1: Create an instance of AnnotationCommandImpl and an instance of
//AnnotationReceiverImpl.
			

			Command annotationCommandImpl = null; //Edit this line.
			Receiver annotationReceiverImpl = null; //Edit this line.
			
//TODO 2: Set the AnnotationReceiverImpl instance on the AnnotationCommandImpl
//instance.



			logger.info("Annotation based commandImpl created ");
			
			// Find the engine in the registry.
			TaskEngineLocator locator = TaskEngineLocator.INSTANCE;
			TaskEngine engine = locator.getTaskEngine();
				
//TODO 3: Submit the annotationCommandImpl to the engine. You will need a  
//try/catch block.


	}

}
