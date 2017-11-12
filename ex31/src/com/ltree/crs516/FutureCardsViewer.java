package com.ltree.crs516;

import java.util.Observable;
import java.util.Observer;

public class FutureCardsViewer extends CardsViewer {
	
	/**
	 * Chart that displays cards that have not been dealt.
	 */ 
	private static final long serialVersionUID = 1L;

//TODO 1: Add a method with signature 
//public void update(Observable o, Object arg). In that method 
//cast the observable to CardDeck and obtain the ranks not yet dealt.
//Then call updateChart with the input argument the int[] of ranks not yet dealt.


	public void update(Observable o, Object arg){
		CardDeck cardDeck = (CardDeck) o;
		updateChart(cardDeck.getRanksLeft());
	}


	/**
	 * {@inheritDoc}
	 */
	protected void updateChart(int[] ranksNotDealt) {
		for (int j = 0; j < ranksNotDealt.length; j++) {
			int numberOfcardsDealt = ranksNotDealt[j];
			for (int i = 0; i < 4 - numberOfcardsDealt; i++) {
				tiles[i][j].setText("");
			}
			for (int i = 4 - numberOfcardsDealt; i < 4; i++) {
				tiles[i][j].setText(Rank.convertIntToRank(j + 1).getName());
			}			
		}
	}
}
