package com.robots;

import java.awt.Color;
import java.util.concurrent.atomic.AtomicInteger;

import com.robots.parts.Motor;

import com.robots.parts.Pulley;
import com.robots.parts.Wheel;


/**
 * R2D2
 * 
 */
public final class R2D2 extends BasicRobotCyclicBarrier {

	//Note that even though it is private the static keyword means it is 
	//shared by all instances of R2D2.
	private static AtomicInteger lastSerial = new AtomicInteger(0);
    private final int serial;

	public R2D2() {
        parts.add(new Motor(123456, "Chubb high torque motor", 120));
        parts.add(new Pulley(334563, "Low resistance pully", 100));
        parts.add(new Wheel(884457, "Rubber skinned alloy wheel", 200));
        parts.add(new Wheel(884458, "Rubber skinned alloy wheel", 200));
        parts.add(new Wheel(884459, "Rubber skinned alloy wheel", 200));
        setColor(new Color(98, 98, 249));
		//Using lastSerial++ would be unsage since lastSerial is shared.
        serial = lastSerial.incrementAndGet();
		setName("R2D2 "+serial);
    }

	public int getSerial() {
		return serial;
	}

    public void beep() {
        System.out.println("bi bi bib be bi bi be bi");
    }
    
}
