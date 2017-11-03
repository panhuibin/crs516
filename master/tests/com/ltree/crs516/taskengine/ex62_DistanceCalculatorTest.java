package com.ltree.crs516.taskengine;

import static com.ltree.crs516.data.DataConstants.DATA_DIR;
import static org.junit.Assert.assertEquals;

import java.io.File;

import org.junit.Before;
import org.junit.Test;

public class DistanceCalculatorTest {

	private DistanceCalculator testSubject;

//Our code in DistanceCalculator is now fast enough to handle the large files 
//that were not possible with earlier versions. However it is more complicated 
//code that is harder to maintain.
	
	private File file = new File(DATA_DIR + "SURF_ALL.gz");
	
	@Before
	public void setup() {
		testSubject = new DistanceCalculator();
		testSubject.setFile(file);
	}

//TODO 1: Examine this test and then run this file as a JUnit test to check that
//action4() is giving the expected result for this input file.	

	@Test
	public void testaction4() {
		testSubject.action4();
		double expected = 40320.54708816042;
		assertEquals(expected, testSubject.getMaximumDistance(), 0.01);
	}

	
//TODO 2: Write a new test method called testaction5() which will check that a 
//new method called action5() returns the correct result. The expected answer is 
//the same as the one for action4() above.
	
@	@Test
@	public void testaction5() {
@		testSubject.action5();
@		double expected = 40320.54708816042;
@		assertEquals(expected, testSubject.getMaximumDistance(), 0.01);
@	}
	

%TODO 2:<br/>@Test<br/>public void testaction5() {<br/>&#160;&#160;testSubject.action5();<br/>&#160;&#160;double expected = 25393.191056333137;<br/>&#160;&#160;assertEquals(expected, testSubject.getMaximumDistance(), 0.01);<br/>}<br/><br/>


}
