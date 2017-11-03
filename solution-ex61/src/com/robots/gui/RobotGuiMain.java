package com.robots.gui;

import com.robots.*;

public final class RobotGuiMain {

	public static void main(String[] args) {
		RobotGui robotGui = new RobotGui();
		Robot[] robots = new Robot[8];
		for (int i = 0; i < robots.length; i++) {
			Robot robot = RobotFactory.createRobot();
			robots[i] = robot;
			robotGui.addRobot(robot);
		}
		System.out.println("Force is "
				+ BasicRobotCyclicBarrier.getTheForce().getForceStrength());
		System.out.println("Fuel is " + BasicRobotCyclicBarrier.getFuel().getAmount());
		//Move each of the robots 600 units east.
		for (Robot robot : robots) {
			robot.move(600, Direction.EAST);
		}
	}
	
}
