package com.ltree.crs516.controller;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import com.ltree.crs516.controller.TehiGame.GameState;
import com.ltree.crs516.controller.TehiGame.StateName;
import com.ltree.crs516.domain.CardDeck;
import com.ltree.crs516.domain.TehiHand;

public class TehiGameTest {

	private TehiGame subject;

	@Before
	public void setUp() throws Exception {
		subject = new TehiGame();
	}

	@Test
	public void testTehiGame() {
		assertTrue(subject.equals(subject.getPlayerHand().getGame()));
		assertTrue(subject.equals(subject.getSystemHand().getGame()));
	}

	@Test
	public void testSaveAndLoad() {
		for (StateName stateName : StateName.values()) {
			subject.changeState(stateName);
			CardDeck deck = subject.getDeck();
			GameState state = subject.getState();
			TehiHand playerHand = subject.getPlayerHand();
			TehiHand systemHand = subject.getSystemHand();
			int playerScore = subject.getCumulativePlayerScore();
			int systemScore = subject.getCumulativeSytemScore();
			subject.save();
			try {
				subject.load();
			} catch (IOException e) {
				e.printStackTrace();
				fail(e.getMessage());
			}
			assertTrue(subject.getDeck().equals(deck));
			assertTrue(subject.getState().equals(state));
			assertTrue(subject.getPlayerHand().equals(playerHand));
			assertTrue(subject.getSystemHand().equals(systemHand));
			assertTrue(subject.getCumulativePlayerScore() == playerScore);
			assertTrue(subject.getCumulativeSytemScore() == systemScore);
		}
	}

	@Test
	public void testGetDealButtonText() {
		subject.changeState(StateName.INIT);
		assertTrue(subject.getDealButtonText().contains("Deal"));
		subject.changeState(StateName.DEAL);
		assertTrue(subject.getDealButtonText().contains("Deal"));
		subject.changeState(StateName.STAND);
		assertTrue(subject.getDealButtonText().contains("Deal"));
		subject.changeState(StateName.SWAP);
		assertTrue(subject.getDealButtonText().contains("Deal"));
		subject.changeState(StateName.END);
		assertTrue(subject.getDealButtonText().contains("Game Over"));
		subject.getDeck().removeAllCards();
		subject.changeState(StateName.DEAL);
		assertTrue(subject.getDealButtonText().contains("Game Over"));
		subject.changeState(StateName.STAND);
		assertTrue(subject.getDealButtonText().contains("Game Over"));
		subject.changeState(StateName.SWAP);
		assertTrue(subject.getDealButtonText().contains("Game Over"));
		subject.changeState(StateName.END);
		assertTrue(subject.getDealButtonText().contains("Game Over"));
	}

	@Test
	public void testGetDeck() {
		assertTrue(subject.getDeck().size()==52);
	}

	@Test
	public void testGetGamePaneText() {
		subject.changeState(StateName.INIT);
		assertTrue(subject.getGamePaneText().contains("Welcome to Tehi!"));
		subject.changeState(StateName.DEAL);
		assertTrue(subject.getGamePaneText().contains(
				"Click on a card to swap "));
		subject.changeState(StateName.STAND);
		assertTrue(subject.getGamePaneText()
				.contains("Click the 'Deal' button"));
		subject.changeState(StateName.SWAP);
		assertTrue(subject.getGamePaneText()
				.contains("Click the 'Deal' button"));
		subject.changeState(StateName.END);
		assertTrue(subject.getGamePaneText().contains("won the game!"));
		subject.getDeck().removeAllCards();
		subject.changeState(StateName.DEAL);
		assertTrue(subject.getGamePaneText().contains("won the game"));
		subject.changeState(StateName.STAND);
		assertTrue(subject.getGamePaneText().contains("won the game"));
		subject.changeState(StateName.SWAP);
		assertTrue(subject.getGamePaneText().contains("won the game"));
		subject.changeState(StateName.END);
		assertTrue(subject.getGamePaneText().contains("won the game!"));
	}

