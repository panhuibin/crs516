package com.ltree.crs516;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class TehiHandTest {

	private TehiHand subject;
	
	@Before
	public void setup(){
		subject = new TehiHand();
	}
	
	@Test
	public void testGetScore() {
		subject.add( new PlayingCard(Suite.CLUB, Rank.ACE));
		subject.add( new PlayingCard(Suite.CLUB, Rank.TEN));
		subject.add( new PlayingCard(Suite.CLUB, Rank.NINE));
		subject.add( new PlayingCard(Suite.CLUB, Rank.KING));
		subject.add( new PlayingCard(Suite.CLUB, Rank.QUEEN));
		System.out.println(subject.getScore());
		assertTrue(subject.getScore()==80);
	}

}
