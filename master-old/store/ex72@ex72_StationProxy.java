package com.ltree.crs516.domain;

import java.util.ArrayList;
import java.util.List;

import com.ltree.crs516.domain.Station.ProfileType;

public class StationProxy implements IStation {
	
	private Station station;

	public Station getStation() {
		return station;
	}

	public void setStation(Station station) {
		this.station = station;
	}

	@Override
	public BiologyHeader[] getBiologyHeaders() {
		return station.getBiologyHeaders();
	}

	@Override
	public int getBytesInProfile() {
		return station.getBytesInProfile();
	}

	@Override
	public int getC() {
		return station.getC();
	}

	@Override
	public CharDataEntry[] getCharacterDataEntries() {
		return station.getCharacterDataEntries();
	}

	@Override
	public String getCountry() {
		return station.getCountry();
	}

	@Override
	public String getCountryCode() {
		return station.getCountryCode();
	}

	@Override
	public int getCruiseNumber() {
		return station.getCruiseNumber();
	}

	@Override
	public int getDay() {
		return station.getDay();
	}

	@Override
	public int getL() {
		return station.getL();
	}

	@Override
	public Datum getLatitude() {
		return station.getLatitude();
	}

	@Override
	public int getLatitudePrecision() {
		return station.getLatitudePrecision();
	}

	@Override
	public int getLatitudeSigFig() {
		return station.getLatitudeSigFig();
	}

	@Override
	public String getLatitudeString() {
		return station.getLatitudeString();
	}

	@Override
	public int getLatitudeTotalFig() {
		return station.getLatitudeTotalFig();
	}

	@Override
	public int getLevels() {
		return station.getLevels();
	}

	@Override
	public Datum getLongitude() {
		return station.getLongitude();
	}

	@Override
	public int getLongitudePrecision() {
		return station.getLongitudePrecision();
	}

	@Override
	public int getLongitudeSigFig() {
		return station.getLongitudeSigFig();
	}

	@Override
	public String getLongitudeString() {
		return station.getLongitudeString();
	}

	@Override
	public int getLongitudeTotalFig() {
		return station.getLongitudeTotalFig();
	}

	@Override
	public int getMonth() {
		return station.getMonth();
	}

	@Override
	public int getN() {
		return station.getN();
	}

	@Override
	public int getNumB() {
		return station.getNumB();
	}

	@Override
	public int getNumBiologyHeaders() {
		return station.getNumBiologyHeaders();
	}

	@Override
	public int getNumCharacterDataEntries() {
		return station.getNumCharacterDataEntries();
	}

	@Override
	public int getNumSecondaryHeaders() {
		return station.getNumSecondaryHeaders();
	}

	@Override
	public int getNumTaxaSets() {
		return station.getNumTaxaSets();
	}

	@Override
	public Level[] getProfile() {
		return station.getProfile();
	}

	@Override
	public ProfileType getProfileType() {
		return station.getProfileType();
	}

	@Override
	public String getProfileTypeString() {
		return station.getProfileTypeString();
	}

	@Override
	public int getS() {
		return station.getS();
	}

	@Override
	public SecondaryHeader[] getSecondaryHeaders() {
		return station.getSecondaryHeaders();
	}

	@Override
	public int getStationNumber() {
		return station.getStationNumber();
	}

	@Override
	public int getT() {
		return station.getT();
	}

	@Override
	public List<TaxaList> getTaxaSets() {
		return station.getTaxaSets();
	}

	@Override
	public double getTime() {
		return station.getTime();
	}

	@Override
	public int getTimePrecision() {
		return station.getTimePrecision();
	}

	@Override
	public int getTimeSigFig() {
		return station.getTimeSigFig();
	}

	@Override
	public String getTimeString() {
		return station.getTimeString();
	}

	@Override
	public int getTimeTotalFig() {
		return station.getTimeTotalFig();
	}

	@Override
	public Variable[] getVariables() {
		return station.getVariables();
	}

	@Override
	public int getVariablesInProfile() {
		return station.getVariablesInProfile();
	}

