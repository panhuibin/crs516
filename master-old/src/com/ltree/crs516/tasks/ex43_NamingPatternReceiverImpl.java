package com.ltree.crs516.tasks;

import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ltree.crs516.taskengine.Receiver;


 @SuppressWarnings("serial")
public final class NamingPatternReceiverImpl implements Serializable, Receiver {
	 
	private final Logger logger = LoggerFactory.getLogger(NamingPatternReceiverImpl.class);

	/* As long as method ends with 'action' or 'Action' it will be called 
	 * from execute() of CommandImpl.
	 * Order of invocation is not guaranteed.
	 */
	public void bytesInProfileAction() {
		logger.info("bytesInProfileAction called ");
	}

//TODO 1: Create a public void method whose name ends with 'Action' and which 
//will print out a message that it was called.	
@	public void locationAction() {
@		logger.info("locationAction called ");
@	}
$
$
$
$


% TODO 1: <br/>public void locationAction() {<br/>&#160;&#160;logger.info("locationAction called");<br/>}

//TODO 2: Create another public void method whose name ends with 'Action' and which will 
//print out  out a message that it was called.	
@	public void yearAction() {
@		logger.info("yearAction called ");
@	}
$
$
$
$
$
% TODO 2: <br/>public void yearAction() {<br/>&#160;&#160;logger.info("yearAction called");<br/>}
	
}
