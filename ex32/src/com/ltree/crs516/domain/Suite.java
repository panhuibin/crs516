package com.ltree.crs516.domain;

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
	 * Swaps a name for a Suite.
	 * @return
	 */
	public static Suite suiteFromName(String name) {
		for (Suite suite : values()) {
			if(suite.name.equals(name.trim())){
				return suite;
			}
		}
		throw new IllegalArgumentException("No suite called "+name);
	}
	
	/**
	 * Randomly selects a suite.
	 * @return
	 */
	public static Suite chooseSuite() {
	    return values()[random.nextInt(4)];
	} 

}
