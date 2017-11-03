package com.ltree.crs516;

import java.util.Observable;

public class PastCardsViewer extends CardsViewer {

	/**
	 * Chart that displays cards that have been dealt.
	 */
	private static final long serialVersionUID = 1L;

//TODO 1: Add a method with signature 
//public void update(Observable o, Object arg). In that method 
//call updateChart with the input argument the int[] of ranks dealt.




	/**
	 * {@inheritDoc}
	 */
	protected void updateChart(int[] ranksDealt) {
		for (int j = 0; j < ranksDealt.length; j++) {
			int numberOfcardsDealt = ranksDealt[j];
			for (int i = 0; i < 4 - numberOfcardsDealt; i++) {
				tiles[i][j].setText("");
			}
			for (int i = 4 - numberOfcardsDealt; i < 4; i++) {
				tiles[i][j].setText(Rank.convertIntToRank(j + 1).getName());
			}			
		}
	}
}
