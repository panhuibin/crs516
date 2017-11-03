package com.robots.parts;

/**
 * Motor.
 */
public final class Motor extends Part {
	
	private int voltage;

	public Motor(int serialNumber, String description, int voltage) {
		super(serialNumber, description);
		this.voltage = voltage;
	}

	public int getVoltage() {
		return voltage;
	}

	public void setVoltage(int voltage) {
		this.voltage = voltage;
	}
	
}
