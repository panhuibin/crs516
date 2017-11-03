package com.ltree.crs516.domain;

/**
 * Class to hold character data: originator’s cruise codes, originator’s cast 
 * codes, and principal Investigator’s code. The WOC09 documentation
 * is at http://www.nodc.noaa.gov/OC5/WOD09/pr_wod09.html
 * 
 * @author crs516 development team
 * 
 */
public class CharDataEntry {
	
	/**
	 * Possible character data types.
	 *
	 */
	public enum CharDataType{
		ORIGINATORS_CRUISE, ORIGINATORS_STATION_CODE, PRINCIPAL_INVESTIGATOR;
	}
	
	/**
	 * The character data
	 */
	private String data;

	/**
	 * An array of PrincpalInvestigator objects representing the principal
	 * investigators.
	 */
	private PrincipalInvestigator[] pis;

	/**
	 * Will be one of the values of the CharDataType enum. 
	 */
	private CharDataType type;

	/**
	 * Accessor for the character data.
	 * @return the character data.
	 */
	public String getData() {
		return data;
	}

	 /**
	 * Gets the number of principal investigators. In the WOD09 documentation 
	 * the number of principal investigators is referred to as P.
	 * 
	 * @return The number, P, of principal investigators.
	 */
	public int getP(){
		if(pis == null){
			return 0;
		}
		return pis.length;
	}

	/**
	 * Gets the principal investigators.
	 * @return the array of PrincipalInvestigator objects.
	 */
	public PrincipalInvestigator[] getPis() {
		return pis;
	}

	/**
	 * Gets the character data type.  
	 * @return the type as one of the CharDataType enum values.
	 * @see CharDataType
	 */
	public CharDataType getType() {
		return type;
	}

	/**
	 * Sets the character data. This represents the originator’s 
	 * cruise identification and the originator’s station identification.
	 * The data could be in alphanumeric format.
	 * @param data
	 *            A string, the character data.
	 */
	public void setData(String data) {
		this.data = data;
	}

	/**
	 * Sets the principal investigators.
	 * @param pis an array of PrincipalInvestigator.
	 */
	public void setPis(PrincipalInvestigator[] pis) {
		this.pis = pis;
	}
	
	/**
	 * Sets the type. 
	 * 
	 * @param type
	 *            one of the values of the CharDataType enum.
	 * @see CharDataType
	 */
	public void setType(CharDataType type) {
		this.type = type;
	}
}
