package com.robots;

import java.awt.Color;
import java.util.concurrent.atomic.AtomicInteger;

import com.robots.parts.Motor;
import com.robots.parts.Pulley;

/**
 * C3PO
 * 
 */
public final class C3PO extends BasicRobot {
	
	//Note that even though it is private the static keyword means it is 
	//shared by all instances of C3PO.
	private static AtomicInteger lastSerial = new AtomicInteger(0);

	private final int serial;

	public C3PO() {
		parts.add(new Motor(485575, "Lucinet silent motor - leg", 24));
		parts.add(new Motor(485576, "Lucinet silent motor - leg", 24));
		parts.add(new Motor(485577, "Lucinet silent motor - neck", 24));
		parts.add(new Motor(485578, "Lucinet silent motor - arm", 24));
		parts.add(new Motor(485579, "Lucinet silent motor - arm", 24));
		parts.add(new Pulley(485579, "Lucinet pulley system", 100));
		setColor(new Color(251, 232, 123));
		//Using "lastSerial++" would be unsafe since lastSerial is shared.
		serial = lastSerial.incrementAndGet();
		setName("C3PO " + serial);
	}

	public int getSerial() {
		return serial;
	}

	public void speak() {
		System.out.println("I am C3PO, human cyborg relations droid!");
	}

}
