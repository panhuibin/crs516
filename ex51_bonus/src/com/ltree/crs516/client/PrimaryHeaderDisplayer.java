package com.ltree.crs516.client;

import com.ltree.crs516.domain.Station;
import com.ltree.crs516.domain.Variable;
import com.ltree.crs516.domain.VariableMeta;

/**
*
* This class prepares the display HTML string for the 
* PrimaryHeaders (Strategy Pattern).
* 
*
* @author Crs516 development team
*
*/
public final class PrimaryHeaderDisplayer implements Displayer {

	/**
	 * Generates the HTML string
	 * @param station, station whose data is to be displayer
	 * @return a String with the data represented as html. The <html> tag is not closed
	 * as is common in Swing.
	 */
	@Override
	public String createDisplayString(Station station) {
		StringBuffer strb = new StringBuffer("<html>");
		strb.append("<table>");
		strb.append("<tr><td>OCL unique station number</td><td>"
				+ station.getStationNumber() + "</td></tr>");
		strb.append("<tr><td>Cruise code</td><td>" + station.getCruiseNumber()
				+ "</td></tr>");
		strb.append("<tr><td>NODC country code</td><td>"
				+ station.getCountryCode() + "</td></tr>");
		strb.append("<tr><td>Country</td><td>" + station.getCountry()
				+ "</td></tr>");
		strb.append("<tr><td>Year</td><td>" + station.getYear() + "</td></tr>");
		strb.append("<tr><td>Month</td><td>" + station.getMonth()
				+ "</td></tr>");
		strb.append("<tr><td>Day</td><td>" + station.getDay() + "</td></tr>");
		strb.append("<tr><td>Time</td><td>");
		if (station.isTimePresent()) {
			strb.append(station.getTimeString());
		} else {
			strb.append("Time: missing");
		}
		strb.append("</td></tr>");
		strb.append("<tr><td>Latitude</td><td>");
		if (station.isLatitudePresent()) {
			strb.append(station.getLatitudeString());
		} else {
			strb.append("missing");
		}
		strb.append("</td></tr>");
		strb.append("<tr><td>Longitude</td><td>");
		if (station.isLongitudePresent()) {
			strb.append(station.getLongitudeString());
		} else {
			strb.append("missing");
		}
		strb.append("</td></tr>");
		strb.append("<tr><td>Profile type</td><td>"
				+ station.getProfileTypeString() + "</td></tr>");
		strb.append("<tr><td>Number of levels</td><td>" + station.getLevels()
				+ "</td></tr>");
		strb.append("<tr><td># Variables in profile</td><td>"
				+ station.getVariablesInProfile() + "</td></tr>");
		strb.append("</table>");
		Variable[] variables = station.getVariables();
		if (variables.length > 0) {
			strb.append("<h3>Variables</h3>");
			strb.append("<table border='1'>");
			/* Headings */
			strb.append("<tr>");
			strb.append("<th>Variable</th>");
			strb.append("<th>Quality</th>");
			strb.append("<th>Variable-specific metadata</th>");
			strb.append("</tr>");
			for (Variable var : variables) {
				strb.append("<tr>");
				strb.append("<td>" + var.getCodeString() + "</td>");
				strb.append("<td>" + var.getQualityFlagString() + "</td>");
				strb.append("<td>");
				if (var.getNumMetaData() > 0) {
					VariableMeta[] metaData = var.getMetaData();
					strb.append("<ul>");
					for (VariableMeta meta : metaData) {
						strb.append("<li>");
						strb.append("Code: " + meta.getCode() + "  "
								+ meta.getVarCode() + "  " + "("
								+ meta.getVarComment() + ")" + "<br />");
						strb.append("Value: " + meta.getValueString()
								+ "  " + meta.getValueMeaning());
						strb.append("</li>");
					}
					strb.append("</ul>");
				} else {
					strb.append("None.");
				}
				strb.append("</td>");
				strb.append("</tr>");
			}
			strb.append("</table>");
		}
		return(strb.toString());
	}

}
