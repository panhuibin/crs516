package com.ltree.crs516;

import java.util.Observable;

public class FutureCardsViewer extends CardsViewer {
	
	/**
	 * Chart that displays cards that have not been dealt.
	 */ 
	private static final long serialVersionUID = 1L;

@	/**
@	 * The Observable is an instance of CardDeck and 
@	 * the Object is an int[] with the ranks that 
@	 * have already been dealt from the deck.
@	 */
@	@Override
@	public void update(Observable observable, Object arg) {
//TODO 1: Add a method with signature 
//public void update(Observable o, Object arg). In that method 
//cast the observable to CardDeck and obtain the ranks not yet dealt.
//Then call updateChart with the input argument the int[] of ranks not yet dealt.
@		CardDeck deck = (CardDeck)observable;
@		int[] ranksNotDealt = deck.getRanksLeft();		
@		updateChart(ranksNotDealt);
$
$
$
$
%TODO 1:<br/>&#160;&#160;CardDeck deck = (CardDeck)observable;<br/>&#160;&#160;int[] ranksNotDealt = deck.getRanksLeft();<br/>&#160;&#160;updateChart(ranksNotDealt);<br/><br/>
@	}

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
