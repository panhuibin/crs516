package com.ltree.crs516.client;

import com.ltree.crs516.domain.CharDataEntry;
import com.ltree.crs516.domain.Station;
import com.ltree.crs516.domain.PrincipalInvestigator;

public class CharacterDataDisplayHelper implements DisplayHelper {

	public String mineStation(Station station) {		
		//{{Marker 1
				StringBuffer strb = new StringBuffer("<html>");
				if(station.isCharacterDataPresent()){
					strb.append("<h1>Character Data and Principal Investigator</h1>");
					strb.append("<h3>There are "+ station.getNumCharacterDataEntries()
																	+" entries</h3>");
					strb.append("<table border='1'>");
					
					/*Headings*/
					strb.append("<tr>");
					strb.append("<th>Entry Type</th>");
					strb.append("<th>Value</th>");
					strb.append("</tr>");
		
					/*Data*/
		
					CharDataEntry[] entries = station.getCharacterDataEntries();
					for(CharDataEntry entry : entries){
						
						strb.append("<tr>");
						strb.append("<td >"+entry.getType()+"</td>");
						if(entry.getType()
								==CharDataEntry.CharDataType.PRINCIPAL_INVESTIGATOR){
							strb.append("<td>");
							PrincipalInvestigator[] pis = entry.getPis();
							strb.append("<table border='0'  id='investigators'>");
							strb.append("<tr>");
							strb.append("<th>Code</th>");
							strb.append("<th>Name</th>");
							strb.append("</tr>");
							for(PrincipalInvestigator pi : pis){
								strb.append("<tr>");
								strb.append("<td>"+pi.getPiCode()+"</td>");
								strb.append("<td>"+pi.getPiName()+"</td>");
								strb.append("</tr>");
							}
							
							strb.append("</table>");
							strb.append("</td>");
						}else{
							strb.append("<td>"+entry.getData()+"</td>");
						}
						strb.append("</tr>");
					}
					strb.append("</table>");
				}
				else{
					strb.append("<h1>No Character Data Present</h1>");
					
				}
				String theDisplayString = strb.toString();
		
		//}}end Marker 1
		return theDisplayString;
	}

}
