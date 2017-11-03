package com.ltree.crs516.client;

import java.awt.BorderLayout;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ltree.crs516.domain.Station;

 @SuppressWarnings("serial")
public class DisplayTab extends JPanel implements Observer{

	/**
	 * To display the data.
	 */
	protected JEditorPane textArea = new JEditorPane("text/html", "");
	protected Logger logger = LoggerFactory.getLogger(getClass());
	private DisplayHelper helper;
	private String title;
	protected Station station;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public DisplayTab() {
		super();
		setLayout(new BorderLayout());
		this.add(new JScrollPane(textArea), BorderLayout.CENTER);
	}
	
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
	public void display(Station theStation) {	
		station = theStation;
		//textArea.setText("");
		final String theDisplayString = createDisplayString(theStation);
		SwingUtilities.invokeLater(new Runnable(){
			@Override
			public void run() {
				textArea.setText(theDisplayString);
			}
		});

	}

	public void setTextArea(JEditorPane textArea) {
		this.textArea = textArea;
	}

	public void setHelper(DisplayHelper helper) {
		this.helper = helper;
	}
	
	protected String createDisplayString(Station station) {
	String theDisplayString = helper.mineStation(station);
		return theDisplayString;
	}

}
