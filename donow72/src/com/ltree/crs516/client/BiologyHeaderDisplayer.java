package com.ltree.crs516.client;

import com.ltree.crs516.domain.BiologyHeader;
import com.ltree.crs516.domain.IStation;

/**
*
* This class prepares the display HTML string for the 
* BiologyHeaderTab (Strategy Pattern).
*
* @author Crs516 development team
*
*/
public final class BiologyHeaderDisplayer implements Displayer {

	@Override
	public String createDisplayString(IStation station) {
		StringBuffer strb = new StringBuffer("<html>");
		if (station.isBiologyHeadersPresent()) {
			strb.append("<table border='1'>");

			/* Headings */
			strb.append("<tr>");
			strb.append("<th>OCL code</th>");
			strb.append("<th>Meaning</th>");
			strb.append("<th>Value</th>");
			strb.append("<th>Value Meaning</th>");
			strb.append("</tr>");
			/* Data */
			BiologyHeader[] headers = station.getBiologyHeaders();
			for (BiologyHeader header : headers) {
				strb.append("<tr>");
				strb.append("<td align='right'>" + header.getHeaderCode()
						+ "</td>");
				strb.append("<td>" + header.getHeaderString() + "</td>");
				strb.append("<td align='right'>" + header.getValueString()
						+ "</td>");
				strb.append("<td>" + header.getValueMeaning() + "</td>");
				strb.append("</tr>");
			}
			strb.append("</table>");
		} else {
			strb.append("<h1>No Biology Headers Present</h1>");
		}
		String theDisplayString = strb.toString();
		return theDisplayString;
	}

}
