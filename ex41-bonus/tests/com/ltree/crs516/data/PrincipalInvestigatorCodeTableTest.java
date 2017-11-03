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
public class PrincipalInvestigatorCodeTableTest {

	@Parameters
	public static Collection<Object[]> parameters() {
		Object[][] data = new Object[][] { 
				{ 1551,"AMAROV; G."},
				{ 960,"ANGOT; MICHEL"},
				{ 241,"ASADA; E."},
				{ 278,"BAARS; DR. MARTIEN A."},
				{ 312,"BALTZ; KENNEY"},
				{ 316,"BAUER; DR. ROGER"},
				{ 2616,"BELLAIL; ROBERT"},
				{ 2478,"BETHOUX; JEAN-PIERRE"},
		};
		return Arrays.asList(data);
	}

	private int piCode;
	private String expected;

	public PrincipalInvestigatorCodeTableTest(int piCode, String expected) {
		this.piCode = piCode;
		this.expected = expected;
	}

	@Test
	public void testConvert() {
		assertThat(CodeTables.getPI(piCode),is(equalTo(expected)));
	}

}
