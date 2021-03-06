package com.ltree.crs516.domain;

import java.util.ArrayList;

import com.ltree.crs516.domain.Station.ProfileType;

public class StationProxy implements IStation {

	/**
	 * An array of BiologyHeader objects with information necessary to
	 * understand how biological data were sampled.
	 */
	private BiologyHeader[] biologyHeaders;

	/**
	 * The number of bytes for this station in the original file.
	 */
	private int bytesInProfile;

	/**
	 * An array of CharDataEntry objects with originator's cruise codes,
	 * originator's station codes, and principal investigator integer code.
	 */
	private CharDataEntry[] characterDataEntries;

	/**
	 * The country corresponding to the NODC country code.
	 */
	private String country;

	/**
	 * The NODC country code.
	 */
	private String countryCode;

	/**
	 * The cruise code.
	 */
	private int cruiseNumber;

	/**
	 * The day. Note that some data have been submitted with a day of zero (0).
	 */
	private int day;

	/**
	 * The latitude (sometimes missing)
	 */
	private Datum latitude;

	/**
	 * Number of depths.
	 */
	private int levels;

	/**
	 * The longitude (sometimes missing).
	 */
	private Datum longitude;

	/**
	 * The month.
	 */
	private int month;

	/**
	 * The number of biology header entries.
	 */
	private int numBiologyHeaders;

	/**
	 * The number of character data entries.
	 */
	private int numCharacterDataEntries;

	/**
	 * The number of secondary header entries.
	 */
	private int numSecondaryHeaders;

	/**
	 * Number of taxonomic data sets.
	 */
	private int numTaxaSets;

	/**
	 * An array of Level objects each of which contains profile data for one
	 * depth.
	 */
	private Level[] profile;

	
	
	/**
	 * Profile type. 
	 */
	private ProfileType profileType;

	/**
	 * An array of SecondaryHeader objects with information such as
	 * meteorological data, water column characteristics (such as depth to
	 * bottom), information about the instrument used, ship, institute, and
	 * project.
	 */
	private SecondaryHeader[] secondaryHeaders;

	/**
	 * A flag to indicate if there are secondary headers.
	 */
	private boolean secondaryHeadersPresent;

	/**
	 * The unique station number.
	 */
	private int stationNumber;

	/**
	 * A flag to indicate if taxonomic data entries are present.
	 */
	private boolean taxaDataPresent;

	/**
	 * An ArrayList of TaxaList objects, each of which represents a taxonomic
	 * data set.
	 */
	private ArrayList<TaxaList> taxaSets;

	/**
	 * The time.
	 */
	private Datum time;

	/**
	 * An array of Variable objects each describing the type of variable as well as
	 * a quality control flag for each variable (if all values of that variable
	 * have been flagged for that station)
	 */
	private Variable[] variables;

	/**
	 * The number of variables in the profile.
	 */
	private int variablesInProfile;

	/**
	 * The year.
	 */
	private int year;

	
	/**
	 * Gets the Biology headers.
	 * 
	 * @return an array of BiologyHeader objects.
	 */
	@Override
	public BiologyHeader[] getBiologyHeaders() {
		return biologyHeaders;
	}

	/**
	 * Gets the number of bytes in the profile.
	 * 
	 * @return an int, the number of bytes in the profile.
	 */
	@Override
	public int getBytesInProfile() {
		return bytesInProfile;
	}

	/**
	 * Convenience method corresponding to the number of character data 
	 * entries is called C in the WODC09 documentation.
	 * 
	 * @return an int, the number of character data entries.
	 */
	@Override
	public int getC() {
		return numCharacterDataEntries;
	}

	/**
	 * Gets the array of character data entries.
	 * 
	 * @return a CharDataEntry[], the array of characterDataEntries.
	 */
	@Override
	public CharDataEntry[] getCharacterDataEntries() {
		return characterDataEntries;
	}

	/**
	 * Gets the country that owns the country code.
	 * 
	 * @return a string, the country.
	 */
	@Override
	public String getCountry() {
		return country;
	}

	/**
	 * Gets the country code.
	 * 
	 * @return a string,  the countryCode.
	 */
	@Override
	public String getCountryCode() {
		return countryCode;
	}

