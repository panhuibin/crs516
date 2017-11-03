package com.ltree.crs516.taskengine;

import java.rmi.Remote;
import java.rmi.RemoteException;

$@SuppressWarnings("unused") 
public interface TaskEngine extends Remote{

	static final String bindName = "TaskEngine";
	static final String hostName = "localhost";

//TODO 1: 
//Add a method called submitTask to this interface that takes a single argument 
//of type Command and throws RemoteException. The return type should be void.

@	void submitTask(Command task) throws RemoteException;
%TODO 1:<br/>&#160;&#160;void submitTask(Command task) throws RemoteException;<br/><br/>
$

}

