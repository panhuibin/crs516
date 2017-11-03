package com.ltree.crs516;

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

	@Test
	public void testObservable(){

//TODO 1: Create two mock objects, obs1 and obs2 that implement Observer.
		obs1 = mock(Observer.class);
		obs2 = mock(Observer.class);
//TODO 2: Register the two mocks as observers of subject using the addObserver()
//method. Note that since the method is not yet present you will get an error message.
		subject.addObserver(obs1);
		subject.addObserver(obs2);
//TODO 3: Call the deal() method of subject		
		subject.deal();
		int[] ranksDealt = subject.getRanksDealt();
//TODO 4: Verify that both obs1 and obs2 got a call to their respective update methods and that 
//the arguments were subject and ranksDealt		
		verify(obs1, times(1)).update(subject, ranksDealt);
		verify(obs2, times(1)).update(subject, ranksDealt);
	}
	
	//The rest of the tests are not about the Observer pattern so you can just take a quick look at them.
	@Test
	public void testGetCard() {
		assertTrue(subject.peek(-2)==null);
		PlayingCard card = subject.peek(0);
		assertTrue(card.getSuite()==Suite.SPADE);
		assertTrue(card.getRank()==Rank.ACE);
		//getCard should not remove the card off the deck
		card = subject.peek(0);
		assertTrue(card.getSuite()==Suite.SPADE);
		assertTrue(card.getRank()==Rank.ACE);
		card = subject.peek(10);
		assertTrue(card.getSuite()==Suite.SPADE);
		assertTrue(card.getRank()==Rank.JACK);
		card = subject.peek(20);
		assertTrue(card.getSuite()==Suite.CLUB);
		assertTrue(card.getRank()==Rank.EIGHT);
		card = subject.peek(30);
		assertTrue(card.getSuite()==Suite.DIAMOND);
		assertTrue(card.getRank()==Rank.FIVE);
		card = subject.peek(40);
		assertTrue(card.getSuite()==Suite.HEART);
		assertTrue(card.getRank()==Rank.TWO);
		card = subject.peek(50);
		assertTrue(card.getSuite()==Suite.HEART);
		assertTrue(card.getRank()==Rank.QUEEN);
		card = subject.peek(51);
		assertTrue(card.getSuite()==Suite.HEART);
		assertTrue(card.getRank()==Rank.KING);
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
		}
		PlayingCard[] dealtCards = subject.deal(5);
		for (int i = 0; i < dealtCards.length; i++) {
			assertTrue(expectedHand[i] == dealtCards[i]);
		}
	}

	/**
	 * Tests the peek method by checking which cards are in which position before
	 * shuffling the deck.
	 */
	@Test
	public void testPeekIndex() {
		for (int k = 0; k < 4; k++) {
			for (int i = 0; i < 13; i++) {
				PlayingCard card = subject.peek(13 * k + i);
				assertTrue(card.getRank().getRankNumber() == (14 * k + i + 1) % 14);
			}
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
	


}
