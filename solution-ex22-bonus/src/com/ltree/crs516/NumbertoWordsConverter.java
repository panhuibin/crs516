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
//outlined in the above documentation. Don't forget to throw the exception when 
//the input is out of range. Use NumbertoWordsConverterTest.java in the tests 
//folder to check that you are getting the expected results.

//Note: This is a solution but not the only one. You probably have another 
//solution that is just as good or better!
	public String convert(int num) {
		
		if (num < 0) {
			return "minus " + convert(-1 * num);
		}
		if(num>1000){
			throw new IllegalArgumentException("Number is out of range");
		}
		if(num == 1000){
			return "one thousand";
		}
		if (num > 99) {
			int hundredsDigit = num / 100;
			int underOneHundredNumber = num - 100 * hundredsDigit;
			String underOneHundredWords = 
				(underOneHundredNumber == 0) ? 
									"" : " " + convert(underOneHundredNumber);
			return zeroToNineteenWord[hundredsDigit] +" hundred"
														+ underOneHundredWords;
		}
		if (num > 19) {
			int tensDigit = num / 10;
			int onesDigit = num - 10 * tensDigit;
			String onesString = (onesDigit == 0) ? "" : " "
					+ convert(num - 10 * tensDigit);

				return tensWord[tensDigit] + onesString;
		}
		return zeroToNineteenWord[num];
	}

	private static String[] zeroToNineteenWord = new String[] { "zero", "one", "two", "three",
		"four", "five", "six", "seven", "eight", "nine", "ten",
		"eleven", "twelve", "thirteen", "fourteen", "fifteen",
		"sixteen", "seventeen", "eighteen", "nineteen"};
	
	private static String[] tensWord = new String[] { "unused", "unused", "twenty", "thirty",
		"forty", "fifty", "sixty", "seventy", "eighty", "ninety"};

}


