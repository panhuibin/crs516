package com.ltree.crs516.client;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * A tab to hold profile data
 *
 * @author crs516 development team
 *
 */
 @SuppressWarnings("serial")
public final class ProfileDataTab extends DisplayTab {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	/*Button for user to request scatter plots*/
	private JButton scatterPlotButton = new JButton("See Scatter Plots");

	public ProfileDataTab() {
		super();
		/*Extra button so user can request scatter plots */
		logger.debug("Initializing {}",getClass().getName());
		JPanel buttonPanel = new JPanel();
		this.add(buttonPanel, BorderLayout.SOUTH);
		scatterPlotButton.addActionListener(new ScatterListener());
		buttonPanel.add(scatterPlotButton);
	}

	class ScatterListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			ScatterDialog sd = new ScatterDialog(station);
			sd.pack();
			sd.setModal(true);
			sd.setVisible(true);
		}
	}
	
	@Override
	public String toString() {
		return "ProfileDataTab [title=" + getTitle() + "]";
	}
}
