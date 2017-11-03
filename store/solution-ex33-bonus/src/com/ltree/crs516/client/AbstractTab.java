package com.ltree.crs516.client;

import java.awt.LayoutManager;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JEditorPane;
import javax.swing.JPanel;

import com.ltree.crs516.domain.Station;

 @SuppressWarnings("serial")
public abstract class AbstractTab extends JPanel implements Observer{

	/**
	 * To display the data.
	 */
	protected JEditorPane textArea = new JEditorPane("text/html", "");

	public AbstractTab() {
		super();
	}

	public AbstractTab(LayoutManager arg0) {
		super(arg0);
	}

	public AbstractTab(boolean arg0) {
		super(arg0);
	}

	public abstract void display(Station station);

	public AbstractTab(LayoutManager arg0, boolean arg1) {
		super(arg0, arg1);
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
		display((Station)theStation);
	}


	public void setTextArea(JEditorPane textArea) {
		this.textArea = textArea;
	}

}
