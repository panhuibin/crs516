package com.ltree.crs516.domain;

import static org.junit.Assert.*;

import java.util.Random;

import org.junit.Before;
import org.junit.Test;

import com.ltree.crs516.controller.TehiGame;

public class TehiHandTest {

	private TehiHand subject;
	private TehiGame game;
	private static Random random = new Random();

	@Before
	public void setUp() throws Exception {
		subject = new TehiHand();
		game = new TehiGame();
	}

	@Test
	public void testTehiHandTehiGame() {
		subject = new TehiHand(game);
		assertTrue(subject.size() == 5);
		assertTrue(subject.getGame() == game);
	}

	@Test
	public void testGetDeck() {
		subject = new TehiHand(game);
		assertTrue(subject.getDeck()==game.getDeck());
	}

	@Test
	public void testGetGame() {
		subject = new TehiHand(game);
		assertTrue(subject.getGame()==game);	}

	@Test
	public void testGetScore() {
		for(int count=0; count<100;count++){
		int numberOfFaceCards = random.nextInt(6);
		int[] nonFaceCardNumbers = new int[5-numberOfFaceCards];
		int nonFaceCardTotalValue = 0;
		for(int i=0; i< nonFaceCardNumbers.length;i++){
			nonFaceCardNumbers[i] = random.nextInt(10)+1;
			nonFaceCardTotalValue+=nonFaceCardNumbers[i];
		}
		int expectedScore = 2*numberOfFaceCards*nonFaceCardTotalValue;
		for (int i : nonFaceCardNumbers) {
			subject.add(new PlayingCard(Suite.chooseSuite(), Rank.convertIntToRank(i)));
		}
		for (int i = 0; i< numberOfFaceCards; i++) {
			subject.add(new PlayingCard(Suite.chooseSuite(), Rank.convertIntToRank(random.nextInt(3)+11)));
		}
		assertTrue(subject.getScore()==expectedScore);
		subject.clear();
		}
	}

}
