package com.ltree.crs516.tasks;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

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
@		Class<?> clazz= receiver.getClass();
$
$
%TODO 1: Class<?> clazz= receiver.getClass();
//TODO 2: Get an array of Method objects representing the methods of 
//the receiver.		
@		Method[] methods = clazz.getMethods();
$
$
% TODO 2: Method[] methods = clazz.getMethods();
//TODO 3: Loop through the Method objects and for the ones that represent 
//methods with names that end with "action" or "Action" invoke them on the 
//receiver. Since they take no arguments just input new Object[0] for the args.
//You will have to catch a few exceptions. 
#//Just highlight the loop, then right-click and select Surround With | Try/catch Block.

@		for (Method method : methods) {
@			if(method.getName().endsWith("action") 
@					|| method.getName().endsWith("Action")){
@				try {
@					method.invoke(receiver, new Object[0]);
@				} catch (IllegalAccessException | IllegalArgumentException
@						| InvocationTargetException e) {
@					logger.error("Failed to execue {}", method.getName(),e);
@				}
@			}
@		}
$
$
$
$
$
$
$
$
$
$
$
$
% TODO 3:<br/>for (Method method : methods) {<br/>&#160;&#160;if(method.getName().endsWith("action")<br/>&#160;&#160;|| method.getName().endsWith("Action")){<br/>&#160;&#160;&#160;&#160;try {<br/>&#160;&#160;&#160;&#160;&#160;&#160;method.invoke(receiver, new Object[0]);<br/>&#160;&#160;&#160;&#160;} catch (IllegalAccessException | IllegalArgumentException<br/>&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;| InvocationTargetException e) {<br/>&#160;&#160;&#160;&#160;&#160;&#160;logger.error("Failed to execue {}", method.getName(),e);<br/>&#160;&#160;&#160;&#160;}<br/>&#160;&#160;}<br/>}
	}//End of run() method.

	public void setReceiver(Receiver receiver) {
		this.receiver = receiver;
	}

}
