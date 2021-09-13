package com.covid.main;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import org.junit.Test;
import org.junit.function.ThrowingRunnable;

public class GovernmentRecordTest {

	
	@Test()
	public void test1() {
		 
		try {
			Government govObj=new Government("database.properties");		
			Exception exception= assertThrows(ApplicationValidationException.class, new ThrowingRunnable() {
			  public void run() throws Throwable {
				  govObj.recordTestResult(null, 10, false);
				  }
			 });
			
			String expectedMessage = "TestHash value cannot be null";
		    String actualMessage = exception.getMessage();
		    assertEquals("Test 1: TestHash value cannot be null",expectedMessage,actualMessage);
			}catch (ApplicationValidationException e) {
			}
	}
	
	@Test()
	public void test2() {
		 
		try {
			Government govObj=new Government("database.properties");
		
			Exception exception= assertThrows(ApplicationValidationException.class, new ThrowingRunnable() {
			  public void run() throws Throwable {
				  govObj.recordTestResult("", 10, false);
				  
			  }
			 });
			
			String expectedMessage = "TestHash value cannot be empty";
		    String actualMessage = exception.getMessage();
		    assertEquals("Test 2: TestHash value cannot be empty",expectedMessage,actualMessage);
			}catch (ApplicationValidationException e) {
				
			}
	}
	
	@Test()
	public void test3() {
		 
		try {
			Government govObj=new Government("database.properties");
		
			Exception exception= assertThrows(ApplicationValidationException.class, new ThrowingRunnable() {
			  public void run() throws Throwable {
				  govObj.recordTestResult("hash1", 366, false);
			  }
			 });
			
			String expectedMessage = "Invalid date";
		    String actualMessage = exception.getMessage();
		    assertEquals("Test 3: Invalid date",expectedMessage,actualMessage);
			}catch (ApplicationValidationException e) {
			}
	}
	
	@Test()
	public void test4() {
		 
		try {
			Government govObj=new Government("database.properties");
		
			Exception exception= assertThrows(ApplicationValidationException.class, new ThrowingRunnable() {
			  public void run() throws Throwable {
				  govObj.recordTestResult("hash1", -10, false);
			  }
			 });
			
			String expectedMessage = "Invalid date";
		    String actualMessage = exception.getMessage();
		    assertEquals("Test 4: Invalid date",expectedMessage,actualMessage);
			}catch (ApplicationValidationException e) {
			}
	}
	
	@Test()
	public void test5() {
		 
		try {
			Government govObj=new Government("database.properties");
		
			Exception exception= assertThrows(ApplicationValidationException.class, new ThrowingRunnable() {
			  public void run() throws Throwable {
				  govObj.recordTestResult("TEST##HASH##DEV1", 10, false);
			  }
			 });
			
			String expectedMessage = "Test hash already exist";
		    String actualMessage = exception.getMessage();
		    assertEquals("Test 5: Test hash already exist",expectedMessage,actualMessage);
			}catch (ApplicationValidationException e) {
			}
	}
	
	
	
}
