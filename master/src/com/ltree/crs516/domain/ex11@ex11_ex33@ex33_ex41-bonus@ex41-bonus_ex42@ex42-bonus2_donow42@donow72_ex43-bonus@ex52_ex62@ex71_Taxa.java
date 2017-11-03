package com.ltree.crs516.domain;

/**
 * Represents a taxonomic data entry. 
 * 
 * @author crs516 development team
 */
public class Taxa {
	
	/**
	 * Taxa or integrated parameter code.
	 */
	private int code;

	/**
	 * The meaning of the code.
	 */
	private String taxVar;

	/**
	 * The actual value.
	 */
	private Datum value;

	/**
	 * The meaning of the datum.
	 */
	private String valueMeaning;
	
	/**
	 * Gets the originator's flag.
	 * 
	 * @return the originator's flag for this value.
	 */
	public int getOriginatorFlag() {
		return value.getOriginatorsFlag();
	}

	/**
	 * Gets the quality control flag for this value.
	 * 
	 * @return an int, the quality control flag.
	 */
	public int getQualityFlag() {
		return value.getQualityFlag();
	}

	/**
	 * Gets the meaning of the quality control flag's value.
	 * 
	 * @return a string, the meaning of the quality control flag's value.
	 */
	public String getQualityFlagString() {
		return value.getQualityFlagString();
	}

	/**
	 * Gets the OCL code.
	 * 
	 * @return An int, the code for this taxa.
	 */
	public int getCode() {
		return code;
	}

	/**
	 * Sets the OCL code.
	 * @param code an int, the code to set.
	 */
	public void setCode(int code) {
		this.code = code;
	}

	/**
	 * The meaning of the Taxa or integrated parameter OCL code.
	 * @return the string interpretation of the code.
	 */
	public String getTaxVar() {
		return taxVar;
	}

	
	/**
	 * Sets the string meaning of the taxa code.
	 * @param taxVar the String to set.
	 */
	public void setTaxVar(String taxVar) {
		this.taxVar = taxVar;
	}

	/**
	 * Gets the value of this taxa.
	 * @return the value as a Datum.
	 */
	public Datum getValue() {
		return value;
	}

	/**
	 * Gets the value of this taxa as a string formatted according to 
	 * the precision.
	 * @return A String, the value.
	 */
	public String getValueString() {
		if (value == null) {
			return null;
		}
		return value.getValueString();
	}

	/**
	 * Sets the value.
	 * @param value
	 *            a Datum representing the value.
	 */
	public void setValue(Datum value) {
		this.value = value;
	}

	/**
	 * Returns the precision of the value.
	 * @return an int, the precision.
	 */
	public int getPrecision() {
		return value.getPrecision();
	}

	/**
	 * Gets the number of significant figures in the value.
	 * @return an int, the number of significant figures.
	 */
	public int getSigFig() {
		return value.getSigFig();
	}

	/**
	 * Gets the total number of figures in the value.
	 * @return an int, the total number of significant figures.
	 */
	public int getTotalFig() {
		return value.getTotalFig();
	}

	/**
	 * Gets the meaning of the value of this taxa.
	 * @return a String, the meaning of the value
	 */
	public String getValueMeaning() {
		return valueMeaning;
	}

	/**
	 * Sets the meaning of the value of this taxa.
	 * @param valueMeaning a String, the meaning of the value.
	 */
	public void setValueMeaning(String valueMeaning) {
		this.valueMeaning = valueMeaning;
	}
}
