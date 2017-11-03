package com.ltree.crs516.tasks;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import org.junit.Before;
import org.junit.Test;

import com.ltree.crs516.taskengine.Receiver;

public class NamingPatternCommandImplTest {

	private NamingPatternCommandImpl testSubject;
	private TestNamingPatternReceiverImpl receiver;
	
	@Before
	public void setUp() throws Exception {
		testSubject = new NamingPatternCommandImpl();
		receiver = new TestNamingPatternReceiverImpl();
		testSubject.setReceiver(receiver);
	}

	@Test
	public void testExecute() {
		testSubject.run();
		assertThat(receiver.getActionCalled(),is(equalTo(1)));
		assertThat(receiver.getAction2Called(),is(equalTo(1)));
	}

	public class TestNamingPatternReceiverImpl implements Receiver {
		
		private int actionCalled;
		private int action2Called;
		
		public int getActionCalled() {
			return actionCalled;
		}

		public int getAction2Called() {
			return action2Called;
		}

		public void action() {
			actionCalled = 1;
		}

		public void secondAction() {
				action2Called = 1;
		}

	}
	
}
