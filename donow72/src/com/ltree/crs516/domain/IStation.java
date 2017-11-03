package com.ltree.crs516.domain;

import java.util.ArrayList;

import com.ltree.crs516.domain.Station.ProfileType;

public interface IStation {

	/**
	 * Gets the Biology headers.
	 * 
	 * @return an array of BiologyHeader objects.
	 */
	public abstract BiologyHeader[] getBiologyHeaders();

	/**
	 * Gets the number of bytes in the profile.
	 * 
	 * @return an int, the number of bytes in the profile.
	 */
	public abstract int getBytesInProfile();

	/**
	 * Convenience method corresponding to the number of character data 
	 * entries is called C in the WODC09 documentation.
	 * 
	 * @return an int, the number of character data entries.
	 */
	public abstract int getC();

	/**
	 * Gets the array of character data entries.
	 * 
	 * @return a CharDataEntry[], the array of characterDataEntries.
	 */
	public abstract CharDataEntry[] getCharacterDataEntries();

	/**
	 * Gets the country that owns the country code.
	 * 
	 * @return a string, the country.
	 */
	public abstract String getCountry();

	/**
	 * Gets the country code.
	 * 
	 * @return a string,  the countryCode.
	 */
	public abstract String getCountryCode();

	/**
	 * Gets the cruise number.
	 * 
	 * @return an int, the cruise number.
	 */
	public abstract int getCruiseNumber();

	/**
	 * Gets the day.
	 * 
	 * @return an int, the day.
	 */
	public abstract int getDay();

	/**
	 * Convenience method to return the number of levels, called L in the
	 * documentation.
	 * 
	 * @return an int, the number of levels.
	 */
	public abstract int getL();

	/**
	 * Gets the latitude.
	 * 
	 * @return a Datum object representing the latitude.
	 */
	public abstract Datum getLatitude();

	/**
	 * Gets the precision to which the latitude is given.
	 * 
	 * @return an int, the latitude precision.
	 */
	public abstract int getLatitudePrecision();

	/**
	 * Gets the number of significant figures to which the latitude is
	 * measured.
	 * 
	 * @return an int, the number of significant figures.
	 */
	public abstract int getLatitudeSigFig();

	/**
	 * Gets a the latitude properly formatted according to the precision.
	 * 
	 * @return a string, the latitude.
	 */
	public abstract String getLatitudeString();

	/**
	 * Gets the total number of figures in the latitude.
	 * 
	 * @return an int, the total number of figures.
	 */
	public abstract int getLatitudeTotalFig();

	/**
	 * Gets the number of depths in the profile.
	 * 
	 * @return an int, the number of levels.
	 */
	public abstract int getLevels();

	/**
	 * Gets the longitude.
	 * 
	 * @return a Datum object representing the longitude.
	 */
	public abstract Datum getLongitude();

	/**
	 * Gets the precision to which the longitude is given.
	 * 
	 * @return an int, the precision of the longitude.
	 */
	public abstract int getLongitudePrecision();

	/**
	 * Gets the number of significant figures in the longitude.
	 * 
	 * @return an int, the number of significant figures in the longitude.
	 */
	public abstract int getLongitudeSigFig();

	/**
	 * Gets the longitude as a string formatted according to the precision.
	 * 
	 * @return a String, the longitude.
	 */
	public abstract String getLongitudeString();

	/**
	 * Gets the number of significant figures in the longitude.
	 * 
	 * @return an int, the number of significant figures in the longitude.
	 */
	public abstract int getLongitudeTotalFig();

	/**
	 * Gets the month.
	 * 
	 * @return an int, the month.
	 */
	public abstract int getMonth();

	/**
	 * Convenience method to get the number of variables in the profile, called
	 * N in the WOD09 documentation.
	 * 
	 * @return an int, the number of variables in the profile.
	 */
	public abstract int getN();

	/**
	 * Convenience method to get the number of biology headers, called B in the
	 * WOD09 documentation.
	 * 
	 * @return an int, the number of biology headers;
	 */
	public abstract int getNumB();

	/**
	 * Gets the number of biology headers.
	 * 
	 * @return an int, the number of biology headers.
	 */
	public abstract int getNumBiologyHeaders();

	/**
	 * Gets the number of character data entries.
	 * 
	 * @return an int, the number of character data entries.
	 */
	public abstract int getNumCharacterDataEntries();

	/**
	 * Gets the number of secondary headers.
	 * 
	 * @return an int, the number of secondary headers.
	 */
	public abstract int getNumSecondaryHeaders();

	/**
	 * Gets the number of taxonomic data sets.
	 * 
	 * @return an int, the number of taxonomic data sets.
	 */
	public abstract int getNumTaxaSets();

