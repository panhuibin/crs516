package com.robots;

import java.awt.Color;
import java.awt.Point;
import java.util.List;

import com.robots.parts.Part;

/**
 * Robot
 */
public interface Robot {
	
	void move(int distance, Direction direction);

	List<Part> getParts();

	String getName();

	Point getXY();

	void setXY(Point p);

	double dist(Robot r2);

	Color getColor();

	int getSerial();

}
