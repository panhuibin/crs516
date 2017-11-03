package com.ltree.crs516.domain;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class RankTest {

	Rank[] ranks;
	String[] names;
	
	@Before
	public void setUp() throws Exception {
		ranks = new Rank[]{Rank.ACE, Rank.TWO, Rank.THREE, Rank.FOUR, Rank.FIVE, Rank.SIX, Rank.SEVEN, Rank.EIGHT, Rank.NINE, Rank.TEN, Rank.JACK, Rank.QUEEN, Rank.KING};
		names = new String[]{"A","2","3","4", "5","6","7","8","9","10","J","Q","K"};
	}

	@Test
	public void testGetName() {
		for (int i=0; i< ranks.length;i++) {
			assertTrue(ranks[i].getName() == names[i]);
		}	}

	@Test
	public void testGetRankNumber() {
		for (int i=0; i< ranks.length;i++) {
			assertTrue(ranks[i].getRankNumber()== i+1);
		}
	}

	@Test
	public void testConvertIntToRank() {
		for (int i=1; i<= ranks.length;i++) {
			assertTrue(Rank.convertIntToRank(i)== ranks[i-1]);
		}
	}

	@Test
	public void testRankFromName() {
		for (int i=0; i< names.length;i++) {
			assertTrue(Rank.rankFromName(names[i])== ranks[i]);
		}
	}

	//Will occasionally fail.
	@Test
	public void testChooseRank() {
		int[] tally = new int[13];
		for(int i=0; i<65000;i++){
			Rank r = (Rank.chooseRank());
			tally[r.getRankNumber()-1]++;
		}
		for(int i=0;i<tally.length;i++){
			assertTrue(Math.abs(tally[i]-5000)<250);
		}
	}
	
}