	/**
	 * Gets the cruise number.
	 * 
	 * @return an int, the cruise number.
	 */
	@Override
	public int getCruiseNumber() {
		return cruiseNumber;
	}

	/**
	 * Gets the day.
	 * 
	 * @return an int, the day.
	 */
	@Override
	public int getDay() {
		return day;
	}

	/**
	 * Convenience method to return the number of levels, called L in the
	 * documentation.
	 * 
	 * @return an int, the number of levels.
	 */
	@Override
	public int getL() {
		return levels;
	}

	/**
	 * Gets the latitude.
	 * 
	 * @return a Datum object representing the latitude.
	 */
	@Override
	public Datum getLatitude() {
		return latitude;
	}

	/**
	 * Gets the precision to which the latitude is given.
	 * 
	 * @return an int, the latitude precision.
	 */
	@Override
	public int getLatitudePrecision() {
		return latitude.getPrecision();
	}

	/**
	 * Gets the number of significant figures to which the latitude is
	 * measured.
	 * 
	 * @return an int, the number of significant figures.
	 */
	@Override
	public int getLatitudeSigFig() {
		return latitude.getSigFig();
	}

	/**
	 * Gets a the latitude properly formatted according to the precision.
	 * 
	 * @return a string, the latitude.
	 */
	@Override
	public String getLatitudeString() {
		return latitude.getValueString();
	}

	/**
	 * Gets the total number of figures in the latitude.
	 * 
	 * @return an int, the total number of figures.
	 */
	@Override
	public int getLatitudeTotalFig() {
		return latitude.getTotalFig();
	}

	/**
	 * Gets the number of depths in the profile.
	 * 
	 * @return an int, the number of levels.
	 */
	@Override
	public int getLevels() {
		return levels;
	}

	/**
	 * Gets the longitude.
	 * 
	 * @return a Datum object representing the longitude.
	 */
	@Override
	public Datum getLongitude() {
		return longitude;
	}

	/**
	 * Gets the precision to which the longitude is given.
	 * 
	 * @return an int, the precision of the longitude.
	 */
	@Override
	public int getLongitudePrecision() {
		return longitude.getPrecision();
	}

	/**
	 * Gets the number of significant figures in the longitude.
	 * 
	 * @return an int, the number of significant figures in the longitude.
	 */
	@Override
	public int getLongitudeSigFig() {
		return longitude.getSigFig();
	}

	/**
	 * Gets the longitude as a string formatted according to the precision.
	 * 
	 * @return a String, the longitude.
	 */
	@Override
	public String getLongitudeString() {
		return longitude.getValueString();
	}

	/**
	 * Gets the number of significant figures in the longitude.
	 * 
	 * @return an int, the number of significant figures in the longitude.
	 */
	@Override
	public int getLongitudeTotalFig() {
		return longitude.getTotalFig();
	}

	/**
	 * Gets the month.
	 * 
	 * @return an int, the month.
	 */
	@Override
	public int getMonth() {
		return month;
	}

	/**
	 * Convenience method to get the number of variables in the profile, called
	 * N in the WOD09 documentation.
	 * 
	 * @return an int, the number of variables in the profile.
	 */
	@Override
	public int getN() {
		return variablesInProfile;
	}

	/**
	 * Convenience method to get the number of biology headers, called B in the
	 * WOD09 documentation.
	 * 
	 * @return an int, the number of biology headers;
	 */
	@Override
	public int getNumB() {
		return numBiologyHeaders;
	}

	/**
	 * Gets the number of biology headers.
	 * 
	 * @return an int, the number of biology headers.
	 */
	@Override
	public int getNumBiologyHeaders() {
		return numBiologyHeaders;
	}

	/**
	 * Gets the number of character data entries.
	 * 
	 * @return an int, the number of character data entries.
	 */
	@Override
	public int getNumCharacterDataEntries() {
		if(characterDataEntries!=null)
		return characterDataEntries.length;
		return 0;
	}

	/**
	 * Gets the number of secondary headers.
	 * 
	 * @return an int, the number of secondary headers.
	 */
	@Override
	public int getNumSecondaryHeaders() {
		return numSecondaryHeaders;
	}

