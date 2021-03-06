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
 * @author crs516 development team.
 * 
 */
 @SuppressWarnings("serial")
public final class TaskEngineImpl extends UnicastRemoteObject 
														implements TaskEngine {

	private final Logger logger = LoggerFactory.getLogger(getClass());


	private final ExecutorService workerPool 
									= WODThreadPools.getDaemonThreadService(); 

	/**
	 * Submit the command object to the workerPool thread pool for execution.
	 */
	@Override
	public synchronized void submitTask(Command command) {
		
		workerPool.submit(command);
	}
	
//==== The rest of the code is communication and threading code. We shall study 
//threading in a later chapter ====.	

	public TaskEngineImpl() throws RemoteException {

		try {
			//Bind a proxy in rmiregistry for client to look up.
			Naming.rebind(bindName, this);
			logger.info("Task engine is registered in rmiRegistry");
		} catch (MalformedURLException e) {
			logger.error("Bind name is malformed. ", e);
		}
	}

	/*
	 * Shuts down the workerPool thread pool gracefully. We shall study 
	 * this later.
	 */
	public void stop() throws RemoteException {
		//Can't run this in the worker thread pool since we are 
		//shutting it down! It has to run in a separate thread.
		new Thread(new Runnable(){

			@Override
			public void run() {
				try {
					workerPool.shutdown();// Don't accept any new jobs.
					if(!workerPool.isTerminated()){
						if(workerPool.awaitTermination(15, TimeUnit.SECONDS)){
							logger.info("Cancelling active jobs.");
							workerPool.shutdownNow();//Try to cancel active jobs.
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
