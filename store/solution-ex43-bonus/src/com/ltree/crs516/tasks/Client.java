package com.ltree.crs516.tasks;

import java.rmi.RemoteException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ltree.crs516.taskengine.Command;
import com.ltree.crs516.taskengine.Receiver;
import com.ltree.crs516.taskengine.TaskEngine;

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
			Command annotationCommandImpl = new AnnotationCommandImpl();
			Receiver annotationReceiverImpl = new AnnotationReceiverImpl();
			annotationCommandImpl.setReceiver(annotationReceiverImpl);
			logger.info("Annotation based commandImpl created ");
			TaskEngineLocator locator = TaskEngineLocator.INSTANCE;
			TaskEngine engine = locator.getTaskEngine();
			try {
				engine.submitTask(annotationCommandImpl);
			} catch (RemoteException e) {
				logger.error("Failed to communicate with the engine");
			}
	}

}
