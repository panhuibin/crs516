package com.ltree.crs516.client;

import static com.ltree.crs516.data.DataConstants.DATA_DIR;

import java.awt.BorderLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Date;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ltree.crs516.controller.WODController;


/**
 * This JFrame that holds the GUI. It has the main method. It has tabs to display
 * the various data.
 * original version if file-centric
 * 
 * @author crs516 development team
 * 
 */
public final class WOD extends JFrame implements WODClient{
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	private static final long serialVersionUID = 1L;

	/**
	 * The controller in the MVC pattern.
	 */
	private WODController controller;
	
	public void setController(WODController controller) {
		this.controller = controller;
		controller.setOwningFrame(this);
	}

	/**
	 * Same fileChooser is used throughout the application.
	 */
	private final JFileChooser fileChooser 
	= new JFileChooser(DATA_DIR);
	
	/**
	 * Holds the buttons for next and previous.
	 */
	private JPanel bottomPanel;
	
	/**
	 * This class generates the license information.
	 */
	private class AboutDialog extends JDialog {

		private static final long serialVersionUID = 1L;

		public AboutDialog() {
			JTextArea area = new JTextArea();
			area.setText("\n ===========================================================\n" +
							" WOD Browser : a free library for the Java(tm) platform" +
							"\n ===========================================================" +
							" \n" +
							" \n (C) Copyright 2011, by Crs516 development team" +
							" \n" +
							" \n This file is part of World Ocean Database 2009 Data Browser" +
							" \n (WOD09 Browser). WOD09 Browser is free software; you can" +
							" \n redistribute it and/or modify it under the terms of the" +
							" \n GNU General Public License as published by the Free Software" +
							" \n Foundation; either version 2 of the License, or (at your option)" +
							" \n any later version. WOD09 Browser is distributed in the hope" +
							" \n that it will be useful, but WITHOUT ANY WARRANTY; without even" +
							" \n the implied warranty of MERCHANTABILITY or FITNESS FOR A" +
							" \n PARTICULAR PURPOSE.  See the GNU General Public License" +
							" \n for more details. You should have received a copy of the GNU" +
							" \n General Public License along with the WOD09 Browser program; if not, write to" +
							" \n the Free Software Foundation, Inc., 51 Franklin St, Fifth" +
							" \n Floor, Boston, MA  02110-1301  USA" +
							"\n" +
							"\n [Oracle and Java are registered trademarks of Oracle and/or its affiliates." +
							" \n Other names may be trademarks of their respective owners.]" +
							"\n");
			JScrollPane scroll = new JScrollPane(area);
			add(scroll, BorderLayout.CENTER);
			pack();
			Point loc =WOD.this.getLocation(); 
			setLocation(loc.x+100, loc.y+100);
		}
	}

	/**
	 * Listens to the About menu item and displays the copyright information.
	 */
	private class AboutListener implements ActionListener {
		AboutDialog dialog = new AboutDialog();

		/**
		 * The only constructor.
		 * 
		 */
		public AboutListener() {
		}

		public void actionPerformed(ActionEvent arg0) {
			Point loc =WOD.this.getLocation(); 
			dialog.setLocation(loc.x+100, loc.y+100);
			dialog.setVisible(true);
		}
	}

	/**
	 * Listens to the FileLoadItem menu item and displays the JFileChooser when
	 * activated.
	 */
	private class FileListener implements ActionListener {
		/**
		 * Tells controller to switch files
		 */
		public void actionPerformed(ActionEvent arg0) {
			chooseFile(); 
		}

		private void chooseFile() {
				String theFileStr = null;
				int result = fileChooser.showDialog(WOD.this, "Read");
				if (result == JFileChooser.APPROVE_OPTION) {
					File chosen = fileChooser.getSelectedFile();
					theFileStr = chosen.toString();
					fileName = chosen.getName();
				}
				if (theFileStr != null) {
					logger.info("Reading " + fileName);
					controller.loadFile(theFileStr);
				}
			}
		}

	
	/**
	 * Listens to the "Next" button and requests the controller for 
	 * the next station. Controller will ask data layer for the next
	 * station.
	 */
	private class NextButtonListener implements ActionListener {

		public void actionPerformed(ActionEvent arg0) {
			controller.next();
		}
	}

	/**
	 * Listens to the "Previous" button and requests controller for 
	 * the previous station. The controller asks the data layer for the
	 * previous station.
	 */
	private class PrevButtonListener implements ActionListener {

		public void actionPerformed(ActionEvent arg0) {
			controller.previous();
		}
	}

	/**
	 * Listens to the JTextField StationField and requests the controller for the
	 * station typed into the station field.
	 */
	private class StationFieldListener implements ActionListener {

		public void actionPerformed(ActionEvent arg0) {
			String text = stationField.getText();
			int theStation = controller.getCurrentStation();
			try {
				theStation = Integer.parseInt(text);
			} catch (NumberFormatException e) {
				// bad string
				sendMessage("Not a valid station");
				stationField.setText("" + controller.getCurrentStation());
				return;
			}
			controller.setCurrentStation(theStation);
		}
	}

