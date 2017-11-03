package com.ltree.crs516.data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;

import com.ltree.crs516.controller.TehiGame;
import com.ltree.crs516.controller.TehiGame.StateName;
import com.ltree.crs516.domain.CardDeck;
import com.ltree.crs516.domain.TehiHand;

public class TehiFileBasedDAO implements TehiDAO {

	private final String SAVED_DECK = "savedDeck";
	private final String SAVED_STATE = "savedState";
	private final String SAVED_PLAYER_HAND = "savedPlayerHand";
	private final String SAVED_SYSTEM_HAND = "savedSystemHand";
	private final String SAVED_SCORES = "savedScores";

	@Override
	public StateName getState() throws IOException {
		try (BufferedReader reader = new BufferedReader(new FileReader(
				SAVED_STATE));) {
			String stateAsString = reader.readLine();
			return TehiGame.StateName.valueOf(stateAsString);
		}
	}
	
	@Override
	public void saveState(StateName stateName) {
		try (PrintWriter writer = new PrintWriter(new File(SAVED_STATE));) {
			writer.print(stateName.name());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void saveDeck(CardDeck deck) {
//TODO 1: Get an  ObjectOutputStream that writes to the file called SAVED_DECK.
//and write the deck to that file. CardDeck is serializable. If you don't 
//remember how to write to an ObjectOutputStream take a look at the other save methods below.






	}

	public CardDeck getDeck() throws IOException {
		CardDeck deck = null;
//TODO 2: Get an  ObjectInputStream chained to a FileInputStream that reads from the file SAVED_DECK. 
//Read the object stored in that file and cast it to CardDeck. 
//Call setImages() on the CardDeck to make the PlayingCards reload their images.
//Return a reference to the CardDeck.		







		return null;
	}
			
	private void saveHand(TehiHand playerHand, File savedFile) {
		try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(savedFile));){
			oos.writeObject(playerHand);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private TehiHand getHand(File savedFile, TehiGame game) throws IOException {
		TehiHand hand = null;
		try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(savedFile));) {
			hand = (TehiHand)ois.readObject();			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		hand.setImages();
		hand.setGame(game);
		return hand;
	}

	@Override
	public void saveSystemHand(TehiHand systemHand) {
		saveHand(systemHand, new File(SAVED_SYSTEM_HAND));
	}

	@Override
	public TehiHand getSystemHand(TehiGame game) throws IOException {
		return getHand(new File(SAVED_SYSTEM_HAND), game);
	}

	@Override
	public void savePlayerHand(TehiHand playerHand) {
		saveHand(playerHand, new File(SAVED_PLAYER_HAND));
	}

	@Override
	public TehiHand getPlayerHand(TehiGame game) throws IOException {
		return getHand(new File(SAVED_PLAYER_HAND), game);
	}

	@Override
	public void saveScores(int cumulativePlayerScore, int cumulativeSytemScore)  {
		try (PrintWriter writer = new PrintWriter(SAVED_SCORES);) {
				writer.println(cumulativePlayerScore + ":"
						+ cumulativeSytemScore);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
	}

	@Override
	public int[] getScores() throws IOException, IOException {
		try (BufferedReader reader = new BufferedReader(new FileReader(
				SAVED_SCORES));) {
			String[] scoresAsStrings = reader.readLine().split(":");
			return new int[]{Integer.parseInt(scoresAsStrings[0]), Integer.parseInt(scoresAsStrings[1])};
		}
	}
}
