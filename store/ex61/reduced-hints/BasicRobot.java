package com.robots;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import com.robots.parts.Part;

public abstract class BasicRobot implements Robot {
	
	//All Robots share the same fuel source.
	private static final Fuel fuel = new Fuel();

	//All robots share the same 'force'.
	private static final Force theForce = new Force();

	private Color color;

	private String name;

	// x and y range from 0 to 199.
	private int x = (int) (Math.random() * 200);

	private int y = (int) (Math.random() * 200);
	
	protected List<Part> parts = new ArrayList<Part>();
	
	//Used later -- ignore for now.
	private static int meetingPoint = 0;

	//We shall discuss the importance of atomic references later.
	public final static AtomicInteger speed = new AtomicInteger(10);
	
	/**
	 * Distance from another Robot
	 */
	public double dist(Robot r2) {
		Point p1 = getXY();
		Point p2 = r2.getXY();
		return Math.sqrt(Math.pow(p1.getX() - p2.getX(), 2)
				+ Math.pow(p1.getY() - p2.getY(), 2));
	}

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


//TODO 1: Create a cached thread pool.	
	protected static ExecutorService threadPool = null; //Edit this	
	
	public void move(final int distance, final Direction direction) {
//TODO 2,3: The logic in the move() method moves the robot and will run in 
//its own thread. Write a statement of the form 
//threadPool.submit(new Runnable(){});
//with all the code starting from from //{{Marker 1 to //}}End Marker 1 in the 
//body of the run() method.



//TODO 4: Go back to the exercise manual for instructions to test what you have done.
		
// {{Marker 1
				
				try {
					for (int i = 0; i < distance; i += 1) {

//TODO 6 (Do TODO 5 further down first): Both Force and Fuel are shared fields. To make the 
//two operations atomic, both of the statements between //{{Marker 2 
//and  //}}End Marker 2 will have to be nested synchronized blocks. One will 
//synchronize on theForce and the other on fuel. Follow the instructions in the 
//manual to make Eclipse insert the synchronized blocks for you.						
						
						//{{Marker 2		
						
								theForce.reduce();
								fuel.reduce();

						//}}End Marker 2

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
				} catch (Exception e) {
					e.printStackTrace();
				} finally {

					System.out.println("Ending Force is "
							+ BasicRobot.getTheForce().getForceStrength());
					System.out.println("Ending Fuel is "
							+ BasicRobot.getFuel().getAmount());
					System.out.println(getName() + "  Done!");
				}// End of finally block.


// }}End Marker 1.

	}

//TODO 5: Note that the fields x and y would be shared if two threads were trying to move 
//the robot. In our application only one thread moves a Robot so we won't  
//synchronize their access. Go back to the manual to test what you have
//done before returning to TODO 6.

	public Point getXY() {
		return new Point(x, y);
	}
	
	public void setXY(Point p) {
		x = (int) p.getX();
		y = (int) p.getY();
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public void setName(String name) {
		this.name = name;
	}

	//Not used until later
	public static int getMeetingPoint() {
		return meetingPoint;
	}

}
