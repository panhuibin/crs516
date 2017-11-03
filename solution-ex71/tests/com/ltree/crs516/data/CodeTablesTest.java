package com.ltree.crs516.data;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class CodeTablesTest {
	//Don't need an instance as all methods are static.
	//Most of the tests for this class are parametrized tests 
	//in their own test cases.

	@Test
	public void testGetTaxaMeaningIntDouble() {
			assertThat(CodeTables.getTaxaMeaning(28,68.1),is(equalTo("Used provided Volume_filtered used for \"volume filtered\"")));
	}

	@Test
	public void testGetVariableSpecificCodes() {
		assertThat(CodeTables.getVariableSpecificCodes(15)[0],is(equalTo("Analysis temperature")));
		assertThat(CodeTables.getVariableSpecificCodes(15)[1],is(equalTo("degrees C")));
	}

}