	@Override
	public int getYear() {
		return station.getYear();
	}

	@Override
	public boolean isBiologyHeadersPresent() {
		return station.isBiologyHeadersPresent();
	}

	@Override
	public boolean isCharacterDataPresent() {
		return station.isCharacterDataPresent();
	}

	@Override
	public boolean isLatitudePresent() {
		return station.isLatitudePresent();
	}

	@Override
	public boolean isLongitudePresent() {
		return station.isLongitudePresent();
	}

	@Override
	public boolean isProfilePresent() {
		return station.isProfilePresent();
	}

	@Override
	public boolean isSecondaryHeadersPresent() {
		return station.isSecondaryHeadersPresent();
	}

	@Override
	public boolean isTaxaDataPresent() {
		return station.isTaxaDataPresent();
	}

	@Override
	public boolean isTimePresent() {
			return station.isTimePresent();
	}

	@Override
	public void setBiologyHeaders(BiologyHeader[] biologyHeaders) {
		station.setBiologyHeaders(biologyHeaders);

	}

	@Override
	public void setBytesInProfile(int bytesInProfile) {
		station.setBytesInProfile(bytesInProfile);
	}

	@Override
	public void setCharacterDataEntries(CharDataEntry[] characterDataEntries) {
		station.setCharacterDataEntries(characterDataEntries);
	}

	@Override
	public void setCountry(String country) {
		station.setCountry(country);
	}

	@Override
	public void setCountryCode(String countryCode) {
		station.setCountryCode(countryCode);
	}

	@Override
	public void setCruiseNumber(int cruiseNumber) {
		station.setCruiseNumber(cruiseNumber);
	}

	@Override
	public void setDay(int day) {
		station.setDay(day);
	}

	@Override
	public void setLatitude(Datum latitude) {
		station.setLatitude(latitude);
	}

	@Override
	public void setLevels(int levels) {
		station.setLevels(levels);
	}

	@Override
	public void setLongitude(Datum longitude) {
		station.setLongitude(longitude);
	}

	@Override
	public void setMonth(int month) {
		station.setMonth(month);
	}

	@Override
	public void setNumBiologyHeaders(int numBiologyHeaders) {
		station.setNumBiologyHeaders(numBiologyHeaders);
	}

	@Override
	public void setNumCharacterDataEntries(int numCharacterDataEntries) {
		station.setNumCharacterDataEntries(numCharacterDataEntries);
	}

	@Override
	public void setNumSecondaryHeaders(int numSecondaryHeaders) {
		station.setNumSecondaryHeaders(numSecondaryHeaders);
	}

	@Override
	public void setNumTaxaSets(int numTaxaSets) {
		station.setNumTaxaSets(numTaxaSets);
	}

	@Override
	public void setProfile(Level[] profile) {
		station.setProfile(profile);
	}

	@Override
	public void setProfileType(ProfileType profileType) {
		station.setProfileType(profileType);
	}

	@Override
	public void setSecondaryHeaders(SecondaryHeader[] secondaryHeaders) {
		station.setSecondaryHeaders(secondaryHeaders);
	}

	@Override
	public void setSecondaryHeadersPresent(boolean secondaryHeadersPresent) {
		station.setSecondaryHeadersPresent(secondaryHeadersPresent);
	}

	@Override
	public void setStationNumber(int stationNumber) {
		station.setStationNumber(stationNumber);
	}

	@Override
	public void setTaxaDataPresent(boolean taxaDataPresent) {
		station.setTaxaDataPresent(taxaDataPresent);
	}

	@Override
	public void setTaxaSets(ArrayList<TaxaList> taxaSets) {
		station.setTaxaSets(taxaSets);
	}

	@Override
	public void setTime(Datum time) {
		station.setTime(time);
	}

	@Override
	public void setVariables(Variable[] variables) {
		station.setVariables(variables);
	}

	@Override
	public void setVariablesInProfile(int variablesInProfile) {
		station.setVariablesInProfile(variablesInProfile);
	}

	@Override
	public void setYear(int year) {
		station.setYear(year);

	}

}
