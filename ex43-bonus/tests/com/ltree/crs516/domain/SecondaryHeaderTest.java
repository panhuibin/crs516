package com.ltree.crs516.domain;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import com.ltree.crs516.data.SamplesGenerator;

public class SecondaryHeaderTest {
	
	private SecondaryHeader testSubject;
	private int headerCode = 1;
	private String headerString = "headerString";
	private Datum value = SamplesGenerator.getTestDatum();
	private String valueMeaning = "valueMeaning";

	@Before
	public void setUp() throws Exception {
		testSubject = new SecondaryHeader();
		testSubject.setHeaderCode(headerCode);
		testSubject.setHeaderString(headerString);
		testSubject.setValue(value);
		testSubject.setValueMeaning(valueMeaning);
	}

	@Test
	public void testGetHeaderCode() {
		assertThat(testSubject.getHeaderCode(),is(equalTo(headerCode)));
	}

	@Test
	public void testGetHeaderString() {
		assertThat(testSubject.getHeaderString(),is(equalTo(headerString)));
	}

	@Test
	public void testGetPrecision() {
		assertThat(testSubject.getPrecision(),is(equalTo(value.getPrecision())));
	}

	@Test
	public void testGetSigFig() {
		assertThat(testSubject.getSigFig(),is(equalTo(value.getSigFig())));
	}

	@Test
	public void testGetTotalFig() {
		assertThat(testSubject.getTotalFig(),is(equalTo(value.getTotalFig())));
	}

	@Test
	public void testGetValue() {
		assertThat(testSubject.getValue(),is(equalTo(value)));
	}

	@Test
	public void testGetValueMeaning() {
		assertThat(testSubject.getValueMeaning(),is(equalTo(valueMeaning)));
	}

	@Test
	public void testGetValueString() {
		assertThat(testSubject.getValueString(),is(equalTo(value.getValueString())));
	}

}
