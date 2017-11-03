package com.ltree.crs516.domain;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;

import javax.imageio.ImageIO;

/**
 * Represents a card.
 * @author crs516 development team.
 *
 */
public class PlayingCard implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private final Suite suite;
	private final Rank rank;
	private final boolean faceCard;
	private transient BufferedImage image;

	public PlayingCard(Suite suite, Rank rank) {
		super();
		this.suite = suite;
		this.rank = rank;
		if(rank.compareTo(Rank.TEN)>0){
			faceCard=true;
		}
		else{
			faceCard=false;
		}
		setImage();
	}
	
	public void setImage() {
		try {
	          image = (ImageIO.read(new File("images/200px-Playing_card_"+suite+"_"+rank+".svg.png")));
	       } catch (IOException ex) {
	    	   ex.printStackTrace();
	       }
	}

	public boolean isFaceCard() {
		return faceCard;
	}

	public Suite getSuite() {
		return suite;
	}

	public Rank getRank() {
		return rank;
	}

	@Override
	public String toString() {
		return getSuite()+":"+getRank();
	}

	public static PlayingCard fromString(String cardAsString){
		String[] tokens = cardAsString.split(":");
		return new PlayingCard(Suite.suiteFromName(tokens[0]), Rank.rankFromName(tokens[1]));
	}
	
	public BufferedImage getImage() {
		return image;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (faceCard ? 1231 : 1237);
		result = prime * result + ((rank == null) ? 0 : rank.hashCode());
		result = prime * result + ((suite == null) ? 0 : suite.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PlayingCard other = (PlayingCard) obj;
		if (faceCard != other.faceCard)
			return false;
		if (rank != other.rank)
			return false;
		if (suite != other.suite)
			return false;
		return true;
	}

}
