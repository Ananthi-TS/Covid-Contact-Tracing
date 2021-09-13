package com.covid.main;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import org.junit.Test;
import org.junit.function.ThrowingRunnable;

import com.covid.main.ApplicationValidationException;
import com.covid.main.Government;
import com.covid.main.MobileDevice;

public class MobileRecordContactTest {

	@Test()
	public void test1() {
		 
		try {
			MobileDevice mobile=new MobileDevice("testfiles\\mobile\\constructor\\Test1.txt",new Government("database.properties"));
		
			Exception exception= assertThrows(ApplicationValidationException.class, new ThrowingRunnable() {
			  public void run() throws Throwable {
				  mobile.recordContact(null, 10, 10);
			  }
			 });
			
			String expectedMessage = "Individual value cannot be null";
		    String actualMessage = exception.getMessage();
		    assertEquals("Test 1: Null individual value",expectedMessage,actualMessage);
			}catch (ApplicationValidationException e) {
			}
	}
	
	@Test()
	public void test2() {
		 
		try {
			MobileDevice mobile=new MobileDevice("testfiles\\mobile\\constructor\\Test1.txt",new Government("database.properties"));
		
			Exception exception= assertThrows(ApplicationValidationException.class, new ThrowingRunnable() {
			  public void run() throws Throwable {
				  mobile.recordContact(" ", 10, 10);
			  }
			 });
			
			String expectedMessage = "Individual value cannot be empty";
		    String actualMessage = exception.getMessage();
		    assertEquals("Test 2: Empty individual value",expectedMessage,actualMessage);
			}catch (ApplicationValidationException e) {
			}
	}
	
	@Test()
	public void test3() {
		 
		try {
			MobileDevice mobile=new MobileDevice("testfiles\\mobile\\constructor\\Test1.txt",new Government("database.properties"));
		
			Exception exception= assertThrows(ApplicationValidationException.class, new ThrowingRunnable() {
			  public void run() throws Throwable {
				  mobile.recordContact("asdf", 652 , 10);
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
			MobileDevice mobile=new MobileDevice("testfiles\\mobile\\constructor\\Test1.txt",new Government("database.properties"));
		
			Exception exception= assertThrows(ApplicationValidationException.class, new ThrowingRunnable() {
			  public void run() throws Throwable {
				  mobile.recordContact("asdf", 10, 1500);
			  }
			 });
			
			String expectedMessage = "Invalid duration";
		    String actualMessage = exception.getMessage();
		    assertEquals("Test 4: Invalid date",expectedMessage,actualMessage);
			}catch (ApplicationValidationException e) {
			}
	}
	
	@Test()
	public void test5() {
		 
		try {
			MobileDevice mobile=new MobileDevice("testfiles\\mobile\\constructor\\Test1.txt",new Government("database.properties"));
		
			Exception exception= assertThrows(ApplicationValidationException.class, new ThrowingRunnable() {
			  public void run() throws Throwable {
				  mobile.recordContact("asdf", -10, 10);
			  }
			 });
			
			String expectedMessage = "Invalid date";
		    String actualMessage = exception.getMessage();
		    assertEquals("Test 5: Invalid date",expectedMessage,actualMessage);
			}catch (ApplicationValidationException e) {
			}
	}
	
	@Test()
	public void test6() {
		 
		try {
			MobileDevice mobile=new MobileDevice("testfiles\\mobile\\constructor\\Test1.txt",new Government("database.properties"));
		
			Exception exception= assertThrows(ApplicationValidationException.class, new ThrowingRunnable() {
			  public void run() throws Throwable {
				  mobile.recordContact("asd", 10, -10);
			  }
			 });
			
			String expectedMessage = "Invalid duration";
		    String actualMessage = exception.getMessage();
		    assertEquals("Test 6: Invalid duration",expectedMessage,actualMessage);
			}catch (ApplicationValidationException e) {
			}
	}
	

	@Test()
	public void test7() {
		 
		try {
			MobileDevice mobile=new MobileDevice("testfiles\\mobile\\constructor\\Test1.txt",new Government("database.properties"));
			mobile.recordContact("HASH1", 15, 10);
			mobile.recordContact("HASH2", 12, 12);
			mobile.recordContact("HASH3", 10, 15);
		    assertEquals("Test 7 : Recording three valid interactions",3,mobile.getContactListSize());
			}catch (ApplicationValidationException e) {
			}
	}
	
	
}
