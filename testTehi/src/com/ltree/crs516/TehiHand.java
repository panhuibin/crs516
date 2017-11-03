package com.ltree.crs516;

import java.util.ArrayList;

 @SuppressWarnings("serial")
public final class TehiHand extends ArrayList<PlayingCard>{
	
	private CardDeck deck;
	
	CardDeck getDeck() {
		return deck;
	}

	public TehiHand(CardDeck deck) {
		this.deck=deck;
		for(int i=0; i<5;i++){
			add(deck.deal());
		}
	}
	
	public TehiHand(){}
	
	int getScore(){
		int eyes = 0;
		int nonFaceSum = 0;
		for (PlayingCard card : this) {
			if(card.isFaceCard()){
				eyes +=2;
			}else{
				nonFaceSum += card.getRank().getRankNumber();
			}			
		}
		return eyes*nonFaceSum;
	}

	@Override
	public String toString() {
		return super.toString();
	}

}
