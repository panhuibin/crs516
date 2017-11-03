package com.ltree.crs516.taskengine;

import java.io.Serializable;

public interface Command extends Runnable, Serializable{
	//Just a marker interface -- no methods. The Runnable interface gives us
	//the run() method while the Serializable interface is so that the 
	//Command object can be serialized and sent to the task engine.
}
