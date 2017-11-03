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
public class TaxaTablesTest {

	@Parameters
	public static Collection<Object[]> parameters() {
		Object[][] data = new Object[][] { 
				{ 1,46111,"PGC code=2270000, Rank=genus, Taxonomic Name -[ Author or comments ]-=Acanthometron"},
				{ 16,8,"FOLSOM SPLITTER; complete enumeration using Folsom Splitter"},
				{ 17,2,"spp.  (multiple species) "},
				{ 18,-2,"MEDIUM"},
				{ 19,-4,"VERY SMALL"},
				{ 20,70,"per m<sup>3</sup> -- Parameter per cubic meter"},
				{ 26,23,"Perez IMECOCAL; Folsom splitter to 1/8-1/16 (~ 800-900 individuals) then stereoscopic microscope"},
				{ 27,4000000,"Zooplankton Counts"},
				{ 28,72,"Original measurement in units of \"per ml\""},
				{ 30,-400, " All Biomass Types (excluding ichthyoplankton)"},
				{ 30,4262500,"Mollusca:  Gastropoda (snails & slugs)"},
				{ 18,-2,"MEDIUM"},
				{ 5,9,"LARVA+JUV+ADULTS  (Codes 6+7+8)"},
				{ 6,5,"GROUPED  BOTH SEXES PRESENT"},
				{ 7,8,"VERY RARE; VR; RR"},
				{ 8,5,"HETEROTROPH-PARASITIC"},
				{ 9,4,"MEROPLANKTONIC ; Adults are benthic or nektonic"}
		};
		return Arrays.asList(data);
	}

	private int code;
	private int codeVal;
	private String expected;

	public TaxaTablesTest(int code, int codeVal, String expected) {
		this.code = code;
		this.codeVal = codeVal;
		this.expected = expected;
	}

	@Test
	public void testConvert() {
		assertThat(CodeTables.getTaxaMeaning(code, codeVal),is(equalTo(expected)));
	}

}
