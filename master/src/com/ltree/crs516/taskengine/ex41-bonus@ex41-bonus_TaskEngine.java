package com.ltree.crs516.taskengine;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface TaskEngine extends Remote{

	static final String bindName = "TaskEngine";
	static final String hostName = "localhost";
	
	void submitTask(Command task) throws RemoteException;
	void stop() throws RemoteException;

}
