package com.ltree.crs516.domain;

import java.util.Random;

public enum Rank {
	ACE("A", 1), TWO("2", 2), THREE("3", 3), FOUR("4", 4), FIVE("5", 5), SIX(
			"6", 6), SEVEN("7", 7), EIGHT("8", 8), NINE("9", 9), TEN("10", 10), JACK(
			"J", 11), QUEEN("Q", 12), KING("K", 13);

	public String getName() {
		return name;
	}

	public int getRankNumber() {
		return rankNumber;
	}

	private String name;
	private int rankNumber;
	private static Random random = new Random();

	private Rank(String name, int rankNumber) {
		this.name = name;
		this.rankNumber = rankNumber;
	}

	/**
	 * Converts an int to a Rank. The int has to be in the range
	 * 0 to 12 inclusive.
	 * 
	 * @param rankInt
	 * @return The corresponding Rank object.
	 */
	public static Rank convertIntToRank(int rankInt) {
		for (Rank rank : Rank.values()) {
			if (rank.rankNumber == rankInt) {
				return rank;
			}
		}
		return null;
	}

	/**
	 * Swaps a name for a Rank.
	 * @return
	 */
	public static Rank rankFromName(String name) {
		for (Rank rank : values()) {
			if(rank.name.equals(name.trim())){
				return rank;
			}
		}
		throw new IllegalArgumentException("No rank called "+name);
	}

	/**
	 * Randomly selects a suite.
	 * @return
	 */
	public static Rank chooseRank() {
	    return values()[random.nextInt(13)];
	} 
	
	public String toString() {
		return name;
	}
}
