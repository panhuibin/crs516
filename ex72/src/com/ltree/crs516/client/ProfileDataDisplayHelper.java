package com.ltree.crs516.client;

import com.ltree.crs516.domain.Datum;
import com.ltree.crs516.domain.Station;
import com.ltree.crs516.domain.Level;
import com.ltree.crs516.domain.Variable;
import com.ltree.crs516.domain.Station.ProfileType;

public class ProfileDataDisplayHelper implements DisplayHelper {

	@Override
	public String mineStation(Station station) {	
		StringBuffer strb = new StringBuffer("<html>");
		StringBuffer summaryBuffer  = new StringBuffer();
		if (station.isProfilePresent()) {
			Level[] profile = station.getProfile();
			int numOfLevels = profile.length;
			int numOfVars = station.getVariablesInProfile();
			if(profile.length>0){
				strb.append("<h1>Profile Data</h1>");
				strb.append("<h2>Details for each level</h2>");
				summaryBuffer.append("<h2>Summary Table</h2>");
			}
			else{
				strb.append("<h1>No Profile Data</h1>");
			}
			String[][] tableData = new String[numOfLevels][numOfVars+1];
			/* Depth of level i is item [i][0]*/
			for (int i = 0; i < profile.length; i++) {
				Level level = profile[i];
				strb.append("<table border='0'>");
				strb.append("<tr>");
				if(station.getProfileType()== ProfileType.OBSERVED){
					strb.append("<td>");
					strb.append("<table>" +
							"<tr><td>Depth: "+level.getDepthString()+"</td></tr>" +
							"<tr><td>Depth Err Code: "+level.getDepthErrorCode()+"</td></tr>" +
							"<tr><td>Orig Depth Err Code Flag: "+level.getOrigDepthErrorFlag()+"</td></tr>" +
							"</table>");
					strb.append("</td>");
				}else{strb.append("<td>Depth: "+level.getDepthString()+"</td>");}
				tableData[i][0]=level.getDepthString();
				strb.append("<td>");
				if (level.isDataPresent()) {
					Variable[] variables = station.getVariables();
					strb.append("<table border='1'>");
					/*Headings*/
					strb.append("<tr>");
					strb.append("<th>Variable</th>");
					strb.append("<th>Value</th>");
					strb.append("<th>Qual. Flag</th>");
					strb.append("<th>Orig. Flag</th>");
					strb.append("</tr>");
					/*Data*/
					Datum[] observations = level.getData();
					int obsCounter = 0;
					for (Datum obs : observations) {

						if(obs == null){
							strb.append("<tr>");
							strb.append("<td>"+variables[obsCounter++].getCodeString()+"</td>");
							strb.append("<td>missing</td>");
							strb.append("<td></td>");
							strb.append("<td></td>");
							strb.append("</tr>");
							tableData[i][obsCounter]="-";
							continue;
						}
						strb.append("<tr>");
						strb.append("<td>"+variables[obsCounter++].getCodeString()+"</td>");
						strb.append("<td>"+obs.getValueString()+"</td>");
						strb.append("<td>"+obs.getQualityFlag()+"</td>");
						strb.append("<td>"+obs.getOriginatorsFlag()+"</td>");
						strb.append("</tr>");
						tableData[i][obsCounter]=obs.getValueString();
					}
					strb.append("</table>");
				}
				strb.append("</td></tr></table>");//end of whole row
			}
			//make the summary table using tableData;
			summaryBuffer.append("<table border='1'>");
			//row for depth
			summaryBuffer.append("<tr><th>Depth</th>");
			for(int i = 0; i<numOfLevels;i++){
				summaryBuffer.append("<td>"+tableData[i][0]+"</td>");
			}
			summaryBuffer.append("</tr>");
			//the other rows
			for(int j=0; j< numOfVars;j++){
			summaryBuffer.append("<tr><th>"+station.getVariables()[j].getCodeString()+"</th>");
			for(int i = 0; i<numOfLevels;i++){
				summaryBuffer.append("<td>"+tableData[i][j+1]+"</td>");
			}
			summaryBuffer.append("</tr>");
			}
			summaryBuffer.append("</table>");
			if(numOfLevels>0){strb = strb.append(summaryBuffer);}
		} else {
			strb.append("<h1>No Profile Data Present</h1>");
		}
		return(strb.toString());
	}

}