	/**
	 * Gets an array of Level objects, one Level object for each depth in the
	 * profile.
	 * 
	 * @return a Level[] containing the Level data for each depth in the profile.
	 */
	public abstract Level[] getProfile();

	/**
	 * Gets the profile type.
	 * 
	 * @return one of the values of the ProfileType enum.
	 */
	public abstract ProfileType getProfileType();

	/**
	 * Return the profile type as a String.
	 * 
	 * @return  a String, whether "observed" (0 in the original data)
	 *  or "standard level" (1 in the original data).
	 */
	public abstract String getProfileTypeString();

	/**
	 * Convenience method to get the number of secondary headers, called S in
	 * the WOD09 documentation.
	 * 
	 * @return an int, the number of secondary headers.
	 */
	public abstract int getS();

	/**
	 * Gets the secondary headers.
	 * 
	 * @return a SecondaryHeader[] with secondary header data.
	 */
	public abstract SecondaryHeader[] getSecondaryHeaders();

	/**
	 * Gets the unique station number.
	 * 
	 * @return an int, the station number.
	 */
	public abstract int getStationNumber();

	/**
	 * Convenience method to return the number of taxonomic data sets, called T
	 * in the WOD09 documentation.
	 * 
	 * @return an int, the number of taxonomic data sets.
	 */
	public abstract int getT();

	/**
	 * Gets the taxonomic data sets.
	 * 
	 * @return an ArrayList<TaxaList>, the TaxaLists representing the 
	 * taxonomic data sets.
	 */
	public abstract ArrayList<TaxaList> getTaxaSets();

	/**
	 * Gets the time.
	 * 
	 * @return a double, the time. If time is not present it returns -1.0
	 */
	public abstract double getTime();

	/**
	 * Gets the precision to which the time is given.
	 * 
	 * @return an int, the time precision.
	 */
	public abstract int getTimePrecision();

	/**
	 * Gets the number of significant figures in the time.
	 * 
	 * @return an int, the time significant figures.
	 */
	public abstract int getTimeSigFig();

	/**
	 * Gets the time as a string formatted according to the precision.
	 * 
	 * @return a String, representing the time.
	 */
	public abstract String getTimeString();

	/**
	 * Gets the total number of figures in the time.
	 * 
	 * @return the total number of figures.
	 */
	public abstract int getTimeTotalFig();

	/**
	 * Gets the variables.
	 * 
	 * @return an array of Variable objects.
	 */
	public abstract Variable[] getVariables();

	/**
	 * Gets the number of variables in the profile.
	 * 
	 * @return an int, the number of variables in the profile.
	 */
	public abstract int getVariablesInProfile();

	/**
	 * Gets the year.
	 * 
	 * @return an int, the year.
	 */
	public abstract int getYear();

	/**
	 * Checks if there are biology headers.
	 * 
	 * @return  a boolean, true if there are biology headers and false otherwise.
	 */
	public abstract boolean isBiologyHeadersPresent();

	/**
	 * Checks if there are character data entries.
	 * 
	 * @return a boolean, true if there are character data entries and false otherwise.
	 */
	public abstract boolean isCharacterDataPresent();

	/**
	 * Checks if the latitude is present..
	 * 
	 * @return a boolean, true if the latitude is present and false otherwise.
	 */
	public abstract boolean isLatitudePresent();

	/**
	 * Checks if the longitude is present.
	 * 
	 * @return a boolean, true if the longitude is present and false otherwise.
	 */
	public abstract boolean isLongitudePresent();

	/**
	 * Checks if there is profile data.
	 * 
	 * @return a boolean, true if there is profile data and false otherwise.
	 */
	public abstract boolean isProfilePresent();

	/**
	 * Checks if there are secondary headers.
	 * 
	 * @return a boolean, true if there are secondary headers and false otherwise..
	 */
	public abstract boolean isSecondaryHeadersPresent();

	/**
	 * Checks if there are taxonomic data.
	 * 
	 * @return a boolean, true if there are taxonomic data present and false otherwise.
	 */
	public abstract boolean isTaxaDataPresent();

	/**
	 * Checks it time data is present.
	 * 
	 * @return a boolean,  true if time is present and false otherwise.
	 */
	public abstract boolean isTimePresent();

	/**
	 * Sets the biology header array.
	 * 
	 * @param biologyHeaders,
	 *            the array of biology headers to set.
	 */
	public abstract void setBiologyHeaders(BiologyHeader[] biologyHeaders);

	/**
	 * Sets the number of bytes in the profile.
	 * 
	 * @param bytesInProfile,
	 *            the bytesInProfile to set.
	 */
	public abstract void setBytesInProfile(int bytesInProfile);

	/**
	 * Sets the array of character data entries.
	 * 
	 * @param characterDataEntries.
	 *            the CharDataEntry[] to set.
	 */
	public abstract void setCharacterDataEntries(
			CharDataEntry[] characterDataEntries);