	/**
	 * Gets the number of taxonomic data sets.
	 * 
	 * @return an int, the number of taxonomic data sets.
	 */
	@Override
	public int getNumTaxaSets() {
		return numTaxaSets;
	}

	/**
	 * Gets an array of Level objects, one Level object for each depth in the
	 * profile.
	 * 
	 * @return a Level[] containing the Level data for each depth in the profile.
	 */
	@Override
	public Level[] getProfile() {
		return profile;
	}

	/**
	 * Gets the profile type.
	 * 
	 * @return one of the values of the ProfileType enum.
	 */
	@Override
	public ProfileType getProfileType() {
		return profileType;
	}

	/**
	 * Return the profile type as a String.
	 * 
	 * @return  a String, whether "observed" (0 in the original data)
	 *  or "standard level" (1 in the original data).
	 */
	@Override
	public String getProfileTypeString() {
		if(getProfileType()!=null){
			return getProfileType().name();
		}
		return "";
	}

	/**
	 * Convenience method to get the number of secondary headers, called S in
	 * the WOD09 documentation.
	 * 
	 * @return an int, the number of secondary headers.
	 */
	@Override
	public int getS() {
		return numSecondaryHeaders;
	}

	/**
	 * Gets the secondary headers.
	 * 
	 * @return a SecondaryHeader[] with secondary header data.
	 */
	@Override
	public SecondaryHeader[] getSecondaryHeaders() {
		return secondaryHeaders;
	}

	/**
	 * Gets the unique station number.
	 * 
	 * @return an int, the station number.
	 */
	@Override
	public int getStationNumber() {
		return stationNumber;
	}

	/**
	 * Convenience method to return the number of taxonomic data sets, called T
	 * in the WOD09 documentation.
	 * 
	 * @return an int, the number of taxonomic data sets.
	 */
	@Override
	public int getT() {
		return numTaxaSets;
	}

	/**
	 * Gets the taxonomic data sets.
	 * 
	 * @return an ArrayList<TaxaList>, the TaxaLists representing the 
	 * taxonomic data sets.
	 */
	@Override
	public ArrayList<TaxaList> getTaxaSets() {
		return taxaSets;
	}

	/**
	 * Gets the time.
	 * 
	 * @return a double, the time. If time is not present it returns -1.0
	 */
	@Override
	public double getTime() {
		if (!isTimePresent()) {
			return -1;
		}
		return time.getValue();
	}

	/**
	 * Gets the precision to which the time is given.
	 * 
	 * @return an int, the time precision.
	 */
	@Override
	public int getTimePrecision() {
		return time.getPrecision();
	}

	/**
	 * Gets the number of significant figures in the time.
	 * 
	 * @return an int, the time significant figures.
	 */
	@Override
	public int getTimeSigFig() {
		return time.getSigFig();
	}

	/**
	 * Gets the time as a string formatted according to the precision.
	 * 
	 * @return a String, representing the time.
	 */
	@Override
	public String getTimeString() {

		return time.getValueString();
	}

	/**
	 * Gets the total number of figures in the time.
	 * 
	 * @return the total number of figures.
	 */
	@Override
	public int getTimeTotalFig() {
		return time.getTotalFig();
	}

	/**
	 * Gets the variables.
	 * 
	 * @return an array of Variable objects.
	 */
	@Override
	public Variable[] getVariables() {
		return variables;
	}

	/**
	 * Gets the number of variables in the profile.
	 * 
	 * @return an int, the number of variables in the profile.
	 */
	@Override
	public int getVariablesInProfile() {
		return variablesInProfile;
	}

	/**
	 * Gets the year.
	 * 
	 * @return an int, the year.
	 */
	@Override
	public int getYear() {
		return year;
	}

	/**
	 * Checks if there are biology headers.
	 * 
	 * @return  a boolean, true if there are biology headers and false otherwise.
	 */
	@Override
	public boolean isBiologyHeadersPresent() {
		return(biologyHeaders!=null && biologyHeaders.length>0);
	}

	/**
	 * Checks if there are character data entries.
	 * 
	 * @return a boolean, true if there are character data entries and false otherwise.
	 */
	@Override
	public boolean isCharacterDataPresent() {
		return(characterDataEntries!=null && characterDataEntries.length>0);
	}

