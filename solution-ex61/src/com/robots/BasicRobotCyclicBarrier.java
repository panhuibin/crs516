package com.robots;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import com.robots.parts.Part;

public abstract class BasicRobotCyclicBarrier implements Robot {

//TODO 1: Create a cyclic barrier with 4 parties and for the barrier action use 
//an instance of the BarrierAction class defined below.
	
	private static CyclicBarrier cb = new CyclicBarrier(8); //Edit this
	
	private static int meetingPoint = 50;

	//Results from the various threads will be stored here as they complete their part of the process.
	//It is synchronized since it is accessed by multiple threads.
	private static List<String> results = Collections.synchronizedList(new ArrayList<String>());
	
	//The barrier action is a Runnable that runs when all parties have arrived. The threads are
	//not released until the barrier action has finished executing. Here you would 
	//process those results or update common state for the threads. Ours simply lists 
	//the results to the console and empties the list. 
	static class BarrierAction implements Runnable {
		@Override
		public void run() {
			StringBuilder sb = new StringBuilder();
			for (String result : results) {
				sb.append(result);
			}
			System.out.println("Processing results from " + sb);
			
//TODO 2: Call clear() on the list (results) to empty it. 
			results.clear();
		}
	}

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

						if (getXY().x == meetingPoint) {
							results.add(getName() + " ");
							
//TODO 3: Call await() on the cyclic barrier. The thread will wait until 
//barrier releases it.
							cb.wait();
							
						}

						synchronized (BasicRobotCyclicBarrier.this) {
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

	private Color color;

	private String name;

	protected List<Part> parts = new ArrayList<Part>();

	// x and y range from 0 to 199
	private int x = (int) (Math.random() * 200);

	private int y = (int) (Math.random() * 200);

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

	boolean spin = false;


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
