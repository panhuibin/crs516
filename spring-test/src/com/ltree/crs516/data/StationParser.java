package com.ltree.crs516.data;

import static com.ltree.crs516.data.StandardCodes.biologyHeaders;
import static com.ltree.crs516.data.StandardCodes.qualityFlagBiology;
import static com.ltree.crs516.data.StandardCodes.qualityFlagObservedCodes;
import static com.ltree.crs516.data.StandardCodes.qualityFlagStandardCodes;
import static com.ltree.crs516.data.StandardCodes.standardDepths;
import static com.ltree.crs516.data.StandardCodes.taxVar;

import java.io.IOException;
import java.nio.CharBuffer;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ltree.crs516.domain.BiologyHeader;
import com.ltree.crs516.domain.CharDataEntry;
import com.ltree.crs516.domain.Datum;
import com.ltree.crs516.domain.IStation;
import com.ltree.crs516.domain.Level;
import com.ltree.crs516.domain.PrincipalInvestigator;
import com.ltree.crs516.domain.SecondaryHeader;
import com.ltree.crs516.domain.Station;
import com.ltree.crs516.domain.Station.ProfileType;
import com.ltree.crs516.domain.StationProxy;
import com.ltree.crs516.domain.Taxa;
import com.ltree.crs516.domain.TaxaList;
import com.ltree.crs516.domain.Variable;
import com.ltree.crs516.domain.VariableMeta;

public final class StationParser {

	private static final Logger logger = LoggerFactory.getLogger(StationParser.class);

	/**
	 * For efficiency we shall use a CharBuffer to store the string that has the
	 * data for a station as we parse it.
	 */
	private CharBuffer charBuffer = null;


	private void readBasicInformation(String stationString, IStation station)	
			throws IOException {
		String tmpStr;
		int bytesInProfile;
		char currentChar;
		// Field 1: The WOD Version identifier
		currentChar = charBuffer.get();
		if (currentChar != 'B') {
			String message = "File of wrong format. Got WOD version identifier "
					+ currentChar + " but expected 'B'";
			IOException e = new IOException(message);
			throw e;
		}
		// Fields 2, 3: Bytes in profile
		bytesInProfile = readIntField();
		station.setBytesInProfile(bytesInProfile);
		if (bytesInProfile != stationString.length()) {
			String message = "Length of String does not match expected number of bytes";
			IOException e = new IOException(message);
			throw e;
		}
		// Fields 4, 5. WOD unique cast number
		station.setStationNumber(readIntField());
		// Field 6. Country code
		tmpStr = readAlpha(2);
		station.setCountryCode(tmpStr);
		station.setCountry(CodeTables.getCountry(tmpStr));
		// Fields 7, 8. Cruise number
		station.setCruiseNumber(readIntField());
		// Field 9. Year
		station.setYear(readInt(4));
		// Field 10. Month
		station.setMonth(readInt(2));
		// Field 11. Day
		station.setDay(readInt(2));
		// Field 12. Time
		// the readVariable method will use 12a, b, c, d */
		station.setTime(readVariable());
		// Field 13. Latitude
		station.setLatitude(readVariable());
		// Field 14 Longitude
		station.setLongitude(readVariable());
		// Fields 15, 16. Number of Levels (L)
		station.setLevels(readIntField());
	}

	private int setProfileType(IStation station) throws IOException {
		int profileType;
		// Field 17. Profile type
		profileType = readInt(1);
		switch (profileType) {
		case 0:
			station.setProfileType(ProfileType.OBSERVED);
			break;
		case 1:
			station.setProfileType(ProfileType.STANDARD_LEVEL);
			break;
		default:
			String message = "Unknown Profile Type " + profileType;
			IOException e = new IOException(message);
			throw e;
		}
		return profileType;
	}

