package com.ltree.crs516.domain;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import org.junit.Before;
import org.junit.Test;

import com.ltree.crs516.domain.Datum.Builder;

public class DatumTest {

	private Datum testSubject;
	private Double value = 1.2;
	private int sigFig = 3;
	private int totalFig = 4;
	private int precision = 3;
	private String meaning = "meaning";
	private int originatorsFlag = 6;
	private int qualityFlag = 7;
	private String qualityFlagString = "qualityFlagString";

	
	@Before
	public void setUp() throws Exception {
		
// TODO 1: Instantiate the testSubject using the builder class you just wrote
// and the fields defined above. 
//The mandatory fields are value, sigFig, totalFig,  precision and the optional 
//ones are meaning, originatorsFlag, qualityFlag, and qualityFlagString. 	

		testSubject = new Datum.Builder(value, sigFig, totalFig, precision)
				.meaning(meaning)
				.originatorsFlag(originatorsFlag)
				.qualityFlag(qualityFlag)
				.qualityFlagString(qualityFlagString)
				.build();








	}

	@Test
	public void testGetMeaning() {
		assertThat(testSubject.getMeaning(), equalTo(meaning));
	}

	@Test
	public void testGetOriginatorsFlag() {
		assertThat(testSubject.getOriginatorsFlag(), equalTo(originatorsFlag));
	}

	@Test
	public void testGetPrecision() {
		assertThat(testSubject.getPrecision(), equalTo(precision));
	}

	@Test
	public void testGetQualityFlag() {
		assertThat(testSubject.getQualityFlag(), equalTo(qualityFlag));
	}

	@Test
	public void testGetQualityFlagString() {
		assertThat(testSubject.getQualityFlagString(), equalTo(qualityFlagString));
	}

	@Test
	public void testGetSigFig() {
		assertThat(testSubject.getSigFig(), equalTo(sigFig));
	}

	@Test
	public void testGetTotalFig() {
		assertThat(testSubject.getTotalFig(), equalTo(totalFig));
	}

	@Test
	public void testGetValue() {
		assertThat(testSubject.getValue(), equalTo(value));
	}

	@Test
	public void testGetValueString() {
		assertThat(testSubject.getValueString(), equalTo("1.200"));
	}

}
