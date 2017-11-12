package com.ltree.crs516;

public class NumbertoWordsConverter {
	/**
	 * Given an integer in the range -20 to 20 will return a String with
	 * that number converted to words. For example, an input of 15 results in 
	 * an output of "fifteen". An input of -4 returns "minus four".
	 * If the integer is not in the range -20 to 20 it throws an 
	 * IllegalArgumentException.
	 * 
	 * @param num
	 *            , an int, a number to be converted to words.
	 * @return a String, the number as words.
	 */
	
//TODO 1: Complete the method convert() so that it meets the requirements 
//outlined in the above documentation. 
//Don't forget to throw the exception when the input is out of range. 
//Use NumbertoWordsConverterTest.java in the tests 
//folder to check that you are getting the expected results.

	
	public String convert(int num) {
		if(num<0){
			return "minus " + convert(num*-1);
		}else if(num>20){
			throw new IllegalArgumentException();
		}else{
			return words[num];
		}
	}

	String[] words = {
			"zero",
			"one",
			"two",
			"three",
			"four",
			"five",
			"six",
			"seven",
			"eight",
			"nine",
			"ten",
			"eleven",
			"twelve",
			"thirteen",
			"fourteen",
			"fifteen",
			"sixteen",
			"seventeen",
			"eighteen",
			"nineteen",
			"twenty"
	};
	



}
