package com.ltree.crs516.controller;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Observer;

import org.junit.Before;
import org.junit.Test;

import com.ltree.crs516.client.WODClient;
import com.ltree.crs516.data.DataService;
import com.ltree.crs516.domain.Station;


public class WODControllerTest {

	private WODController testSubject;
	private DataService dataService;
	private Observer observer;
	private Station station; 
	
	@Before
	public void setUp() throws Exception {
		
//TODO 1: Create Mocks for the owner and the dataService variables.		
		WODClient owner = null;
		dataService = null;

// TODO 2: Configure dataService so that when the size() method is 
// called it returns the number 50.




// TODO 3: Make an instance of Station and store it in the variable station.

		station = null;

// TODO 4: Configure dataService so that when the read() method is 
// called with any int as the input it returns the station you just
// instantiated.




// TODO 5: Make an instance of WODController, the test subject. Give it owner as
// the input argument to the constructor.

		testSubject = null;


// TODO 6: Set the dataService property of the testSubject to be the 
// mock dataService you created.




// TODO 7: Create a mock Observer.

		observer = null;


// TODO 8: Register the mock Observer as an observer of the testSubject.



	}

	@Test
	public void testNext(){
//TODO 9-12
//Check that when you call next() on testSubject it 
//	the value of getCurrentIndex() is incremented
//	the size() method of dataService is called
//	the update() method of observer is called		

















	}

// TODO 13: The other methods follow the same pattern. Read through them 
// to make sure you understand them.

	@Test
	public void testGetCurrentStation() {
		assertThat(testSubject.getCurrentIndex(),equalTo(0));
	}

	@Test
	public void testSetCurrentStation() {
		testSubject.setCurrentStation(10);
		assertThat(testSubject.getCurrentIndex(),equalTo(10));
		verify(dataService, atLeastOnce()).size();
	}
	
	@Test
	public void testSetCurrentStationTopBoundary() {
		testSubject.setCurrentStation(51);
		assertThat(testSubject.getCurrentIndex(),equalTo(50));
		verify(dataService, atLeastOnce()).size();
		
//If you see errors here about the update() method it is probably 
//because your WodController does not yet implement Observable.
		verify(observer, times(1)).update(testSubject, station);
	}
	
	@Test
	public void testSetCurrentStationBottomBoundary() {
		testSubject.setCurrentStation(0);
		assertThat(testSubject.getCurrentIndex(),equalTo(1));
		verify(observer, times(1)).update(testSubject, station);
	}

	@Test
	public void testObserversNotifiedWhenStationSet() {
		testSubject.setCurrentStation(10);
		assertThat(testSubject.getCurrentIndex(),equalTo(10));
		verify(observer, times(1)).update(testSubject, station);
	}
		
	@Test
	public void testNextAtTop(){
		testSubject.setCurrentStation(50);
		testSubject.next();
		assertThat(testSubject.getCurrentIndex(),equalTo(50));
		verify(dataService, atLeastOnce()).size();
		verify(observer, times(2)).update(testSubject, station);
	}

	@Test
	public void testPreviousAtBottom() {
		testSubject.setCurrentStation(1);
		testSubject.previous();
		assertThat(testSubject.getCurrentIndex(),equalTo(1));
		verify(dataService, atLeastOnce()).size();
		verify(observer, times(2)).update(testSubject, station);
	}
	
}
