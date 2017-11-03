package com.ltree.crs516;

import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

public class SuiteTest {

	Suite[] suites;
	String[] names;
	
	@Before
	public void setUp() throws Exception {
		suites = new Suite[]{Suite.CLUB,Suite.DIAMOND,Suite.HEART,Suite.SPADE};
		names = new String[]{"club","diamond","heart","spade"};
	}

	@Test
	public void testChooseSuite() {
		Map<Suite, Integer> tally = new HashMap<>();
		for (Suite suite : suites) {
			tally.put(suite, 0);
		}
		for(int i=0; i<40000;i++){
			Suite s = (Suite.chooseSuite());
			tally.put(s, tally.get(s)+1);
		}
		for (Suite s : Suite.values()) {
			assertTrue(Math.abs(tally.get(s)-10000)<250);
		}
	}

}
