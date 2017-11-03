package com.ltree.crs516;

import java.util.Random;

enum Rank {
	ACE("A", 1), TWO("2", 2), THREE("3", 3), FOUR("4", 4), FIVE("5", 5), SIX(
			"6", 6), SEVEN("7", 7), EIGHT("8", 8), NINE("9", 9), TEN("10", 10), 
			JACK("J", 11), QUEEN("Q", 12), KING("K", 13);

	private String name;
	private int rankNumber;
	private static Random random = new Random();

	public String getName() {
		return name;
	}

	public int getRankNumber() {
		return rankNumber;
	}

	private Rank(String name, int rankNumber) {
		this.name = name;
		this.rankNumber = rankNumber;
	}

	/**
	 * Converts an int to a Rank.
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
	 * Selects a rank at random.
	 * 
	 * @return a randomly selected rank.
	 */
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
