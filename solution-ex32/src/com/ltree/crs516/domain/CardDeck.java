package com.ltree.crs516.domain;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Observable;
import java.util.Stack;


public final class CardDeck extends Observable implements Iterable<PlayingCard>, Serializable  {



	private static final long serialVersionUID = 1L;
	
	private Stack<PlayingCard> cardStack = new Stack<>();

	public CardDeck(){
		Rank[] rankArray = Rank.values();
		Suite[] suiteArray = Suite.values();
		for (Suite suite : suiteArray) {
			for (Rank rank : rankArray) {
				cardStack.add(new PlayingCard(suite, rank));
			}
		}
	}
	
	/**
	 * Gets the ith card, 0<=i<52. The card is not removed from the deck.
	 * @param index
	 * @return The card at position index in the deck or null if the input is not in range.
	 */
	PlayingCard peek(int index){
		if(index<0 || index>cardStack.size()-1){
			return null;
		}
		PlayingCard card =  cardStack.get(cardStack.size()-1-index);
		return card;
	}

	public void shuffle(){
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
	
	public PlayingCard deal(){
		PlayingCard card;
		try {
			card = cardStack.pop();
		} catch (NoSuchElementException e) {
			System.out.println(getClass()+"ran out of cards");
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
	
	public int[] getRanksDealt(){
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
	
	@Override
	public Iterator<PlayingCard> iterator() {
		return cardStack.iterator();
	}
	
	public void removeAllCards(){
		cardStack.removeAllElements();
	}

	public void addCard(PlayingCard card) {
		cardStack.add(card);
	}

	public void setImages() {
		for (PlayingCard card : cardStack) {
			card.setImage();
		}

	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((cardStack == null) ? 0 : cardStack.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj){
			return true;
		}
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CardDeck other = (CardDeck) obj;
		if (cardStack == null) {
			if (other.cardStack != null)
				return false;
		} else if (!cardStack.equals(other.cardStack))
			return false;
		return true;
	}
	
}