	/**
	 * Checks if the latitude is present..
	 * 
	 * @return a boolean, true if the latitude is present and false otherwise.
	 */
	@Override
	public boolean isLatitudePresent() {
		if (latitude == null) {
			return false;
		}
		return true;
	}

	/**
	 * Checks if the longitude is present.
	 * 
	 * @return a boolean, true if the longitude is present and false otherwise.
	 */
	@Override
	public boolean isLongitudePresent() {
		if (longitude == null) {
			return false;
		}
		return true;
	}

	/**
	 * Checks if there is profile data.
	 * 
	 * @return a boolean, true if there is profile data and false otherwise.
	 */
	@Override
	public boolean isProfilePresent() {
		if (profile == null) {
			return false;
		}
		return true;
	}

	/**
	 * Checks if there are secondary headers.
	 * 
	 * @return a boolean, true if there are secondary headers and false otherwise..
	 */
	@Override
	public boolean isSecondaryHeadersPresent() {
		return secondaryHeadersPresent;
	}

	/**
	 * Checks if there are taxonomic data.
	 * 
	 * @return a boolean, true if there are taxonomic data present and false otherwise.
	 */
	@Override
	public boolean isTaxaDataPresent() {
		return taxaDataPresent;
	}

	/**
	 * Checks it time data is present.
	 * 
	 * @return a boolean,  true if time is present and false otherwise.
	 */
	@Override
	public boolean isTimePresent() {
		if (time == null) {
			return false;
		}
		return true;
	}

	/**
	 * Sets the biology header array.
	 * 
	 * @param biologyHeaders,
	 *            the array of biology headers to set.
	 */
	@Override
	public void setBiologyHeaders(BiologyHeader[] biologyHeaders) {
		this.biologyHeaders = biologyHeaders;
	}

	/**
	 * Sets the number of bytes in the profile.
	 * 
	 * @param bytesInProfile,
	 *            the bytesInProfile to set.
	 */
	@Override
	public void setBytesInProfile(int bytesInProfile) {
		this.bytesInProfile = bytesInProfile;
	}

	/**
	 * Sets the array of character data entries.
	 * 
	 * @param characterDataEntries.
	 *            the CharDataEntry[] to set.
	 */
	@Override
	public void setCharacterDataEntries(CharDataEntry[] characterDataEntries) {
		this.characterDataEntries = characterDataEntries;
	}
	
	/**
	 * Sets the country.
	 * 
	 * @param country,
	 *            the country to set.
	 */
	@Override
	public void setCountry(String country) {
		this.country = country;
	}

	/**
	 * Sets the country code.
	 * 
	 * @param countryCode,
	 *            the countryCode to set.
	 */
	@Override
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	/**
	 * Sets the cruise number.
	 * 
	 * @param cruiseNumber,
	 *            the cruiseNumber to set.
	 */
	@Override
	public void setCruiseNumber(int cruiseNumber) {
		this.cruiseNumber = cruiseNumber;
	}

	/**
	 * Sets the day.
	 * 
	 * @param day,
	 *            the day to set.
	 */
	@Override
	public void setDay(int day) {
		this.day = day;
	}

	/**
	 * Sets the latitude.
	 * 
	 * @param latitude,
	 *            a Datum representing the latitude.
	 */
	@Override
	public void setLatitude(Datum latitude) {
		this.latitude = latitude;
	}

	/**
	 * Sets the number of depths (levels).
	 * 
	 * @param levels
	 *            the number of depth levels to set.
	 */
	@Override
	public void setLevels(int levels) {
		this.levels = levels;
	}

	/**
	 * Sets the longitude.
	 * 
	 * @param longitude,
	 *            a Datum representing the longitude.
	 */
	@Override
	public void setLongitude(Datum longitude) {
		this.longitude = longitude;
	}

	/**
	 * Sets the month.
	 * 
	 * @param month,
	 *            the month to set.
	 */
	@Override
	public void setMonth(int month) {
		this.month = month;
	}

