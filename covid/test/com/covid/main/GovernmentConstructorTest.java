package com.covid.main;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import org.junit.Test;
import org.junit.function.ThrowingRunnable;

import com.covid.main.ApplicationValidationException;
import com.covid.main.Government;

public class GovernmentConstructorTest {
	
	
	@Test()
	public void test1() {
		Government objGovernment;
		try {
			objGovernment = new Government("testfiles\\government\\constructor\\Test1.properties");
			assertEquals("Test 1: Valid property file","testfiles\\government\\constructor\\Test1.properties",objGovernment.getConfigurationFile());
		} catch (ApplicationValidationException e) {
			//e.printStackTrace();
		}
	}

	@Test()
	public void test2() {
		Government objGovernment;
		try {
			objGovernment = new Government("testfiles\\government\\constructor\\Test2.properties");
			assertEquals("Test 2: Valid property file with additional arguments","testfiles\\government\\constructor\\Test2.properties",objGovernment.getConfigurationFile());
		} catch (ApplicationValidationException e) {
			//e.printStackTrace();
		}
	}
	
	
	@Test()
	public void test3() {
		
		Exception exception= assertThrows(ApplicationValidationException.class, new ThrowingRunnable() {
			  public void run() throws Throwable {
				  new Government("testfiles\\government\\constructor\\Test2sd.properties");
			  }
			 });
			
			String expectedMessage = "Exception : Cannot find database.properties file";
		    String actualMessage = exception.getMessage();
		    assertEquals("Test 3: Invalid database property file path",expectedMessage,actualMessage);
		
	}
	
	@Test()
	public void test4() {
		
		Exception exception= assertThrows(ApplicationValidationException.class, new ThrowingRunnable() {
			  public void run() throws Throwable {
				  new Government("testfiles\\government\\constructor\\Test3.properties");
			  }
			 });
			
			String expectedMessage = "Exception : Missing parameters in the database property file";
		    String actualMessage = exception.getMessage();
		    assertEquals("Test 4: Empty value for database parameter",expectedMessage,actualMessage);
		
	}
	
	@Test()
	public void test5() {
		
		Exception exception= assertThrows(ApplicationValidationException.class, new ThrowingRunnable() {
			  public void run() throws Throwable {
				  new Government("testfiles\\government\\constructor\\Test4.properties");
			  }
			 });
			
			String expectedMessage = "Exception : Missing parameters in the database property file";
		    String actualMessage = exception.getMessage();
		    assertEquals("Test 5: Parameter missing in database.properties",expectedMessage,actualMessage);
		
	}
	
	@Test()
	public void test6() {
		
		Exception exception= assertThrows(ApplicationValidationException.class, new ThrowingRunnable() {
			  public void run() throws Throwable {
				  new Government("testfiles\\government\\constructor\\Test5.properties");
			  }
			 });
			
			String expectedMessage = "Exception occured while creating SQL connection";
		    String actualMessage = exception.getMessage();
		    assertEquals("Test 5: Incorrect parameter in database.properties file",expectedMessage,actualMessage);
		
	}
}

