	package com.ltree.crs516.data;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

 @RunWith(Parameterized.class)
public class QualityFlagTableTest {

	@Parameters
	public static Collection<Object[]> parameters() {
		Object[][] data = new Object[][] { 
				{ 0,"accepted cast"},
				{ 1,"failed annual standard deviation check"},
				{ 2,"two or more density inversions ( Levitus; 1982 criteria )"},
				{ 3,"flagged cruise"},
				{ 4,"failed seasonal standard deviation check"},
				{ 5,"failed monthly standard deviation check"},
				{ 6,"failed annual and seasonal standard deviation check"},
				{ 7,"bullseye from standard level data or failed annual and monthly standard deviation check"},
		};
		return Arrays.asList(data);
	}

	private int qualityControlFlagInt;
	private String expected;

	public QualityFlagTableTest(int qualityControlFlagInt, String expected) {
		this.qualityControlFlagInt = qualityControlFlagInt;
		this.expected = expected;
	}

	@Test
	public void testConvert() {
		assertThat(CodeTables.getqualityFlag(qualityControlFlagInt),is(equalTo(expected)));
	}

}
