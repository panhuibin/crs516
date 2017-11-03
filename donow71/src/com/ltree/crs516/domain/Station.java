package com.ltree.crs516.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a WOD09 station. A station is data from one 
 * or more casts at one geographic location. The WOC09 documentation
 * is at http://www.nodc.noaa.gov/OC5/WOD09/pr_wod09.html
 * A station's data will include primary header, 
 * secondary header, variable-specific second header, 
 * character data, biological header, 
 * taxa-specific and biomass data, and measured variables.
 * 
 * There are classes to represent the components of a station.
 * The Station itself is a DTO (Data Transfer Object) to deliver
 * these classes to the view.
 * 
 * @see SecondaryHeader
 * @see CharDataEntry
 * @see BiologyHeader
 * @see Level
 * @see TaxaList
 * @see Variable
 * 
 * @author crs516 development team
 * 
 */
public class Station {
	
	public static class StationBuilder {
		
		//Mandatory fields
		private final int bytesInProfile;
		private final int stationNUmber;
		private final String countryCode;
		private final String country;
		private final int cruiseNumber;
		private final int year;
		private final int month;
		private final int day;
		private final Datum time;
		private final Datum latitude;
		private final Datum longitude;
		private final int levels;
		private final ProfileType profileType;

		//optional fields
		private int variablesInProfile;
		private Variable[] variables = new Variable[0];
		private int numCharacterDataEntries;
		private CharDataEntry[] characterDataEntries = new CharDataEntry[0];
		private boolean secondaryHeadersPresent;
		private int numSecondaryHeaders;
		private SecondaryHeader[] secondaryHeaders = new SecondaryHeader[0];
		private int numBiologyHeaders;
		private BiologyHeader[] biologyHeaders = new BiologyHeader[0];
		private boolean taxaDataPresent;
		private int numTaxaSets;
		private List<TaxaList> taxaSets = new ArrayList<>();
		private Level[] profile = new Level[0];
		
		/**
		 * Constructor receives all the mandatory state.
		 * @param bytesInProfile
		 * @param stationNUmber
		 * @param countryCode
		 * @param country
		 * @param cruiseNumber
		 * @param year
		 * @param month
		 * @param day
		 * @param time
		 * @param latitude
		 * @param longitude
		 * @param levels
		 * @param profileType
		 */
		public StationBuilder(int bytesInProfile, int stationNUmber, 
				String countryCode, String country, int cruiseNumber, int year, 
				int month, int day, Datum time, Datum latitude, Datum longitude, 
				int levels, ProfileType profileType) {			
			this.bytesInProfile = bytesInProfile;
			this.stationNUmber = stationNUmber;
			this.countryCode = countryCode;
			this.country = country;
			this.cruiseNumber = cruiseNumber;
			this.year = year;
			this.month = month;
			this.day = day;
			this.time = time;
			this.latitude = latitude;
			this.longitude = longitude;
			this.levels = levels;
			this.profileType = profileType;
		}

		public Station build() {
			return new Station(this);
		}
		
		public StationBuilder variablesInProfile(int val) {
			variablesInProfile = val;
			return this;
		}
		
		public StationBuilder variables(Variable[] val) {
			variables = val;
			return this;
		}

		public StationBuilder numCharacterDataEntries(int val) {
			numCharacterDataEntries = val;
			return this;
		}

		public StationBuilder characterDataEntries(CharDataEntry[] val) {
			characterDataEntries = val;
			return this;
		}

		public StationBuilder secondaryHeadersPresent(boolean val) {
			secondaryHeadersPresent = val;
			return this;
		}

		public StationBuilder numSecondaryHeaders(int val) {
			numSecondaryHeaders = val;
			return this;
		}

		public StationBuilder secondaryHeaders(SecondaryHeader[] val) {
			secondaryHeaders = val;
			return this;
		}

		public StationBuilder numBiologyHeaders(int val) {
			numBiologyHeaders = val;
			return this;
		}

		public StationBuilder biologyHeaders(BiologyHeader[] val) {
			biologyHeaders = val;
			return this;
			
		}

		public StationBuilder taxaDataPresent(boolean val) {
			taxaDataPresent = val;
			return this;
		}

