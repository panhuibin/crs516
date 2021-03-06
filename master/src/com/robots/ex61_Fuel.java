package com.robots;

public final class Fuel {

	private long amount = 1_000_000;

//TODO 1: This method is accessed from a thread other than those from move().
//Synchronize it.
%	public synchronized long getAmount() {<br/><br/>
@	public synchronized long getAmount() {
$	public long getAmount() {
		return amount;
	}

	public  void reduce() {
		// By some quirk in the universe, reducing fuel boosts the force.
		long theAmount = amount;
		BasicRobot.getTheForce().boostForce();
		// Reducing the fuel takes some time ...
		try {
			int r = (int) (Math.random() * 30);
			Thread.sleep(r / BasicRobot.getSpeed());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		amount = theAmount - 100;
	}

	/**
	 * Increases the intensity of the force
	 */
	public  void boostFuel() {
		// Increase the energy in the universe.
		long theAmount = amount;
		try {
			int r = (int) (Math.random() * 1);
			Thread.sleep(r / BasicRobot.getSpeed());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		amount = theAmount + 100;
	}

}
