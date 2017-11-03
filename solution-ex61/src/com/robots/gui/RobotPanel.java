package com.robots.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.List;
import java.util.Vector;

import javax.swing.JPanel;

import com.robots.BasicRobotCyclicBarrier;
import com.robots.Robot;

 @SuppressWarnings("serial")
public final class RobotPanel extends JPanel {

	/*
	 * Need to synchronize access to the Vector as we add robots and the paint
	 * method iterates over it.
	 */
	List<Robot> robotList = new Vector<>();

	void addRobot(Robot r) {
		synchronized (robotList) {
			robotList.add(r);
		}
	}

	public void paintComponent(Graphics g1) {
		super.paintComponent(g1);
		int maxX = this.getWidth();
		int maxY = this.getHeight();
		int d = 20; // Diameter of robot.
		int i, j;
		Graphics2D g = (Graphics2D) g1;
		// scaling factor for size of panel.
		double xsc = 1.0 * maxX / 200, ysc = 1.0 * maxY / 200;
		// Draw the robots.
		synchronized (robotList) {
			for (Robot robot : robotList) {
				Point p = robot.getXY();
				i = (int) (xsc * (p.getX()) - d / 2);
				j = (int) (ysc * (p.getY()) - d / 2);
				g.setColor(robot.getColor());
				g.fillOval(i, j, d, d);
				g.drawString(robot.getName(), i + d + 5, j);
			}
			g.setColor(Color.black);
			g.setColor(Color.black);
			int meetingPoint = BasicRobotCyclicBarrier.getMeetingPoint();
			if (meetingPoint > 0 && meetingPoint < 200) {
				i = (int) (xsc * meetingPoint);
				g.drawLine(i, 0, i, maxY);
				g.setFont(new Font(Font.MONOSPACED, g.getFont().getStyle(), g.getFont().getSize()));
				g.drawString("x="+meetingPoint, i+d, maxY-d);
			}
		}
	}

	public RobotPanel() {
		super();
		setDoubleBuffered(true);
		setBackground(Color.WHITE);
		// This thread updates the graphics.
		new Thread(new Runnable() {
			public void run() {
				while (true) {
							repaint();
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						System.out.println(e.getMessage());
					}
				}
			}
		}).start();
	}

}
