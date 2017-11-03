package com.robots;

/**
 * Direction.
 */
public enum Direction {
	
    NORTH(0), SOUTH(180), EAST(90), WEST(270);

    private final int bearing;

    private Direction(int bearing) {
        this.bearing = bearing;
    }

    public int getBearing() {
        return bearing;
    }
    
}
