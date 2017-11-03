package com.ltree.crs516.tasks;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ltree.crs516.taskengine.Command;
import com.ltree.crs516.taskengine.Receiver;

 @SuppressWarnings("serial")
public final class AnnotationCommandImpl implements Command {

	private final Logger logger = LoggerFactory.getLogger(AnnotationReceiverImpl.class);
	private Receiver receiver;
	private final List<SequenceAndMethod> seqAndMeths = new ArrayList<>();

	/**
	 * Invokes methods of receiver that are annotated with @Action in the order 
	 * dictated by the value attribute of the annotations.
	 */
	@Override
	public void run() {
		Class<?> clazz = receiver.getClass();
		Method[] methods = clazz.getMethods();
		for (Method method : methods) {
			Action actionAnnotation = method.getAnnotation(Action.class);
			logger.debug("{}", actionAnnotation);
			if (actionAnnotation != null) {
				int sequenceNum = actionAnnotation.value();
				seqAndMeths.add(new SequenceAndMethod(sequenceNum, method));
			}
		}
		Collections.sort(seqAndMeths);
		for (SequenceAndMethod seqAndMeth : seqAndMeths) {
			Method currentMethod = seqAndMeth.getMethod();
			try {
				currentMethod.invoke(receiver, new Object[0]);
			} catch (IllegalAccessException | IllegalArgumentException
					| InvocationTargetException e) {
				logger.error("Failed to execute {}", currentMethod.getName(), e);
			}
		}
	}

	public void setReceiver(final Receiver receiver) {
		this.receiver = receiver;
	}

	/**
	 * The annotation @Action has a value attribute which indicates
	 * the order in which the methods should be executed.
	 * This class encapsulates a Method object and its sequence
	 * number. It implements Comparable to allow easy sorting by just calling
	 * Collections.sort(seqAndMethods).
	 * @author crs 516 development team
	 *
	 */
	final class SequenceAndMethod implements Comparable<SequenceAndMethod> {
		private final Integer sequenceNumber;
		private final Method method;

		public SequenceAndMethod(int sequenceNum, Method method) {
			super();
			this.sequenceNumber = sequenceNum;
			this.method = method;
		}

		/**
		 * Specified by the Comparable interface.
		 */
		@Override
		public int compareTo(final SequenceAndMethod other) {
			//The sequence number determines order.
			return this.sequenceNumber.compareTo(other.sequenceNumber);
		}

		public Method getMethod() {
			return method;
		}
	}

}
