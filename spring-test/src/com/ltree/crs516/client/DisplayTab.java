package com.ltree.crs516.client;

import java.awt.BorderLayout;
import java.util.Observable;
import java.util.Observer;

import javax.inject.Inject;
import javax.inject.Named;
import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ltree.crs516.domain.IStation;

public class DisplayTab extends JPanel implements Observer {

	/**
	 * To display the data.
	 */
	protected JEditorPane textArea = new JEditorPane("text/html", "");
	
	@Inject @Named("primaryHeaderDisplayer")
	protected Displayer displayer;
	private static final long serialVersionUID = 1L;
	private String title;

	private Logger logger = LoggerFactory.getLogger(getClass());

	public DisplayTab() {
		logger.debug("Initializing {}",getClass().getName());
		setLayout(new BorderLayout());
		this.add(new JScrollPane(textArea), BorderLayout.CENTER);
	}

	public void display(IStation station) {
			textArea.setText("");
			String theDisplayString = displayer.createDisplayString(station);	
			textArea.setText(theDisplayString);
		}

	public String getTitle() {
		return title;
	}

	public void setDisplayer(Displayer displayer) {
		this.displayer = displayer;
	}

	public void setTextArea(JEditorPane textArea) {
		this.textArea = textArea;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Displays the data that goes with a station. This method is called
	 * by the WODController to notify the tabs that the station has changed.
	 * (Observer/Observable pattern).
	 *
	 * @param theStation
	 *            The Station whose data is to be displayed.
	 *            
	 * @param  controller -- not currently used.           
	 */
	public void update(Observable controller, Object theStation) {
		display((IStation)theStation);
	}

	@Override
	public String toString() {
		return "DisplayTab [title=" + title + "]";
	}
}
