package com.ltree.crs516.tasks;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.rmi.RemoteException;

import org.junit.Before;
import org.junit.Test;

import com.ltree.crs516.taskengine.Command;
import com.ltree.crs516.taskengine.TaskEngine;
import com.ltree.crs516.tasks.Client.CompositeCommandFactory;
import com.ltree.crs516.tasks.Client.EngineNotAvailableException;
import com.ltree.crs516.tasks.Client.TaskEngineFactory;

public class ClientTest {

	private Client testSubject;
	
	@Before
	public void setup(){
		testSubject = new Client();
	}
	
	@Test
	public void testPrepareAndSubmitCommand() throws RemoteException, EngineNotAvailableException {
		TaskEngineFactory factory = new TaskEngineFactory();
		TaskEngine mockEngine = mock(TaskEngine.class);
		factory.setEngine(mockEngine);
		testSubject.setTaskEngineFactory(factory);
		
		CompositeCommandFactory compositeCommandFactory = new CompositeCommandFactory();
		CompositeCommandImpl mockCompositeCommand = mock(CompositeCommandImpl.class);
		compositeCommandFactory.setCommand(mockCompositeCommand);
		testSubject.setCompositeCommandFactory(compositeCommandFactory);
		
		testSubject.prepareAndSubmitCommand();
		verify(mockCompositeCommand, times(3)).addCommand(any(Command.class));
		verify(mockEngine, times(1)).submitTask(mockCompositeCommand);
	}

}
