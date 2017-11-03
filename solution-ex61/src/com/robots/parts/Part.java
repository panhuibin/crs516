package com.robots.parts;

/**
 * Part.
 */
public abstract class Part {
	
	private int serialNumber;
	private String description;

	protected Part(int serialNumber, String description) {
		this.serialNumber = serialNumber;
		this.description = description;
	}

	public int getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(int serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
}
