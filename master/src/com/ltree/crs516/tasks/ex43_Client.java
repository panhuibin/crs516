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

^//TODO 1,2,3: Create an instance of AnnotationCommandImpl and an instance of
^//AnnotationReceiverImpl and set the set the AnnotationReceiverImpl 
^//instance on the AnnotationCommandImpl instance. Find the task engine using the 
^//TaskEngineLocator and submit the annotationCommandImpl.
		
			
#//TODO 1: Create an instance of AnnotationCommandImpl and an instance of
#//AnnotationReceiverImpl.
#			
@			Command annotationCommandImpl = new AnnotationCommandImpl();
@			Receiver annotationReceiverImpl = new AnnotationReceiverImpl();
@//In bonus uncomment the next two lines
@//			Command namingPatterhCommandImpl = new NamingPatternCommandImpl(); //Edit this line.
@//			Receiver namingPatternReceiverImpl = new NamingPatternReceiverImpl(); //Edit this line.

&			Command annotationCommandImpl = null; //Edit this line.
&			Receiver annotationReceiverImpl = null; //Edit this line.
%TODO 1:<br/>Command annotationCommandImpl = new AnnotationCommandImpl();<br/>Receiver annotationReceiverImpl = new AnnotationReceiverImpl();<br/>
#			
#//TODO 2: Set the AnnotationReceiverImpl instance on the AnnotationCommandImpl
#//instance.
#
@			annotationCommandImpl.setReceiver(annotationReceiverImpl);
@//In bonus uncomment the next line
@//			namingPatterhCommandImpl.setReceiver(namingPatternReceiverImpl);

&
%TODO 2:<br/>			annotationCommandImpl.setReceiver(annotationReceiverImpl);
#			logger.info("Annotation based commandImpl created ");
#			
#			// Find the engine in the registry.
#			TaskEngineLocator locator = TaskEngineLocator.INSTANCE;
#			TaskEngine engine = locator.getTaskEngine();
#				
#//TODO 3: Submit the annotationCommandImpl to the engine. You will need a  
#//try/catch block.
@			try {
@				engine.submitTask(annotationCommandImpl);
@//In bonus uncomment the next line
@//				engine.submitTask(namingPatterhCommandImpl);
@			} catch (RemoteException e) {
@				logger.error("Failed to communicate with the engine");
@			}

%TODO 3:<br/>try {<br/>&#160;&#160;engine.submitTask(annotationCommandImpl);<br/>} catch (RemoteException e) {<br/>&#160;&#160;logger.error("Failed to communicate with the engine");<br/>}<br/>

	}

}
