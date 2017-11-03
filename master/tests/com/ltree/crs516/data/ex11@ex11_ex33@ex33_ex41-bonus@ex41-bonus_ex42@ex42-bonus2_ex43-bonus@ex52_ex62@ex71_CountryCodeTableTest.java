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
public class CountryCodeTableTest {

	@Parameters
	public static Collection<Object[]> parameters() {
		Object[][] data = new Object[][] { 
				{ "CO","COLOMBIA"},
				{ "ID","INDONESIA"},
				{ "NO","NORWAY"},
				{ "CN","CHINA"},
				{ "AG","ANTIGUA"},
				{ "HN","HONDURAS"},
				{ "NG","NIGERIA"},
				{ "TT","TRINIDAD AND TOBAGO"},
		};
		return Arrays.asList(data);
	}

	private String codeStr;
	private String expected;

	public CountryCodeTableTest(String codestStr, String expected) {
		this.codeStr = codestStr;
		this.expected = expected;
	}

	@Test
	public void testConvert() {
		assertThat(CodeTables.getCountry(codeStr),is(equalTo(expected)));
	}

}