		public StationBuilder numTaxaSets(int val) {
			numTaxaSets = val;
			return this;
		}

		public StationBuilder taxaSets(List<TaxaList> val) {
			taxaSets = val;
			return this;
		}

		public StationBuilder profile(Level[] val) {
			profile = val;
			return this;
		}

		public ProfileType getProfileType() {
			return profileType;
		}
		
		public int getLevels() {
			return levels;
		}

		public int getBytesInProfile() {
			return bytesInProfile;
		}
		
		public int getVariablesInProfile() {
			return variablesInProfile;
		}
		
	}//end of StationBuilder

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
	 * Profile type. In the raw data the possibilities are 0 (Observed) and 1 (Standard level).
	 */
	public enum ProfileType{OBSERVED, STANDARD_LEVEL}
	
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
	 * A List of TaxaList objects, each of which represents a taxonomic
	 * data set.
	 */
	private List<TaxaList> taxaSets;

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
	 * The sole constructor.
	 * 
	 */
	private Station(StationBuilder builder) {
		bytesInProfile = builder.bytesInProfile;
		stationNumber = builder.stationNUmber;
		countryCode = builder.countryCode;
		country = builder.country;
		cruiseNumber = builder.cruiseNumber;
		year = builder.year;
		month = builder.month;
		day = builder.day;
		time = builder.time;
		latitude = builder.latitude;
		longitude = builder.longitude;
		levels = builder.levels;
		profileType = builder.profileType;
		variablesInProfile = builder.variablesInProfile;
		variables = builder.variables;
		numCharacterDataEntries = builder.numCharacterDataEntries;
		characterDataEntries = builder.characterDataEntries;
		secondaryHeadersPresent = builder.secondaryHeadersPresent;
		numSecondaryHeaders = builder.numSecondaryHeaders;
		secondaryHeaders = builder.secondaryHeaders;
		numBiologyHeaders = builder.numBiologyHeaders;
		biologyHeaders = builder.biologyHeaders;
		taxaDataPresent = builder.taxaDataPresent;
		numTaxaSets = builder.numTaxaSets;
		taxaSets = builder.taxaSets;
		profile = builder.profile;
	}

	/**
	 * Gets the Biology headers.
	 * 
	 * @return an array of BiologyHeader objects.
	 */
	public BiologyHeader[] getBiologyHeaders() {
		return biologyHeaders;
	}

	/**
	 * Gets the number of bytes in the profile.
	 * 
	 * @return an int, the number of bytes in the profile.
	 */
	public int getBytesInProfile() {
		return bytesInProfile;
	}

	/**
	 * Convenience method corresponding to the number of character data 
	 * entries is called C in the WODC09 documentation.
	 * 
	 * @return an int, the number of character data entries.
	 */
	public int getC() {
		return numCharacterDataEntries;
	}

	/**
	 * Gets the array of character data entries.
	 * 
	 * @return a CharDataEntry[], the array of characterDataEntries.
	 */
	public CharDataEntry[] getCharacterDataEntries() {
		return characterDataEntries;
	}

	/**
	 * Gets the country that owns the country code.
	 * 
	 * @return a string, the country.
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * Gets the country code.
	 * 
	 * @return a string,  the countryCode.
	 */
	public String getCountryCode() {
		return countryCode;
	}

	/**
	 * Gets the cruise number.
	 * 
	 * @return an int, the cruise number.
	 */
	public int getCruiseNumber() {
		return cruiseNumber;
	}

	/**
	 * Gets the day.
	 * 
	 * @return an int, the day.
	 */
	public int getDay() {
		return day;
	}

	/**
	 * Convenience method to return the number of levels, called L in the
	 * documentation.
	 * 
	 * @return an int, the number of levels.
	 */
	public int getL() {
		return levels;
	}

	/**
	 * Gets the latitude.
	 * 
	 * @return a Datum object representing the latitude.
	 */
	public Datum getLatitude() {
		return latitude;
	}

	/**
	 * Gets the precision to which the latitude is given.
	 * 
	 * @return an int, the latitude precision.
	 */
	public int getLatitudePrecision() {
		return latitude.getPrecision();
	}

