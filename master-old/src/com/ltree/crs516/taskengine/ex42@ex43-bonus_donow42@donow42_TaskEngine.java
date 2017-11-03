package com.ltree.crs516.taskengine;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface TaskEngine extends Remote{

	static final String bindName = "TaskEngine";
	static final String hostName = "localhost";
	
	abstract void submitTask(Command task) throws RemoteException;

	abstract void stop()throws RemoteException;

}
