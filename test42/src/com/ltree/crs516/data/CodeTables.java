package com.ltree.crs516.data;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import static com.ltree.crs516.data.DataConstants.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Each of the various tables will be represented by a CodeTable object. Since we
 * don't need all of them in all data sets, many will be lazily loaded. They
 * will all be stored in a HashMap<String, CodeTable> where the key is the name
 * of the file on disk they go with.
 * 
 * @author crs516 development team
 * 
 */
final class CodeTables {
	
	//No instances.
	private CodeTables(){}

	/**
	 * Names of the code files.
	 */
	private final static ArrayList<String> codeFileNames = new ArrayList<String>();

	/**
	 * Contains codes from country_list.txt. Lazily loaded.
	 */
	private final static HashMap<String, String> countryCodes = new HashMap<String, String>();;

	/**
	 * Contains depth dependent variable codes from
	 * Depth_dependent_variable.txt. The String[] is of length 2 and has the
	 * form {variableName, units}.
	 */
	private final static HashMap<Integer, String[]> depthVariableCodes = new HashMap<Integer, String[]>();;

	private final static Logger logger = LoggerFactory
			.getLogger(CodeTables.class);
	
	/**
	 * Principal investigator codes.
	 */
	private final static HashMap<Integer, String> piCodes = new HashMap<Integer, String>();

	/**
	 * Contains codes from flags_for_entire_cast_as_fn_of_variable.txt.
	 */
	private final static HashMap<Integer, String> qualityFlagCodes = new HashMap<Integer, String>();

	/**
	 * Contains codes for secondary headers.
	 */
	private final static HashMap<Integer, String> secondaryHeaders = new HashMap<Integer, String>();

	/**
	 * Will cache the names of the files that correspond to abbreviations of the
	 * table names as key/value pairs, e.g. key: s_1_ value: s_1_accession.txt.
	 * It starts off empty.
	 */
	private final static Hashtable<String, String> tableNames = new Hashtable<String, String>();

	/**
	 * This HashMap will be a cache of codeTable objects representing individual
	 * tables (lazily populated).
	 */
	private final static HashMap<String, CodeTable> tables = new HashMap<String, CodeTable>();

	/**
	 * Contains codes from variableSpecificCodes.txt and their meanings. It will
	 * be populated as needed.
	 */
	private final static HashMap<Integer, String[]> variableSpecificCodes = new HashMap<Integer, String[]>();
	
	/**
	 * These tables are not at the NOAA site or in the documentation but some
	 * scientists have used them :(
	 */
	private final static List<String> KNOWN_MISSING_TABLES = Arrays.asList(
			"b_1_","b_2_","b_3_", "b_9_", "b_16_",
			"s_5_", "s_7_", "s_8_", "s_10_", "s_11_","s_13_",
			"s_15_", "s_22_", "s_23_", "s_24_", "s_25_", "s_30_", "s_31_",
			"s_33_","s_34_" ,"s_46_", "s_71_","s_77_", "s_87_", "s_88_", "s_94_", "s_98_", "s_99_", "t_2_",
			"t_3_","t_4_", "t_10_", "t_13_", "t_29_","v_15_", "v_16_","v_17_", "v_18_");

	/**
	 * Might as well pre-load this information since it is almost always needed.
	 */
	static {
		populateCountryCodes();
		populateDepthVariableCodes();
		populateQualityFlagCodes();
		populateSecondaryHeaders();
		populateCodeFileNames();
		populatePICodes();
	}

	/**
	 * Looks up the meaning of a biological header code. In the code tables
	 * biological header files have the prefix “b”.
	 * 
	 * @param code
	 *            , an int, the biological header code.
	 * @param codeVal
	 *            , an int, the value of the code being looked up.
	 * 
	 * @return a String, the meaning of the code
	 */
	static String getBiolHeaderMeaning(int code, int codeVal) {
		String ans = lookup(codeVal, "b_" + code + "_");
		if (ans == null) {
			ans = "";
		}
		return ans;
	}

	/**
	 * Looks up the country that goes with a country code.
	 * 
	 * @param codeStr
	 *            a String, the country code
	 * @return a String, the name of the country that corresponds to the code.
	 */
	static String getCountry(String codeStr) {
		return countryCodes.get(codeStr);
	}

