package com.ltree.crs516;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Observable;

public final class CardDeck extends Observable {

	private LinkedList<PlayingCard> cardStack = new LinkedList<>();

	CardDeck(){
		Rank[] rankArray = Rank.values();
		Suite[] suiteArray = Suite.values();
		for (Suite suite : suiteArray) {
			for (Rank rank : rankArray) {
				cardStack.add(new PlayingCard(suite, rank));
			}
		}
	}
	
	/**
	 * Gets the ith card, 0 <= i < 52. The card is not removed from the deck.
	 * @param index
	 * @return The card at position index in the deck or null if the input is not in range.
	 */
	PlayingCard peek(int index){
		if(index<0 || index>cardStack.size()-1){
			return null;
		}
		return cardStack.get(index);
	}

	void shuffle(){
		Collections.shuffle(cardStack);
	}		

	/**
	 * Deals n cards from the top of the deck
	 * @param n
	 */
	PlayingCard[] deal(int n){
		PlayingCard[] result = new PlayingCard[n];
		for(int i=0; i<n;i++){
			result[i]=deal();
		}
		return result;
	}
	
	PlayingCard deal(){
		PlayingCard card;
		try {
			card = cardStack.pop();
		} catch (NoSuchElementException e) {
			throw new IllegalStateException("Ran out of cards!");
		}
		setChanged();
		notifyObservers(getRanksDealt());
		return card;
	}
	
	int[] getRanksLeft(){
		int[] result = new int[13];
		Arrays.fill(result, 0);
		for (PlayingCard card : cardStack) {
			int rankNUmber = card.getRank().getRankNumber()-1;
			result[rankNUmber]++;
		}
		return result;
	}
	
	int[] getRanksDealt(){
		int[] result = new int[13];
		int[] dealt = getRanksLeft();
		for (int i=0; i<dealt.length;i++) {
			result[i]=4-dealt[i];
		}
		return result;
	}
	
	public int size(){
		return cardStack.size();
	}

	public boolean isEmpty() {
		return (size()==0)?true:false;
	}
}
