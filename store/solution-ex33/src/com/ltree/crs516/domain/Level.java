package com.ltree.crs516.domain;

/**
 * Represents the data obtained at a particular depth. The depth and the 
 * data obtained at that depth are represented at Datum objects.
 *  @author crs516 development team
 *  @see Datum
 */
public class Level {

	/**
	 * The depth at which this data was measured.
	 */
	private Datum depth;

	/**
	 * Checks if there is data associated with this level.
	 * @return null if data is missing
	 */
	public boolean isDataPresent() {
		if(data == null){
			return false;
		}
		return true;
	}

	/**
	 * An array of Datum objects representing the data.
	 */
	private Datum[] data;
	
	/**
	 * Accessor for the data for this level.
	 * @return the Datum[] that represents the data.
	 */
	public Datum[] getData() {
		return data;
	}
	
	/**
	 * Sets the data for this level.
	 * @param data as a Datum[].
	 */
	public void setData(Datum[] data) {
		this.data = data;
	}
	
	/**
	 * Gets the originators depth error flag.
	 * @return an int, the error flag.
	 */
	public int getOrigDepthErrorFlag() {
		if(depth == null){
			return -1;
		}
		return depth.getOriginatorsFlag();
	}
	
	/**
	 * Gets the interpretation of the error code as a String.
	 * @return A String, the interpretation of the error code.
	 */
	public String getDepthErrorCodeString() {
		if(depth == null){
			return null;
		}
		return depth.getQualityFlagString();
	}

	/**
	 * Gets the depth error code.
	 * @return and int, the error code.
	 */
	public int getDepthErrorCode() {
		if(depth == null){
			return -1;
		}
		return depth.getQualityFlag();
	}
	
	/**
	 * Gets the precision to which the measurements were taken .
	 * @return an int, the precision.
	 */
	public int getPrecision() {
		if(depth == null){
			return -1;
		}
		return depth.getPrecision();
	}

	/**
	 * Gets the number of significant figures in the measurement of this depth.
	 * @return an int, the number of significant figures.
	 */
	public int getSigFig() {
		if(depth == null){
			return -1;
		}
		return depth.getSigFig();
	}
	
	/**
	 * Gets for the total figures in the measurement of the depth.
	 * @return an int, the total figures.
	 */
	public int getTotalFig() {
		if(depth == null){
			return -1;
		}
		return depth.getTotalFig();
	}
	
	/**
	 * Gets the the depth of this level.
	 * @return A Datum, representing the depth.
	 */
	public Datum getDepth() {
		return depth;
	}
	
	/**
	 * Sets the depth.
	 * @param value A Datum representing the depth.
	 */
	public void setDepth(Datum value) {
		this.depth = value;
	}
	
	/**
	 * Gets the depth as a formatted String.
	 * @return A String, the depth properly formated to the correct precision.
	 */
	public String getDepthString() {
		if(depth==null){
			return null;
		}
		return depth.getValueString();
	}
}
