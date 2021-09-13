package com.covid.main;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import org.junit.Test;
import org.junit.function.ThrowingRunnable;

import com.covid.main.ApplicationValidationException;
import com.covid.main.Government;
import com.covid.main.MobileDevice;

public class FindGatheringsTest {
//This test class is used to test the find gatherings method
	
	@Test()
	public void test1() {
		//Testing find gatherings with valid conditions
		try {
			Government objGovernment=new Government("testfiles\\database\\findGatherings1.properties");
			//objGovernment.recordTestResult("TEST##HASH##DEV1", 341, true);
			assertEquals("Test 1: Passing devices with valid conditions",1,objGovernment.findGatherings(10, 2, 2, 0.1f));
			
		} catch (ApplicationValidationException e) {
			System.out.println("Test exception");
			e.printStackTrace();
		}
	}
	
	@Test()
	public void test2() {
		//Testing find gatherings by Passing devices, some of which may havent synchronized to government properly
		try {
			Government objGovernment=new Government("testfiles\\database\\findGatherings2.properties");
			//objGovernment.recordTestResult("TEST##HASH##DEV1", 341, true);
			
			assertEquals("Test 2: Passing devices, some of which may havent synchronized to government properly",2,objGovernment.findGatherings(10, 2, 2, 0.1f));
			
		} catch (ApplicationValidationException e) {
			System.out.println("Test exception");
			e.printStackTrace();
		}
	}
	
	@Test()
	public void test3() {
		//Testing find gatherings by Passing devices, in which one device has entry of other and the other device has no record of this device
		try {
			Government objGovernment=new Government("testfiles\\database\\findGatherings2.properties");
			//objGovernment.recordTestResult("TEST##HASH##DEV1", 341, true);
			
			assertEquals("Test 3: Passing devices, in which one device has entry of other and the other device has no record of this device",2,objGovernment.findGatherings(10, 2, 2, 0.1f));
			
		} catch (ApplicationValidationException e) {
			System.out.println("Test exception");
			e.printStackTrace();
		}
	}
	
	@Test()
	public void test4() {
		
		Exception exception= assertThrows(ApplicationValidationException.class, new ThrowingRunnable() {
			  public void run() throws Throwable {
				  Government objGovernment=new Government("testfiles\\database\\findGatherings1.properties");
				  objGovernment.findGatherings(-10, 2, 2, 0.1f);
			  }
			 });
			
			String expectedMessage = "Invalid date";
		    String actualMessage = exception.getMessage();
		    assertEquals("Test 4: Invalid date",expectedMessage,actualMessage);
		
	}
	
	@Test()
	public void test5() {
		
		Exception exception= assertThrows(ApplicationValidationException.class, new ThrowingRunnable() {
			  public void run() throws Throwable {
				  Government objGovernment=new Government("testfiles\\database\\findGatherings1.properties");
				  objGovernment.findGatherings(365, 2, 2, 0.1f);
			  }
			 });
			
			String expectedMessage = "Invalid date";
		    String actualMessage = exception.getMessage();
		    assertEquals("Test 5: Invalid date",expectedMessage,actualMessage);
		
	}
	
	@Test()
	public void test6() {
		
		Exception exception= assertThrows(ApplicationValidationException.class, new ThrowingRunnable() {
			  public void run() throws Throwable {
				  Government objGovernment=new Government("testfiles\\database\\findGatherings1.properties");	
				  objGovernment.findGatherings(10, -2, 2, 0.1f);
			  }
			 });
			
			String expectedMessage = "Invalid minimum size";
		    String actualMessage = exception.getMessage();
		    assertEquals("Test 6: Invalid minimum size",expectedMessage,actualMessage);
		
	}
	
	@Test()
	public void test7() {
		
		Exception exception= assertThrows(ApplicationValidationException.class, new ThrowingRunnable() {
			  public void run() throws Throwable {
				  Government objGovernment=new Government("testfiles\\database\\findGatherings1.properties");	
				  objGovernment.findGatherings(10, 2, 0, 0.1f);  
			  }
			 });
			
			String expectedMessage = "Invalid minimum time";
		    String actualMessage = exception.getMessage();
		    assertEquals("Test 7: Invalid minimum time",expectedMessage,actualMessage);
		
	}
	
	@Test()
	public void test8() {
		
		Exception exception= assertThrows(ApplicationValidationException.class, new ThrowingRunnable() {
			  public void run() throws Throwable {
				  Government objGovernment=new Government("testfiles\\database\\findGatherings1.properties");	
				  objGovernment.findGatherings(10, 2, 155555, 0.1f);  
			  }
			 });
			
			String expectedMessage = "Invalid minimum time";
		    String actualMessage = exception.getMessage();
		    assertEquals("Test 8: Invalid minimum time",expectedMessage,actualMessage);
		
	}
	
	@Test()
	public void test9() {
		
		Exception exception= assertThrows(ApplicationValidationException.class, new ThrowingRunnable() {
			  public void run() throws Throwable {
				  Government objGovernment=new Government("testfiles\\database\\findGatherings1.properties");	
				  objGovernment.findGatherings(10, 2, -15, 0.1f);  
			  }
			 });
			
			String expectedMessage = "Invalid minimum time";
		    String actualMessage = exception.getMessage();
		    assertEquals("Test 9: Invalid minimum time",expectedMessage,actualMessage);
		
	}
	
	@Test()
	public void test10() {
		
		Exception exception= assertThrows(ApplicationValidationException.class, new ThrowingRunnable() {
			  public void run() throws Throwable {
				  Government objGovernment=new Government("testfiles\\database\\findGatherings1.properties");	
				  objGovernment.findGatherings(10, 2, 2, -0.1f);  
			  }
			 });
			
			String expectedMessage = "Invalid density";
		    String actualMessage = exception.getMessage();
		    assertEquals("Test 10: Invalid density",expectedMessage,actualMessage);
		
	}
}
