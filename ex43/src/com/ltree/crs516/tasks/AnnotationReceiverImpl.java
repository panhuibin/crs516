package com.ltree.crs516.tasks;

import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ltree.crs516.taskengine.Receiver;

 @SuppressWarnings("serial")
public final class AnnotationReceiverImpl implements Serializable, Receiver {

	private final Logger logger = LoggerFactory.getLogger(AnnotationReceiverImpl.class);

//TODO 1: Annotate the three methods below with the @Action annotation
// asking for whatever order you want.
	
	
	public void method1() {
		logger.info("method 1 called ");
		//Here you would put the logic to be executed.
	}

	public void method2() {
		logger.info("method 2 called ");
		//Here you would put the logic to be executed.
	}

	public void method3() {
		logger.info("method 3 called ");
		//Here you would put the logic to be executed.
	}

}
