package com.ltree.crs516.taskengine;

import static org.mockito.Mockito.verify;

import java.io.IOException;
import java.rmi.NoSuchObjectException;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TaskEngineImplTest {
	private TaskEngine testSubject;
	private static Logger logger = LoggerFactory
			.getLogger(TaskEngineImpl.class);
	private static Registry registry;;

	/**
	 * This static method executes once before the whole test case rather than before
	 * each test.
	 */
	@BeforeClass
	public static void oneTimeInitialization() {
		try {
			registry = java.rmi.registry.LocateRegistry.createRegistry(1099);
			logger.info("RMI registry ready.");
		} catch (RemoteException e) {
			logger.error(
					"Failed to start RMI registry. Perhaps you have another task engine one running",
					e);
		}
	}

	@Before
	public void setUp() throws Exception {
		try {
			testSubject = new TaskEngineImpl();
		} catch (IOException exc) {
			logger.error(
					"Failed to start engine. Perhaps you have another one running",
					exc);
		}
	}

	@After
	public void tearDown() {
		testSubject = null;
	}

	@Test
	public void testSubmitTask() throws RemoteException, InterruptedException {
		Command command1 = Mockito.mock(Command.class);
		Command command2 = Mockito.mock(Command.class);
		testSubject.submitTask(command1);
		testSubject.submitTask(command2);
		Thread.sleep(1000);
		verify(command1).run();
		Thread.sleep(1000);
		verify(command2).run();
	}

	/**
	 * This static method executes once after all the tests after
	 * each test.
	 */
	@AfterClass
	public static void finalCleanup() {
		// Better not close registry in @After the next test would probably 
		//find the port still occupied.
		if (registry != null) {
			try {
				UnicastRemoteObject.unexportObject(registry, true);
			} catch (NoSuchObjectException e) {
				logger.info("Registry is already closed.");
			}
			logger.info("Registry is finally closed.");

		}
		else{
			logger.error("Registry was already null. If you got this " +
					"far you must be using another one that was already running." +
					"Probably another task engine.");
		}
	}
	
}
