package com.ltree.crs516;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.Collection;
import java.util.Random;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

 @RunWith(Parameterized.class)
public class NumbertoWordsConverterTest {

private int input;
private String expected;
private Random random = new Random();
		
#	@Parameters
#	public static Collection<Object[]> parameters() {

^//TODO 1: Create a method with signature 
^// public static Collection<Object[]> parameters().
^//Annotate it with the appropriate annotation so that it will supply parameters
^//for the tests. The method should return an array whose elements are of
^//the form {number, number-in-words}. An example would be {7,"seven"}.
^//As the code you write passes tests you will add more elements to the array.
^
^
^
^
^
^
#//TODO 1: As your code passes each test uncomment the next parameter pair. Don't 
#//forget to throw an IllegalArgumentException if the number is out of the 
#//prescribed range. You will probably go through several strategies before you 
#//settle for one.
		
&		Object[][] data = new Object[][] { 	
&			{1, "one"}
&//			, {0,"zero"}
&//			, {7,"seven"}
&//			, {11,"eleven"}
&//			, {12,"twelve"}
&//			, {-2, "minus two"}
&//			, {17, "seventeen"}
&//			, {20, "twenty"}
&			};
@		Object[][] data = new Object[][] { 	
@			{1, "one"}, {0,"zero"}, {7,"seven"}, {11,"eleven"},{12,"twelve"},
@			{-2, "minus two"}, {17, "seventeen"},{20, "twenty"}
@		};
#		return Arrays.asList(data);
#	}
%TODO 1:<br />Object[][] data = new Object[][] {<br />&#160;&#160;{1, "one"}, {0,"zero"}, {7,"seven"}, {11,"eleven"},{12,"twelve"},<br />&#160;&#160;{-2, "minus two"}, {17, "seventeen"},{20, "twenty"}<br />};<br />return Arrays.asList(data);<br /><br />

	public NumbertoWordsConverterTest(int input, String expected) {
		this.input = input;
		this.expected = expected;
	}

	@Test
	public void testConvert() {
		assertThat(new NumbertoWordsConverter().convert(input)
													, is(equalTo(expected)));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testOutOfRange(){
		//Cook up an int that is out of range.
		int testNumber = random.nextInt();
		while(Math.abs(testNumber)<=20){
			testNumber = random.nextInt();
		}
		new NumbertoWordsConverter().convert(testNumber);
	}
}

$//------------- Bonus 1: Extend your code to pass pairs like -----------------
$
$//{23, "twenty three"},{34, "thirty four"}, {-77, "minus seventy seven"}
$
$
$//------------- Bonus 2: Extend your code to pass pairs like -----------------
$
$//{100, "one hundred"}, {110, "one hundred ten"}
$//, {131, "one hundred thirty one"}
$//,{222, "two hundred twenty two"}
$//, {1000, "one thousand"}
$	
	