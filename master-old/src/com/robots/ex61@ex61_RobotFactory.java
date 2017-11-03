package com.robots;

/**
 * RobotFactory
 */
public final  class RobotFactory {
	
	public static Robot createRobot() {
		Robot robot = null;
		if (Math.random() > 0.5) {
			robot = new C3PO();
		} else {
			robot = new R2D2();
		}
		return robot;
	}
	
}
