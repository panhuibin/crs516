package com.ltree.crs516;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class PlayingCardTest {

	private PlayingCard subject;
	
	@Before
	public void setUp() throws Exception {
		subject = new PlayingCard(Suite.chooseSuite(), Rank.chooseRank());
	}

	@Test
	public void testIsFaceCard() {
		for(int i=0;i<10;i++){
			subject = new PlayingCard(Suite.chooseSuite(), Rank.values()[i]);
			assertTrue(!subject.isFaceCard());
		}
		for(int i=10;i<13;i++){
			subject = new PlayingCard(Suite.chooseSuite(), Rank.values()[i]);
			assertTrue(subject.isFaceCard());
		}
	}

}
