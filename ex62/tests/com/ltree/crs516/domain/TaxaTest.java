package com.ltree.crs516.domain;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import com.ltree.crs516.data.SamplesGenerator;

public class TaxaTest {

	private Taxa testSubject;
	private int code = 1;
	private String taxVar = "taxVar";
	private Datum value = SamplesGenerator.getTestDatum();
	private String valueMeaning = "valueMeaning";
	
	@Before
	public void setUp() throws Exception {
		testSubject = new Taxa();
		testSubject.setCode(code);
		testSubject.setTaxVar(taxVar);
		testSubject.setValue(value);
		testSubject.setValueMeaning(valueMeaning);
	}

	@Test
	public void testGetOriginatorFlag() {
		assertThat(testSubject.getOriginatorFlag(),is(equalTo(value.getOriginatorsFlag())));
	}

	@Test
	public void testGetQualityFlag() {
		assertThat(testSubject.getQualityFlag(),is(equalTo(value.getQualityFlag())));
	}

	@Test
	public void testGetQualityFlagString() {
		assertThat(testSubject.getQualityFlagString(),is(equalTo(value.getQualityFlagString())));
	}

	@Test
	public void testGetCode() {
		assertThat(testSubject.getCode(),is(equalTo(code)));	
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
	public void testGetValueMeaning() {
		assertThat(testSubject.getValueMeaning(),is(equalTo(valueMeaning)));
	}

}
