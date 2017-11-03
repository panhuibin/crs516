package com.ltree.crs516.tasks;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import org.junit.Before;
import org.junit.Test;

import com.ltree.crs516.taskengine.Receiver;

public class AnnotationCommandImplTest {

	private AnnotationCommandImpl testSubject;
	private TestAnnotationReceiverImpl receiver;

	@Before
	public void setUp() throws Exception {
		testSubject = new AnnotationCommandImpl();
		receiver = new TestAnnotationReceiverImpl();
		testSubject.setReceiver(receiver);
	}

	@Test
	public void testExecute() {
		testSubject.run();
		assertThat(1, is(equalTo(receiver.getActionCalled())));
		assertThat(1, is(equalTo(receiver.getAction2Called())));
	}

	public class TestAnnotationReceiverImpl implements Receiver {

		private int actionCalled;
		private int action2Called;

		public int getActionCalled() {
			return actionCalled;
		}

		public int getAction2Called() {
			return action2Called;
		}

//TODO 1: Add an Action annotation with attribute 1 to this method

		public void action() {
			actionCalled = 1;
			action2Called = -1; // If action is called last rather than first 
			//this will be detectable as the variable will be set to -1.
		}

//TODO 2:Add an Action annotation with attribute 2 to this method		

		public void action2() {
			action2Called = 1;
		}

	}

}
