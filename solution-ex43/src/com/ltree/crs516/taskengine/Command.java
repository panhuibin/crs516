package com.ltree.crs516.taskengine;

import java.io.Serializable;

public interface Command extends Runnable, Serializable{

	void setReceiver(Receiver receiver);

}
