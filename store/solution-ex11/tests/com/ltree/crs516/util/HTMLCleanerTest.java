package com.ltree.crs516.util;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.Matchers.*;

public class HTMLCleanerTest {
	String testString ;
	@Before
	public void setUp() throws Exception {
		testString = "<sdfsdfa> dfdf,<ertertert<<sdfsdfsdfds>><<>";
	}

	@Test
	public void testEscape() {
		String testString = "<sdfsdfa> dfdf,<ertertert<<sdfsdfsdfds>><<>";
		assertThat(HTMLCleaner.escape(testString), not(containsString("<")));
		assertThat(HTMLCleaner.escape(testString), not(containsString(">")));
	}

}
