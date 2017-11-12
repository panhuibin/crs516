package com.ltree.crs516.tasks;

import com.ltree.crs516.taskengine.TaskEngine;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public enum TaskEngineLocator {
    INSTANCE;

    public TaskEngine getTaskEngine() throws RemoteException, NotBoundException {
        //{{Marker 1

        // Find the engine in the registry.
        Registry registry;
        registry = LocateRegistry.getRegistry(TaskEngine.hostName);

        //}}End marker 1
        return (TaskEngine) registry.lookup(TaskEngine.bindName);
    }
}