	/**
	 * Gets the number of significant figures to which the latitude is
	 * measured.
	 * 
	 * @return an int, the number of significant figures.
	 */
	public int getLatitudeSigFig() {
		return latitude.getSigFig();
	}

	/**
	 * Gets a the latitude properly formatted according to the precision.
	 * 
	 * @return a string, the latitude.
	 */
	public String getLatitudeString() {
		return latitude.getValueString();
	}

	/**
	 * Gets the total number of figures in the latitude.
	 * 
	 * @return an int, the total number of figures.
	 */
	public int getLatitudeTotalFig() {
		return latitude.getTotalFig();
	}

	/**
	 * Gets the number of depths in the profile.
	 * 
	 * @return an int, the number of levels.
	 */
	public int getLevels() {
		return levels;
	}

	/**
	 * Gets the longitude.
	 * 
	 * @return a Datum object representing the longitude.
	 */
	public Datum getLongitude() {
		return longitude;
	}

	/**
	 * Gets the precision to which the longitude is given.
	 * 
	 * @return an int, the precision of the longitude.
	 */
	public int getLongitudePrecision() {
		return longitude.getPrecision();
	}

	/**
	 * Gets the number of significant figures in the longitude.
	 * 
	 * @return an int, the number of significant figures in the longitude.
	 */
	public int getLongitudeSigFig() {
		return longitude.getSigFig();
	}

	/**
	 * Gets the longitude as a string formatted according to the precision.
	 * 
	 * @return a String, the longitude.
	 */
	public String getLongitudeString() {
		return longitude.getValueString();
	}

	/**
	 * Gets the number of significant figures in the longitude.
	 * 
	 * @return an int, the number of significant figures in the longitude.
	 */
	public int getLongitudeTotalFig() {
		return longitude.getTotalFig();
	}

	/**
	 * Gets the month.
	 * 
	 * @return an int, the month.
	 */
	public int getMonth() {
		return month;
	}

	/**
	 * Convenience method to get the number of variables in the profile, called
	 * N in the WOD09 documentation.
	 * 
	 * @return an int, the number of variables in the profile.
	 */
	public int getN() {
		return variablesInProfile;
	}

	/**
	 * Convenience method to get the number of biology headers, called B in the
	 * WOD09 documentation.
	 * 
	 * @return an int, the number of biology headers;
	 */
	public int getNumB() {
		return numBiologyHeaders;
	}

	/**
	 * Gets the number of biology headers.
	 * 
	 * @return an int, the number of biology headers.
	 */
	public int getNumBiologyHeaders() {
		return numBiologyHeaders;
	}

	/**
	 * Gets the number of character data entries.
	 * 
	 * @return an int, the number of character data entries.
	 */
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
	public int getNumSecondaryHeaders() {
		return numSecondaryHeaders;
	}

	/**
	 * Gets the number of taxonomic data sets.
	 * 
	 * @return an int, the number of taxonomic data sets.
	 */
	public int getNumTaxaSets() {
		return numTaxaSets;
	}

	/**
	 * Gets an array of Level objects, one Level object for each depth in the
	 * profile.
	 * 
	 * @return a Level[] containing the Level data for each depth in the profile.
	 */
	public Level[] getProfile() {
		return profile;
	}

	/**
	 * Gets the profile type.
	 * 
	 * @return one of the values of the ProfileType enum.
	 */
	public ProfileType getProfileType() {
		return profileType;
	}

	/**
	 * Return the profile type as a String.
	 * 
	 * @return  a String, whether "observed" (0 in the original data)
	 *  or "standard level" (1 in the original data).
	 */
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
	public int getS() {
		return numSecondaryHeaders;
	}

	/**
	 * Gets the secondary headers.
	 * 
	 * @return a SecondaryHeader[] with secondary header data.
	 */
	public SecondaryHeader[] getSecondaryHeaders() {
		return secondaryHeaders;
	}

	/**
	 * Gets the unique station number.
	 * 
	 * @return an int, the station number.
	 */
	public int getStationNumber() {
		return stationNumber;
	}

	/**
	 * Convenience method to return the number of taxonomic data sets, called T
	 * in the WOD09 documentation.
	 * 
	 * @return an int, the number of taxonomic data sets.
	 */
	public int getT() {
		return numTaxaSets;
	}

