package com.ltree.crs516;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class ShuffleTester {

	private CardDeck subject;

	@Before
	public void setup() {
		subject = new CardDeck();
	}

	/**
	 * Test that the shuffling is thorough.
	 */
	@Test
	public void test() {

		int[][] resultMatrix = new int[14][52];
		for (int i = 0; i < 52; i++) {
			resultMatrix[0][i] = i;
		}

		//If we shuffle the deck 130,000 times and if the shuffling is good
		//then if roughly 10,000 of those shuffles should give an Ace on top,
		//10,000 a 2, etc. In the same way roughly 10,000 of those shuffles
		//should have an Ace as the second card, 10,000 a 2 as the second card ...
		//and about 10,000 should give an Ace as the bottom card, 10,000 a 2
		//as the bottom card etc.
		for (int shuffleTimes = 0; shuffleTimes < 130_000; shuffleTimes++) {
			subject.shuffle();
			for (int i = 0; i < 52; i++) {
				PlayingCard card = subject.peek(i);
				int rankNumber = card.getRank().getRankNumber();
				resultMatrix[rankNumber][i]++;
			}
		}
		for (int i = 0; i < resultMatrix.length; i++) {
			int[] row = resultMatrix[i];
			System.out.print(Rank.convertIntToRank(i) + "\t");
			for (int j = 0; j < row.length; j++) {
				System.out.print(resultMatrix[i][j] + "\t");
				//The entries should be close to 10,000. Note that you will get the occasional failure. 
				if (i > 0) {
					assertTrue(resultMatrix[i][j] > 9500);
					assertTrue(resultMatrix[i][j] < 10500);
				}
			}
			System.out.println();
		}
	}

}
