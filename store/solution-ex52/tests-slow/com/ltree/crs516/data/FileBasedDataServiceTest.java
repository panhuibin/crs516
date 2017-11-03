package com.ltree.crs516.data;

import static com.ltree.crs516.data.DataConstants.DATA_DIR;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ltree.crs516.domain.BiologyHeader;
import com.ltree.crs516.domain.CharDataEntry;
import com.ltree.crs516.domain.CharDataEntry.CharDataType;
import com.ltree.crs516.domain.Datum;
import com.ltree.crs516.domain.Level;
import com.ltree.crs516.domain.PrincipalInvestigator;
import com.ltree.crs516.domain.SecondaryHeader;
import com.ltree.crs516.domain.Station;
import com.ltree.crs516.domain.Taxa;
import com.ltree.crs516.domain.TaxaList;
import com.ltree.crs516.domain.Variable;
import com.ltree.crs516.domain.VariableMeta;

public class FileBasedDataServiceTest {

	private static Logger logger = LoggerFactory
			.getLogger(FileBasedDataServiceTest.class);

	private File smallFile = new File(DATA_DIR + "test.txt");
	private FileBasedDataService testSubject = createDataService(smallFile);
	private File largerFile = new File(DATA_DIR + "OSDO5711.gz");
	private ExecutorService executor = Executors.newSingleThreadExecutor();

	@Before
	public void setUp() throws Exception {
		testSubject = new FileBasedDataService(null);
		assertNotNull(testSubject);
	}

	/**
	 * Test that the larger test file can be read within 10 seconds
	 */
	@Test(timeout = 10000)
	public void testDataServiceCreation() {
		testSubject = createDataService(largerFile);
	}