	/**
	 * Looks up the depth variable that goes with a code
	 * 
	 * @param number
	 * @return A String, the name of the variable that goes with the code
	 */
	static String getDepthVariableName(int number) {
		String[] answer = depthVariableCodes.get(number);
		if (answer != null) {
			return answer[0];
		} else {
			return null;
		}
	}

	/**
	 * Looks up the units of a depth variable
	 * 
	 * @param number
	 * @return A String, the units of that variable
	 */
	static String getDepthVariableUnit(int number) {
		String[] answer = depthVariableCodes.get(number);
		if (answer != null) {
			return answer[1];
		} else {
			return null;
		}
	}

	/**
	 * Looks up the meaning of a variable specific header code. This information
	 * comes from files that start with 'v_'.
	 * 
	 * @param varCode
	 *            Code
	 * @param varCodeVal
	 *            Value of code
	 * @return meaning of the value of the code.
	 */
	static String getMetaMeaning(int varCode, int varCodeVal) {
		String ans = lookup(varCodeVal, "v_" + varCode + "_");
		if (ans == null) {
			ans = "";
		}
		return ans;
	}

	/**
	 * Looks up a primary investigator given the code.
	 * 
	 * @param piCode
	 *            an int, the primary investigator's code
	 * @return a String, the PI's name
	 */
	static String getPI(int piCode) {
		return piCodes.get(piCode);
	}

	/**
	 * Looks up the meaning of a quality control flag.
	 * 
	 * @param qualityControlFlagInt
	 *            an int, the flag code.
	 * @return A String, the meaning of the flag.
	 */
	static String getqualityFlag(int qualityControlFlagInt) {
		return qualityFlagCodes.get(qualityControlFlagInt);
	}

	
	/**
	 * Looks up the value that goes with a secondary header code.
	 * This tells what it is, e.g., meteorological data, sea floor depth, 
	 * instrument, ship (platform), institute, and project ...
	 * 
	 * @param code
	 *            an int, the code.
	 * @return A String, the value.
	 */
	static String getsecondaryHeader(int code) {
		return secondaryHeaders.get(code);
	}

	/**
	 * Looks up the meaning of a secondary header code value
	 * 
	 * @param code
	 *            , an int, the code, i.e., the n in the 's_n_' part of the file
	 *            name.
	 * @param codeVal
	 *            an int, the code value whose meaning you seek.
	 * @return A String, the meaning of the code
	 */
	static String getSecHeaderMeaning(int code, int codeVal) {
		String ans = null;
		ans = lookup(codeVal, "s_" + code + "_");
		if (ans == null) {
			ans = "" + codeVal;
		}
		return ans;
	}

	/**
	 * Called to lookup the full name of a table file on disk given the first
	 * few characters. The answer is cached in the tableName HashTable so that
	 * future searches are quicker. An empty String in the HashTable implies
	 * that we have searched and failed to find the file before so no need to
	 * try again.
	 * 
	 * @param abbrev
	 *            , a String, the first few letters of the table name.
	 * @return a String composed by using the headings and data
	 */
	static String getTableName(String abbrev) {
		abbrev = abbrev.trim();
		String tableName = tableNames.get(abbrev);
		if (tableName == null) {
			tableName = "";
			tableNames.put(abbrev, "");
			// look up the table name in dir.txt and cache it.
			// The hunt
			for (String line : codeFileNames) {
				line = line.trim();
				if (line.length() >= abbrev.length()) {
					if (abbrev.equals(line.substring(0, abbrev.length()))) {
						// We have a match
						tableNames.put(abbrev, line);
						tableName = line;
						break; // We got what we want
					}
				}
			}
		}
		if (tableName.equals("")) {
			// there is no such data in the file
			// Maybe it was not an abbreviation but the full name of the file
			logger.info("No table name corresponding to abbreviation {}. Maybe it is not an abbreviation.",abbrev);
			return abbrev;
		}
		return tableName;
	}

