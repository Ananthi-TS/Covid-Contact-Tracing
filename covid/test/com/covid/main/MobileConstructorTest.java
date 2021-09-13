package com.covid.main;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import org.junit.Test;
import org.junit.function.ThrowingRunnable;

import com.covid.main.ApplicationValidationException;
import com.covid.main.Government;
import com.covid.main.MobileDevice;

public class MobileConstructorTest {

	@Test
	public void test1() {
		try {
			MobileDevice mobile=new MobileDevice("testfiles\\mobile\\constructor\\Test1.txt",new Government("C:\\Users\\anant\\OneDrive\\Desktop\\database.properties"));
			String hash="Ananthi"+"192.168.175.72";
			assertEquals("Test 1: Passing correct configuration file",mobile.getHashValuefin(),Integer.toString(hash.hashCode()));
		} catch (ApplicationValidationException e) {
		}
	}
	
	@Test()
	public void test2() {
		
			Exception exception= assertThrows(ApplicationValidationException.class, new ThrowingRunnable() {
			  public void run() throws Throwable {
				  new MobileDevice("testfiles\\mobile\\constructor\\T.txt",
							new Government("C:\\Users\\anant\\OneDrive\\Desktop\\database.properties"));
			  }
			 });
			
			String expectedMessage = "Cannot find the device configuration file";
		    String actualMessage = exception.getMessage();
		    assertEquals("Test 2: Invalid device property file",expectedMessage,actualMessage);

	}
	
	@Test
	public void test3() {
		
		Exception exception= assertThrows(ApplicationValidationException.class, new ThrowingRunnable() {
			  public void run() throws Throwable {
				  new MobileDevice(null,
							new Government("C:\\Users\\anant\\OneDrive\\Desktop\\database.properties"));
			  }
			 });
			
			String expectedMessage = "Configuration file cannot be null";
		    String actualMessage = exception.getMessage();
		    assertEquals("Test 3: Null device property file",expectedMessage,actualMessage);
		
		
	}
	
	@Test
	public void test4() {
		
		Exception exception= assertThrows(ApplicationValidationException.class, new ThrowingRunnable() {
			  public void run() throws Throwable {
				  new MobileDevice(" ",
							new Government("C:\\Users\\anant\\OneDrive\\Desktop\\database.properties"));
			  }
			 });
			
			String expectedMessage = "Configuration file cannot be empty";
		    String actualMessage = exception.getMessage();
		    assertEquals("Test 4: Empty device property file",expectedMessage,actualMessage);
		
		
	}
	
	@Test
	public void test5() {
		
		Exception exception= assertThrows(ApplicationValidationException.class, new ThrowingRunnable() {
			  public void run() throws Throwable {
				  new MobileDevice("testfiles\\\\mobile\\\\constructor\\\\Test1.txt",null);
			  }
			 });
			
			String expectedMessage = "Government object cannot be null";
		    String actualMessage = exception.getMessage();
		    assertEquals("Test 5: Empty Government object",expectedMessage,actualMessage);
		
		
	}
	
	@Test
	public void test6() {
		
		Exception exception= assertThrows(ApplicationValidationException.class, new ThrowingRunnable() {
			  public void run() throws Throwable {
				  new MobileDevice("testfiles\\\\mobile\\\\constructor\\\\Test2.txt",new Government("database.properties"));
			  }
			 });
		String expectedMessage = "Missing/empty parameters in configuration file";
		    String actualMessage = exception.getMessage();
		    assertEquals("Test 6: Empty Device name",expectedMessage,actualMessage);
		
		
	}
	
	@Test
	public void test7() {
		
		Exception exception= assertThrows(ApplicationValidationException.class, new ThrowingRunnable() {
			  public void run() throws Throwable {
				  new MobileDevice("testfiles\\\\mobile\\\\constructor\\\\Test3.txt",new Government("database.properties"));
			  }
			 });
		String expectedMessage = "Missing/empty parameters in configuration file";
		    String actualMessage = exception.getMessage();
		    assertEquals("Test 7: No device property",expectedMessage,actualMessage);
		
		
	}
	
	@Test
	public void test8() {
		
		Exception exception= assertThrows(ApplicationValidationException.class, new ThrowingRunnable() {
			  public void run() throws Throwable {
				  new MobileDevice("testfiles\\\\mobile\\\\constructor\\\\Test4.txt",new Government("database.properties"));
			  }
			 });
		String expectedMessage = "Missing/empty parameters in configuration file";
		    String actualMessage = exception.getMessage();
		    assertEquals("Test 8: Empty Network address",expectedMessage,actualMessage);
		
		
	}
	
	@Test
	public void test9() {
		
		Exception exception= assertThrows(ApplicationValidationException.class, new ThrowingRunnable() {
			  public void run() throws Throwable {
				  new MobileDevice("testfiles\\\\mobile\\\\constructor\\\\Test5.txt",new Government("database.properties"));
			  }
			 });
			
			String expectedMessage = "Missing/empty parameters in configuration file";
		    String actualMessage = exception.getMessage();
		    assertEquals("Test 8: No network address",expectedMessage,actualMessage);
		
		
	}
}