	private class GetAllDataListener implements ActionListener {

		public void actionPerformed(ActionEvent arg0) {
			sendMessage("This might take a while. Bear with me ...");
			controller.getDetails();
		}
	}
	
	/**
	 * Name of file being viewed.
	 */
	private String fileName = "";

	/**
	 * JLabel for displaying simple information messages to the user.
	 */
	private JLabel messageLabel = new JLabel("");

	/**
	 * Slider to make it easy to navigate the data.
	 */
	private WODSlider slider;

	/* Holds the various tabs in the GUI.*/
	private JTabbedPane tabbedPane = new JTabbedPane();
	
	/**
	 * TextField for easy location of a particular station.
	 */
	private JTextField stationField;

	/**
	 * Constructs the GUI with the given title.
	 * 
	 * @param title,
	 *            the title to appear in the title bar
	 */
	public WOD(String title) {
		this();
		setTitle(title + " " + fileName);
	}
	
	/**
	 * Default constructor.
	 */
	public WOD() {
		logger.info("Starting up at " + new Date());
		setLayout(new BorderLayout());
		JMenuBar menuBar = new JMenuBar();
		JMenu FileMenu = new JMenu("File");
		JMenuItem FileLoadItem = new JMenuItem("Load");
		FileLoadItem.addActionListener(new FileListener());
		FileMenu.add(FileLoadItem);
		JMenuItem exitItem = new JMenuItem("Exit");
		exitItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		FileMenu.add(exitItem);
		menuBar.add(FileMenu);
		JMenu HelpMenu = new JMenu("Help");
		JMenuItem AboutItem = new JMenuItem("About");
		AboutItem.addActionListener(new AboutListener());
		HelpMenu.add(AboutItem);
		menuBar.add(HelpMenu);
		setJMenuBar(menuBar);
		add(tabbedPane, BorderLayout.CENTER);
		bottomPanel = new JPanel();
		stationField = new JTextField();
		stationField.setColumns(3);
		stationField.setHorizontalAlignment(JTextField.RIGHT);
		stationField.addActionListener(new StationFieldListener());
		bottomPanel.add(stationField);
		slider = new WODSlider();
		slider.setPaintTicks(true);
		bottomPanel.add(slider);
		JButton nextButton = new JButton("Next");
		nextButton.addActionListener(new NextButtonListener());
		JButton prevButton = new JButton("Previous");
		prevButton.addActionListener(new PrevButtonListener());
		bottomPanel.add(nextButton);
		bottomPanel.add(prevButton);
		JButton allDataButton = new JButton("Get Full Station");
		allDataButton.addActionListener(new GetAllDataListener());
		bottomPanel.add(allDataButton);
		add(bottomPanel, BorderLayout.SOUTH);
		getContentPane().add(messageLabel, BorderLayout.NORTH);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public void addTab(DisplayTab tab){
		tabbedPane.add(tab.getTitle(), tab);
	}

	public void setTabs(List<DisplayTab> tabs){
		for(DisplayTab tab : tabs){
			tabbedPane.add(tab.getTitle(), tab);
		}
	}
	
	/**
	 * Sets the slider etc. Will be called by controller when the controller
	 * loads a new station. 
	 */
	public void setLocators(int currentStation, int dbSize){
		int magnitude = (int) Math.ceil(Math.log10(dbSize));
		slider.setMajorTickSpacing((int) Math.pow(10, magnitude - 1));
		slider.setMinorTickSpacing((int) (Math.pow(10, magnitude - 1) / 5));
		slider.setSuppressDisplay(true);
		slider.setMinimum(1);
		slider.setSuppressDisplay(true);
		slider.setMaximum(dbSize);
		slider.setSuppressDisplay(true);
		slider.setValue(currentStation);
		stationField.setText("" + currentStation);
		if (stationField.getColumns() < stationField.getText().length()) {
			stationField.setColumns(stationField.getText().length());
			bottomPanel.validate();
		}
		slider.setSuppressDisplay(false);
	}
	
	/**
	 * General purpose method to show a text message in the message area.
	 */
	public void sendMessage(String message) {
		messageLabel.setText("<html><font color='blue'>" + message);
	}
	
	private class WODSlider extends JSlider{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private volatile boolean suppressDisplay = false;

		public void setSuppressDisplay(boolean suppressDisplay) {
			this.suppressDisplay = suppressDisplay;
		}
		
		public WODSlider(){
			addChangeListener(new ChangeListener() {
				public void stateChanged(ChangeEvent e) {
					JSlider source = (JSlider) e.getSource();
					if (!source.getValueIsAdjusting()) {
						int fps = (int) source.getValue();
						if(!suppressDisplay){
							controller.setCurrentStation(fps);
						}
						suppressDisplay = false;
					}
				}
			});
		}	
	}
}
