package com.ltree.crs516.client;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import java.util.Observable;

import javax.swing.JEditorPane;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.ltree.crs516.domain.Station;

public class CharacterDataTabTest {

	private CharacterDataTab testSubject;
	private JEditorPane textArea;
	private Displayer mockDisplayer;
	private Station mockStation;
	private String testString = "testString";
	private Observable mockObservable;
	
	@Before
	public void setUp() throws Exception {
		testSubject = new CharacterDataTab();
		mockDisplayer = Mockito.mock(Displayer.class);
		testSubject.setDisplayer(mockDisplayer);
		textArea = new JEditorPane("text/html","");
		testSubject.setTextArea(textArea);
		mockStation = Mockito.mock(Station.class);
		mockObservable = Mockito.mock(Observable.class);
	}

	@Test
	public void testDisplay() {
		when(mockDisplayer.createDisplayString(mockStation)).thenReturn(testString);
		testSubject.display(mockStation);
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		assertTrue(textArea.getText().contains(testString));
	}
	
	@Test
	public void testupdate() {
		when(mockDisplayer.createDisplayString(mockStation)).thenReturn(testString);
		testSubject.update(mockObservable, mockStation);
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		assertTrue(textArea.getText().contains(testString));
	}

}
