package com.robots.gui;

import java.awt.BorderLayout;

import javax.swing.JFrame;

import com.robots.Robot;

 @SuppressWarnings("serial")
public final class RobotGui extends JFrame {

	private RobotPanel panel;

	public RobotGui() {
		panel = new RobotPanel();
		add(panel, BorderLayout.CENTER);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(400, 400);
		setVisible(true);
	}

	public void addRobot(Robot r) {
		panel.addRobot(r);
	}
}