	/**
	 * Sets the country.
	 * 
	 * @param country,
	 *            the country to set.
	 */
	public abstract void setCountry(String country);

	/**
	 * Sets the country code.
	 * 
	 * @param countryCode,
	 *            the countryCode to set.
	 */
	public abstract void setCountryCode(String countryCode);

	/**
	 * Sets the cruise number.
	 * 
	 * @param cruiseNumber,
	 *            the cruiseNumber to set.
	 */
	public abstract void setCruiseNumber(int cruiseNumber);

	/**
	 * Sets the day.
	 * 
	 * @param day,
	 *            the day to set.
	 */
	public abstract void setDay(int day);

	/**
	 * Sets the latitude.
	 * 
	 * @param latitude,
	 *            a Datum representing the latitude.
	 */
	public abstract void setLatitude(Datum latitude);

	/**
	 * Sets the number of depths (levels).
	 * 
	 * @param levels
	 *            the number of depth levels to set.
	 */
	public abstract void setLevels(int levels);

	/**
	 * Sets the longitude.
	 * 
	 * @param longitude,
	 *            a Datum representing the longitude.
	 */
	public abstract void setLongitude(Datum longitude);

	/**
	 * Sets the month.
	 * 
	 * @param month,
	 *            the month to set.
	 */
	public abstract void setMonth(int month);

	/**
	 * Sets the number of biology headers
	 * 
	 * @param numBiologyHeaders,
	 *            the number of headers to set.
	 */
	public abstract void setNumBiologyHeaders(int numBiologyHeaders);

	/**
	 * Sets the number of character data entries.
	 * 
	 * @param numCharacterDataEntries,
	 *            the numCharacterDataEntries to set.
	 */
	public abstract void setNumCharacterDataEntries(int numCharacterDataEntries);

	/**
	 * Sets the number of secondary headers
	 * 
	 * @param numSecondaryHeaders,
	 *            the numSecondaryHeaders to set.
	 */
	public abstract void setNumSecondaryHeaders(int numSecondaryHeaders);

	/**
	 * Sets the number of taxonomic data sets
	 * 
	 * @param numTaxaSets,
	 *            the number of taxa sets to set.
	 */
	public abstract void setNumTaxaSets(int numTaxaSets);

	/**
	 * Sets the profile which is an array of Level objects.
	 * 
	 * @param profile,
	 *            a Level[] to set
	 */
	public abstract void setProfile(Level[] profile);

	/**
	 * Sets the profile type.
	 * 
	 * @param profileType,
	 *            the profileType to set.
	 */
	public abstract void setProfileType(ProfileType profileType);

	/**
	 * Sets the secondary headers.
	 * 
	 * @param secondaryHeaders,
	 *            the secondaryHeaders to set.
	 */
	public abstract void setSecondaryHeaders(SecondaryHeader[] secondaryHeaders);

	/**
	 * Sets the flag indicating if there are secondary headers present.
	 * 
	 * @param secondaryHeadersPresent,
	 *            boolean value secondaryHeadersPresent to set.
	 */
	public abstract void setSecondaryHeadersPresent(
			boolean secondaryHeadersPresent);

	/**
	 * Sets the unique station number.
	 * 
	 * @param stationNumber,
	 *            the stationNumber to set.
	 */
	public abstract void setStationNumber(int stationNumber);

	/**
	 * Sets the flag indicating the presence or absence of taxonomic data.
	 * 
	 * @param taxaDataPresent,
	 *            a boolean indicating if taxa data are present.
	 */
	public abstract void setTaxaDataPresent(boolean taxaDataPresent);

	/**
	 * Sets the ArrayList of TaxaList, each TaxaList representing a taxa set
	 * 
	 * @param taxaSets,
	 *            the ArrayList<TaxaList> to set.
	 */
	public abstract void setTaxaSets(ArrayList<TaxaList> taxaSets);

	/**
	 * 
	 * Sets the time. When called with a null argument it means time is not 
	 * present in the data.
	 * 
	 * @param time,
	 *            the time to set.
	 */
	public abstract void setTime(Datum time);

	/**
	 * Sets the array of Variable objects representing variables in this
	 * profile.
	 * 
	 * @param variables,
	 *            the Variables[] to set.
	 */
	public abstract void setVariables(Variable[] variables);

	/**
	 * Sets the number of variables in the profile.
	 * 
	 * @param variablesInProfile,
	 *            an int, the number of variables in the profile to set.
	 */
	public abstract void setVariablesInProfile(int variablesInProfile);

	/**
	 * Sets the year.
	 * 
	 * @param year,
	 *            the year to set.
	 */
	public abstract void setYear(int year);

}