	@Test
	public void testGetState() {
		subject.changeState(StateName.INIT);
		assertTrue(subject.getState() instanceof TehiGame.Initial);
		subject.changeState(StateName.DEAL);
		assertTrue(subject.getState() instanceof TehiGame.Deal);
		subject.changeState(StateName.END);
		assertTrue(subject.getState() instanceof TehiGame.EndGame);
		subject.changeState(StateName.STAND);
		assertTrue(subject.getState() instanceof TehiGame.StandOrSwap);
		subject.changeState(StateName.SWAP);
		assertTrue(subject.getState() instanceof TehiGame.StandOrSwap);
	}

	@Test
	public void testIsDealButtonEnabled() {
		subject.changeState(StateName.INIT);
		assertTrue(!subject.isDealButtonEnabled());
		subject.changeState(StateName.DEAL);
		assertTrue(!subject.isDealButtonEnabled());
		subject.changeState(StateName.STAND);
		assertTrue(subject.isDealButtonEnabled());
		subject.changeState(StateName.SWAP);
		assertTrue(subject.isDealButtonEnabled());
		subject.changeState(StateName.END);
		assertTrue(!subject.isDealButtonEnabled());
		subject.getDeck().removeAllCards();
		subject.changeState(StateName.DEAL);
		assertTrue(!subject.isDealButtonEnabled());
		subject.changeState(StateName.STAND);
		assertTrue(!subject.isDealButtonEnabled());
		subject.changeState(StateName.SWAP);
		assertTrue(!subject.isDealButtonEnabled());
		subject.changeState(StateName.END);
		assertTrue(!subject.isDealButtonEnabled());
	}

	@Test
	public void testIsPlayerPanelActive() {
		subject.changeState(StateName.INIT);
		assertTrue(subject.isPlayerPanelActive());
		subject.changeState(StateName.DEAL);
		assertTrue(subject.isPlayerPanelActive());
		subject.changeState(StateName.STAND);
		assertTrue(!subject.isPlayerPanelActive());
		subject.changeState(StateName.SWAP);
		assertTrue(!subject.isPlayerPanelActive());
		subject.changeState(StateName.END);
		assertTrue(!subject.isPlayerPanelActive());
	}

	@Test
	public void testIsStandButtonEnabled() {
		subject.changeState(StateName.INIT);
		assertTrue(subject.isStandButtonEnabled());
		subject.changeState(StateName.DEAL);
		assertTrue(subject.isStandButtonEnabled());
		subject.changeState(StateName.STAND);
		assertTrue(!subject.isStandButtonEnabled());
		subject.changeState(StateName.SWAP);
		assertTrue(!subject.isStandButtonEnabled());
		subject.changeState(StateName.END);
		assertTrue(!subject.isStandButtonEnabled());
	}

	@Test
	public void testIsSystemPanelActive() {
		subject.changeState(StateName.INIT);
		assertTrue(!subject.isSystemPanelActive());
		subject.changeState(StateName.DEAL);
		assertTrue(!subject.isSystemPanelActive());
		subject.changeState(StateName.STAND);
		assertTrue(!subject.isSystemPanelActive());
		subject.changeState(StateName.SWAP);
		assertTrue(!subject.isSystemPanelActive());
		subject.changeState(StateName.END);
		assertTrue(!subject.isSystemPanelActive());
	}

	@Test
	public void testIsSystemPanelVisible() {
		subject.changeState(StateName.INIT);
		assertTrue(!subject.isSystemPanelVisible());
		subject.changeState(StateName.DEAL);
		assertTrue(!subject.isSystemPanelVisible());
		subject.changeState(StateName.STAND);
		assertTrue(subject.isSystemPanelVisible());
		subject.changeState(StateName.SWAP);
		assertTrue(subject.isSystemPanelVisible());
		subject.changeState(StateName.END);
		assertTrue(subject.isSystemPanelVisible());
		subject.getDeck().removeAllCards();
		subject.changeState(StateName.DEAL);
		assertTrue(subject.isSystemPanelVisible());
		subject.changeState(StateName.STAND);
		assertTrue(subject.isSystemPanelVisible());
		subject.changeState(StateName.SWAP);
		assertTrue(subject.isSystemPanelVisible());
		subject.changeState(StateName.END);
		assertTrue(subject.isSystemPanelVisible());
	}

}
