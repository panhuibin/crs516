package com.ltree.crs516.data;

import static com.ltree.crs516.data.DataConstants.CODE_TABLES_DIR;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Represents a code table. The tables are available at
 * http://www.nodc.noaa.gov/OC5/WOD09/wod_codes.html Most instances of this
 * class will be loaded lazily to conserve memory resources.
 * 
 * @author crs516 development team
 * 
 */
final class CodeTable {

	private static final Logger logger = LoggerFactory
			.getLogger(CodeTable.class);
	
	/**
	 * Some keys, for example 0000288 would be interpreted by java to be octal
	 * and it would say it is not an int. This parse method avoids that problem
	 * by making it 288, for example.
	 * 
	 * @param input
	 *            a String to be converted to an int
	 * @return an int
	 */
	private static int parseInt(String input) {
		if (input.startsWith("-")) {
			return -1 * parseInt(input.substring(1));
		}
		while (input.length() > 1 && input.startsWith("0")) {
			input = input.substring(1);
		}
		return Integer.parseInt(input);
	}

	/**
	 * Flag indicating there is no need to read the file as all data is in
	 * memory.
	 */
	private volatile boolean allLoaded = false;

	/**
	 * Name of code.
	 */
	private String code_name;

	/**
	 * Caches the rows of the table we have previously read. There is a high
	 * probability of needing them again. In the String[] each String is a
	 * column entry.
	 */
	private Hashtable<Integer, String[]> data = new Hashtable<Integer, String[]>();

	/**
	 * Only one table (t_28) has doubles for the key. This table will not be
	 * used at all in many cases often so start with it as null
	 */
	private Hashtable<Double, String[]> doubleData = null;

	/**
	 * The column heading for most tables (something like "WODC code".)
	 */
	private String heading;

	/**
	 * The column headings for accession tables.
	 */
	private String[] headings;

	/**
	 * In the case of table t_27_cbv each code value has different units. Here
	 * the key is the code and the value is the units.
	 */
	private Hashtable<Integer, String> t_27UnitsTable = null;

	/**
	 * For caching table 27 values.
	 */
	private Hashtable<Integer, String> t_27ValueTable = null;

	/**
	 * The name of the file on disk that holds the table.
	 */
	private String tableName;

	private String units;

	/**
	 * Sole Constructor
	 * 
	 * @param theTableName
	 *            , a String, the name of the file on disk representing the
	 *            table
	 * @throws IOException
	 */
	CodeTable(String theTableName) throws IOException {
		// Table t_27 has a unique structure and needs special
		// treatment.
		tableName = theTableName.trim();
		// Use the tableName to get info about the headings in the table
		// from the tableMetaData.txt file.
		String metaDataLine;
		try (BufferedReader in = new BufferedReader(new FileReader(
				CODE_TABLES_DIR + "tableMetaData.txt"));) {
			while ((metaDataLine = in.readLine()) != null) {
				if (metaDataLine.length() > 0) {
					String[] tokens = metaDataLine.split("\t");
					if (tokens[0].equals(tableName)) {
						tokens[0] = tokens[0].trim();
						code_name = tokens[1].trim();
						String[] headingTokens = tokens[2].split(",");
						if (headingTokens.length > 1) {
							heading = headingTokens[1];
						} else {
							heading = "-";
						}
						headings = tokens[3].split(",");
						if (tableName.substring(0, 4).equals("t_27")) {
							// Table t_27 is unique because each row has
							// different units. The 'heading' is really the 
							//value in t_27.
							try (BufferedReader t_27Reader = new BufferedReader(
									new FileReader(CODE_TABLES_DIR + tableName));) {
								String unitsConcatenated = tokens[3];
								String[] unitsStrings = unitsConcatenated
										.split(",");
								// Get the information from the actual t_27 file.
								t_27ValueTable = new Hashtable<Integer, String>();
								t_27UnitsTable = new Hashtable<Integer, String>();
								String t_27Line = "";
								int counter = 0;
								while ((t_27Line = t_27Reader.readLine()) != null) {
									if (t_27Line.length() == 0) {
										continue;
									}
									char initialChar = t_27Line.trim()
											.charAt(0);
									if (Character.isDigit(initialChar)
											|| initialChar == '-') {
										String[] t_27tokens = t_27Line.trim()
												.split(",");
										int key = parseInt(t_27tokens[0]);
										String value = t_27tokens[1];
										t_27UnitsTable.put(key,
												unitsStrings[counter++].trim());
										t_27ValueTable.put(key, value);
									}
								}
								// Sanity check -- Did we align the units and
								// the values?
								if (t_27UnitsTable.keySet().size() != t_27ValueTable
										.keySet().size()) {
									throw new IllegalStateException(
											"Units and rows of "
													+ "table t_27 do not match");
								}
							}

						} else {
							// tokens[2] is of the form 'units,WMO code 3700'.
							units = tokens[2];
							if (units.contains(",")) {
								units = units.split(",")[1];
							}
						}
						break;
					}
				}
			}
		}
		if (headings == null) {
			// Did not find the table.
			throw new IOException("Can't create the codeTable object for "
					+ tableName);
		}
	}