	private void readVariables(IStation station) {
		int number;
		int numVar;
		// Field 18. # Variables in profile (N)
		numVar = readInt(2);
		station.setVariablesInProfile(numVar);
		Variable[] variables = new Variable[numVar];
		for (int j = 0; j < numVar; j++) {
			// Read variable
			Variable variable = new Variable();
			variables[j] = variable;
			// Fields 19, 20. Variable code
			number = readIntField();
			variable.setCode(number);
			variable.setCodeString(CodeTables.getDepthVariableName(number));
			variable.setCodeUnit(CodeTables.getDepthVariableUnit(number));
			// Field 21
			int qualityControlFlagInt = readInt(1);
			variable.setQualityFlag(qualityControlFlagInt);
			variable.setQualityFlagString(CodeTables
					.getqualityFlag(qualityControlFlagInt));
			// Fields 22, 23
			int numberMeta = readIntField();
			variable.setNumMetaData(numberMeta);
			if (numberMeta == 0) {
				continue; // Back to field 19
			} else {
				VariableMeta[] metaData = new VariableMeta[numberMeta];
				for (int k = 0; k < numberMeta; k++) {
					// Fields 24, 25
					VariableMeta metaDatum = new VariableMeta();
					Integer varCode = readIntField();
					if (varCode == null) {
						continue; // Missing (A hyphen, '-' is read by 
							//readIntField as a null)
					}
					metaDatum.setCode("" + varCode);
					metaDatum.setVarCode(CodeTables
							.getVariableSpecificCodes(varCode)[0]);
					metaDatum.setVarComment(CodeTables
							.getVariableSpecificCodes(varCode)[1]);
					Datum datum = readVariable();
					if (datum != null) {
						datum.setMeaning(CodeTables.getMetaMeaning(varCode,
								datum.getValue().intValue()));
					}
					metaDatum.setValue(datum);
					metaData[k] = metaDatum;
				}
				variable.setMetaData(metaData);
			}
		}
		station.setVariables(variables);
	}

	private void readProfile(int profileType, IStation station) {	
		int levels = station.getL();
		logger.debug("{} levels ", levels);
		if (charBuffer.position() == station.getBytesInProfile()) {
			levels = 0;
		}
		Level[] profile = new Level[levels];
		for (int k = 0; k < levels; k++) { // make a level}
			if (charBuffer.position() == station.getBytesInProfile()) {
				logger.debug("reached end of station");
				break;
			}
			// Fields 1-4
			Level level = new Level();
			logger.debug("Profie type is " + station.getProfileType());
			if (station.getProfileType() == ProfileType.OBSERVED) {
				Datum depth = readVariable();
				level.setDepth(depth);
				if (depth == null) {
					continue;
				}
				int depthErrorCodeInt = readInt(1);
				depth.setQualityFlag(depthErrorCodeInt);
				if (profileType == 0) {
					depth.setQualityFlagString(qualityFlagStandardCodes
							.get(depthErrorCodeInt));
				} else if (profileType == 1) {
					depth.setQualityFlagString(qualityFlagObservedCodes
							.get(depthErrorCodeInt));
				}
				depth.setOriginatorsFlag(readInt(1));
			} else {
				double val = standardDepths.get((k + 1));
				Datum depth = new Datum.Builder(val, ("" + val).length(),
						("" + val).length(), 0).meaning("Standard depth")
						.build();
				level.setDepth(depth);
			}
			Datum[] data = new Datum[station.getN()];
			logger.debug("expect {} observations", station.getN());
			for (int i = 0; i < data.length; i++) {
				// Fields 7-12
				Datum datum = readVariable();
				if (datum == null) {
					continue;
				}
				datum.setQualityFlag(readInt(1));
				datum.setOriginatorsFlag(readInt(1));
				data[i] = datum;
			}// Data for this level is done.
			level.setData(data);
			profile[k] = level;
		}// All levels are done.
		station.setProfile(profile);
	}

