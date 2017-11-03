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


		@Action(1)
		public void action() {
			actionCalled = 1;
			action2Called = -1; // If action is called last rather than first 
			//this will be detectable as the variable will be set to -1.
		}


		@Action(2)
		public void action2() {
			action2Called = 1;
		}

	}

}
