package com.ltree.crs516;

import static org.junit.Assert.*;

import java.util.LinkedList;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

^//Reduced Hints.
 //This is actually the default but it protects the test if the default were to 
 //change in the future.

 //default runner.
 @RunWith(JUnit4.class)
public class LinkedListTest {

	private LinkedList<String> testSubject;
	
#//TODO 1: Write a public void method (you can choose the name but setup is a 
#//nice one) and annotate it with @Before. In that method instantiate your 
#//LinkedList<String> and store the reference in the field called testSubject.
^//TODO 1: Write a method that will run before each test. In that method 
^//instantiate the test subject and store it in a reference called testSubject.
	
@	@Before
@	public void setup(){
@		testSubject = new LinkedList<String>();
@	}
$
$
$
$
%TODO 1:<br/>@Before<br/>public void setup(){<br/>&#160;&#160;testSubject = new LinkedList<String>();<br/>}<br/><br/>

//TODO 2: Test that when you call the method testSubject.add("one") you 
//get the boolean true returned.

@	@Test
@	public void testAddOne(){
@		boolean expected = true;
@		boolean actual = testSubject.add("one");
@		assertTrue(actual == expected);
@	}
$
$
$
$
$
$
%TODO 2:<br/>@Test<br/>public void testAddOne(){<br/>&#160;&#160;boolean expected = true;<br/>&#160;&#160;boolean actual = testSubject.add("one");<br/>&#160;&#160;assertTrue(actual == expected);<br/>}<br/><br/>
//After doing TODO 2, save the document and go back to the manual to get 
//directions to run the test. You will see if what you have so far works. 
//Small steps are always better!

#//TODO 3(bonus): Test that when you call testSubject.add("one"), 
#//testSubject.add("two"), testSubject.add("three") and then call 
#//testSubject.getLast(), you get the third string that was added.
^//TODO 3(bonus): Test that when you add several strings to the testSubject
^//and then call its getLast() method, you get the last string that was added.

@	@Test
@	public void testAddThreeStringsAndCallGetLast(){
@		String expected = "three";
@		testSubject.add("one");
@		testSubject.add("two");
@		testSubject.add("three");
@		String actual = testSubject.getLast();
@		assertTrue(actual == expected);
@	}

%TODO 3:<br/>@Test<br/>public void testAddThreeStringsAndCallGetLast(){<br/>&#160;&#160;String expected = "three";<br/>&#160;&#160;testSubject.add("one");<br/>&#160;&#160;testSubject.add("two");<br/>&#160;&#160;testSubject.add("three");<br/>&#160;&#160;String actual = testSubject.getLast();<br/>&#160;&#160;assertTrue(actual == expected);<br/>}<br/><br/>

$
$
$
$
$
$
#//TODO 4(bonus): Test that when you call testSubject.add("one"), 
#//testSubject.add("two"), and then call testSubject.add(1, "three"), 
#//if you call testSubject.get(1) you get "three".
^//TODO 4(bonus): LinkedList has a method add(int index, E element) which adds
^//the specified item at the specified position in the list. Test that when 
^//you use that method to add an element and then call get(index) you get the 
^//element that you added at that position.

@	@Test
@	public void testAddWithTwoParameters(){
@	  String expected = "three";
@	  testSubject.add("one");
@	  testSubject.add("two");
@	  testSubject.add(1,"three");
@	  String actual = testSubject.get(1);
@	  assertEquals(expected, actual);
@	}

%TODO 4:<br/>@Test<br/>public void testAddWithTwoParameters(){<br/>&#160;&#160;String expected = "three";<br/>&#160;&#160;testSubject.add("one");<br/>&#160;&#160;testSubject.add("two");<br/>&#160;&#160;testSubject.add(1,"three");<br/>&#160;&#160;String actual = testSubject.get(1);<br/>&#160;&#160;assertEquals(expected, actual);<br/>}<br/><br/>
$
$
$
$
$
$
$

//TODO 5(bonus): Test that when you call testSubject.add(index, myString) 
//and index is a negative integer you get an IndexOutOfBoundsException.

@	@Test(expected=IndexOutOfBoundsException.class)
@	public void testAddWithNegativeIndex(){
@		testSubject.add(-1,"one");
@	}
$
$
$
$
%TODO 5:<br/>@Test(expected=IndexOutOfBoundsException.class)<br/>public void testAddWithNegativeIndex(){<br/>&#160;&#160;testSubject.add(-1,"one");<br/>}<br/><br/>

//TODO 6: Test that when you call testSubject.add(index, "myString") and index
//is an integer larger than testSubject.size() you get an IndexOutOfBoundsException.

@	@Test(expected=IndexOutOfBoundsException.class)
@	public void testAddWithLargerThanMaxIndex(){
@		testSubject.add("one");
@		testSubject.add("two");
@		testSubject.add(testSubject.size()+1,"three");
@	}
%TODO 6:<br/>@Test(expected=IndexOutOfBoundsException.class)<br/>public void testAddWithLargerThanMaxIndex(){<br/>&#160;&#160;testSubject.add("one");<br/>&#160;&#160;testSubject.add("two");<br/>&#160;&#160;testSubject.add(testSubject.size()+1,"three");<br/>}<br/><br/>
$
$
$
$
$
$
}
