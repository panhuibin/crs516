package com.ltree.crs516.domain;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.ltree.crs516.data.SamplesGenerator;
import com.ltree.crs516.domain.Station.ProfileType;

/**
 * Check that station created by FileBasedDataService has data that 
 * NOAA example in Appendix 8 of the documentation
 * OUTPUT FROM wodFOR.f for Cast 67064
 * says it should have.
 * 
 * @author cmawata
 * 
 */
public class StationTest {

	private IStation testSubject = SamplesGenerator
			.getTestStation();

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testGetBiologyHeaders() {
		//From NOAA example
		// Biological header # 2 18.000 (4)
		assertThat(testSubject.getBiologyHeaders()[0].getHeaderCode(),
				is(equalTo(2)));
		assertThat(testSubject.getBiologyHeaders()[0].getValueString(),
				is(equalTo("18.00")));
		assertThat(testSubject.getBiologyHeaders()[0].getTotalFig(),
				is(equalTo(4)));
	}

	@Test
	public void testGetCharacterDataEntries() {
		CharDataEntry[] charDataEntries = testSubject.getCharacterDataEntries();
		//From NOAA example
		// Originators Cruise Code: STOCS85A
		assertThat(charDataEntries[0].getData(), is(equalTo("STOCS85A")));
		//From NOAA example
		// Primary Investigator: 215 ... for variable #: 0
		// Primary Investigator: 216 ... for variable #: 0
		// Primary Investigator: 217 ... for variable #: 0
		// Primary Investigator: 218 ... for variable #: 14
		PrincipalInvestigator[] pis = charDataEntries[1].getPis();
		int[] piCodes = new int[] { 215, 216, 217, 218 };
		for (int i = 0; i < piCodes.length; i++) {
			assertThat(pis[i].getPiCode(), is(equalTo(piCodes[i])));
		}
	}

	@Test
	public void testGetBasicInformation() {
		
		assertThat(testSubject.getStationNumber(),is(equalTo(67064)));
		//From NOAA example
		// CC cruise Latitde Longitde YYYY MM DD Time Cast # levels
		// 31 11203 61.933 -172.267 1934 8 7 10.37 67064 4
		assertThat(testSubject.getCountryCode(), is(equalTo("31")));
		assertThat(testSubject.getCruiseNumber(), is(equalTo(11203)));
		assertThat(testSubject.getLatitudeString(), is(equalTo("61.93")));
		assertThat(testSubject.getLongitudeString(), is(equalTo("-172.27")));
		assertThat(testSubject.getYear(), is(equalTo(1934)));
		assertThat(testSubject.getMonth(), is(equalTo(8)));
		assertThat(testSubject.getDay(), is(equalTo(7)));
		assertThat(testSubject.getTime(), is(equalTo(10.37)));
		assertThat(testSubject.getLevels(), is(equalTo(4)));
	}

	@Test
	public void testBiologyHeaders() {
		BiologyHeader[] biologyHeaders = testSubject.getBiologyHeaders();
		//From NOAA example
		// Biological header # 2 18.000 (4)
		// Biological header # 3 76.000 (2)
		// Biological header # 4 2.000 (1)
		// Biological header # 7 103.000 (3)
		// Biological header # 9 0.050 (1)
		// Biological header # 13 11.000 (2)
		// Biological header # 16 10.367 (4)
		// Biological header # 30 9500110.000 (7)

		int[] biologyHeaderCodes = new int[] { 2, 3, 4, 7, 9, 13, 16, 30 };
		String[] biologyHeaderValues = new String[] { "18.00", "76", "2",
				"103", "0.05", "11", "10.37", "9500110" };
		int[] totalFigs = new int[] { 4, 2, 1, 3, 2, 2, 4, 7 };
		for (int i = 0; i < 8; i++) {
			BiologyHeader bh = biologyHeaders[i];
			assertThat(bh.getHeaderCode(), is(equalTo(biologyHeaderCodes[i])));
			assertThat(bh.getValueString(), is(equalTo(biologyHeaderValues[i])));
			assertThat(bh.getTotalFig(), is(equalTo(totalFigs[i])));
		}
	}

	@Test
	public void testSecondaryHeaders() {
		//From NOAA example
		// Secondary header # 1 9500110. (7)
		// Secondary header # 3 1427. (4)
		// Secondary header # 4 393. (3)
		// Secondary header # 7 76. (2)
		// Secondary header # 10 60. (2)

		int[] secondaryHeaderCodes = new int[] { 1, 3, 4, 7, 10, 29, 91 };
		String[] secondaryHeaderValues = new String[] { "9500110", "1427",
				"393", "76", "60", "7", "3" };
		int[] totalFigs = new int[] { 7, 4, 3, 2, 2, 1, 1, };
		SecondaryHeader[] secondaryHeaders = testSubject.getSecondaryHeaders();
		for (int i = 0; i < secondaryHeaders.length; i++) {
			SecondaryHeader sh = secondaryHeaders[i];
			assertThat(sh.getHeaderCode(), is(equalTo(secondaryHeaderCodes[i])));
			assertThat(sh.getValueString(),
					is(equalTo(secondaryHeaderValues[i])));
			assertThat(sh.getTotalFig(), is(equalTo(totalFigs[i])));
		}
	}

