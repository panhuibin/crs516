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
public class VariableHeaderCodesTablesTest {

	@Parameters
	public static Collection<Object[]> parameters() {
		Object[][] data = new Object[][] { 
				{ 1,5500025, "date submitted (mm/dd/yyyy)=9/17/1974, date entered in database(mm/dd/yyyy)=, submitting person=, submitting institute=US NAVY SHIPS OF OPPORTUNITY" },
				{ 2,304,"BEST (BENGUELA SOURCE & TRANSPORT)"},
				{ 3,203,"Salinity: unknown (pre-PSS78)"},
				{ 4,1068,"KERNFORSCHUNGSANLAGE JUELICH- ATMOSPHAERISCHE CHEMIE (KFAAC) (JUELICH)"},
				{ 5,774,"BOTTLE: Fluorometer  Turner Designs  model 10-AU-005-CE"},
				{ 6,1262,"Nondispersive Infrared spectrometry (NDIR) xCO2 at analysis temperature"},
				{ 8,64,"molesC&#0183;m-2&#0183;day<sup>-1</sup>"},
				{ 10,1630,"Laminar flow design"},
				{ 11,102,"Whatman GF/F 25"},
				{ 12,25,"dawn  noon"},
		};
		return Arrays.asList(data);
	}

	private int varCode;
	private int varCodeVal;
	private String expected;

	public VariableHeaderCodesTablesTest(int code, int codeVal, String expected) {
		this.varCode = code;
		this.varCodeVal = codeVal;
		this.expected = expected;
	}

	@Test
	public void testConvert() {
		assertThat(CodeTables.getMetaMeaning(varCode, varCodeVal),is(equalTo(expected)));
	}

}
