package com.ltree.crs516.domain;

/**
 * *Represents variable-specific metadata. 
 * A Variable object will have an array of VariableMeta objects.
 * 
 * @author crs516 development team
 * @see Variable
 *
 */
public class VariableMeta {
	
	private String code;
	private String varCode;
	private String varComment;
	private Datum value;

	/**
	 * Gets the Variable code's interpretation.
	 * @return a string, the meaning of the variable code.
	 */
	public String getVarCode() {
		return varCode;
	}
	
	/**
	 * Sets the variable code.
	 * @param varCode The variable code to set.
	 */
	public void setVarCode(String varCode) {
		this.varCode = varCode;
	}
	
	/**
	 * Gets the variable code
	 * @return a string,  the variable code.
	 */
	public String getCode() {
		return code;
	}
	
	/**
	 * Sets the code
	 * @param code, the code to set.
	 */
	public void setCode(String code) {
		this.code = code;
	}
	
	/**
	 * Gets the comment that goes with this variable
	 *  code
	 * @return the comment.
	 */
	public String getVarComment() {
		return varComment;
	}
	
	/**
	 * @param varComment, the comment to set.
	 */
	public void setVarComment(String varComment) {
		this.varComment = varComment;
	}
	
	/**
	 * Returns the value of the variable code as a String or 
	 * null is the value is not present.
	 * 
	 * @return a String representing the value or
	 * null is the value is not present.
	 */
	public String getValueString() {
	if(value == null){
		return null;
	}
		return value.getValueString();
	}

	/**
	 * Returns the precision of the reading 
	 * 
	 * @return an int, the precision of the value.
	 */
	public int getValuePrecision() {
		return value.getPrecision();
	}
	
	/**
	 * Checks if the value is present.
	 * @return a boolean, true if the value is present.
	 */
	public boolean isValuePresent() {
		if(value == null){
			return false;
		}
		return true;
	}
	
	/**
	 * Gets the number of significant figures. 

	 * @return an int, the number of significant figures.
	 */
	public int getValueSigFig() {
		return value.getSigFig();
	}
	
	/**
	 * Gets the total number of significant figures in the value.
	 * @return the total number of significant figures in the value.
	 */
	public int getValueTotalFig() {
		return value.getTotalFig();
	}
	
	/**
	 * Gets the meaning of this metadata. 
	 * 
	 * @return a String, the meaning of the metadata value.
	 */
	public String getValueMeaning() {
		if(value == null){
			return null;
		}
		return value.getMeaning();
	}
	
	/**
	 * Gets the value of this metadata.
	 * @return A Datum representing the value
	 */
	Datum getValue() {
		return value;
	}
	
	/**
	 * Sets the value of the metadata.
	 * @param a Datum, the value.
	 */
	public void setValue(Datum value) {
		this.value = value;
	}
}
