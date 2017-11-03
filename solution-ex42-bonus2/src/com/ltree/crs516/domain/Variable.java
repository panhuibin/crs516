package com.ltree.crs516.domain;

/**
 * Represents a measured variable (e.g. temperature, salinity, nutrients). A
 * station will have an array of these. The are declared in the primary header
 * section of the data file. The WOC09 documentation is at
 * http://www.nodc.noaa.gov/OC5/WOD09/pr_wod09.html
 * 
 * @author crs516 development team
 * 
 */
public class Variable {

	 /**
	 * OCL variable code.
	 */
	private int code;

	/**
	 * The interpretation of the variable code.
	 */
	private String codeString;

	/**
	 * The quality control flag for the variable.
	 */
	private int qualityFlag;

	/**
	 * Interpretation of the quality control flag.
	 */
	private String qualityFlagString;

	/**
	 * The number of variable-specific metadata.
	 */
	private int numMetaData;

	/**
	 * An array of VariableMeta objects, one for each of the variable-specific
	 * metadata.
	 */
	private VariableMeta[] metaData;

	private String codeUnit;

	/**
	 * Returns the array of VariableMeta objects.
	 * 
	 * @return Returns the metaData.
	 */
	public VariableMeta[] getMetaData() {
		return metaData;
	}

	/**
	 * Sets the variable specific metadata information.
	 * 
	 * @param metaData
	 *            , the metadata to set.
	 */
	public void setMetaData(VariableMeta[] metaData) {
		this.metaData = metaData;
		if (metaData != null) {
			numMetaData = metaData.length;
		} else {
			numMetaData = 0;
		}
	}

	/**
	 * Gets the number of variable specific metadata.
	 * 
	 * @return the number of metadata.
	 */
	public int getNumMetaData() {
		return numMetaData;
	}

	/**
	 * Convenience method to return the number of variable specific metadata
	 * called M int the WOD09 documentation.
	 * 
	 * @return the number of variable specific metadata.
	 */
	public int getM() {
		return numMetaData;
	}

	/**
	 * Sets the number of variable metadata.
	 * 
	 * @param numMetaData
	 *            The numMetaData to set.
	 */
	public void setNumMetaData(int numMetaData) {
		this.numMetaData = numMetaData;
	}

	/**
	 * Gets the quality control flag for this variable.
	 * 
	 * @return an int, the quality control flag.
	 */
	public int getQualityFlag() {
		return qualityFlag;
	}

	/**
	 * Sets the quality control flag
	 * 
	 * @param qualityFlag
	 *            an int, the quality flag value to set.
	 */
	public void setQualityFlag(int qualityFlag) {
		this.qualityFlag = qualityFlag;
	}

	/**
	 * Gets the meaning of the quality flag.
	 * 
	 * @return a String representing the quality flag.
	 */
	public String getQualityFlagString() {
		if (qualityFlagString == null) {
			return "see documentation ";
		}
		return qualityFlagString;
	}

	/**
	 * 
	 * Sets the string explaining the meaning of the quality flag.
	 * 
	 * @param qualityFlagString
	 *            , a String, the string to set.
	 */
	public void setQualityFlagString(String qualityFlagString) {
		this.qualityFlagString = qualityFlagString;
	}

	/**
	 * Gets the OCL code.
	 * 
	 * @return Returns the code.
	 */
	public int getCode() {
		return code;
	}

	/**
	 * Gets the meaning of the code as a string.
	 * 
	 * @return a String, the meaning of the code.
	 */
	public String getCodeString() {
		if (codeString == null) {
			return "see documentation";
		}
		return codeString;
	}

	/**
	 * Sets the code.
	 * 
	 * @param code
	 *            , an int, the code to set.
	 */
	public void setCode(int code) {
		this.code = code;
	}

	/**
	 * Sets the string explaining the meaning of the code.
	 * 
	 * @param codeString
	 *            , the string to set.
	 */
	public void setCodeString(String codeString) {
		this.codeString = codeString;
	}

	public void setCodeUnit(String depthVariableUnit) {
		this.codeUnit = depthVariableUnit;
	}

	public String getCodeUnit() {
		return codeUnit;
	}
}
