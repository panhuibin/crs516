package com.ltree.crs516.client;

import java.util.List;

import com.ltree.crs516.domain.Station;
import com.ltree.crs516.domain.Taxa;
import com.ltree.crs516.domain.TaxaList;
import com.ltree.crs516.util.HTMLCleaner;

public class TaxaDisplayHelper implements DisplayHelper {

	@Override
	public String mineStation(Station station) {
		StringBuffer strb = new StringBuffer("<html>");
		if (station.isTaxaDataPresent()) {
			strb.append("<h1>Taxonomic Data Sets and Integrated Parameters: "
					+ station.getNumTaxaSets() + " sets.</h1>");
			List<TaxaList> taxaSets = station.getTaxaSets();
			for (int i = 0; i < taxaSets.size(); i++) {
				strb.append("<h2>Set " + (i + 1) + ":</h2>");
				TaxaList taxaSet = taxaSets.get(i);
				strb.append("<table border='1'>");
				/* Headings */
				strb.append("<tr>");
				strb.append("<th>Code</th>");
				strb.append("<th>Code Meaning</th>");
				strb.append("<th>Value</th>");
				strb.append("<th>Value Meaning</th>");
				strb.append("<th>Quality Flag</th>");
				strb.append("<th>Quality Flag Meaning</th>");
				strb.append("</tr>");
				/* Data */
				for (int j = 0; j < taxaSet.size(); j++) {
					Taxa taxa = taxaSet.get(j);
					strb.append("<tr>");
					strb.append("<td>" + taxa.getCode() + "</td>");
					strb.append("<td>" + HTMLCleaner.escape(taxa.getTaxVar()) + "</td>");
					strb.append("<td>" + taxa.getValueString() + "</td>");
					/* 
					 * Below, replace angle brackets with entities 
					 * so that the GUI does not try to 
					 * interpret them.
					 */
					String meaningString = HTMLCleaner.escape(taxa.getValueMeaning());
					strb.append("<td>" + meaningString + "</td>");
					strb.append("<td>" + taxa.getQualityFlag() + "</td>");
					strb.append("<td>" + taxa.getQualityFlagString() + "</td>");
					strb.append("</tr>");
				}
				strb.append("</table><p/>");
			}// end of a set
		}// All sets (if any) are done
		else {
			strb.append("<h1>No Taxa or integrated parameter data</h1>");
		}
		return(strb.toString());
	}

}
