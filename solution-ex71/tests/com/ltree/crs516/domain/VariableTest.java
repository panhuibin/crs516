package com.ltree.crs516.domain;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import com.ltree.crs516.data.SamplesGenerator;

public class VariableTest {

	private Variable testSubject;
	private String codeString = "codeString";
	private int code = 1;
	private String depthVariableUnit = "depthVariableUnit";
	private String qualityFlagString = "qualityFlagString";
	private VariableMeta[] metaData = SamplesGenerator.getTestVariableMeta();
	private int qualityFlag = 0;
	
	@Before
	public void setUp() throws Exception {
		testSubject = new Variable();
		testSubject.setCode(code);
		testSubject.setCodeString(codeString);
		testSubject.setCodeUnit(depthVariableUnit);
		testSubject.setMetaData(metaData);
		testSubject.setQualityFlag(qualityFlag);
		testSubject.setQualityFlagString(qualityFlagString);
	}

	@Test
	public void testGetMetaData() {
		assertThat(testSubject.getMetaData(),is(equalTo(metaData)));
	}

	@Test
	public void testGetNumMetaData() {
		assertThat(testSubject.getNumMetaData(),is(equalTo(metaData.length)));
	}

	@Test
	public void testGetQualityFlag() {
		assertThat(testSubject.getQualityFlag(),is(equalTo(qualityFlag)));
	}

	@Test
	public void testGetQualityFlagString() {
		assertThat(testSubject.getQualityFlagString(),is(equalTo(qualityFlagString)));
	}

	@Test
	public void testGetCode() {
		assertThat(testSubject.getCode(),is(equalTo(code)));
	}

	@Test
	public void testGetCodeString() {
		assertThat(testSubject.getCodeString(),is(equalTo(codeString)));
	}

	@Test
	public void testGetCodeUnit() {
		assertThat(testSubject.getCodeUnit(),is(equalTo(depthVariableUnit)));
	}

}
