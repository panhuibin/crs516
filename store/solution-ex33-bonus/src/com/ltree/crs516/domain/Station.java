package com.ltree.crs516.domain;

/**
 * This class represents a WOD09 station. A station is data from one 
 * or more casts at one geographic location. The WOC09 documentation
 * is at http://www.nodc.noaa.gov/OC5/WOD09/pr_wod09.html
 * A station's data will include primary header, 
 * secondary header, variable-specific second header, 
 * character data, biological header, 
 * taxa-specific and biomass data, and measured variables.
 * 
 * @author crs516 develoment team
 * 
 */
public class Station {

	/**
	 * A flag to indicate if this station has any biology headers.
	 */
	@SuppressWarnings("unused")
	/*Property is present in NOAA documentation. So far no use for it*/
	private boolean biologyHeadersPresent;
	
	/**
	 * An array of CharDataEntry objects with originator's cruise codes,
	 * originator's station codes, and principal investigator integer code.
	 */
	private CharDataEntry[] characterDataEntries;
	
	/**
	 * A flag to indicate if there are any character data entries.
	 */
	@SuppressWarnings("unused")
	/*Property is present in NOAA documentation. So far no use for it*/
	private boolean characterDataPresent;
	
	/**
	 * An array of BiologyHeader objects with information necessary to
	 * understand how biological data were sampled.
	 */
	private BiologyHeader[] biologyHeaders;
	
	/**
	 * Checks if there are biology headers.
	 * 
	 * @return  a boolean, true if there are biology headers and false otherwise.
	 */
	public boolean isBiologyHeadersPresent() {
		return(biologyHeaders!=null && biologyHeaders.length>0);
	}
	
	/**
	 * The number of character data entries.
	 */
	@SuppressWarnings("unused")
	/*Property is present in NOAA documentation. So far no use for it*/
	private int numCharacterDataEntries;
	
	/**
	 * Gets the Biology headers.
	 * 
	 * @return an array of BiologyHeader objects.
	 */
	public BiologyHeader[] getBiologyHeaders() {
		return biologyHeaders;
	}
	
	/**
	 * Checks if there are character data entries.
	 * 
	 * @return a boolean, true if there are character data entries and false otherwise.
	 */
	public boolean isCharacterDataPresent() {
		return(characterDataEntries!=null && characterDataEntries.length>0);
	}
	
	/**
	 * Gets the number of character data entries.
	 * 
	 * @return an int, the number of character data entries.
	 */
	public int getNumCharacterDataEntries() {
		if(characterDataEntries!=null)
		return characterDataEntries.length;
		return 0;
	}
	
	/**
	 * Gets the array of character data entries.
	 * 
	 * @return a CharDataEntry[], the array of characterDataEntries.
	 */
	public CharDataEntry[] getCharacterDataEntries() {
		return characterDataEntries;
	}

	public void setBiologyHeadersPresent(boolean biologyHeadersPresent) {
		this.biologyHeadersPresent = biologyHeadersPresent;
	}

	public void setCharacterDataEntries(CharDataEntry[] characterDataEntries) {
		this.characterDataEntries = characterDataEntries;
	}

	public void setCharacterDataPresent(boolean characterDataPresent) {
		this.characterDataPresent = characterDataPresent;
	}

	public void setBiologyHeaders(BiologyHeader[] biologyHeaders) {
		this.biologyHeaders = biologyHeaders;
	}

	public void setNumCharacterDataEntries(int numCharacterDataEntries) {
		this.numCharacterDataEntries = numCharacterDataEntries;
	}
}
