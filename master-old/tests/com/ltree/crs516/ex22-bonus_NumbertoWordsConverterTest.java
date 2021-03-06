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

	@Parameters
	public static Collection<Object[]> parameters() {

//TODO 1: As your code passes each test uncomment the next parameter pair. Don't 
//forget to throw an IllegalArgumentException if the number if out of the 
//prescribed range. You will probably go through several strategies before you 
//settle for one.
		
$		Object[][] data = new Object[][] { 	
$			{1, "one"}
$//			, {0,"zero"}
$//			, {7,"seven"}
$//			, {11,"eleven"}
$//			,{12,"twelve"}
$//			, {-2, "minus two"}
$//			, {17, "seventeen"}

@		Object[][] data = new Object[][] { 	
@			{1, "one"}, {0,"zero"}, {7,"seven"}, {11,"eleven"},{12,"twelve"}
@			, {-2, "minus two"}, {17, "seventeen"},{20, "twenty"}
@			, {23, "twenty three"},{34, "thirty four"}
@			, {-77, "minus seventy seven"},{100, "one hundred"}
@			, {110, "one hundred ten"}, {131, "one hundred thirty one"}
@			,{222, "two hundred twenty two"}, {1000, "one thousand"}
		};
		return Arrays.asList(data);
	}

	private int input;
	private String expected;
	private Random random = new Random();

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
		while(Math.abs(testNumber)<=1000){
			testNumber = random.nextInt();
		}
		new NumbertoWordsConverter().convert(testNumber);
	}
}

$//------------- Bonus 1: Extend your code to pass these pairs -----------------
$
$//{20, "twenty"}//, {23, "twenty three"}//,{34, "thirty four"}
$//, {-77, "minus seventy seven"}
$
$
$//------------- Bonus 2: Extend your code to pass these pairs -----------------
$
$//{100, "one hundred"}//, {110, "one hundred ten"}
$//, {131, "one hundred thirty one"}//,{222, "two hundred twenty two"}
$//, {1000, "one thousand"}
$	

%@Parameters<br />public static Collection<Object[]> parameters() {<br />&#160;&#160;Object[][] data = new Object[][] { <br />&#160;&#160;&#160;&#160;{1, "one"}, {0,"zero"}, {7,"seven"}, {11,"eleven"},{12,"twelve"},<br />&#160;&#160;&#160;&#160;{-2, "minus two"}, {17, "seventeen"},{20, "twenty"},<br />&#160;&#160;&#160;&#160;{23, "twenty three"},{34, "thirty four"},<br />&#160;&#160;&#160;&#160;{-77, "minus seventy seven"},{100, "one hundred"},<br />&#160;&#160;&#160;&#160;{110, "one hundred ten"}, {131, "one hundred thirty one"},<br />&#160;&#160;&#160;&#160;{222, "two hundred twenty two"}, {1000, "one thousand"}<br />&#160;&#160;};<br />&#160;&#160;return Arrays.asList(data);<br />}<br /><br />