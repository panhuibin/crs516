package com.robots;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import com.robots.parts.Part;

public abstract class BasicRobot implements Robot {

//TODO 1: Create a countdown latch with 8 parties. It will release the 
//robots when 8 of them have congregated at the line x=50.
	
	private static CountDownLatch cdl = null; //Edit this
	
	private static int meetingPoint = 50;
	
	public void move(final int distance, final Direction direction) {
		Executors.newSingleThreadExecutor().submit(new Runnable() {

			@Override
			public void run() {
				try {
					for (int i = 0; i < distance; i += 1) {

						synchronized (fuel) {
							synchronized (theForce) {
								theForce.reduce();
								fuel.reduce();
							}
						}

						if (getXY().x == meetingPoint) {//Robot is at the line

//TODO 2: Call countdown() on the countdown latch cdl. In our example the event is the robot reaching 
//the rendezvous point. Note that this does not make the thread stop.

							

//TODO 3: Call await() on the countdown latch. This will actually make the thread wait until the
//count is down to zero.


							
						}

						synchronized (BasicRobot.this) {
							switch (direction) {
							case EAST:
								x = (x + 1) % 200;
								break;
							case WEST:
								x = (200 + x - 1) % 200;
								break;
							case NORTH:
								y = (200 + y - 1) % 200;
								break;
							case SOUTH:
								y = (y + 1) % 200;
								break;
							}
						}
						Thread.sleep(50 / getSpeed());
					}
				} catch (Exception e) {
					e.printStackTrace();
				} finally {

					System.out.println("Ending Force is "
							+ BasicRobot.getTheForce().getForceStrength());
					System.out.println("Ending Fuel is "
							+ BasicRobot.getFuel().getAmount());
					System.out.println(getName() + "  Done!");
				}
			}
		});

	}
	
	
	
	public final static AtomicInteger speed = new AtomicInteger(10);
	private static final Fuel fuel = new Fuel();
	private static final Force theForce = new Force();
	private Color color;
	private String name;
	protected List<Part> parts = new ArrayList<Part>();
	boolean spin = false;

	// x and y range from 0 to 199
	private int x = (int) (Math.random() * 200);
	private int y = (int) (Math.random() * 200);

	public static Fuel getFuel() {
		return fuel;
	}

	public static Force getTheForce() {
		return theForce;
	}

	public static int getSpeed() {
		return speed.get();
	}

	public static void setSpeed(int speed) {
		BasicRobot.speed.set(speed);
	}

	/**
	 * Distance from another Robot
	 */
	public double dist(Robot r2) {
		Point p1 = getXY();
		Point p2 = r2.getXY();
		return Math.sqrt(Math.pow(p1.getX() - p2.getX(), 2)
				+ Math.pow(p1.getY() - p2.getY(), 2));
	}

	public Color getColor() {
		return color;
	}

	public String getName() {
		return name;
	}

	public List<Part> getParts() {
		return parts;
	}

	public synchronized Point getXY() {
		return new Point(x, y);
	}

	public synchronized void setXY(Point p) {
		x = (int) p.getX();
		y = (int) p.getY();
	}

	public synchronized void setColor(Color color) {
		this.color = color;
	}

	public synchronized void setName(String name) {
		this.name = name;
	}

	public static int getMeetingPoint() {
		return meetingPoint;
	}
}
