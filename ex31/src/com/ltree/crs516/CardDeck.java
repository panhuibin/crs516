package com.ltree.crs516;


import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Observable;

//TODO 1: Note that this class extends Observable.
public final class CardDeck extends Observable {

	private LinkedList<PlayingCard> cardStack = new LinkedList<>();

	/**
	 * Initially the cards are ordered by suite and rank.
	 */
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
	 * Deals 1 card from the top of the deck
	 */
	PlayingCard deal(){
		PlayingCard card;
		try {
			card = cardStack.pop();
		} catch (NoSuchElementException e) {
			throw new IllegalStateException("Ran out of cards!");
		}

//TODO 1: Call setChanged() and notify the observers giving then an int[]
//containing the ranks of the cards that have been dealt. [The method getRanksDealt()
//gives you the ranks of the cards that have been dealt].		
		setChanged();
		notifyObservers(getRanksDealt());
		return card;
	}
	
	/**
	 * Gets the ith card, 0 <= i < 52. The
	 * card is not removed.
	 * @param index
	 * @return
	 */
	PlayingCard peek(int index){
		if(index<0 || index>cardStack.size()-1){
			return null;
		}
		return cardStack.get(index);
	}

	/**
	 * Shuffles the stack of cards.
	 */
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
	
	/**
	 * Gets the ranks of the cards left in the deck.
	 * @return an int[] containing the ranks of the remaining cards
	 */
	int[] getRanksLeft(){
		int[] result = new int[13];
		Arrays.fill(result, 0);
		for (PlayingCard card : cardStack) {
			int rankNUmber = card.getRank().getRankNumber()-1;
			result[rankNUmber]++;
		}
		return result;
	}
	
	/**
	 * Gets the ranks of the cards no longer in the deck.
	 * @return an int[] containing the ranks of the already dealt cards
	 */
	int[] getRanksDealt(){
		int[] result = new int[13];
		int[] dealt = getRanksLeft();
		for (int i=0; i<dealt.length;i++) {
			result[i]=4-dealt[i];
		}
		return result;
	}
	
	/**
	 * Accessor to the number of cards left in the deck.
	 * @return an int, the size of the deck.
	 */
	public int size(){
		return cardStack.size();
	}

	/**
	 * Checks if the deck is empty.
	 * @return true if the deck has no cards, false otherwise.
	 */
	public boolean isEmpty() {
		return (size()==0)?true:false;
	}
}
