package com.ltree.crs516.data;

import java.io.IOException;

import com.ltree.crs516.controller.TehiGame;
import com.ltree.crs516.controller.TehiGame.StateName;
import com.ltree.crs516.domain.CardDeck;
import com.ltree.crs516.domain.TehiHand;

public interface TehiDAO {

//TODO 1: Add a method called saveDeck that accepts an argument of type CardDeck.
//The return type should be void.	

	

//TODO 2: Add a method called getDeck that takes no argument and 
//returns a CardDeck. It should throw IOException.	


	void saveState(StateName stateName);
	StateName getState() throws IOException;
	void saveSystemHand(TehiHand systemHand);
	TehiHand getSystemHand(TehiGame tehiGame) throws IOException;	
	void savePlayerHand(TehiHand playerHand);
	TehiHand getPlayerHand(TehiGame tehiGame) throws IOException;
	int[] getScores() throws IOException, IOException;
	void saveScores(int cumulativePlayerScore, int cumulativeSytemScore);
	
}
