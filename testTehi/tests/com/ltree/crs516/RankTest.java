package com.ltree.crs516;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

 @RunWith(Parameterized.class)
public class RankTest {

	private int rankNumber;
	private Rank expectedRank;

	public RankTest(int number, Rank expectedRank) {
		super();
		this.rankNumber = number;
		this.expectedRank = expectedRank;
	}

	@Parameters
	public static Collection<Object[]> parameters() {
		Object[][] data = new Object[][] { { 1, Rank.ACE }, { 2, Rank.TWO },
				{ 3, Rank.THREE }, { 4, Rank.FOUR }, { 5, Rank.FIVE },
				{ 6, Rank.SIX }, { 7, Rank.SEVEN }, { 8, Rank.EIGHT },
				{ 9, Rank.NINE }, { 10, Rank.TEN }, { 11, Rank.JACK },
				{ 12, Rank.QUEEN }, { 13, Rank.KING } };
		return Arrays.asList(data);
	}


	@Test
	public void testConvertIntToRank() {
		Rank rank = Rank.convertIntToRank(rankNumber);
		assertThat(rank, is(equalTo(expectedRank)));
	}

}
