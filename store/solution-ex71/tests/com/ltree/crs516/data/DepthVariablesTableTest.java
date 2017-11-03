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
public class DepthVariablesTableTest {

	@Parameters
	public static Collection<Object[]> parameters() {
		Object[][] data = new Object[][] { 
				{ 1, "Temperature", "Degrees Celsius (&#0176;C)"},
				{ 21,"Dissolved Inorganic carbon [DIC]", "Millimole per liter (mM)"},
				{ 33,"Tritium [3H]", "Tritium Unit (TU)"},
				{ 43,"Delta Oxygen-18 [18O]", "Per mille ()"}
		};
		return Arrays.asList(data);
	}

	private int number;
	private String expected;
	private String expectedUnit;

	public DepthVariablesTableTest(int number, String expected, String expectedUnit) {
		this.number = number;
		this.expected = expected;
		this.expectedUnit = expectedUnit;
	}

	@Test
	public void testConvert() {
		assertThat(CodeTables.getDepthVariableName(number),is(equalTo(expected)));
		assertThat(CodeTables.getDepthVariableUnit(number),is(equalTo(expectedUnit)));
	}

}
