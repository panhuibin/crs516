package com.ltree.crs516.taskengine;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ltree.crs516.util.WODThreadPools;

/**
 * The invoker in a command design pattern.
 * 
 * @author crs516 development team
 * 
 */
 @SuppressWarnings("serial")
public final class TaskEngineImpl extends UnicastRemoteObject implements TaskEngine {

	private final Logger logger = LoggerFactory.getLogger(getClass());
	private final ExecutorService worker = WODThreadPools.getDaemonThreadService(); 

	/**
	 * Submit the command object to the worker thread pool for execution.
	 */
	@Override
	public synchronized void submitTask(Command command) {
		worker.submit(command);
	}
	
	public TaskEngineImpl() throws RemoteException {
	//This code is specific to RMI. In your code you would do what it takes to 
	//publish your distributed object.
		try {
			//Bind a proxy in rmiregistry for client to look up.
			Naming.rebind(bindName, this);
			logger.info("Task engine is registered in rmiRegistry");
		} catch (MalformedURLException e) {
			logger.error("Bind name is malformed. ", e);
		}
	}

	/*
	 * Shuts down the worker thread pool gracefully. 
	 */
	public void stop() throws RemoteException {
		//Can't run this in the worker thread pool since we are 
		//shutting it down! Needs to run in its own thread.
		new Thread(new Runnable(){

			@Override
			public void run() {
				try {
					worker.shutdown(); // Don't accept any new jobs.
					if(!worker.isTerminated()){
						if(worker.awaitTermination(15, TimeUnit.SECONDS)){
							logger.info("Cancelling active jobs.");
							worker.shutdownNow(); //Try to cancel active jobs
						}
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				//Really shut down the JVM!
				logger.info("Task engine says bye!.");
				System.exit(0);
			}
		}).start();
	}
	
}
