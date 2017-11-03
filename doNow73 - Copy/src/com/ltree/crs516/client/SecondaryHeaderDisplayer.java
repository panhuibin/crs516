package com.ltree.crs516.client;

import com.ltree.crs516.domain.SecondaryHeader;
import com.ltree.crs516.domain.Station;

/**
*
* This class prepares the display HTML string for the 
* Secondary Header Data (Strategy Pattern).
* 
* @author crs516 development team
*
*/
public final class SecondaryHeaderDisplayer implements Displayer {

	/**
	 * Generates the HTML string
	 * @param station, station whose data is to be displayer
	 * @return a String with the data represented as HTML. The <html> tag is not close
	 * as is common in Swing.
	 */
	@Override
	public String createDisplayString(Station station) {
		StringBuffer strb = new StringBuffer("<html>");
		if (station.isSecondaryHeadersPresent()) {
			strb.append("<h1>Secondary Headers</h1>");
			strb.append("<table border='1'>");
			/*Headings*/
			strb.append("<tr>");
			strb.append("<th>Code</th>");
			strb.append("<th>Meaning</th>");
			strb.append("<th>Value</th>");
			strb.append("<th>Value Meaning</th>");
			strb.append("</tr>");
			/*Data*/
			SecondaryHeader[] headers = station.getSecondaryHeaders();
			for (SecondaryHeader header : headers) {
				strb.append("<tr>");
				strb.append("<td>"+ header.getHeaderCode() + "</td>");
				strb.append("<td>"+ header.getHeaderString() +"</td>");
				strb.append("<td>"+header.getValueString()+"</td>");
				strb.append("<td>"+header.getValueMeaning()+"</td>");
				strb.append("</tr>");
			}
			strb.append("</table>");
		} else {
			strb.append("<h1>No Secondary Headers Present</h1>");
		}
		return(strb.toString());
	}

}
