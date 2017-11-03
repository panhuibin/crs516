package com.robots.parts;

/**
 * Wheel.
 */
public final class Wheel extends Part {
	
	private int diameter;

	public Wheel(int serialNumber, String description, int diameter) {
		super(serialNumber, description);
		this.diameter = diameter;
	}

	public int getDiameter() {
		return diameter;
	}

	public void setDiameter(int diameter) {
		this.diameter = diameter;
	}
	
}
