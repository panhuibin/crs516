package com.ltree.crs516.domain;

import java.io.Serializable;
import java.util.ArrayList;

import com.ltree.crs516.controller.TehiGame;

 @SuppressWarnings("serial")
public final class TehiHand extends ArrayList<PlayingCard> implements Serializable{
	
	private CardDeck deck;
	private transient TehiGame game;
	
	public TehiHand(){}

	public TehiHand(TehiGame game){
		this.game = game;
		CardDeck deck = game.getDeck();
		this.deck=deck;
		for(int i=0; i<5;i++){
			add(deck.deal());
		}
	}

	public CardDeck getDeck() {
		return deck;
	}

	public TehiGame getGame() {
		return game;
	}
	
	public int getScore(){
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
	
	public void setGame(TehiGame game) {
		this.game = game;
	}

	@Override
	public String toString() {
		return super.toString();
	}

	public void setImages() {
		for (PlayingCard card : this) {
			card.setImage();
		}
		
	}

}
