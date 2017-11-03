package com.ltree.crs516.data;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;

import com.ltree.crs516.controller.TehiGame;
import com.ltree.crs516.domain.CardDeck;
import com.ltree.crs516.domain.TehiHand;

public class TehiFileBasedDAOTest {

	private TehiFileBasedDAO subject;
	private TehiGame game;
	private TehiHand hand;
	private CardDeck deck;
	private static Random random = new Random();

	
	@Before
	public void setUp() throws Exception {
		subject = new TehiFileBasedDAO();
		game = new TehiGame();
		hand = new TehiHand(new TehiGame());
		deck = new CardDeck();
		deck.shuffle();
		for(int i=0; i<5;i++){
			hand.add(deck.deal());
		}
	}

	@Test
	public void testSaveAndGetState() {
		for (TehiGame.StateName stateName : TehiGame.StateName.values()) {
			subject.saveState(stateName);
			try {
				assertEquals(stateName, subject.getState());
			} catch (IOException e) {
				e.printStackTrace();
				fail(e.getMessage());
			}
			
		}
		
	}

	@Test
	public void testSaveAndGetDeck() {
		subject.saveDeck(deck);
		try {
			assertEquals(deck, subject.getDeck());
		} catch (IOException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	@Test
	public void testSaveAndGetSystemHand() {
		subject.saveSystemHand(hand);
		try {
			assertEquals(hand, subject.getSystemHand(game));
		} catch (IOException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	@Test
	public void testSaveAndGetPlayerHand() {
		subject.savePlayerHand(hand);
		try {
			assertEquals(hand, subject.getPlayerHand(game));
		} catch (IOException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	@Test
	public void testSaveAndGetScores() {
		int playerScore = random.nextInt();
		int systemScore = random.nextInt();
		subject.saveScores(playerScore, systemScore);
		try {
			int[] scores = subject.getScores();
			assertEquals(playerScore, scores[0]);
			assertEquals(systemScore, scores[1]);
		} catch (IOException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

}
