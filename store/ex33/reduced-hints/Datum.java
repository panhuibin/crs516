package com.ltree.crs516.domain;

/**
 * When reading numerical information like time, latitude, depth etc we 
 * are given
 * a) number of significant digits
 * b) total digits
 * c) precision of measurement
 * d) data value
 * e) WOD quality control flag
 * f) Originators flag
 * Without all of the first four pieces of information, 
 * a), b), c), d), a reading is not valid. 
 * This class represents that information and is a place to put 
 * information that goes with one data value. 
 * 
 * @author crs516 development team
 *
 */
public class Datum {

	/**
	 * Meaning of the originator's quality flag.
	 */
	private String meaning;
	
	/**
	 * Originator's flag.
	 */
	private int originatorsFlag;
	
	/**
	 * Precision of a variable (number of places to the right of the 
	 * decimal point).
	 */
	private int precision;
	
	/**
	 * WOD quality control flag. This is not used for all variables.
	 */
	private int qualityFlag;
	
	/**
	 * The interpretation of the quality flag from the various code tables.
	 */
	private String qualityFlagString = "See Documentation";
	
	/**
	 * Number of significant digits in a value.
	 */
	private int sigFig;
	
	/**
	 * Total number of digits in a value. This is usually the same as sigFig, 
	 * but can vary in cases of negative numbers, converted values, and data 
	 * in which the values are reported with more precision than an instrument 
	 * is capable of recording.
	 */
	private int totalFig;

	/**
	 * The actual value.
	 */
	private Double value;

	/**
	 * Gets the meaning of the Originator's flag.
	 * 
	 * @return A String looked up from the relevant code table.
	 */
	public String getMeaning() {
		if(meaning == null){
			return "see documentation.";
		}
		return meaning;

	}

	/**
	 * Gets the originator's flag.
	 * 
	 * @return an int, the originator;'s flag.
	 */
	public int getOriginatorsFlag() {
		return originatorsFlag;
	}
	
	/**
	 * Gets the precision.
	 * @return an int, the precision to which the value was read.
	 */
	public int getPrecision() {
		return precision;
	}
	
	/**
	 * Gets the OCL quality control flag.
	 * @return an int, the quality flag.
	 */
	public int getQualityFlag() {
		return qualityFlag;
	}

	/**
	 * Gets the OCL quality control flag's meaning.
	 * @return a string from the relevant code table.
	 */
	public String getQualityFlagString() {
		return qualityFlagString;
	}
		
	/**
	 * Gets the number of significant figures to which the value was read.
	 * @return an int, the number of significant figures.
	 */
	public int getSigFig() {
		return sigFig;
	}
		
	/**
	 * Gets the total number of figures.
	 * @return an int, the total number of figures.
	 */
	public int getTotalFig() {
		return totalFig;
	}

	/**
	 * Gets the value.
	 * @return a Double, representing the value. For a formatted string use the 
	 * getValueString() method instead.
	 * 
	 */
	public Double getValue() {
		return value;
	}
		
	/**
	 * Gets the value as a String.
	 * @return a String representation of the actual value properly formatted 
	 * according to the precision.
	 */
	public String getValueString() {
		if(value == null){
			return null;
		}
		String formatString = "%"+getTotalFig()+"."+getPrecision()+"f";
		//Just to explain why tests will fail until the exercise is done.
		if(formatString.equals("%0.0f")){
			throw new IllegalStateException("Test will not pass until after " +
					"the exercise is done.");
		}
		return String.format(formatString, value);
	}
	
	/**
	 * Sets the meaning of the originator's flag.
	 * @param meaning, a String from the relevant code table.
	 */
	public void setMeaning(String meaning) {
		this.meaning = meaning;
	}
	
	/**
	 * Sets the originator's flag.
	 * @param originatorsFlag an int, the originator's flag.
	 */
	public void setOriginatorsFlag(int originatorsFlag) {
		this.originatorsFlag = originatorsFlag;
	}
	
	/**
	 * Sets the precision.
	 * @param precision, an int that will be the precision.
	 */
	void setPrecision(int precision) {
		this.precision = precision;
	}
	
	/**
	 * Sets the OCL quality control flag.
	 * @param qualityFlag, an int, the flag.
	 */
	public void setQualityFlag(int qualityFlag) {
		this.qualityFlag = qualityFlag;
	}

	/**
	 * Sets the String meaning of the OCL quality control flag.
	 * @param qualityFlagString,the meaning of the quality control flag. 
	 */
	public void setQualityFlagString(String qualityFlagString) {
		this.qualityFlagString = qualityFlagString;
	}

	/**
	 * Sets the number of significant figures.
	 * @param sigFig an int,  the number of significant figures.
	 */
	void setSigFig(int sigFig) {
		this.sigFig = sigFig;
		
	}
	
	/**
	 * Sets the total number of figures.
	 * @param totalFig, an int, the total number of figures.
	 */
	void setTotalFig(int totalFig) {
		this.totalFig = totalFig;
	}

	/**
	 * Sets the actual value.
	 * @param value, a Double representing the actual value. Null is possible.
	 */
	void setValue(Double value) {
		this.value =value;
	}
	
	/**
	 * Returns a String representing the value, formatted according to 
	 * the precision.
	 * 
	 */
	@Override
	public String toString(){
		return getValueString();
	}

//TODO 1: Add an access modifier so that the constructor is not visible outside the Datum class.	
	
	Datum(){}
	
//TODO 2: Datum objects will be instantiated using the Builder. 
//Perform the TODO tasks in Builder.
	
	public static class Builder{
		//Mandatory parameters
		private final Double value;

		//TODO 2a: Add three more mandatory parameters:
		//An int called sigFig,
		//an int called totalFig,
		//an int called precision.
		//You will see error messages appear on the constructor.
		//Those will go away when you have completed the code.
		




		
		//Optional parameters
		private String meaning = "";
		
		//TODO 2b: Add the following optional parameters. 
		//An int called originatorsFlag with default value 0,
		//an int called qualityFlag with default value 0,
		//a String called qualityFlagString with default value 
		//"See Documentation". 







//TODO 3: Complete the public constructor for Builder that takes the four
//mandatory arguments. 
		public Builder(Double value, int sigFig, int totalFig, int precision) {
			this.value = value;
			
			



		}

//TODO 4: The four non-mandatory parameters meaning, originatorsFlag, 
//qualityFlag, qualityFlagString will optionally be supplied 
//Complete the method meaning(String val).

		public Builder meaning(String val){

			return null;
		}

		//TODO 4a: Write a method that accepts the value of originatorsFlag.
		





		//TODO 4b: Write a method that accepts the value of qualityFlag.






		//TODO 4c: Write a method that accepts the value of qualityFlagString.






//TODO 5: In the build() create a Datum and return it to the caller..

		public Datum build(){
			return null;
		}
	}
	
	/**
	 * Private constructor that takes a builder and guarantees that an 
	 * invalid Datum is not published.
	 * @param builder
	 */
	private Datum(Builder builder){
		value = builder.value;

//TODO 6: Replace the nulls and zeros below with the appropriate code.
		
		sigFig = 0;
		totalFig = 0;
		precision = 0;
		meaning = null;
		originatorsFlag = 0;
		qualityFlag = 0;
		qualityFlagString = null;
	}

}