	private void readBiologyHeaders(int profileType, IStation station) {
		// Field 1
		Integer totalBiologyHeaderBytes = readIntField();
		if (totalBiologyHeaderBytes != null) {
			// Field 3, 4
			int numEntries = readIntField();
			station.setNumBiologyHeaders(numEntries); // B in the documentation
			BiologyHeader[] biolHeaders = new BiologyHeader[numEntries];
			for (int k = 0; k < numEntries; k++) {
				BiologyHeader header = new BiologyHeader();
				// Fields 5, 6
				int code = readIntField();
				header.setHeaderCode(code);
				header.setHeader(biologyHeaders.get(code));
				if (biologyHeaders.get(code) != null) {
					header.setHeaderString(biologyHeaders.get(code));
				}
				// Fields 7, 8, 9, 10
				Datum datum = readVariable();
				header.setValue(datum);
				header.setValueMeaning(CodeTables.getBiolHeaderMeaning(code,
						datum.getValue().intValue()));
				biolHeaders[k] = header;
			}
			station.setBiologyHeaders(biolHeaders);
			// Taxonomic and Integrated Parameters exist only if there are
			// biology headers.
			readTaxonomicData(profileType, station);
		}// End Biology Headers.
	}

	private void readTaxonomicData(int profileType, IStation station) {		
	// Fields 1, 2
		int numberofTaxaSets = readIntField(); // called T in documentation
		station.setTaxaDataPresent(false);
		if (numberofTaxaSets > 0) {
			station.setTaxaDataPresent(true);
			station.setNumTaxaSets(numberofTaxaSets);
			ArrayList<TaxaList> taxaSets = new ArrayList<TaxaList>();
			for (int m = 0; m < numberofTaxaSets; m++) {
				TaxaList taxaList = new TaxaList();
				// TaxaList will contain multiple entries (Taxa).
				// Fields 3, 4 called X in the documentation
				int numberofEntries = readIntField();
				for (int n = 0; n < numberofEntries; n++) {
					Taxa taxa = new Taxa();
					// Fields 5, 6
					int code = readIntField();
					taxa.setCode(code);
					if (taxVar.get(code) != null) {
						taxa.setTaxVar(taxVar.get(code));
					}
					taxa.setTaxVar(taxVar.get(code));
					Datum datum = readVariable();
					taxa.setValue(datum);
					taxa.setValueMeaning(CodeTables.getTaxaMeaning(code, datum
							.getValue().intValue()));
					if (code == 28) {
						// The CBV (Comparable Biological Value ) calculation
						// method table uses doubles for the keys.
						taxa.setValueMeaning(CodeTables.getTaxaMeaning(code,
								datum.getValue().doubleValue()));
					}
					int qualityControlFlagInt = readInt(1);
					datum.setQualityFlag(qualityControlFlagInt);
					if (profileType == 0) {
						datum.setQualityFlagString(qualityFlagStandardCodes
								.get(qualityControlFlagInt));
					} else if (profileType == 1) {
						datum.setQualityFlagString(qualityFlagObservedCodes
								.get(qualityControlFlagInt));
					}
					if (code == 27) {
						// Special case of CBV Taxa code 27
						datum.setQualityFlagString(qualityFlagBiology
								.get(qualityControlFlagInt));
					}
					int originatorflag = readInt(1);
					if (originatorflag != 0) {
						logger.warn(
								"originator's flag should always be 0! I have {}",
								originatorflag);
					}
					datum.setOriginatorsFlag(originatorflag);
					taxaList.add(taxa);
				}// End of this taxa entry
				taxaSets.add(taxaList);
			}// End of this taxalist
			station.setTaxaSets(taxaSets);
		}// End of creating the ArrayList<TaxaList>
	}

	private void readSecondaryHeaders(IStation station) {		
		// Fields 1, 2
		Integer totalSecondaryHeaderBytes = readIntField();
		if (totalSecondaryHeaderBytes == null) {
			station.setSecondaryHeadersPresent(false);
		} else {
			station.setSecondaryHeadersPresent(true);
			// Fields 3, 4
			int numEntries = readIntField(); // Called S in documentation
			station.setNumSecondaryHeaders(numEntries);
			SecondaryHeader[] secHeaders = new SecondaryHeader[numEntries];
			for (int k = 0; k < numEntries; k++) {
				SecondaryHeader header = new SecondaryHeader();
				// Fields 5, 6
				int code = readIntField();
				header.setHeaderCode(code);
				header.setHeaderString(CodeTables.getsecondaryHeader(code));
				// Fields 7, 8, 9, 10
				Datum datum = readVariable();
				header.setValue(datum);
				header.setValueMeaning(CodeTables.getSecHeaderMeaning(code,
						datum.getValue().intValue()));
				secHeaders[k] = header;
			}
			station.setSecondaryHeaders(secHeaders);
		}// End Secondary Headers
	}

