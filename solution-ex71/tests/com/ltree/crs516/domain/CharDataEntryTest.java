package com.ltree.crs516.domain;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import com.ltree.crs516.data.SamplesGenerator;
import com.ltree.crs516.domain.CharDataEntry.CharDataType;

public class CharDataEntryTest {

	private CharDataEntry testSubject;
	private String data = "data";
	private CharDataType type = CharDataType.ORIGINATORS_CRUISE;
	private PrincipalInvestigator[] pis = SamplesGenerator.getTestPis();

	@Before
	public void setUp() throws Exception {
		testSubject = new CharDataEntry();
		testSubject.setData(data);
		testSubject.setPis(pis);
		testSubject.setType(type);
	}

	@Test
	public void testGetData() {
		assertThat(testSubject.getData(),is(equalTo(data)));
	}

	@Test
	public void testGetPis() {
		assertThat(testSubject.getPis()[1],is(equalTo(pis[1])));
	}

	@Test
	public void testGetType() {
		assertThat(testSubject.getType(),is(equalTo(type)));
	}

}
