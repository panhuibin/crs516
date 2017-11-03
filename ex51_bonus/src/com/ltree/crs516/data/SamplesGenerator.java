package com.ltree.crs516.data;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ltree.crs516.domain.Datum;
import com.ltree.crs516.domain.PrincipalInvestigator;
import com.ltree.crs516.domain.Station;
import com.ltree.crs516.domain.VariableMeta;

public final class SamplesGenerator {

	private static final Logger logger = LoggerFactory
			.getLogger(SamplesGenerator.class);
	/**
	 * For testing we use the example and sample output given in Appendix 8 of wod09readme.pdf 
	 */
	private static String testStationString = 
			  "B41411567064315112031934 8 744210374426193562-17227140 7110101201013011182205814"
			+ "01118220291601118220291701118220291901024721 8STOCS85A3 41032151032165-500632175"
			+ "-5002321826017117709500110134401427143303931722076210220602291107291110339241812"
			+ "44218001322076141102173301031922205213220112164421037230770950011018191155085272"
			+ "00121100001333125000210110600220220680022722148002284426840002291103002307704282"
			+ "01000210115507911800121100001333125000151105002103302270022022068002274411816002"
			+ "28442684000229110300230770420500000210115506945900121100001333125000151105002103"
			+ "30113002202206800227331904002284426840002291103002307704202500002101166015966800"
			+ "12110000133312500021022016002171101002202206800227331128002284426840002291103002"
			+ "30770433750000191155088803001211000013331250002102201600220220680022733112800228"
			+ "44268400022911030023077042820400021011550888030012110000133312500015110200210330"
			+ "53500220220680022744142800022844268400022911030023077042820400021011550888030012"
			+ "11000013331250001522043002102203200220220680022733125600228442684000229110300230"
			+ "77042820400021011550853710012110000133312500015110200210220160022022068002273311"
			+ "28002284426840002291103002307704282010001100003328960044230900033267500222650033"
			+ "12050023300200332810002201000332895004423090003326700022271003311230023300200332"
			+ "81000220250022290004423191003328620022290003311540023300200332810002205000342-12"
			+ "300442324100332728003321170033125600222180033280500";

	
	private SamplesGenerator(){}
		
	public static Station getTestStation() {
		Station station = null;
		try {
			station = new StationParser().makeStation(testStationString);
		} catch (IOException e) {
			logger.error("Failed to create test station. {}", e.getMessage());
			e.printStackTrace();
		}
		return station;
	}
	
	public static Datum getTestDatum() {
		return getTestStation().getLongitude();
	}

	public static PrincipalInvestigator[] getTestPis() {
		return getTestStation().getCharacterDataEntries()[1].getPis();
	}

	public static Datum[] getTestData() {
		return getTestStation().getProfile()[0].getData();
	}

	public static VariableMeta[] getTestVariableMeta() {
		return getTestStation().getVariables()[2].getMetaData();
	}

}