	/**
	 * Looks up the meaning of a taxa code value.
	 * 
	 * @param code
	 *            an int, the integer code.
	 * @param codeVal
	 *            a double, the code value to be looked up.
	 * @return a String, the meaning of the code value.
	 */
	static String getTaxaMeaning(int code, double codeVal) {
		String ans = lookupDouble(codeVal, "t_" + code + "_");
		if (ans == null) {
			ans = "";
		}
		return ans;
	}

	/**
	 * Looks up the meaning of a taxa code value
	 * 
	 * @param code
	 * @param codeVal
	 * @return A String, the meaning of the code
	 */
	static String getTaxaMeaning(int code, int codeVal) {
		String ans = lookup(codeVal, "t_" + code + "_");
		if (ans == null) {
			ans = "";
		}
		return ans;
	}

	/**
	 * Looks up the meaning of a variable specific header code
	 * 
	 * @param varCode
	 * @return a A String[], the variable corresponding to the code
	 */
	static synchronized String[] getVariableSpecificCodes(Integer varCode) {
		if (variableSpecificCodes.size() == 0) {
			populateVariableSpecificCodes();
		}
		String[] ans = variableSpecificCodes.get(varCode);
		if (ans == null) {
			ans = new String[] { "unknown", "unknown" };
			logger.warn("Got an unknown variable specific code, {}", varCode);
		}
		return ans;
	}

	/**
	 * Look up the meaning of an integer code in the corresponding code table.
	 * 
	 * @param key
	 *            , an int, the integer code.
	 * @param tableName
	 *            , a String, the name of the table. The first few letters will
	 *            do.
	 * @return a String, the value corresponding to the key input.
	 */
	private static String lookup(int key, String tableName) {
		if (tableName == null) {
			return null;
		}
		if(KNOWN_MISSING_TABLES.contains(tableName)){
			return null; //it is hopeless;
		}
		tableName = getTableName(tableName);// in case it was an abbreviation
		if (tableName == null) {
			return null;
		}
		// check to see if the CodeTable object already exists in the cache.
		CodeTable table = tables.get(tableName);
		if (table == null && !KNOWN_MISSING_TABLES.contains(tableName)) {
			try {
				table = new CodeTable(tableName);
			} catch (IOException e) {
				logger.error("Can't create the table {}", tableName);
				return null;
			}
			tables.put(tableName, table);
		} else if(table == null){
			return null;
		}
		return table.get(key);
	}

	/**
	 * Looks up the meaning of a double code value in the corresponding
	 * codeTable. Only table t_28 has keys that are doubles.
	 * 
	 * @param codeValue
	 *            a double, the value of the code.
	 * @param tableName
	 *            a String, the name of the table.
	 * @return a String, the meaning of the code value.
	 */
	private static String lookupDouble(double codeValue, String tableName) {
		if (tableName == null) {
			return null;
		}
		if(KNOWN_MISSING_TABLES.contains(tableName)){
			return null; //it is hopeless;
		}
		tableName = getTableName(tableName);// in case it was an abbreviation
		if (tableName == null) {
			return null;
		}
		// check to see if the CodeTable object already exists in the cache.
		CodeTable table = tables.get(tableName);
		if (table == null) {
			// create a new codeTable object
			try {
				table = new CodeTable(tableName);
			} catch (IOException e) {
				logger.error("Can't create the table {}", tableName, e);
				return null;
			}
			tables.put(tableName, table);
		}
		return table.getDouble(codeValue);
	}

	/**
	 * Called to load the names of all the code file names
	 */
	private static void populateCodeFileNames() {
		String line;
		try(BufferedReader in = new BufferedReader(new FileReader(
				CODE_TABLES_DIR+"dir.txt"));) {
			while ((line = in.readLine()) != null) {
				if (line.length() > 0) {
					codeFileNames.add(line);
				}
			}
		} catch (IOException e) {
			logger.error("Failed to read a file name, {}", e.getMessage(), e);
		}
	}

	/**
	 * Reads in the country codes from country_list.txt.
	 * 
	 */
	private static void populateCountryCodes() {
		String line;
		try(BufferedReader in = new BufferedReader(new FileReader(
				CODE_TABLES_DIR+"country_list.txt"));) {
			while ((line = in.readLine()) != null) {
				if (line.length() > 0) {
					countryCodes.put(line.substring(0, 2), line.substring(3)
							.trim());
				}
			}
		} catch (IOException e) {
			logger.warn("Exception in populateCountryCodes! {}", e.getMessage());
		}
	}

