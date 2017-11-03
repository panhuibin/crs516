package com.ltree.crs516;

import static org.junit.Assert.*;

import java.util.LinkedList;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

 //This is actually the default but it protects the test if the default were to 
 //change in the future.

 //default runner.
 @RunWith(JUnit4.class)
public class LinkedListTest {

	private LinkedList<String> testSubject;
	
//TODO 1: Write a public void method (you can choose the name but setup is a 
//nice one) and annotate it with @Before. In that method instantiate your 
//LinkedList<String> and store the reference in the field called testSubject.
	





//TODO 2: Test that when you call the method testSubject.add("one") you 
//get the boolean true returned.







//After doing TODO 2, save the document and go back to the manual to get 
//directions to run the test. You will see if what you have so far works. 
//Small steps are always better!

//TODO 3(bonus): Test that when you call testSubject.add("one"), 
//testSubject.add("two"), testSubject.add("three") and then call 
//testSubject.getLast(), you get the third string that was added.









//TODO 4(bonus): Test that when you call testSubject.add("one"), 
//testSubject.add("two"), and then call testSubject.add(1, "three"), 
//if you call testSubject.get(1) you get "three".










//TODO 5(bonus): Test that when you call testSubject.add(index, myString) 
//and index is a negative integer you get an IndexOutOfBoundsException.






//TODO 6: Test that when you call testSubject.add(index, "myString") and index
//is an integer larger than testSubject.size() you get an IndexOutOfBoundsException.







}
