package com.robots.parts;

/**
 * Pulley
 */
public final class Pulley extends Part {
	
	private long maxLoad;

	public Pulley(int serialNumber, String description, long maxLoad) {
		super(serialNumber, description);
		this.maxLoad = maxLoad;
	}

	public long getMaxLoad() {
		return maxLoad;
	}

	public void setMaxLoad(long maxLoad) {
		this.maxLoad = maxLoad;
	}
	
}
