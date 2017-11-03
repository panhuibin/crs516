package com.ltree.crs516.domain;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Observer;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;

public class CardDeckTest {
	
	private CardDeck subject;
	private Observer obs1;
	private Observer obs2;

	@Before
	public void setUp() throws Exception {
		subject = new CardDeck();
	}

	/**
	 * Tests the peek method by checking which cards are in which position before
	 * shuffling the deck.
	 */
	@Test
	public void testPeekIndex() {
		assertTrue(subject.peek(-2)==null);
		PlayingCard card = null;
		for(int i=0; i<52;i++){
			card = subject.peek(i);
			assertTrue(card.getSuite()==Suite.values()[(51-i)/13]);
			assertTrue(card.getRank()==Rank.values()[(51-i)%13]);
		}
		assertTrue(subject.peek(52)==null);
	}

	/**
	 * The test shuffles the deck 130000 times. If the shuffles are good we should get each rank appearing in
	 * the ith position approximately 10000 times.
	 */
	@Test
	public void testShuffle() {
		int[][] resultMatrix = new int[14][52];
		for (int i = 0; i < 52; i++) {
			resultMatrix[0][i] = i;
		}
		for (int shuffleTimes = 0; shuffleTimes < 130000; shuffleTimes++) {
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
				if (i > 0) {
					assertTrue(resultMatrix[i][j] > 9500);
					assertTrue(resultMatrix[i][j] < 10500);
				}
			}
			System.out.println();
		}
	}

	/**
	 * Checks that the deck deals cards from the top of the stack.
	 */
	@Test
	public void testDeal() {
		PlayingCard[] expectedHand = new PlayingCard[5];
		for (int i = 0; i < expectedHand.length; i++) {
			expectedHand[i] = subject.peek(i);
			System.out.println(expectedHand[i]);
		}
		PlayingCard[] dealtCards = subject.deal(5);
		for (int i = 0; i < dealtCards.length; i++) {
			System.out.println(dealtCards[i]);
			assertTrue(expectedHand[i].equals(dealtCards[i]));
		}
	}

	/**
	 * Checks that every time we deal we get the correct array when the getRanksLeft() method
	 * is called.
	 */
	@Test
	public void testGetRanksLeft() {
		subject.shuffle();
		int[] expected = new int[13];
		Arrays.fill(expected, 4);
		for (int count = 0; count < 52; count++) {
			PlayingCard card = subject.deal();
			expected[card.getRank().getRankNumber() - 1]--;
			int[] actual = subject.getRanksLeft();
			for (int i = 0; i < actual.length; i++) {
				assertTrue(expected[i] == actual[i]);
			}
		}
	}

	@Test
	public void testGetRanksDealt() {
		subject.shuffle();
		int[] expected = new int[13];
		Arrays.fill(expected, 0);
		for (int count = 0; count < 52; count++) {
			PlayingCard card = subject.deal();
			expected[card.getRank().getRankNumber() - 1]++;
			int[] actual = subject.getRanksDealt();
			for (int i = 0; i < actual.length; i++) {
				assertTrue(expected[i] == actual[i]);
			}
		}
	}

	@Test
	public void testSize() {
		subject.shuffle();
		int expected = 52;
		for (int count = 0; count < 52; count++) {
			subject.deal();
			expected--;
			assertTrue(expected == subject.size());
		}
	}

	@Test
	public void testIsEmpty() {
		subject.shuffle();
		boolean expected = false;
		for (int count = 0; count < 51; count++) {
			subject.deal();
			assertTrue(expected == subject.isEmpty());
		}
		expected = true;
		subject.deal();
		assertTrue(expected == subject.isEmpty());
		
	}
	
	//TODO: add this test
	@Test
	public void testObserver(){
		obs1 = mock(Observer.class);
		obs2 = mock(Observer.class);
		subject.addObserver(obs1);
		subject.addObserver(obs2);
		subject.deal();
		verify(obs1, times(1)).update(subject, subject.getRanksDealt());
		verify(obs2, times(1)).update(subject, subject.getRanksDealt());
	}

}
