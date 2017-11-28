package com.ltree.crs516.tasks;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ltree.crs516.taskengine.Command;
import com.ltree.crs516.taskengine.Receiver;

 @SuppressWarnings("serial")
public final class NamingPatternCommandImpl implements Command {

	private final Logger logger = LoggerFactory.getLogger(NamingPatternReceiverImpl.class);
	private Receiver receiver;

	/**
	 * Will be called by task engine
	 */
	@Override
	public void run() {
//TODO 1: Get the Class object for the receiver.		
		Class<?> clazz = receiver.getClass();
		logger.info("receiverClass="+clazz.getName());

//TODO 2: Get an array of Method objects representing the methods of 
//the receiver.		
		Method[] methods = clazz.getMethods();
		logger.info("methods="+ Arrays.toString(methods));
//TODO 3: Loop through the Method objects and for the ones that represent 
//methods with names that end with "action" or "Action" invoke them on the 
//receiver. Since they take no arguments just input new Object[0] for the args.
//You will have to catch a few exceptions. 
//Just highlight the loop, then right-click and select Surround With | Try/catch Block.

		for (Method method : methods) {
			if(method.getName().endsWith("action")
					|| method.getName().endsWith("Action")){
				try {
					method.invoke(receiver, new Object[0]);
				} catch (IllegalAccessException | IllegalArgumentException
						| InvocationTargetException e) {
					logger.error("Failed to execue {}", method.getName(),e);
				}
			}
		}

	}//End of run() method.

	public void setReceiver(Receiver receiver) {
		this.receiver = receiver;
	}

}
