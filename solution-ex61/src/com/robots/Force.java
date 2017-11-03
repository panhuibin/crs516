package com.robots;

public class Force {
	
	private long forceStrength = 1_000_000;

//TODO 1: Apart from threads coming from move() which already have a lock, the 
//method getForceStrength() has another client. Synchronize it.	
	public synchronized long getForceStrength() {
		return forceStrength;
	}

	public void reduce() {
		long theAmount = forceStrength;
		// By some quirk in the universe, reducing force boosts the fuel.
		BasicRobotCyclicBarrier.getFuel().boostFuel();
		try {
			int r = (int) (Math.random() * 1);
			Thread.sleep(r / BasicRobotCyclicBarrier.getSpeed());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		forceStrength = theAmount - 100;
	}

	/**
	 * Increases the intensity of the force
	 */
	public void boostForce() {
		long theAmount = forceStrength;
		// Here we increase the intensity of the force.
		try {
			int r = (int) (Math.random() * 3);
			Thread.sleep(r / BasicRobotCyclicBarrier.getSpeed());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		forceStrength = theAmount + 100;
	}

}