	/**
	 * Called to lookup a value
	 * 
	 * @param key
	 *            an int, the value of a code
	 * @return a String composed by using the headings and data corresponding to
	 *         the key
	 */
	String get(int key) {
		// The data for this key might have been cached from a previous lookup.
		String[] dataRow = data.get(key);
		if (dataRow == null) {
			// Find the value and cache it.
			if (allLoaded == false) {
				// This table is not all read into memory.
				// The hunt:
				String line;
				try {
					String fileLocation = CODE_TABLES_DIR + tableName;
					if (tableName.equals("t_1_taxa_list.txt")) {
						// This file is large so we split it up with 100
						// key/value pairs per file.
						String fileName;
						if (Math.abs(key) < 100) {
							fileName = "t_1_0" + ("" + key).substring(0, 2);
						} else {
							fileName = "t_1_" + ("" + key).substring(0, 3);
						}
						fileLocation = CODE_TABLES_DIR + "taxaList/" + fileName;
					}
					if (tableName.equals("s_1_accession.txt")) {
						// This file is large so we split it up.
						String fileName;
						if (Math.abs(key) < 10) {
							fileName = "s_1_0" + ("" + key).substring(0, 1);
						} else {
							fileName = "s_1_" + ("" + key).substring(0, 2);
						}
						fileLocation = CODE_TABLES_DIR + "s_1_accession/"
								+ fileName;
					}
					if (tableName.equals("v_1_accession.txt")) {
						// This file is large so we split it up.
						String fileName;
						if (Math.abs(key) < 10) {
							fileName = "v_1_0" + ("" + key).substring(0, 1);
						} else {
							fileName = "v_1_" + ("" + key).substring(0, 2);
						}
						fileLocation = CODE_TABLES_DIR + "v_1_accession/"
								+ fileName;
					}
					if (tableName.equals("b_30_accession.txt")) {
						// This file is large so we split it up.
						String fileName;
						if (Math.abs(key) < 10) {
							fileName = "b_30_0" + ("" + key).substring(0, 1);
						} else {
							fileName = "b_30_" + ("" + key).substring(0, 2);
						}
						fileLocation = CODE_TABLES_DIR + "b_30_accession/"
								+ fileName;
					}
					try (BufferedReader in = new BufferedReader(new FileReader(
							fileLocation));) {
						while ((line = in.readLine()) != null) {
							if (line.length() > 0) {
								String[] tokens = line.split("\t");
								// Some tables like s_21 use tabs as delimiters.
								// Most use commas.
								if (tokens.length != 2) {
									tokens = line.split(",");
								}
								if (tokens.length > 0) {
									try {
										int rowKey = parseInt(tokens[0].trim());
										if (rowKey != key) {
											// Not a match.
											continue;
										}
									} catch (Exception e) {
										continue;
									}
									// LOG.info("Got a match!");
									// In case of leading/trailing spaces...
									tokens[0] = tokens[0].trim();
									if (headings.length == 3) {
										// Just lump all the other tokens
										// together since any commas in there 
										//are meaningless.
										ArrayList<String> tokensList = new ArrayList<String>();
										tokensList.add(tokens[0]);
										String theToken = "";
										for (int i = 1; i < tokens.length; i++) {
											theToken = theToken
													+ tokens[i].trim();
										}
										tokensList.add(theToken);
										tokens = tokensList
												.toArray(new String[tokensList
														.size()]);
									}
									if (headings.length > 3) {
										// Not just a CODE/Meaning pair
										// watch out -- some tables use commas
										// inside quotes, e.g. 13,"Lightning 
										//visible, no thunder heard".
										ArrayList<String> tokensList = new ArrayList<String>();
										int inquotes = 0;
										String theToken = "";
										for (String token : tokens) {
											theToken = theToken + token.trim();
											if (token.indexOf("\"") != -1) {
												// There is a quote in this
												// token. Quotes are only
												// used to allow inclusion of
												// commas in the string so 
												//there will be a max of 1 
												//quotation mark in a token.
												inquotes = (inquotes + 1) % 2;
											}
											if (inquotes == 0) {
												tokensList.add(theToken);
												theToken = "";
											} else {
												theToken = theToken + ",";
											}
										}
										tokens = tokensList
												.toArray(new String[tokensList
														.size()]);
									}
									data.put(key, tokens);
									dataRow = tokens;
									break; // We got what we want.
								}
							}
						}
					}
				} catch (IOException e) {
					logger.error(e.getMessage(), e);
				}
			}// End of if(allLoaded == false).
		}
		if (dataRow == null || dataRow.length == 0) {
			data.put(key, new String[0]);
			// There is no such data in the file.
			return "";
		}
		if (dataRow.length == 2) {
			// Just key-value
			return dataRow[1];
		}
		String ans = "";
		if (headings.length != dataRow.length) {
			// This happens in files like b_10 where commas are used instead
			// of the usual semicolons so there is really one value.
			for (int i = 1; i < dataRow.length; i++) {
				ans += " " + dataRow[i];
			}
			return ans.trim();
		}
		// For multi-valued ones dataRow will be longer than 2.
		ans += headings[1] + "=" + dataRow[1];
		for (int i = 1; i < dataRow.length - 1; i++) {
			ans += ", " + headings[i + 1] + "=" + dataRow[i + 1];
		}
		return ans;
	}