	/**
	 * Gets the taxonomic data sets.
	 * 
	 * @return a List<TaxaList>, the TaxaLists representing the 
	 * taxonomic data sets.
	 */
	public List<TaxaList> getTaxaSets() {
		return taxaSets;
	}

	/**
	 * Gets the time.
	 * 
	 * @return a double, the time. If time is not present it returns -1.0
	 */
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
	public int getTimePrecision() {
		return time.getPrecision();
	}

	/**
	 * Gets the number of significant figures in the time.
	 * 
	 * @return an int, the time significant figures.
	 */
	public int getTimeSigFig() {
		return time.getSigFig();
	}

	/**
	 * Gets the time as a string formatted according to the precision.
	 * 
	 * @return a String, representing the time.
	 */
	public String getTimeString() {

		return time.getValueString();
	}

	/**
	 * Gets the total number of figures in the time.
	 * 
	 * @return the total number of figures.
	 */
	public int getTimeTotalFig() {
		return time.getTotalFig();
	}

	/**
	 * Gets the variables.
	 * 
	 * @return an array of Variable objects.
	 */
	public Variable[] getVariables() {
		return variables;
	}

	/**
	 * Gets the number of variables in the profile.
	 * 
	 * @return an int, the number of variables in the profile.
	 */
	public int getVariablesInProfile() {
		return variablesInProfile;
	}

	/**
	 * Gets the year.
	 * 
	 * @return an int, the year.
	 */
	public int getYear() {
		return year;
	}

	/**
	 * Checks if there are biology headers.
	 * 
	 * @return  a boolean, true if there are biology headers and false otherwise.
	 */
	public boolean isBiologyHeadersPresent() {
		return(biologyHeaders!=null && biologyHeaders.length>0);
	}

	/**
	 * Checks if there are character data entries.
	 * 
	 * @return a boolean, true if there are character data entries and false otherwise.
	 */
	public boolean isCharacterDataPresent() {
		return(characterDataEntries!=null && characterDataEntries.length>0);
	}

	/**
	 * Checks if the latitude is present..
	 * 
	 * @return a boolean, true if the latitude is present and false otherwise.
	 */
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
	public boolean isSecondaryHeadersPresent() {
		return secondaryHeadersPresent;
	}

	/**
	 * Checks if there are taxonomic data.
	 * 
	 * @return a boolean, true if there are taxonomic data present and false otherwise.
	 */
	public boolean isTaxaDataPresent() {
		return taxaDataPresent;
	}

	/**
	 * Checks it time data is present.
	 * 
	 * @return a boolean,  true if time is present and false otherwise.
	 */
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
	public void setBiologyHeaders(BiologyHeader[] biologyHeaders) {
		this.biologyHeaders = biologyHeaders;
	}

	/**
	 * Sets the number of bytes in the profile.
	 * 
	 * @param bytesInProfile,
	 *            the bytesInProfile to set.
	 */
	public void setBytesInProfile(int bytesInProfile) {
		this.bytesInProfile = bytesInProfile;
	}

	/**
	 * Sets the array of character data entries.
	 * 
	 * @param characterDataEntries.
	 *            the CharDataEntry[] to set.
	 */
	public void setCharacterDataEntries(CharDataEntry[] characterDataEntries) {
		this.characterDataEntries = characterDataEntries;
	}

	/**
	 * Sets the country.
	 * 
	 * @param country,
	 *            the country to set.
	 */
	public void setCountry(String country) {
		this.country = country;
	}

	/**
	 * Sets the country code.
	 * 
	 * @param countryCode,
	 *            the countryCode to set.
	 */
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	/**
	 * Sets the cruise number.
	 * 
	 * @param cruiseNumber,
	 *            the cruiseNumber to set.
	 */
	public void setCruiseNumber(int cruiseNumber) {
		this.cruiseNumber = cruiseNumber;
	}

	/**
	 * Sets the day.
	 * 
	 * @param day,
	 *            the day to set.
	 */
	public void setDay(int day) {
		this.day = day;
	}

	/**
	 * Sets the latitude.
	 * 
	 * @param latitude,
	 *            a Datum representing the latitude.
	 */
	public void setLatitude(Datum latitude) {
		this.latitude = latitude;
	}

