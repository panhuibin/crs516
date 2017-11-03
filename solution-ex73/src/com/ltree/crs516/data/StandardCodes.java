package com.ltree.crs516.data;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static com.ltree.crs516.data.DataConstants.*;

/**
 * Reads and caches the values of the various codes used in the WOD file format.
 * @author crs 516 development team.
 *
 */
public final class StandardCodes {
	
	/**
	 * Not to be instantiated.
	 */
	private StandardCodes(){}

	static final Logger logger = LoggerFactory.getLogger(StandardCodes.class);

	/**
	 * Contains biology header variables from Table 6 of WOD09 Documentation at
	 * http://www.nodc.noaa.gov/OC5/WOD09/docwod09.html.
	 */
	static final HashMap<Integer, String> biologyHeaders = new HashMap<Integer, String>();

	/**
	 * Contains codes from http://www.nodc.noaa.gov/OC5/WOD05/TAXVAR.TXT
	 */
	static final HashMap<Integer, String> taxVar = new HashMap<Integer, String>();

	/**
	 * Contains codes from table 12 part b, Observed Level Flags. See WOD09
	 * Documentation at http://www.nodc.noaa.gov/OC5/WOD09/docwod09.html
	 */
	static final HashMap<Integer, String> qualityFlagObservedCodes = new HashMap<Integer, String>();

	/**
	 * Contains codes from table 12 part c, Standard Level Flags. See WOD09
	 * Documentation at http://www.nodc.noaa.gov/OC5/WOD09/docwod09.html
	 */
	static final HashMap<Integer, String> qualityFlagStandardCodes = new HashMap<Integer, String>();

	/**
	 * Contains codes from table 12 part d, Biological data flags. See WOD09
	 * Documentation at http://www.nodc.noaa.gov/OC5/WOD09/docwod09.html
	 */
	static final HashMap<Integer, String> qualityFlagBiology = new HashMap<Integer, String>();

	/**
	 * Contains codes from Table 14. Standard levels and depths (meters). See
	 * WOD09 Documentation at http://www.nodc.noaa.gov/OC5/WOD09/docwod09.html
	 */
	static final HashMap<Integer, Integer> standardDepths = new HashMap<Integer, Integer>();

	/**
	 * Contains Instrument Codes from code table v_5_instrument.
	 */
	static final HashMap<Integer, String> tsProbe = new HashMap<Integer, String>();

	/**
	 * Contains method codes from code table: v_6_methods. See WOD09
	 * Documentation at http://www.nodc.noaa.gov/OC5/WOD09/docwod09.html
	 */
	static final HashMap<Integer, String> methods = new HashMap<Integer, String>();

	/**
	 * Contains codes from Originator’s Units, table v_8_orig_units. See WOD09
	 * Documentation at http://www.nodc.noaa.gov/OC5/WOD09/docwod09.html
	 */
	static final HashMap<Integer, String> originatorsVariableUnits = new HashMap<Integer, String>();

	// Eagerly load commonly used codes.
	static {
		populateQualityFlagStandardCodes();
		populateQualityFlagObservedCodes();
		populateQualityFlagBiology();
		populateBiologyHeaders();
		populateTSProbeCodes();
		populateMethodCodes();
		populateTaxVarCodes();
		populateStandardDepths();
		populateOriginatorsVariableUnits();
	}

	/**
	 * Read in the codes from Table 6. List of biological header variables. See
	 * WOD09 Documentation at http://www.nodc.noaa.gov/OC5/WOD09/docwod09.html
	 */
	private static void populateBiologyHeaders() {
		String line;
		try (BufferedReader in = new BufferedReader(new FileReader(
				CODE_TABLES_DIR + "biologyHeaders_table_6.txt"));) {
			while ((line = in.readLine()) != null) {
				if (line.startsWith("#")) {
					continue; // comment
				}
				if (line.length() > 0) {
					String[] pieces = line.split("\t");
					int key = Integer.parseInt(pieces[0].trim());
					String value = pieces[1].trim();
					biologyHeaders.put(key, value);
				}
			}
		} catch (IOException e) {
			logger.error("Exception in populateBiologyHeaders! ", e);
		}
	}

	/**
	 * Reads in the standard level quality flag codes. See table 12 part c of
	 * the WOD09 Documentation at
	 * http://www.nodc.noaa.gov/OC5/WOD09/docwod09.html.
	 */
	private static void populateQualityFlagStandardCodes() {
		String line;
		try (BufferedReader in = new BufferedReader(new FileReader(
				CODE_TABLES_DIR + "standardLevelQualityFlags.txt"));) {
			while ((line = in.readLine()) != null) {
				if (line.length() > 0) {
					int key = Integer.parseInt(line.substring(0, 2).trim());
					String value = line.substring(2).trim();
					qualityFlagStandardCodes.put(key, value);
				}
			}
		} catch (IOException e) {
			logger.error("Exception in Exception in " +
					"populateQualityFlagStandardCodes! ", e);
		}
	}

