package com.ltree.crs516.domain;

/**
 * Contains information necessary to understand how biological data were
 * sampled. “Biological” data are defined as plankton biomass (weights or 
 * volumes) and taxa-specific observations.
 * In WOD09, a cast is comprised of as many as seven parts with the first five 
 * devoted to metadata holding. The "Biological Header" is the fifth part. 
 * 
 * @author crs516 development team
 * 
 */
public class BiologyHeader {
	
	/**
	 * The biological header Code
	 */
	private int headerCode;

	/**
	 * Meaning of the header code.
	 */
	private String headerString;

	/**
	 * The value of the header.
	 */
	private Datum value;

	/**
	 * The meaning of the value. You might have to look it up in the appropriate
	 * tables.
	 */
	private String valueMeaning;

	/**
	 * @return the Header Code.
	 */
	public int getHeaderCode() {

		return headerCode;
	}

	/**
	 * Gets the meaning of the header code as per <i>bioheadr.txt</i>.
	 * 
	 * @return a String, the interpretation of the header code.
	 */
	public String getHeaderString() {
		if (headerString == null)
			return "see documentation";
		return headerString;
	}

	/**
	 * Gets the precision to which the header value was measured.
	 * 
	 * @return an int, the precision.
	 */
	public int getPrecision() {
		return value.getPrecision();
	}

	/**
	 * Gets the number of significant figures in the value recorded.
	 * 
	 * @return an int, the number of significant figures.
	 */
	public int getSigFig() {
		return value.getSigFig();
	}

	/**
	 * Gets the total number of figures in the value.
	 * 
	 * @return an int, the total number of significant figures.
	 */
	public int getTotalFig() {
		return value.getTotalFig();
	}

	/**
	 * Gets the value of the header.
	 * 
	 * @return a Datum object representing the value.
	 */
	public Datum getValue() {
		return value;
	}

	/**
	 * Gets the meaning of the value of the header.
	 * @return a String, the meaning of the value.
	 */
	public String getValueMeaning() {
		return valueMeaning;
	}

	/**
	 * Gets the value of the header as a string.
	 * 
	 * @return A String representing the value of the header.
	 */
	public String getValueString() {
		if (value == null) {
			return null;
		}
		return value.getValueString();
	}

	/**
	 * Sets the header name.
	 * @param header
	 *            the header string to set.
	 */
	public void setHeader(String header) {
		this.headerString = header;
	}

	/**
	 * Sets the header code.
	 * @param headerCode,
	 *            the headerCode to set.
	 */
	public void setHeaderCode(int headerCode) {
		this.headerCode = headerCode;
	}

	/**
	 * Sets the string representation of the header code.
	 * 
	 * @param headerString,
	 *            the meaning of the header code.
	 */
	public void setHeaderString(String headerString) {
		this.headerString = headerString;
	}

	/**
	 * Sets the value of the header
	 * 
	 * @param value,
	 *            a Datum that will be the value of the header
	 */
	public void setValue(Datum value) {
		this.value = value;
	}

	/**
	 * Sets the meaning of the value.
	 * 
	 * @param valueMeaning,
	 *            a String interpretation of the value.
	 * 
	 */
	public void setValueMeaning(String valueMeaning) {
		this.valueMeaning = valueMeaning;
	}

}