	private void readCharacterData(String stationString, IStation station)
			throws IOException {
		// Field 1, 2
		Integer totalCharDataBytes = readIntField();
		if (totalCharDataBytes != null) {
			// Field 3
			int numEntries = readInt(1); // Called C in the documentation
			station.setNumCharacterDataEntries(numEntries);
			CharDataEntry[] entries = new CharDataEntry[numEntries];
			for (int m = 0; m < numEntries; m++) {
				CharDataEntry entry = new CharDataEntry();
				// Field 4
				int dataType = readInt(1);
				if (dataType == 1 || dataType == 2) {
					if (dataType == 1) {
						entry.setType(CharDataEntry.CharDataType.ORIGINATORS_CRUISE);
					} else if (dataType == 2) {
						entry.setType(CharDataEntry.CharDataType.ORIGINATORS_STATION_CODE);
					}
					// Field 5
					entry.setData(readStringField(stationString, 2));
					entries[m] = entry;
				} else {
					logger.debug("Reading PI");
					if (dataType != 3) {
						throw new IOException(
								"At this point the datatype should be 3");
					}
					// Field 5
					int numPI = readInt(2);
					PrincipalInvestigator[] pis = new PrincipalInvestigator[numPI];
					for (int k = 0; k < numPI; k++) {
						PrincipalInvestigator pi = new PrincipalInvestigator();
						// Field 6
						int varCode = readIntField();
						pi.setVariableCode(varCode);
						pi.setVariableCodeString(CodeTables
								.getDepthVariableName(varCode));
						int piCode = readIntField();
						pi.setPiCode(piCode);
						pi.setPiName(CodeTables.getPI(piCode));
						pis[k] = pi;
					}
					entry.setPis(pis);
					entry.setType(CharDataEntry.CharDataType.PRINCIPAL_INVESTIGATOR);
					entries[m] = entry;
				}
				station.setCharacterDataEntries(entries);
			}
		}// End Character Data Header
	}

	/**
	 * Helper method that reads an integer sitting in the next numBytes bytes of
	 * the string. Reading starts at the cursor position.
	 * 
	 * @param line
	 *            , a String representing the station data.
	 * @param bytesInNextField
	 *            , an int, the number of bytes to be read
	 * @return an int sitting in the next bytesInNextField bytes
	 */
	private int readInt(int bytesInNextField) {
		StringBuilder tmpStrBuffer = new StringBuilder();
		for (int j = 0; j < bytesInNextField; j++) {
			tmpStrBuffer.append(charBuffer.get());
		}
		return Integer.parseInt(tmpStrBuffer.toString().trim());
	}

	/**
	 * Helper method for reading integer data. Reads the next byte b, starting
	 * from where the cursor is and then returns the integer in the next b bytes
	 * or null if b is zero.
	 * 
	 * @return an Integer object so that null is a possibility.
	 */
	private Integer readIntField() {
		int bytesInNextField = Integer.parseInt("" + charBuffer.get());
		if (bytesInNextField == 0) {
			return null;
		}
		return readInt(bytesInNextField);
	}

	/**
	 * Helper method to read a number of characters
	 * 
	 * @param str
	 *            String from which to read the characters (represents a
	 *            station)
	 * @param numChars
	 *            the number of characters to read
	 * @return a String with the characters
	 */
	private String readAlpha(int numChars) {
		String tmpStr = "";
		for (int i = 0; i < numChars; i++) {
			tmpStr = tmpStr + charBuffer.get();
		}
		return tmpStr;
	}

