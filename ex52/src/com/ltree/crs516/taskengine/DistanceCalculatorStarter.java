package com.ltree.crs516.taskengine;

import static com.ltree.crs516.data.DataConstants.DATA_DIR;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFrame;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ltree.crs516.util.WODThreadPools;

/**
 * Creates a control that allows the user to start and stop the 
 * distance calculator.
 * 
 * @author crs 516 development team.
 *
 */
 @SuppressWarnings("serial")
public final class DistanceCalculatorStarter extends JFrame {

	//Clicking the start button will create a Distance calculator and
	//run the action method in another thread. (Long jobs on the 
	//GUI thread would make the UI sticky).
	final class startButtonListener implements ActionListener {
		@Override
		public void actionPerformed(final ActionEvent e) {
			startButton.setEnabled(false);
			stopButton.setEnabled(true);
			WODThreadPools.getDaemonThreadService().submit(new Runnable() {

				@Override
				public void run() {
					DistanceCalculator calculator = new DistanceCalculator();
					//Use the naive action() method to process a large file.
					calculator.setFile(new File(DATA_DIR
							+ DistanceCalculator.dataFile[6]));
					calculator.action();
					logger.info("Maximum distance is "
							+ calculator.getMaximumDistance());
				}
			});
		}
	}

	private final class stopButtonListener implements ActionListener {
		@Override
		public void actionPerformed(final ActionEvent e) {
			System.exit(0);
		}
	}

	private final class WindowCloser extends WindowAdapter {

		@Override
		public void windowClosing(final WindowEvent e) {
			shutDown();
		}
	}

	public static void main(final String[] args) {
		new DistanceCalculatorStarter();
	}

	private Logger logger = LoggerFactory.getLogger(getClass());

	final private JButton startButton = new JButton("Start");

	final private JButton stopButton = new JButton("Stop");

	public DistanceCalculatorStarter() {
		// Create GUI for control buttons
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("Task Engine Control");
		setLayout(new GridLayout(1, 2, 5, 5));
		startButton.addActionListener(new startButtonListener());
		add(startButton);
		stopButton.addActionListener(new stopButtonListener());
		stopButton.setEnabled(false);
		addWindowListener(new WindowCloser());
		add(stopButton);
		setLocation(300, 100);
		setSize(250, 100);
		setVisible(true);
	}

	final public void shutDown() {
		System.exit(0);
	}

}