	/**
	 * Sets the number of depths (levels).
	 * 
	 * @param levels
	 *            the number of depth levels to set.
	 */
	public void setLevels(int levels) {
		this.levels = levels;
	}

	/**
	 * Sets the longitude.
	 * 
	 * @param longitude,
	 *            a Datum representing the longitude.
	 */
	public void setLongitude(Datum longitude) {
		this.longitude = longitude;
	}

	/**
	 * Sets the month.
	 * 
	 * @param month,
	 *            the month to set.
	 */
	public void setMonth(int month) {
		this.month = month;
	}

	/**
	 * Sets the number of biology headers
	 * 
	 * @param numBiologyHeaders,
	 *            the number of headers to set.
	 */
	public void setNumBiologyHeaders(int numBiologyHeaders) {
		this.numBiologyHeaders = numBiologyHeaders;
	}

	/**
	 * Sets the number of character data entries.
	 * 
	 * @param numCharacterDataEntries,
	 *            the numCharacterDataEntries to set.
	 */
	public void setNumCharacterDataEntries(int numCharacterDataEntries) {
		this.numCharacterDataEntries = numCharacterDataEntries;
	}

	/**
	 * Sets the number of secondary headers
	 * 
	 * @param numSecondaryHeaders,
	 *            the numSecondaryHeaders to set.
	 */
	public void setNumSecondaryHeaders(int numSecondaryHeaders) {
		this.numSecondaryHeaders = numSecondaryHeaders;
	}

	/**
	 * Sets the number of taxonomic data sets
	 * 
	 * @param numTaxaSets,
	 *            the number of taxa sets to set.
	 */
	public void setNumTaxaSets(int numTaxaSets) {
		this.numTaxaSets = numTaxaSets;
	}

	/**
	 * Sets the profile which is an array of Level objects.
	 * 
	 * @param profile,
	 *            a Level[] to set
	 */
	public void setProfile(Level[] profile) {
		this.profile = profile;
	}

	/**
	 * Sets the profile type.
	 * 
	 * @param profileType,
	 *            the profileType to set.
	 */
	public void setProfileType(ProfileType profileType) {
		this.profileType = profileType;
	}

	/**
	 * Sets the secondary headers.
	 * 
	 * @param secondaryHeaders,
	 *            the secondaryHeaders to set.
	 */
	public void setSecondaryHeaders(SecondaryHeader[] secondaryHeaders) {
		this.secondaryHeaders = secondaryHeaders;
	}

	/**
	 * Sets the flag indicating if there are secondary headers present.
	 * 
	 * @param secondaryHeadersPresent,
	 *            boolean value secondaryHeadersPresent to set.
	 */
	public void setSecondaryHeadersPresent(boolean secondaryHeadersPresent) {
		this.secondaryHeadersPresent = secondaryHeadersPresent;
	}

	/**
	 * Sets the unique station number.
	 * 
	 * @param stationNumber,
	 *            the stationNumber to set.
	 */
	public void setStationNumber(int stationNumber) {
		this.stationNumber = stationNumber;
	}

	/**
	 * Sets the flag indicating the presence or absence of taxonomic data.
	 * 
	 * @param taxaDataPresent,
	 *            a boolean indicating if taxa data are present.
	 */
	public void setTaxaDataPresent(boolean taxaDataPresent) {
		this.taxaDataPresent = taxaDataPresent;
	}

	/**
	 * Sets the ArrayList of TaxaList, each TaxaList representing a taxa set
	 * 
	 * @param taxaSets,
	 *            the ArrayList<TaxaList> to set.
	 */
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
	public void setVariables(Variable[] variables) {
		this.variables = variables;
	}

	/**
	 * Sets the number of variables in the profile.
	 * 
	 * @param variablesInProfile,
	 *            an int, the number of variables in the profile to set.
	 */
	public void setVariablesInProfile(int variablesInProfile) {
		this.variablesInProfile = variablesInProfile;
	}

	/**
	 * Sets the year.
	 * 
	 * @param year,
	 *            the year to set.
	 */
	public void setYear(int year) {
		this.year = year;
	}

}