	/**
	 * Reads in the depth dependent variable codes.
	 * 
	 */
	private static void populateDepthVariableCodes() {
		String line;
		try(BufferedReader in = new BufferedReader(new FileReader(
				CODE_TABLES_DIR+"Depth-dependent_variables.txt"));) {
			while ((line = in.readLine()) != null) {
				if (line.length() > 0) {
					String[] tokens = line.split(",");
					String codeString = tokens[0];
					int i;
					try {
						i = Integer.parseInt(codeString.trim());
					} catch (NumberFormatException e) {
						continue;
					}
					String variableName = tokens[1].trim();
					String variableUnits = tokens[2].trim();
					depthVariableCodes.put(i, new String[] { variableName,
							variableUnits });
				}
			}
		} catch (IOException e) {
			logger.error("Exception in populateVariableCodes!, {} ", e.getMessage());
		}
	}

	private static void populatePICodes() {
		String line;
		try (BufferedReader in = new BufferedReader(new FileReader(
				CODE_TABLES_DIR+"primary_investigator_list.txt"));){
			while ((line = in.readLine()) != null) {
				if (line.length() > 0) {
					String[] tokens = line.split(",");
					if (tokens.length != 2) {
						logger.info("Strange line in primary_investigator_list.txt, {}",line);
					}
					int key = Integer.parseInt(tokens[0].trim());
					String value = tokens[1].trim();
					piCodes.put(key, value);
				}
			}
		} catch (IOException e) {
			logger.error("Exception in populateVariableSpecificCodes! ", e);
		}
	}

	/**
	 * Reads in the quality flag codes. See the file
	 * Definition_of_Quality_Flags.txt in the code tables.
	 * 
	 */
	private static void populateQualityFlagCodes() {
		String line;
		try (BufferedReader in = new BufferedReader(new FileReader(
				CODE_TABLES_DIR+"flags_for_entire_cast_as_fn_of_variable.txt"));){
			while ((line = in.readLine()) != null) {
				if (line.length() > 0) {
					qualityFlagCodes.put(Integer.parseInt(line.substring(0, 1)
							.trim()), line.substring(2).trim());
				}
			}
		} catch (IOException e) {
			logger.error("Exception in populateQualityFlagCodes!, {} ", e.getMessage(),e);
		}
	}

	/**
	 * Reads in the codes for secondary headers. See
	 * "Table 4. List of secondary header variables in WOD09" of readme.pdf of
	 * the WOD09 documentation.
	 */
	private static void populateSecondaryHeaders() {
		String line;
		try(BufferedReader in = new BufferedReader(new FileReader(
				CODE_TABLES_DIR+"secondaryHeaders.txt"));) {
			while ((line = in.readLine()) != null) {
				if (line.length() > 0) {
					String[] pieces = line.split("\t");
					int key = Integer.parseInt(pieces[0].trim());
					String value = pieces[1].trim();
					secondaryHeaders.put(key, value);
				}
			}
		} catch (IOException e) {
			logger.error("Exception in populateSecondaryHeaders!, {} "
					,e.getMessage(),e);
		}
	}

	/**
	 * Reads in the variable specific codes from variableSpecificCodes.txt.
	 * 
	 */
	private static void populateVariableSpecificCodes() {
		String line;
		try(BufferedReader in = new BufferedReader(new FileReader(
				CODE_TABLES_DIR+"variableSpecificCodes.txt"));) {
			while ((line = in.readLine()) != null) {
				if (line.length() > 0) {
					int key = Integer.parseInt(line.substring(0, 4).trim());
					String header = line.substring(4, 31).trim();
					String meaning = "";
					if (line.length() > 49) {
						meaning = line.substring(50).trim();
					}
					variableSpecificCodes.put(key, new String[] { header,
							meaning });
				}
			}
		} catch (IOException e) {
			logger.error("Exception in populateVariableSpecificCodes! ", e);
		}
	}

}
