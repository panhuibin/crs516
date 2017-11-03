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
		
// TODO 1: Mock the owner (its interface type is WODClient) and the dataService.
		WODClient owner = mock(WODClient.class);
		dataService = mock(DataService.class);

// TODO 2: Configure dataService so that when the size() method is 
// called it returns the number 50.

		when(dataService.size()).thenReturn(50);

// TODO 3: Make an instance of Station and store it in the variable station.

		station = new Station();

// TODO 4: Configure dataService so that when the read() method is 
// called with any int as the input it returns the station you just
// instantiated.

		when(dataService.read(anyInt())).thenReturn(station);

// TODO 5: Make an instance of WODController, the test subject. Give it owner as
// the input argument to the constructor.

		testSubject = new WODController(owner);

// TODO 6: Set the dataService property of the testSubject to be the 
// mock dataService you created.

		testSubject.setDataService(dataService);

// TODO 7: Create a mock Observer.

		observer = mock(Observer.class);

// TODO 8: Register the mock Observer as an observer of the testSubject.

		testSubject.addObserver(observer);
	}

	@Test
	public void testNext(){
// TODO 9: On testSubject, set the current station to 10.
		testSubject.setCurrentStation(10);
// TODO 10: Call next() on testSubject. The current station should become 11.

		testSubject.next();

// TODO 11: Assert by calling getCurrentIndex() on testSubject that the current 
// station is 11.

		assertThat(testSubject.getCurrentIndex(),equalTo(11));

// TODO 12 The dataService should have been called at least once. The 
// observer should have been called twice, once when we set the station to 10 
// and once when we called next(). We care about the number of updates to the 
//observer as too many would cause flickering. 

// TODO 12a: Verify that indeed the size() method of dataService
// was called at least once.

		verify(dataService, atLeastOnce()).size();

// TODO 12b: Verify that indeed the update() method of observer
// was called exactly 2 times with the testSubject and station
// as the inputs.

		verify(observer, times(2)).update(testSubject, station);

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
