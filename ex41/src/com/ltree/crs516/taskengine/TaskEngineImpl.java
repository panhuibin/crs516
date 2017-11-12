package com.ltree.crs516.taskengine;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The invoker in a command design pattern.
 * 
 * @author crs516 development team.
 * 
 */
 @SuppressWarnings("serial")
//TODO 1:
//Make this class implement TaskEngine 
 
public final class TaskEngineImpl extends UnicastRemoteObject implements TaskEngine{

	private final Logger logger = LoggerFactory.getLogger(getClass());



	/**
	 * The code in the constructor is for communication.
	 */
	public TaskEngineImpl() throws RemoteException {
		try {
			// The next line binds a proxy in rmiregistry for client to look up.
			//You will study the Proxy pattern later in the course.
			Naming.rebind(bindName, this);
			logger.info("Task engine is registered in rmiRegistry");
		} catch (MalformedURLException e) {
			logger.error("Bind name is malformed. ", e);
		}
	}



//TODO 2:Add the submitTask(Command command) method and in it call the 
//run() method of the Command object that is input to the method

	@Override
	public void submitTask(Command command) {
		logger.info("submmit task for command is called");
		command.run();
	}
}

