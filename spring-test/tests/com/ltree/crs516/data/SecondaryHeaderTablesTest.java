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
public class SecondaryHeaderTablesTest {

	@Parameters
	public static Collection<Object[]> parameters() {
		Object[][] data = new Object[][] { 
				{ 1,7500128, "Date Submitted (mm/dd/yyyy)=2/25/1975, Date entered in database (mm/dd/yyyy)=, Submitting person=, Submitting institute=EDGERTON; GERMESHAUSEN AND GRIER; INC. (EG&G) BOSTON; MASS."},
				{ 2,607,"ANSLOPE (CROSS-SLOPE EXCHANGES AT THE ANTARCTIC SLOPE FRONT) "},
				{ 12,-1,"DIRECTION NOT RECORDED"},
				{ 14,17,"PERCENT BROWN 27 FOREL-ULE SCALE XVII"},
				{ 16,20,"195 DEGREES - 204 DEGREES"},
				{ 17,13,"6.5 METER"},
				{ 18,2,"SMOOTH-WAVELET 1/3-1 2/3 FT (.1-.5 METERS)"},
				{ 20,5,"10 OR 11 SECONDS"},
				{ 21,35,"345 DEGREES - 354 DEGREES"},
				{ 26,35,"Severe dust storm or sandstorm-has begun or has increased during the preceding hour"},
				{ 27,2,"CIRROSTRATUS (CS)"},
				{ 28,6,"6 OKTAS     7/10-8/10"},
				{ 29,7,"bottle/rossette/net"},
				{ 3,208,"06A3 ATAIR (R/V; call sign DBBA; service date 05/04/1962 - 04/29/1987)"},
				{ 32,48,"TSK AXBT receiver MK-300"},
				{ 35,01,"MANUAL"},
				{ 36,34,"FXED INTERVAL GT 3 METERS BUT LT 6 METERS AND LE 0.2  DEG C"},
				{ 37,04,"SINGLE DIGITIZATION; COMPRESSION; FIT WITHIN 0.2 DE G C"},
				{ 4,1047,"FEDERAL INSTITUTE FOR GEOSCIENCES AND NATURAL RESOURCES (BGR) (HANNOVER;GERMANY)"},
				{ 40,3,"Nansen cast (reversing thermometer)"},
				{ 41,2,"200 - 500 METERS"},
				{54,2,"Kizu et al.; 2005 (XBT)"},
				
		};
		return Arrays.asList(data);
	}

	private int code;
	private int codeVal;
	private String expected;

	public SecondaryHeaderTablesTest(int code, int codeVal, String expected) {
		this.code = code;
		this.codeVal = codeVal;
		this.expected = expected;
	}

	@Test
	public void testConvert() {
		assertThat(CodeTables.getSecHeaderMeaning(code, codeVal),is(equalTo(expected)));
	}

}