	/**
	 * Gets the code_name entry of the table.
	 * 
	 * @return a String, the code_name.
	 */
	String getCode_name() {
		return code_name;
	}

	/**
	 * Called to lookup a value where the key is a double Seems to only happen
	 * with table t_28, comparable biology value calculation method so this
	 * method is quite simple. If we discover other tables using doubles for
	 * keys we need to work on this ...
	 * 
	 * @param key
	 * @return a String composed by using the headings and data that corresponds
	 *         to the key
	 */
	String getDouble(double key) {
		if (doubleData == null) {
			doubleData = new Hashtable<Double, String[]>();
		}
		String[] dataRow = doubleData.get(key);
		if (dataRow == null) {
			// Find the value and cache it.
			if (allLoaded == false) {
				// The hunt
				String line;
				String fileLocation = CODE_TABLES_DIR + tableName;
				try (BufferedReader in = new BufferedReader(new FileReader(
						fileLocation));) {
					while ((line = in.readLine()) != null) {
						if (line.length() > 0) {
							String[] tokens = line.split(",");
							if (!(tokens.length > 0)) {
								// Got a a comment line.
								continue;
							}
							double keyToken = 0.0;
							try {
								keyToken = Double.parseDouble(tokens[0]);
							} catch (NumberFormatException e) {
								// First token is not a double.
								continue;
							}
							if (key != keyToken) {
								// Not the one we want.
								continue;
							}
							// Have a match.
							/* In case of leading/trailing spaces ... */
							tokens[0] = "" + keyToken;
							if (headings.length == 3) {
								// Just lump all the other tokens together
								// since any commas in there are meaningless.
								ArrayList<String> tokensList = new ArrayList<String>();
								tokensList.add(tokens[0]);
								String theToken = "";
								for (int i = 1; i < tokens.length; i++) {
									theToken = theToken + tokens[i];
								}
								tokensList.add(theToken);
								tokens = tokensList
										.toArray(new String[tokensList.size()]);
							}
							doubleData.put(key, tokens);
							dataRow = tokens;
							break; // We've got what we want.
						}
					}
				} catch (IOException e) {
					logger.error(e.getMessage(), e);
				}
			}
		}
		if (dataRow == null || dataRow.length == 0) {
			doubleData.put(key, new String[0]);
			// There is no such data in the file.
			return "";
		}
		if (dataRow.length == 2) {
			// Just key-value
			return dataRow[1];
		}
		// For multi-valued ones...
		String ans = "";
		ans += headings[1] + "=" + dataRow[0];
		for (int i = 1; i < dataRow.length; i++) {
			ans += ", " + headings[i + 1] + "=" + dataRow[i];
		}
		return ans;
	}

	/**
	 * Returns the heading.
	 * 
	 * @return a String like 'WODC Code'.
	 */
	String getHeading() {
		return heading;
	}

	/**
	 * In table t_27 each line has its own heading
	 * 
	 * @param code
	 *            an int, the integer code.
	 * @return a String, the heading for that row.
	 */
	String getHeading(int code) {
		// For table t_27
		return t_27ValueTable.get(code);
	}

	/**
	 * Getter for the array of heading.
	 * 
	 * @return a String array that has the column headings.
	 */
	String[] getHeadings() {
		if (tableName.startsWith("t_27")) {
			return t_27ValueTable.values().toArray(new String[0]);
		}
		return headings;
	}

	String getUnits() {
		return units;
	}

	/**
	 * Called to load a whole table file. Useful for commonly used tables that
	 * we need to load eagerly.
	 */
	void loadAll() {
		String line;
		try (BufferedReader in = new BufferedReader(new FileReader(
				CODE_TABLES_DIR + tableName));) {
			while ((line = in.readLine()) != null) {
				if (line.length() > 0) {
					String[] tokens = line.split(",");
					if (tokens.length > 0) { // A non empty line
						// In case of leading/trailing spaces.
						tokens[0] = tokens[0].trim();
						// Some tables use commas inside quotes, e.g.
						// 13,"Lightning visible, no thunder heard".
						ArrayList<String> tokensList = new ArrayList<String>();
						int inquotes = 0;
						String theToken = "";
						for (String token : tokens) {
							theToken = theToken + token.trim();
							if (token.indexOf("\"") != -1) {
								// There is a quote in this token. It looks like
								// quotes are only used to allow inclusion of
								// commas in the string so there will be a max
								// of 1 quotation mark in a token.
								inquotes = (inquotes + 1) % 2;
							}
							if (inquotes == 0) {
								tokensList.add(theToken);
								theToken = "";
							} else {
								theToken = theToken + ",";
							}
						}
						tokens = tokensList.toArray(new String[tokensList
								.size()]);
						try {
							data.put(parseInt(tokens[0]), tokens);
						} catch (NumberFormatException e) {
							// Hit a heading or comment -- not a numerical code.
						}
					}
				}
			}
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
	}
}
