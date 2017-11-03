package com.ltree.crs516;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

 @SuppressWarnings("serial")
public class NumberToWordsRunner extends JFrame {

	private static Logger logger = LoggerFactory.getLogger("NumbertoWordsConverter");
	private NumbertoWordsConverter converter = new NumbertoWordsConverter();
	private JTextField entryField = new JTextField();
	private JButton goButton = new JButton("Convert");
	private JLabel resultLabel = new JLabel("Enter number in above field");

	public static void main(String[] args) {
		new NumberToWordsRunner();
	}

	public NumberToWordsRunner() {
		setLayout(new BorderLayout());
		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new GridLayout(2, 1, 5, 5));
		centerPanel.add(entryField);
		centerPanel.add(resultLabel);
		add(centerPanel, BorderLayout.CENTER);
		JPanel eastPanel = new JPanel();
		eastPanel.setLayout(new GridLayout(2, 1, 5, 5));
		eastPanel.add(goButton);
		goButton.addActionListener(new GoButtonActionListener());
		add(eastPanel, BorderLayout.EAST);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(300, 100);
		setLocation(500,300);
		setVisible(true);
	}
	
	private class GoButtonActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			String numberAsString = entryField.getText();
			if (numberAsString != null) {
				try {
					int num = Integer.parseInt(numberAsString);
					resultLabel.setText(converter.convert(num));
				} catch (IllegalArgumentException e) {
					logger.error(e.getMessage(), e);
					resultLabel.setText(e.getMessage());
				}
			}
		}

	}

}
