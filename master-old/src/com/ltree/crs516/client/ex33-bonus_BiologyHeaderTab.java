
package com.ltree.crs516.client;

import java.awt.BorderLayout;
import java.util.Observable;
$import java.util.Observer;

$import javax.swing.JEditorPane;
$import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

$import com.ltree.crs516.domain.BiologyHeader;
import com.ltree.crs516.domain.Station;


/**
 * A tab to hold information from the biological header section. It contains
 * information on the sampling methods used for collecting taxonomic and biomass
 * measurements. "Biological" data are arbitrarily defined as plankton biomass
 * (weights or volumes) and taxa-specific observations. It does not include
 * chlorophyll data.
 * 
 * @author crs516 development team
 * 
 */
 @SuppressWarnings("serial")
$public final class BiologyHeaderTab extends JPanel implements Observer{
@public final class BiologyHeaderTab extends AbstractTab{

$	/**
$	 * To display the data.
$	 */
$	private JEditorPane textArea = new JEditorPane("text/html", "");

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	public BiologyHeaderTab() {
		logger.debug("Initializing {}",getClass().getName());
		setLayout(new BorderLayout());
		this.add(new JScrollPane(textArea), BorderLayout.CENTER);
	}

@	private Displayer displayer;
@	
@	public void setDisplayer(Displayer displayer) {
@		this.displayer = displayer;
@	}


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
		
//TODO 1 (in second bonus): Extract the material between //{{Marker 1 and  //}}Marker 1
//into a method called createDisplayString().
		
//{{Marker 1
		
$		StringBuffer strb = new StringBuffer("<html>");
$		if (station.isBiologyHeadersPresent()) {
$			strb.append("<table border='1'>");
$
$			/* Headings */
$			strb.append("<tr>");
$			strb.append("<th>OCL code</th>");
$			strb.append("<th>Meaning</th>");
$			strb.append("<th>Value</th>");
$			strb.append("<th>Value Meaning</th>");
$			strb.append("</tr>");
$			/* Data */
$			BiologyHeader[] headers = station.getBiologyHeaders();
$			for (BiologyHeader header : headers) {
$				strb.append("<tr>");
$				strb.append("<td align='right'>" + header.getHeaderCode()
$						+ "</td>");
$				strb.append("<td>" + header.getHeaderString() + "</td>");
$				strb.append("<td align='right'>" + header.getValueString()
$						+ "</td>");
$				strb.append("<td>" + header.getValueMeaning() + "</td>");
$				strb.append("</tr>");
$			}
$			strb.append("</table>");
$		} else {
$			strb.append("<h1>No Biology Headers Present</h1>");
$		}
$		String theDisplayString = strb.toString();
@		//String theDisplayString = createDisplayString(station); //This was 
@		//later replaced by the line below
@		String theDisplayString = displayer.createDisplayString(station);


//}}end Marker 1	


		textArea.setText(theDisplayString);
		
		
	}
%TODO 1:<br/>public void display(Station station) {<br/>textArea.setText("");<br/>//{{Marker 1<br/>//The line below replaces the code that moved to the new method<br/>String theDisplayString = createDisplayString(station);<br/>//}}Marker 1<br/>textArea.setText(theDisplayString);<br/>}<br/><br/>private String createDisplayString(Station station) {<br/>StringBuffer strb = new StringBuffer("<html>");<br/>...//code that was moved from the display() method<br/>String theDisplayString = strb.toString();<br/>return theDisplayString;<br/>}<br/>
	
	
@//Note; This code eventually moved to BiologyHeaderDisplayer	
@//	private String createDisplayString(Station station) {
@//		StringBuffer strb = new StringBuffer("<html>");
@//		if (station.isBiologyHeadersPresent()) {
@//			strb.append("<table border='1'>");
@//
@//			/* Headings */
@//			strb.append("<tr>");
@//			strb.append("<th>OCL code</th>");
@//			strb.append("<th>Meaning</th>");
@//			strb.append("<th>Value</th>");
@//			strb.append("<th>Value Meaning</th>");
@//			strb.append("</tr>");
@//			/* Data */
@//			BiologyHeader[] headers = station.getBiologyHeaders();
@//			for (BiologyHeader header : headers) {
@//				strb.append("<tr>");
@//				strb.append("<td align='right'>" + header.getHeaderCode()
@//						+ "</td>");
@//				strb.append("<td>" + header.getHeaderString() + "</td>");
@//				strb.append("<td align='right'>" + header.getValueString()
@//						+ "</td>");
@//				strb.append("<td>" + header.getValueMeaning() + "</td>");
@//				strb.append("</tr>");
@//			}
@//			strb.append("</table>");
@//		} else {
@//			strb.append("<h1>No Biology Headers Present</h1>");
@//		}
@//		String theDisplayString = strb.toString();
@//		return theDisplayString;
@//	}

$	public void setTextArea(JEditorPane textArea) {
$		this.textArea = textArea;
$	}

}
