
package com.ltree.crs516.client;

import java.awt.BorderLayout;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ltree.crs516.domain.CharDataEntry;
import com.ltree.crs516.domain.PrincipalInvestigator;
import com.ltree.crs516.domain.Station;


/**
 * A tab to hold information from the character data and principal investigator
 * information. The character data is used to report the originator's cruise 
 * identification and the originator's station identification. The Principal 
 * Investigator is the person responsible for collecting the data and is 
 * included whenever available.
 * 
 * @author crs516 development team
 * 
 */
 @SuppressWarnings("serial")
public final class CharacterDataTab extends JPanel implements Observer{
	
	/**
	 * To display the data.
	 */
	private JEditorPane textArea = new JEditorPane("text/html", "");
	private Logger logger = LoggerFactory.getLogger(getClass());

	public CharacterDataTab(){
		logger.debug("Initializing {}",getClass().getName());
		setLayout(new BorderLayout());
		this.add(new JScrollPane(textArea), BorderLayout.CENTER);
	}
	

	/* This method is called
	 * by the WODController to notify the tabs that the station has changed.
	 * (Observer pattern).
	 */
	public void update(Observable controller, Object theStation) {
		display((Station)theStation);
	}

	/**
	 * Displays the data that goes with a station.
	 * @param theStation
	 *            The Station whose data is to be displayed.
	 *            
	 * @param  controller.           
	 */
	public void display(Station station) {
		textArea.setText("");

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

		textArea.setText(theDisplayString);
	}

	public void setTextArea(JEditorPane textArea) {
		this.textArea = textArea;
	}
}
