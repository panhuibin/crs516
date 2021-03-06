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
	
//TODO 1: Scroll towards the end of this file and find //{{Marker 1 after which
//there is an inner class. Study that class and then return to TODO 2.	

//TODO 2: Instantiate an ArrayList<SequenceAndMethod> that we will use to 
//store	Method objects and their sequence numbers.

&	private final List<SequenceAndMethod> seqAndMeths = null;//Edit this.
^	private final List<SequenceAndMethod> seqAndMeths = null;
@	private final List<SequenceAndMethod> seqAndMeths = new ArrayList<>();
% TODO 2:<br/>private final List<SequenceAndMethod> seqAndMeths = new ArrayList<>();<br/><br/>

	/**
	 * Uses reflection to invokes methods of receiver that are annotated with 
	 * @Action in the order indicated by the value attribute of the annotations.
	 */
	@Override
	public void run() {
		Class<?> clazz = receiver.getClass();

//Stage one is to explore the methods looking for the annotation.
		
		Method[] methods = clazz.getMethods();
#		for (Method method : methods) {
#			Action actionAnnotation = method.getAnnotation(Action.class);
#			logger.debug("{}", actionAnnotation);
#			if (actionAnnotation != null) {
#				int sequenceNum = actionAnnotation.value();
#				
#//TODO 3: Create a SequenceAndMethod object giving the constructor the 
#//sequenceNum and method and add it to the list called seqAndMeths that you 
#//created above.
#				
@				seqAndMeths.add(new SequenceAndMethod(sequenceNum, method));
&
&
% TODO 3:<br/>seqAndMeths.add(new SequenceAndMethod(sequenceNum, method));<br/><br/>
#			}
#		}

^		for (Method method : methods) {
^
^//TODO 3: Check if there is an Action annotation on this method. If there is one
^//then 
^//1. Get the sequence number (it is the attribute on the annotation).
^//2. Create a SequenceAndMethod object using the sequence number and method
^//and add it to the list called seqAndMeths that you created above.
^
^
^
^		}
		
//TODO 4: Since SequenceAndMethod implements Comparable you can use the static 
//sort() method of the Collections class to sort the list seqAndMeths. This is 
//Stage two. 
		
@		Collections.sort(seqAndMeths);
$
%TODO 4: Collections.sort(seqAndMeths);<br/><br/>

@//For the bonus replace the above line with 
@//		Collections.sort(seqAndMeths, (sm1,sm2) -> sm1.sequenceNumber.compareTo(sm2.sequenceNumber));		


//Now that we have a sorted list we iterate through it invoking methods.

^//TODO 5: Loop through the seqAndMeths list and (TDOD 6:)for each seqAndMeth object
^//invoke the method. You will need a try catch block.

#		for (SequenceAndMethod seqAndMeth : seqAndMeths) {
#			
#//TODO 5: Get the method associated with this seqAndMeth object (there is a 
#//getter for this on seqAndMeth).			
#			
@			Method currentMethod = seqAndMeth.getMethod();
&			Method currentMethod = null; //edit this line
%TODO 5: Method currentMethod = seqAndMeth.getMethod();<br/><br/>
#
#//TODO 6: Invoke currentMethod on receiver. You will need a try block and catch 
#//a few exceptions. (Method objects have an invoke() method which will take the 
#//receiver and a new Object[0] to represent the fact that there are no 
#//input arguments).
#
@			try {
@				currentMethod.invoke(receiver, new Object[0]);
@			} catch (IllegalAccessException | IllegalArgumentException
@					| InvocationTargetException e) {
@				logger.error("Failed to execute {}", currentMethod.getName(), e);
@			}
&
&
&
&
&
&
%TODO 6: <br/>try {<br/>&#160;&#160;currentMethod.invoke(receiver, new Object[0]);<br/>} catch (IllegalAccessException | IllegalArgumentException<br/>&#160;&#160;&#160;&#160;	| InvocationTargetException e) {<br/>&#160;&#160;logger.error("Failed to execute {}", currentMethod.getName(), e);<br/>}
#		}//End of for loop
	}

	public void setReceiver(final Receiver receiver) {
		this.receiver = receiver;
	}

//{{Marker 1
	
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
	
//}} end Marker 1: Return to TODO 2.
	
@//	Solution to bonus: Instead of the SequencAndMethod class above use this one:
@//	final class SequenceAndMethod {
@//		private final Integer sequenceNumber;
@//		private final Method method;
@//
@//		public SequenceAndMethod(int sequenceNum, Method method) {
@//			super();
@//			this.sequenceNumber = sequenceNum;
@//			this.method = method;
@//		}
@//
@//		public Method getMethod() {
@//		}
@//	}
}