	/**
	 * Reads in the observed level quality flag codes. See table 12 part b of
	 * the WOD09 Documentation at
	 * http://www.nodc.noaa.gov/OC5/WOD09/docwod09.html.
	 * 
	 */
	private static void populateQualityFlagObservedCodes() {
		String line;
		try (BufferedReader in = new BufferedReader(new FileReader(
				CODE_TABLES_DIR + "observed_level_flags.txt"));) {
			while ((line = in.readLine()) != null) {
				if (line.length() > 0) {
					String[] tokens = line.split(",");
					if (tokens.length < 2) {
						continue;
					}
					int key = Integer.parseInt(tokens[0].trim());
					String value = tokens[1].trim();
					qualityFlagObservedCodes.put(key, value);
				}
			}
		} catch (IOException e) {
			logger.error("Exception in populateObservedFlagStandardCodes! ", e);
		}
	}

	/**
	 * Reads in the Quality flag codes for biology
	 */
	private static void populateQualityFlagBiology() {
		String line;
		try (BufferedReader in = new BufferedReader(new FileReader(
				CODE_TABLES_DIR + "biological_data_flags.txt"));) {
			while ((line = in.readLine()) != null) {
				if (line.length() > 0) {
					String[] tokens = line.split(",");
					if (tokens.length < 2) {
						continue;
					}
					int key = Integer.parseInt(tokens[0].trim());
					String value = tokens[1].trim();
					qualityFlagBiology.put(key, value);
				}
			}
		} catch (IOException e) {
			logger.error("Exception in populateQualityFlagBiology! ", e);
		}
	}

	/**
	 * Reads in the Temperature sensor (TS-probe) codes.
	 */
	private static void populateTSProbeCodes() {
		String line;
		try (BufferedReader in = new BufferedReader(new FileReader(
				CODE_TABLES_DIR + "v_5_instrument.txt"));) {
			while ((line = in.readLine()) != null) {
				String[] pieces = line.split(",");
				int key = Integer.parseInt(pieces[0].trim());
				String value = pieces[1].trim();// +": "+pieces[2].trim();
				tsProbe.put(key, value);
			}
		} catch (IOException e) {
			logger.error("Exception in populateTSProbeCodes! ", e);
		}
	}

	/**
	 * Reads in the method codes. See table v_6 of the WOD09 Documentation at
	 * http://www.nodc.noaa.gov/OC5/WOD09/docwod09.html.
	 */
	private static void populateMethodCodes() {
		String line;
		try (BufferedReader in = new BufferedReader(new FileReader(
				CODE_TABLES_DIR + "v_6_methods.txt"));) {
			while ((line = in.readLine()) != null) {
				if (line.length() < 1 || !Character.isDigit(line.charAt(0))) {
					continue;
				}
				String[] pieces = line.split(",");
				int key = Integer.parseInt(pieces[0].trim());
				String value = pieces[1].trim();
				methods.put(key, value);
			}
		} catch (IOException e) {
			logger.error("Exception in populateMethodCodes! ", e);
		}
	}

	/**
	 * Reads in the taxa variable codes.
	 */
	private static void populateTaxVarCodes() {
		String line;
		try (BufferedReader in = new BufferedReader(new FileReader(
				CODE_TABLES_DIR + "taxVars.txt"));) {
			while ((line = in.readLine()) != null) {
				if (line.length() > 0) {
					int key = Integer.parseInt(line.substring(0, 6).trim());
					String value = line.substring(15).trim();
					taxVar.put(key, value);
				}
			}
		} catch (IOException e) {
			logger.error("Exception in populateTaxVarCodes! ", e);
		}
	}

	/**
	 * Reads in the Standard depths. See Table 14. Standard levels and depths
	 * (meters) of the WOD09 Documentation at
	 * http://www.nodc.noaa.gov/OC5/WOD09/docwod09.html.
	 */
	private static void populateStandardDepths() {
		String line;
		try (BufferedReader in = new BufferedReader(new FileReader(
				CODE_TABLES_DIR + "standardDepth.txt"));) {
			while ((line = in.readLine()) != null) {
				if (line.startsWith("#")) {
					continue;
				}
				if (line.length() > 0) {
					String[] tokens = line.split("\t");
					int level = Integer.parseInt(tokens[1].trim());
					int depth = Integer.parseInt(tokens[0].trim());
					standardDepths.put(level, depth);
				}
			}
		} catch (IOException e) {
			logger.error("Exception in populateVariableCodes! ", e);
		}
	}

	/**
	 * Reads in the originator's units. See table v_8 of the WOD09 Documentation
	 * at http://www.nodc.noaa.gov/OC5/WOD09/docwod09.html.
	 * 
	 */
	private static void populateOriginatorsVariableUnits() {
		String line;
		try (BufferedReader in = new BufferedReader(new FileReader(
				CODE_TABLES_DIR + "v_8_orig_units.txt"));) {
			while ((line = in.readLine()) != null) {
				String[] pieces = line.split(",");
				int key = Integer.parseInt(pieces[0].trim());
				String value = pieces[1].trim();
				originatorsVariableUnits.put(key, value);
			}
		} catch (IOException e) {
			logger.error("Exception in Exception in populateVariableCodes! ", e);
		}
	}

}