	@Test
	public void testTaxaSets() {
		List<TaxaList> taxaSets = testSubject.getTaxaSets();
		String[][] taxaValues = createTaxaValues();
		for (int i = 0; i < 1; i++) {
			TaxaList tl = taxaSets.get(i);
			String[] values = taxaValues[i];
			for (int j = 0; j < tl.getNumberofEntries(); j++) {
				Taxa tx = tl.get(j);
				assertThat(tx.getValueString(), is(equalTo(values[j])));
			}
		}

	}

	@Test
	public void testGetProfile() {
		Level[] profile = testSubject.getProfile();
		assertThat(testSubject.getProfileType(),
				is(equalTo(ProfileType.OBSERVED)));
		String[] depthStrings = new String[] { "0", "10", "25", "50" };
		String[][] profileValues = createProfileValues();
		for (int i = 0; i < profile.length; i++) {
			Level level = profile[i];
			assertThat(level.getDepthString(), is(equalTo(depthStrings[i])));
			for (int j = 0; j < level.getData().length; j++) {
				assertThat(level.getData()[j].getValueString(),
						is(equalTo(profileValues[i][j])));

			}
		}
	}
	
	@Test
	public void testGetVariables() {
		//From NOAA example
		//Number of variables in this profile: 7
		assertThat(testSubject.getVariables().length, is(equalTo(7)));
		Variable[] variables = testSubject.getVariables();
		//From NOAA example
		//z fo 1 fo 2 fo 3 fo 4 fo 6 fo 7 fo 9
		int[] codes = new int[]{1,2,3,4,6,7,9};

		String[][][] metadataString = createMetadataStrings();
		for(int i=0; i<variables.length;i++){
			assertThat(variables[i].getCode(), is(equalTo(codes[i])));
			assertThat(variables[i].getQualityFlag(), is(equalTo(0)));
		for(int j=0; j< variables[i].getNumMetaData();j++){
			VariableMeta[] metadata = variables[i].getMetaData();
			for(int k=0; k< metadata.length;k++){
				VariableMeta vm = metadata[k];
				assertThat(vm.getValueString(), is(equalTo(metadataString[i][j][k])));
					assertThat(vm.getCode(), is(equalTo("8")));
				assertThat(vm.getValueTotalFig(), is(equalTo(2)));
			}
		}
		}
	}
	
