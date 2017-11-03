package com.ltree.crs516.domain;

/**
 * Represents a principal investigator. The principal investigator (PI)
 * is the person (or persons), responsible for data collection. Principal 
 * investigators are identified by numeric code and by variable code
 * 
 * @author crs516 development team
 * 
 */
public class PrincipalInvestigator {

	/**
	 * The PI code.
	 */
	private int piCode;
	
	/**
	 * The PI name. 
	 */
	private String piName;
	
	/**
	 * The variable code, an int.
	 */
	private int variableCode;
	
	/**
	 * A String, the meaning of the variable code.
	 */
	private String variableCodeString;
	
	/**
	 * Gets the PI code.
	 * @return an int representing the piCode.
	 */
	public int getPiCode() {
		return piCode;
	}
	
	/**
	 * Gets the PI's name.
	 * @return a String, the PI's name.
	 */ 
	public String getPiName() {
		return piName;
	}
	
	/**
	 * Gets the variable code.
	 * @return an int, the variable Code.
	 */
	public int getVariableCode() {
		return variableCode;
	}
	
	/**
	 * Gets the meaning of the variable code.
	 * @return a string representing the meaning of the variable code.
	 */
	public String getVariableCodeString() {
		return variableCodeString;
	}
	
	/**
	 * Sets the PI code.
	 * @param piCode, the code to set.
	 */
	public void setPiCode(int piCode) {
		this.piCode = piCode;
	}
	
	/**
	 * Sets the PI's name.
	 * @param piName, the name to set.
	 */
	public void setPiName(String piName) {
		this.piName = piName;
	}
	
	/**
	 * Sets the variable code.
	 * @param variableCode, the code to set.
	 */
	public void setVariableCode(int variableCode) {
		this.variableCode = variableCode;
	}
	
	/**
	 * Sets the variable code's meaning.
	 * @param variableCodeString, a String, the meaning of the PI code.
	 */
	public void setVariableCodeString(String variableCodeString) {
		this.variableCodeString = variableCodeString;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + piCode;
		result = prime * result + ((piName == null) ? 0 : piName.hashCode());
		result = prime * result + variableCode;
		result = prime
				* result
				+ ((variableCodeString == null) ? 0 : variableCodeString
						.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PrincipalInvestigator other = (PrincipalInvestigator) obj;
		if (piCode != other.piCode)
			return false;
		if (piName == null) {
			if (other.piName != null)
				return false;
		} else if (!piName.equals(other.piName))
			return false;
		if (variableCode != other.variableCode)
			return false;
		if (variableCodeString == null) {
			if (other.variableCodeString != null)
				return false;
		} else if (!variableCodeString.equals(other.variableCodeString))
			return false;
		return true;
	}
	
	
}
