package com.ltree.crs516.domain;

/**
 * Contains information such as meteorological data, sea floor depth, 
 * instrument, ship (platform), institute, and project. The WOC09 documentation
 * is at http://www.nodc.noaa.gov/OC5/WOD09/pr_wod09.html. The WOD secondary 
 * header information is always in numeric format so it is represented 
 * as a Datum object.
 * 
 * @author crs516 development team
 *
 */
public class SecondaryHeader {
	
	/**
	 * The code.
	 */
	private int headerCode;
	
	/**
	 * The meaning of the code. The code lists are extensive
	 * so you might have to look up the meaning in the code tables.
	 */
	private String headerString;
	
	/**
	 * The value.
	 */
	private Datum value;
	
	/**
	 * The meaning of the value. You might have to look
	 * it up in the appropriate tables.
	 */
	private String valueMeaning;

	/**
	 * Gets the meaning of the value.
	 * @return A String, the meaning of the code value.
	 */
	public String getValueMeaning() {
		return valueMeaning;
	}
	
	/**
	 * Sets the meaning of the value.
	 * @param valueMeaning A String, the meaning of the value.
	 */
	public void setValueMeaning(String valueMeaning) {
		this.valueMeaning = valueMeaning;
	}
	
	/**
	 * Gets the meaning of the header code.
	 * @return A String, the meaning of the header as per the appropriate code table.
	 */
	public String getHeaderString() {
		if(headerString == null){return "see documentation";}
		return headerString;
	}
	
	/**
	 * Gets the header code as an int.
	 * @return An int, the header code.
	 */
	public int getHeaderCode() {
		return headerCode;
	}
	
	/**
	 * Sets the header code.
	 * @param headerCode An int, the header code.
	 */
	public void setHeaderCode(int headerCode) {
		this.headerCode = headerCode;
	}
	
	/**
	 * Gets the value of this header.
	 * @return a Datum, the value of the header code.
	 */
	public Datum getValue() {
		return value;
	}
	
	/**
	 * Returns a String version of the value, properly formatted
	 * according to the precision of the measurement.
	 * @return A String representation of the value.
	 */
	public String getValueString() {
		if(value == null){
			return null;
		}
		return value.getValueString();
	}

	/**
	 * Gets the precision of the value.
	 * @return an int, the precision.
	 */
	public int getPrecision() {
		return value.getPrecision();
	}
	
	/**
	 * Gets the number of significant figures in the value.
	 * @return an int, the number of significant figures in the value.
	 */
	public int getSigFig() {
		return value.getSigFig();
	}
	
	/**
	 * Gets the total number of figures in the value.
	 * @return an int, the total number of figures in the value.
	 */
	public int getTotalFig() {
		return value.getTotalFig();
	}
	
	/**
	 * Sets the value.
	 * @param value a Datum, the value.
	 */
	public void setValue(Datum value) {
		this.value = value;
	}
	
	/**
	 * Sets the meaning of the header. 
	 * @param headerString, a String, the meaning of the header.
	 */
	public void setHeaderString(String headerString) {
		this.headerString = headerString;
	}

} 