	/**
	 * Sets the number of biology headers
	 * 
	 * @param numBiologyHeaders,
	 *            the number of headers to set.
	 */
	@Override
	public void setNumBiologyHeaders(int numBiologyHeaders) {
		this.numBiologyHeaders = numBiologyHeaders;
	}

	/**
	 * Sets the number of character data entries.
	 * 
	 * @param numCharacterDataEntries,
	 *            the numCharacterDataEntries to set.
	 */
	@Override
	public void setNumCharacterDataEntries(int numCharacterDataEntries) {
		this.numCharacterDataEntries = numCharacterDataEntries;
	}

	/**
	 * Sets the number of secondary headers
	 * 
	 * @param numSecondaryHeaders,
	 *            the numSecondaryHeaders to set.
	 */
	@Override
	public void setNumSecondaryHeaders(int numSecondaryHeaders) {
		this.numSecondaryHeaders = numSecondaryHeaders;
	}

	/**
	 * Sets the number of taxonomic data sets
	 * 
	 * @param numTaxaSets,
	 *            the number of taxa sets to set.
	 */
	@Override
	public void setNumTaxaSets(int numTaxaSets) {
		this.numTaxaSets = numTaxaSets;
	}

	/**
	 * Sets the profile which is an array of Level objects.
	 * 
	 * @param profile,
	 *            a Level[] to set
	 */
	@Override
	public void setProfile(Level[] profile) {
		this.profile = profile;
	}

	/**
	 * Sets the profile type.
	 * 
	 * @param profileType,
	 *            the profileType to set.
	 */
	@Override
	public void setProfileType(ProfileType profileType) {
		this.profileType = profileType;
	}

	/**
	 * Sets the secondary headers.
	 * 
	 * @param secondaryHeaders,
	 *            the secondaryHeaders to set.
	 */
	@Override
	public void setSecondaryHeaders(SecondaryHeader[] secondaryHeaders) {
		this.secondaryHeaders = secondaryHeaders;
	}

	/**
	 * Sets the flag indicating if there are secondary headers present.
	 * 
	 * @param secondaryHeadersPresent,
	 *            boolean value secondaryHeadersPresent to set.
	 */
	@Override
	public void setSecondaryHeadersPresent(boolean secondaryHeadersPresent) {
		this.secondaryHeadersPresent = secondaryHeadersPresent;
	}

	/**
	 * Sets the unique station number.
	 * 
	 * @param stationNumber,
	 *            the stationNumber to set.
	 */
	@Override
	public void setStationNumber(int stationNumber) {
		this.stationNumber = stationNumber;
	}

	/**
	 * Sets the flag indicating the presence or absence of taxonomic data.
	 * 
	 * @param taxaDataPresent,
	 *            a boolean indicating if taxa data are present.
	 */
	@Override
	public void setTaxaDataPresent(boolean taxaDataPresent) {
		this.taxaDataPresent = taxaDataPresent;
	}

	/**
	 * Sets the ArrayList of TaxaList, each TaxaList representing a taxa set
	 * 
	 * @param taxaSets,
	 *            the ArrayList<TaxaList> to set.
	 */
	@Override
	public void setTaxaSets(ArrayList<TaxaList> taxaSets) {
		this.taxaSets = taxaSets;
	}

	/**
	 * 
	 * Sets the time. When called with a null argument it means time is not 
	 * present in the data.
	 * 
	 * @param time,
	 *            the time to set.
	 */
	@Override
	public void setTime(Datum time) {
		if (time == null) {
		} else {
			this.time = time;
		}
	}

	/**
	 * Sets the array of Variable objects representing variables in this
	 * profile.
	 * 
	 * @param variables,
	 *            the Variables[] to set.
	 */
	@Override
	public void setVariables(Variable[] variables) {
		this.variables = variables;
	}

	/**
	 * Sets the number of variables in the profile.
	 * 
	 * @param variablesInProfile,
	 *            an int, the number of variables in the profile to set.
	 */
	@Override
	public void setVariablesInProfile(int variablesInProfile) {
		this.variablesInProfile = variablesInProfile;
	}

	/**
	 * Sets the year.
	 * 
	 * @param year,
	 *            the year to set.
	 */
	@Override
	public void setYear(int year) {
		this.year = year;
	}

}