	@Test
	public void testIsLoading() {
		testSubject = new FileBasedDataService(null);
		assertFalse("idle DataService claims it is loading",
				testSubject.isLoading());
		// Make executor load a large file.
		Future<Void> future = executor.submit(new Callable<Void>() {
			@Override
			public Void call() {
				testSubject.setDataFile(largerFile);
				testSubject.load();
				while (testSubject.isLoading()) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				return null;
			}
		});
		// Wait for the loading to finish.
		try {
			future.get();
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
		// At this point loading should be done so isLoading() should be false
		assertFalse("idle DataService claims it is loading",
				testSubject.isLoading());
	}

	@Test
	public void testRead() throws RecordNotFoundException, IOException {
		testSubject = createDataService(smallFile);
		Station station = testSubject.read(0);
		assertNotNull(station);
	}

	/**
	 * Test that read method creates a valid station
	 * 
	 * @throws RecordNotFoundException
	 * @throws IOException
	 */
	@Test
	public void testStation() throws RecordNotFoundException, IOException {
		testSubject = createDataService(smallFile);
		Station station = testSubject.read(0);
		assertThat(station.getCountryCode(), equalTo("31"));
		assertThat(station.getCruiseNumber(), equalTo(11203));
		assertThat(station.getLatitudeString(), equalTo("61.93"));
		assertThat(station.getLongitudeString(), equalTo("-172.27"));
		assertThat(station.getYear(), equalTo(1934));
		assertThat(station.getMonth(), equalTo(8));
		assertThat(station.getDay(), equalTo(7));
		assertThat(station.getTime(), equalTo(10.37));
		assertThat(station.getTimeString(), equalTo("10.37"));
		assertThat(station.getLevels(), equalTo(4));
		assertThat(station.getVariablesInProfile(), equalTo(7));
		assertThat(station.getBytesInProfile(), equalTo(1411));
	}

	@Test
	public void testCharacterData() throws RecordNotFoundException, IOException {
		testSubject = createDataService(smallFile);
		Station station = testSubject.read(0);
		CharDataEntry[] charDataEntrys = station.getCharacterDataEntries();
		assertThat(charDataEntrys.length, equalTo(2));
		CharDataEntry charDataEntry = charDataEntrys[0];
		assertThat(charDataEntry.getData(), equalTo("STOCS85A"));
		assertThat(charDataEntry.getType(),
				equalTo(CharDataType.ORIGINATORS_CRUISE));
		charDataEntry = charDataEntrys[1];
		assertThat(charDataEntry.getData(), equalTo(null));
		assertThat(charDataEntry.getType(),
				equalTo(CharDataType.PRINCIPAL_INVESTIGATOR));
		PrincipalInvestigator[] pis = charDataEntry.getPis();
		assertThat(pis.length, equalTo(4));
		PrincipalInvestigator pi = pis[3];
		assertThat(pi.getPiCode(), equalTo(218));
		assertThat(pi.getPiName(), equalTo("JOHNSON; M.W."));
		assertThat(pi.getVariableCode(), equalTo(-5002));
		assertThat(pi.getVariableCodeString(), equalTo(null));
	}

	@Test
	public void testBiology() throws RecordNotFoundException, IOException {
		testSubject = createDataService(smallFile);
		Station station = testSubject.read(0);
		BiologyHeader[] biologyHeaders = station.getBiologyHeaders();
		assertThat(biologyHeaders.length, equalTo(8));
		BiologyHeader biologyHeader = biologyHeaders[0];
		assertThat(biologyHeader.getHeaderCode(), equalTo(2));
		assertThat(biologyHeader.getHeaderString(),
				equalTo("Sampling duration (minutes)"));
		assertThat(biologyHeader.getValueString(), equalTo("18.00"));
		assertThat(biologyHeader.getSigFig(), equalTo(4));
	}

	@Test
	public void testProfile() throws RecordNotFoundException, IOException {
		testSubject = createDataService(smallFile);
		Station station = testSubject.read(0);
		Level[] levels = station.getProfile();
		assertThat(levels.length, equalTo(4));
		Level level = levels[3];
		assertThat(level.getDepthErrorCode(), equalTo(0));
		assertThat(level.getDepthErrorCodeString(), equalTo("accepted value"));
		assertThat(level.getDepthString(), equalTo("50"));
		assertThat(level.getOrigDepthErrorFlag(), equalTo(0));
		assertThat(level.getPrecision(), equalTo(0));
		assertThat(level.getSigFig(), equalTo(2));
		assertThat(level.getTotalFig(), equalTo(2));
		Datum[] data = level.getData();
		assertThat(data.length, equalTo(7));
		Datum datum = data[0];
		assertThat(datum.getOriginatorsFlag(), equalTo(0));
		assertThat(datum.getValueString(), equalTo("-1.23"));
		assertThat(level.getDepth().toString(), equalTo("50"));
	}

	@Test
	public void testSecondaryHeaders() throws RecordNotFoundException,
			IOException {
		testSubject = createDataService(smallFile);
		Station station = testSubject.read(0);
		SecondaryHeader[] secondaryHeaders = station.getSecondaryHeaders();
		assertThat(secondaryHeaders.length, equalTo(7));
		SecondaryHeader secondaryHeader = secondaryHeaders[3];
		assertThat(secondaryHeader.getHeaderCode(), equalTo(7));
		assertThat(secondaryHeader.getHeaderString(),
				equalTo("Originator’s station number"));
		assertThat(secondaryHeader.getValueMeaning(), equalTo("76"));
		assertThat(secondaryHeader.getPrecision(), equalTo(0));
		assertThat(secondaryHeader.getSigFig(), equalTo(2));
		assertThat(secondaryHeader.getTotalFig(), equalTo(2));
	}

	@Test
	public void testTaxaSets() throws RecordNotFoundException, IOException {
		testSubject = createDataService(smallFile);
		Station station = testSubject.read(0);
		List<TaxaList> taxaSets = station.getTaxaSets();
		assertThat(taxaSets.size(), equalTo(8));
		TaxaList taxaList = taxaSets.get(5);
		assertThat(taxaList.getNumberofEntries(), equalTo(10));
		Taxa taxa = taxaList.get(5);
		assertThat(taxa.getCode(), equalTo(20));
		assertThat(taxa.getOriginatorFlag(), equalTo(0));
		assertThat(taxa.getPrecision(), equalTo(0));
		assertThat(taxa.getQualityFlag(), equalTo(0));
		assertThat(taxa.getSigFig(), equalTo(2));
		assertThat(taxa.getTaxVar(), equalTo("Units"));
		assertThat(taxa.getTotalFig(), equalTo(2));
		assertThat(taxa.getValueString(), equalTo("68"));
	}

	@Test
	public void testVariables() throws RecordNotFoundException, IOException {
		testSubject = createDataService(smallFile);
		Station station = testSubject.read(0);
		Variable[] variables = station.getVariables();
		assertThat(variables.length, equalTo(7));
		Variable variable = variables[5];
		assertThat(variable.getCodeString(), equalTo("see documentation"));
		assertThat(variable.getCode(), equalTo(7));
		assertThat(variable.getCodeUnit(), equalTo(null));
		assertThat(variable.getNumMetaData(), equalTo(1));
		assertThat(variable.getQualityFlag(), equalTo(0));
		assertThat(variable.getQualityFlagString(), equalTo("accepted cast"));
		VariableMeta[] metadata = variable.getMetaData();
		VariableMeta vMeta = metadata[0];
		assertThat(vMeta.isValuePresent(), equalTo(true));
		assertThat(vMeta.getCode(), equalTo("8"));
		assertThat(vMeta.getValueMeaning(),
				equalTo("&#0181;mol&#0183;kg<sup>-1</sup>"));
		assertThat(vMeta.getValuePrecision(), equalTo(0));
		assertThat(vMeta.getValueSigFig(), equalTo(2));
		assertThat(vMeta.getValueTotalFig(), equalTo(2));
		assertThat(vMeta.getVarCode(), equalTo("Originator's units"));
		assertThat(vMeta.getVarComment(), equalTo("Submittor's original units"));
	}

	@Test
	public void testSize() {
		testSubject = createDataService(smallFile);
		assertThat(testSubject.size(), equalTo(1));
		testSubject = createDataService(largerFile);
		assertThat(testSubject.size(), equalTo(72));
	}

	private FileBasedDataService createDataService(File file) {
		FileBasedDataService dataService = new FileBasedDataService(null);
		dataService.load(file);
		while (dataService.isLoading()) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return dataService;
	}

	@After
	public void teardown() {
		logger.info("Shutting down executor");
		executor.shutdown();
	}
}
