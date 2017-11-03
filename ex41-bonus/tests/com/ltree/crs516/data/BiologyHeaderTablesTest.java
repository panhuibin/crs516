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
public class BiologyHeaderTablesTest {

	@Parameters
	public static Collection<Object[]> parameters() {
		Object[][] data = new Object[][] { 
				{ 4,3, "OTHER TYPE (oblique  double oblique  etc.)"},
				{ 6,1, "yes"},
				{ 7,111,"Rectangular Midwater Trawl (RMT)"},
				{ 10,8,"10% filtered paraformaldehyde                     "},
				{ 11,4,"PHYTOPLANKTON CALCULATED; Biomasses of phytoplankton algae were calculated considering cells' volumes by equating real or average volumes of cells to corresponding geometric figures"},
				{ 13,5,"MUD (or MPN); Method of Ultimate Dilution "},
				{ 13,5,"MUD (or MPN); Method of Ultimate Dilution "},
				{ 19,2,"BY MANUFACTURER    "},
				{ 24,1,"OCL CALC  Calc by OCL from Wire out and Wire angle"},
				{ 25,2,"WICKSTEAD (1965); Indonesian Data Reports"},
				{ 30,5800006, "date submitted (mm/dd/yyyy)=8/ 3/1971, date entered in database(mm/dd/yyyy)=, submitting person=, submitting institute=US NAVY SHIPS OF OPPORTUNITY"},
		};
		return Arrays.asList(data);
	}

	private int code;
	private int codeVal;
	private String expected;

	public BiologyHeaderTablesTest(int code, int codeVal, String expected) {
		this.code = code;
		this.codeVal = codeVal;
		this.expected = expected;
	}

	@Test
	public void testConvert() {
		assertThat(CodeTables.getBiolHeaderMeaning(code, codeVal),is(equalTo(expected)));
	}

}
