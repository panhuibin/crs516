package com.ltree.crs516;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ltree.crs516.NumbertoWordsConverter;

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
	
	
	private static final Logger logger = LoggerFactory.getLogger(NumbertoWordsConverter.class);

	
//TODO 1: Complete the method convert() so that it meets the requirements 
//outlined in the above documentation. 
//Don't forget to throw the exception when the input is out of range. 
//Use NumbertoWordsConverterTest.java in the tests 
//folder to check that you are getting the expected results.

//Note: This is a solution but not the only one. You probably have another 
//solution that is just as good or better!
	
	private String[] words = new String[] { "zero", "one", "two", "three",
				"four", "five", "six", "seven", "eight", "nine", "ten",
				"eleven", "twelve", "thirteen", "fourteen", "fifteen",
				"sixteen", "seventeen", "eighteen", "nineteen", "twenty"};

	public String convert(int num) {
		logger.info("Converting the number {}",num);
		if (num < 0) {
			return "minus " + convert(-1 * num);
		}
		if(Math.abs(num)>20){
			throw new IllegalArgumentException("Out of range!");
		}
		return words[num];

	}
}