	/**
	 * Creates an array with the expected values as per NOAA example.
	 * @return
	 */
	private String[][] createTaxaValues() {

		return new String[][] {
//				From NOAA example. for the curious the codes are explained at 
//				the bottom of page 25 of the readme.pdf
//				Taxa-Record 1 : Taxonomic Code [1]# 85272 (5) 
//				Code 1 for example identifies the type of taxon or biomass sampled.				
//				Code # 2 0.000 (1) 00
//				Code # 3 25.000 (3) 00
//				Code # 10 6.000 (1) 00
//				Code # 20 68.000 (2) 00
//				Code # 27 4.800 (2) 00
//				Code # 28 68.400 (4) 00
//				Code # 29 3.000 (1) 00
//				Code # 30 4282010.000 (7) 00				
				new String[] { "85272", "0", "25.0", "6", "68", "4.8", "68.40",
						"3", "4282010" },
				
//				Taxa-Record 2 : Taxonomic Code [1]# 79118 (5)
//				Code # 2 0.000 (1) 00
//				Code # 3 25.000 (3) 00
//				Code # 5 5.000 (1) 00
//				Code # 10 227.000 (3) 00
//				Code # 20 68.000 (2) 00
//				Code # 27 181.600 (4) 00
//				Code # 28 68.400 (4) 00
//				Code # 29 3.000 (1) 00
//				Code # 30 4205000.000 (7) 00
				new String[] { "79118", "0", "25.0", "5", "227", "68", "181.6",
						"68.40", "3", "4205000" },

//				Taxa-Record 3 : Taxonomic Code [1]# 69459 (5)
//				Code # 2 0.000 (1) 00
//				Code # 3 25.000 (3) 00
//				Code # 5 5.000 (1) 00
//				Code # 10 113.000 (3) 00
//				Code # 20 68.000 (2) 00
//				Code # 27 90.400 (3) 00
//				Code # 28 68.400 (4) 00
//				Code # 29 3.000 (1) 00
//				Code # 30 4202500.000 (7) 00						
				new String[] { "69459", "0", "25.0", "5", "113", "68", "90.4",
						"68.40", "3", "4202500" },
						
//				Taxa-Record 4 : Taxonomic Code [1]# 159668 (6)
//				Code # 2 0.000 (1) 00
//				Code # 3 25.000 (3) 00
//				Code # 10 16.000 (2) 00
//				Code # 17 1.000 (1) 00
//				Code # 20 68.000 (2) 00
//				Code # 27 12.800 (3) 00
//				Code # 28 68.400 (4) 00
//				Code # 29 3.000 (1) 00
//				Code # 30 4337500.000 (7) 00						
				new String[] { "159668", "0", "25.0", "16", "1", "68", "12.8",
						"68.40", "3", "4337500" },

//				Taxa-Record 5 : Taxonomic Code [1]# 88803 (5)
//				Code # 2 0.000 (1) 00
//				Code # 3 25.000 (3) 00
//				Code # 10 16.000 (2) 00
//				Code # 20 68.000 (2) 00
//				Code # 27 12.800 (3) 00
//				Code # 28 68.400 (4) 00
//				Code # 29 3.000 (1) 00
//				Code # 30 4282040.000 (7) 00						
				new String[] { "88803", "0", "25.0", "16", "68", "12.8",
						"68.40", "3", "4282040" },
						
//				Taxa-Record 6 : Taxonomic Code [1]# 88803 (5)
//				Code # 2 0.000 (1) 00
//				Code # 3 25.000 (3) 00
//				Code # 5 2.000 (1) 00
//				Code # 10 535.000 (3) 00
//				Code # 20 68.000 (2) 00
//				Code # 27 428.000 (4) 00
//				Code # 28 68.400 (4) 00
//				Code # 29 3.000 (1) 00
//				Code # 30 4282040.000 (7) 00						
				new String[] { "88803", "0", "25.0", "2", "535", "68", "428.0",
						"68.40", "3", "4282040" },
						
//				Taxa-Record 7 : Taxonomic Code [1]# 88803 (5)
//				Code # 2 0.000 (1) 00
//				Code # 3 25.000 (3) 00
//				Code # 5 43.000 (2) 00
//				Code # 10 32.000 (2) 00
//				Code # 20 68.000 (2) 00
//				Code # 27 25.600 (3) 00
//				Code # 28 68.400 (4) 00
//				Code # 29 3.000 (1) 00
//				Code # 30 4282040.000 (7) 00						
				new String[] { "88803", "0", "25.0", "43", "32", "68", "25.6",
						"68.40", "3", "4282040" },
						
//				Taxa-Record 8 : Taxonomic Code [1]# 85371 (5)
//				Code # 2 0.000 (1) 00
//				Code # 3 25.000 (3) 00
//				Code # 5 2.000 (1) 00
//				Code # 10 16.000 (2) 00
//				Code # 20 68.000 (2) 00
//				Code # 27 12.800 (3) 00
//				Code # 28 68.400 (4) 00
//				Code # 29 3.000 (1) 00
//				Code # 30 4282010.000 (7) 00						
				new String[] { "85371", "0", "25.0", "2", "16", "68", "12.8",
						"68.40", "3", "4282010" } };
	}

	private String[][] createProfileValues() {
		return new String[][] {
//				From NOAA example (first number is depth so readings start 
//				with second number. Format is 
//				value (significant figures) quality 
//				where a quality of 00 means accepted
//				0.0 00 8.960 (3) 00 30.900 (4) 00 6.746 (3) 00 0.646 (2) 00 20.500 (3) 00 0.002 (2) 8.100 (3) 00
				new String[] { "8.96", "30.90", "6.75", "0.65", "20.5",
						"0.002", "8.10" },
//				10.0 00 8.950 (3) 00 30.900 (4) 00 6.700 (3) 00 0.707 (2) 00 12.300 (3) 00 0.002 (2) 8.100 (3) 00
				new String[] { "8.95", "30.90", "6.70", "0.71", "12.3",
						"0.002", "8.10" },
//				25.0 00 0.900 (2) 00 31.910 (4) 00 8.615 (3) 00 0.902 (2) 00 15.375 (3) 00 0.002 (2) 8.100 (3) 00
				new String[] { "0.90", "31.91", "8.62", "0.90", "15.4",
						"0.002", "8.10" },
//				50.0 00 -1.230 (3) 00 32.410 (4) 00 7.285 (3) 00 1.168 (3) 00 25.625 (3) 00 0.182 (2) 8.050 (3) 00			
				new String[] { "-1.23", "32.41", "7.28", "1.17", "25.6",
						"0.18", "8.05" } };
	}

	private String[][][] createMetadataStrings() {
//		From NOAA example. Code # 8 means depth precision
//		Measured Variable # 3 Information Code # 8 58. (2)
//		Measured Variable # 4 Information Code # 8 29. (2)
//		Measured Variable # 6 Information Code # 8 29. (2)
//		Measured Variable # 7 Information Code # 8 29. (2)		
		return new String[][][]{
				new String[0][0],
				new String[0][0],
				new String[][]{new String[]{"58"}},
				new String[][]{new String[]{"29"}},
				new String[][]{new String[]{"29"}},
				new String[][]{new String[]{"29"}},
				new String[0][0]
		};
	}

}