	/**
	 * Reads a variable. For compactness, each variable (other than most in the
	 * first header), is written in this fashion: STPVVVVVV[F][O] where: S =
	 * Number of significant digits in a value; T = Total number of digits in a
	 * value. This is usually the same as [S], but can vary in cases of negative
	 * numbers, converted values, and data in which the values are reported with
	 * more precision than an instrument is capable of recording; P = Precision
	 * of a variable (number of places to the right of the decimal point); V =
	 * The actual value. This is read in using [T] and [P]; F = OCL quality
	 * control flag. This is not used for all variables; O = Originators flag.
	 * 
	 * @param str
	 *            a String representing the station data
	 * @return a Datum object with the data.
	 */
	private Datum readVariable() {
		char sigFigChar = charBuffer.get();
		if (sigFigChar == '-') {
			return null;
		} else {
			int sigFig = Integer.parseInt("" + sigFigChar);
			int totalFig = Integer.parseInt("" + charBuffer.get());
			int precision = Integer.parseInt("" + charBuffer.get());
			String value = "";
			for (int j = 0; j < totalFig; j++) {
				value = value + charBuffer.get();
			}
			Double time = makeDouble(value, precision);
			return new Datum.Builder(time, sigFig, totalFig, precision).build();
		}
	}

	/**
	 * Helper method to convert a String to a double with the given precision.
	 * 
	 * @param valueStr
	 *            the String to be converted
	 * @param precision
	 *            the precision to which measurements were done
	 * @return the double equivalent to that String.
	 */
	private double makeDouble(String valueStr, int precision) {
		String doubleStr = valueStr.substring(0, valueStr.length() - precision)
				+ "." + valueStr.substring(valueStr.length() - precision);
		if (doubleStr.charAt(0) == '.') {
			doubleStr = '0' + doubleStr;
		}
		double value = Double.parseDouble(doubleStr);
		return value;
	}

	/**
	 * @return a String obtained by first reading the int numBytes in the next
	 *         byte bytes and then the String in the next numByte bytes.
	 */
	private String readStringField(String str, int bytes) {
		// Bytes in next field
		int numBytes = readInt(bytes);
		if (numBytes == 0) {
			return null;
		}
		// Field 3. data bytes
		String tmpStr = "";
		for (int j = 0; j < numBytes; j++) {
			tmpStr = tmpStr + charBuffer.get();
		}
		return tmpStr;
	}
	
	/**
	 * Given a String in WOD09 format it creates and returns the corresponding
	 * Station object. Refer to table 10 of
	 * http://www.nodc.noaa.gov/OC5/WOD09/docwod09.html
	 * 
	 * @param stationString
	 *            a String in WOD09 format representing a station
	 * @return a Station object with data obtained from the input string
	 * @throws IOException
	 */
	synchronized Station makeStation(String stationString) throws IOException {
		int profileType;
		stationString = stationString.trim();
		logger.debug("Creating Station from {}", stationString);
		Station station = new Station();
		charBuffer = CharBuffer.wrap(stationString);

		/*
		 * The numbers on the comments in the helper methods below follow
		 * section II, File Structure/Format of readme.pdf.
		 */
		readBasicInformation(stationString, station);
		profileType = setProfileType(station);
		readVariables(station);
		readCharacterData(stationString, station);
		readSecondaryHeaders(station);
		readBiologyHeaders(profileType, station);
		readProfile(profileType, station);
		return station;
	}
		
		/**
		 * Creates a ProxyStation
		 * @param stationString the String representation of the Station
		 * @return
		 * @throws IOException
		 */
		public IStation makeProxyStation(String stationString) throws IOException {
			stationString = stationString.trim();
			logger.debug("Creating proxy Station from {}", stationString);
			StationProxy stationProxy = new StationProxy();
			charBuffer = CharBuffer.wrap(stationString);
			readBasicInformation(stationString, stationProxy);
			setProfileType(stationProxy);
			readVariables(stationProxy);
			return stationProxy;
		}
}
