package com.ltree.crs516.taskengine;

import static com.ltree.crs516.data.DataConstants.DATA_DIR;
import static org.junit.Assert.assertEquals;

import java.io.File;

import org.junit.Before;
import org.junit.Test;

import com.ltree.crs516.taskengine.DistanceCalculator;

public class DistanceCalculatorTest {

	private DistanceCalculator testSubject;
	private File file = new File(DATA_DIR + DistanceCalculator.dataFile[1]);

	@Before
	public void setup() {
		testSubject = new DistanceCalculator();
		testSubject.setFile(file);
	}

//NOTE: These assertions are for kilometers.
	

	@Test
	public void testaction4() {
		testSubject.action4();
		double expected = 1190.7165184596765;
		assertEquals(expected, testSubject.getMaximumDistance(), 0.01);
	}

	
}
