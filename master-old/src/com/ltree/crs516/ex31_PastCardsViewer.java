package com.ltree.crs516;

import java.util.Observable;

public class PastCardsViewer extends CardsViewer {

	/**
	 * Chart that displays cards that have been dealt.
	 */
	private static final long serialVersionUID = 1L;

@	/**
@	 * The Observable is an instance of CardDeck and 
@	 * the Object is an int[] with the ranks that 
@	 * have already been dealt from the deck.
@	 */
@	@Override
@	public void update(Observable o, Object arg) {
//TODO 1: Add a method with signature 
//public void update(Observable o, Object arg). In that method 
//call updateChart with the input argument the int[] of ranks dealt.
@		int[] ranksDealt = (int[]) arg;
@		updateChart(ranksDealt);
$
$
$
%TODO 1:<br/>public void update(Observable o, Object arg) {<br/>&#160;&#160;int[] ranksDealt = (int[]) arg;<br/>&#160;&#160;updateChart(ranksDealt);<br />}<br/><br/>		
@	}

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
