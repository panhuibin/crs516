package com.ltree.crs516.domain;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import com.ltree.crs516.data.SamplesGenerator;

public class VariableMetaTest {

	private VariableMeta testSubject;
	private String varCode = "varCode";
	private String code = "code";
	private String varComment = "varComment";
	private Datum value = SamplesGenerator.getTestDatum();
	
	@Before
	public void setUp() throws Exception {
		testSubject = new VariableMeta();
		testSubject.setCode(code);
		testSubject.setValue(value);
		testSubject.setVarCode(varCode);
		testSubject.setVarComment(varComment);
	}

	@Test
	public void testGetVarCode() {
		assertThat(testSubject.getVarCode(),is(equalTo(varCode)));
	}

	@Test
	public void testGetCode() {
		assertThat(testSubject.getCode(),is(equalTo(code)));
	}

	@Test
	public void testGetVarComment() {
		assertThat(testSubject.getVarComment(),is(equalTo(varComment)));
	}

	@Test
	public void testGetValueString() {
		assertThat(testSubject.getValueString(),is(equalTo(value.getValueString())));
	}

	@Test
	public void testGetValuePrecision() {
		assertThat(testSubject.getValuePrecision(),is(equalTo(value.getPrecision())));
	}

	@Test
	public void testIsValuePresent() {
		assertThat(testSubject.isValuePresent(),is(equalTo(true)));
	}

	@Test
	public void testGetValueSigFig() {
		assertThat(testSubject.getValueSigFig(),is(equalTo(value.getSigFig())));
	}

	@Test
	public void testGetValueTotalFig() {
		assertThat(testSubject.getValueTotalFig(),is(equalTo(value.getTotalFig())));
	}

	@Test
	public void testGetValueMeaning() {
		assertThat(testSubject.getValueMeaning(),is(equalTo(value.getMeaning())));

	}

	@Test
	public void testGetValue() {
		assertThat(testSubject.getValue(),is(equalTo(value)));
	}

}
