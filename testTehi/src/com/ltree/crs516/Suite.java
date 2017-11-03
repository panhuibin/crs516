package com.ltree.crs516;

import java.util.Random;

enum Suite {
	SPADE("spade"), CLUB("club"), DIAMOND("diamond"), HEART("heart");
	
	private String name;
	private static Random random = new Random();
	
	private Suite(String name){
		this.name = name;
	}
	
	public String toString(){
		return name;
	}
	
	/**
	 * Randomly selects a suite.
	 * @return
	 */
	public static Suite chooseSuite() {
	    return values()[random.nextInt(4)];
	}

}
