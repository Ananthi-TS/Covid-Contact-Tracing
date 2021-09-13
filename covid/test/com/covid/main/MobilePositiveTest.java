package com.covid.main;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import org.junit.Test;
import org.junit.function.ThrowingRunnable;

import com.covid.main.ApplicationValidationException;
import com.covid.main.Government;
import com.covid.main.MobileDevice;

public class MobilePositiveTest {

	
	@Test()
	public void test1() {
		 
		try {
			MobileDevice mobile=new MobileDevice("testfiles\\mobile\\constructor\\Test1.txt",new Government("database.properrties"));
		
			Exception exception= assertThrows(ApplicationValidationException.class, new ThrowingRunnable() {
			  public void run() throws Throwable {
				  mobile.positiveTest(null);
			  }
			 });
			
			String expectedMessage = "Test hash cannot be null";
		    String actualMessage = exception.getMessage();
		    assertEquals("Test 1: Null Test Hash",expectedMessage,actualMessage);
			}catch (ApplicationValidationException e) {
			}
	}
	
	@Test()
	public void test2() {
		 
		try {
			MobileDevice mobile=new MobileDevice("testfiles\\mobile\\constructor\\Test1.txt",new Government("database.properrties"));
		
			Exception exception= assertThrows(ApplicationValidationException.class, new ThrowingRunnable() {
			  public void run() throws Throwable {
				  mobile.positiveTest("");
			  }
			 });
			
			String expectedMessage = "Test hash cannot be empty";
		    String actualMessage = exception.getMessage();
		    assertEquals("Test 2: Empty Test Hash",expectedMessage,actualMessage);
			}catch (ApplicationValidationException e) {
			}
	}
	
	@Test()
	public void test3() {
		 
		try {
			MobileDevice mobile=new MobileDevice("testfiles\\mobile\\constructor\\Test1.txt",new Government("database.properrties"));
		
			Exception exception= assertThrows(ApplicationValidationException.class, new ThrowingRunnable() {
			  public void run() throws Throwable {
				  mobile.positiveTest("           ");
			  }
			 });
			
			String expectedMessage = "Test hash cannot be empty";
		    String actualMessage = exception.getMessage();
		    assertEquals("Test 3: White space - Test Hash",expectedMessage,actualMessage);
			}catch (ApplicationValidationException e) {
			}
	}
	
	@Test()
	public void test4() {
		 
		try {
			MobileDevice mobile=new MobileDevice("testfiles\\mobile\\constructor\\Test1.txt",new Government("database.properrties"));
			mobile.positiveTest("Testhash1");
			
		    assertEquals("Test 4: Valid Test hash","Testhash1",mobile.getTestHash());
			}catch (ApplicationValidationException e) {
			}
	}
	
}
