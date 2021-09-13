package com.covid.main;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class SynchronizeAndControlFlowTest {
	
	@Test()
	public void test1() {
		
		try {
			Government objGovernment=new Government("testfiles\\database\\controlflow.properties");
			
			MobileDevice objMobileDevice1=new MobileDevice("testfiles\\main\\devices\\device1.txt",objGovernment);
			objMobileDevice1.recordContact("1421396827", 345, 45);
			objMobileDevice1.recordContact("305621101", 343, 20);
			objMobileDevice1.recordContact("-221149134", 346, 17);
			MobileDevice objMobileDevice2=new MobileDevice("testfiles\\main\\devices\\device2.properties",objGovernment);
			objMobileDevice2.recordContact("1545486612", 345, 45);
			objMobileDevice2.recordContact("305621101", 340, 13);
			objMobileDevice2.recordContact("-221149134", 346, 14);
			MobileDevice objMobileDevice3=new MobileDevice("testfiles\\main\\devices\\device3.txt",objGovernment);
			objMobileDevice3.recordContact("1545486612", 343, 20);
			objMobileDevice3.recordContact("1421396827", 340, 13);
			objMobileDevice3.recordContact("-221149134", 337, 26);
			MobileDevice objMobileDevice4=new MobileDevice("testfiles\\main\\devices\\device4.properties",objGovernment);
			objMobileDevice4.recordContact("1545486612", 346, 17);
			objMobileDevice4.recordContact("1421396827", 346, 14);
			objMobileDevice4.recordContact("305621101", 337, 26);
			
			
			
			assertEquals("Test 1a:::Recording device 1 in government",false,objMobileDevice1.synchronize());
			assertEquals("Test 1b:::Recording device 2 in government",false,objMobileDevice2.synchronize());
			assertEquals("Test 1c:::Recording device 3 in government",false,objMobileDevice3.synchronize());
			assertEquals("Test 1d:::Recording device 4 in government",false,objMobileDevice4.synchronize());
			
		} catch (ApplicationValidationException e) {
		}
		
		
	}
	
	@Test()
	public void test2() {
		
		try {
			Government objGovernment=new Government("testfiles\\database\\controlflow.properties");
			objGovernment.recordTestResult("TEST##HASH##DEV2", 341, false); //updating test in the government
			
			MobileDevice objMobileDevice1=new MobileDevice("testfiles\\main\\devices\\device1.txt",objGovernment);
			objMobileDevice1.recordContact("1421396827", 345, 45);
			objMobileDevice1.recordContact("305621101", 343, 20);
			objMobileDevice1.recordContact("-221149134", 346, 17);
			MobileDevice objMobileDevice2=new MobileDevice("testfiles\\main\\devices\\device2.properties",objGovernment);
			objMobileDevice2.recordContact("1545486612", 345, 45);
			objMobileDevice2.recordContact("305621101", 340, 13);
			objMobileDevice2.recordContact("-221149134", 346, 14);
			MobileDevice objMobileDevice3=new MobileDevice("testfiles\\main\\devices\\device3.txt",objGovernment);
			objMobileDevice3.recordContact("1545486612", 343, 20);
			objMobileDevice3.recordContact("1421396827", 340, 13);
			objMobileDevice3.recordContact("-221149134", 337, 26);
			MobileDevice objMobileDevice4=new MobileDevice("testfiles\\main\\devices\\device4.properties",objGovernment);
			objMobileDevice4.recordContact("1545486612", 346, 17);
			objMobileDevice4.recordContact("1421396827", 346, 14);
			objMobileDevice4.recordContact("305621101", 337, 26);
			
			assertEquals("Test 2a:::Recording device 1 in government",false,objMobileDevice1.synchronize());
			assertEquals("Test 2b:::Recording device 2 in government",false,objMobileDevice2.synchronize());
			assertEquals("Test 2c:::Recording device 3 in government",false,objMobileDevice3.synchronize());
			assertEquals("Test 2d:::Recording device 4 in government",false,objMobileDevice4.synchronize());
			
		} catch (ApplicationValidationException e) {
		}
		
		
	}
	
	@Test()
	public void test3() {
		
		try {
			Government objGovernment=new Government("testfiles\\database\\controlflow.properties");
			
			MobileDevice objMobileDevice1=new MobileDevice("testfiles\\main\\devices\\device1.txt",objGovernment);
			objMobileDevice1.recordContact("1421396827", 345, 45);
			objMobileDevice1.recordContact("305621101", 343, 20);
			objMobileDevice1.recordContact("-221149134", 346, 17);
			objMobileDevice1.positiveTest("TEST##HASH##DEV2");
			MobileDevice objMobileDevice2=new MobileDevice("testfiles\\main\\devices\\device2.properties",objGovernment);
			objMobileDevice2.recordContact("1545486612", 345, 45);
			objMobileDevice2.recordContact("305621101", 340, 13);
			objMobileDevice2.recordContact("-221149134", 346, 14);
			MobileDevice objMobileDevice3=new MobileDevice("testfiles\\main\\devices\\device3.txt",objGovernment);
			objMobileDevice3.recordContact("1545486612", 343, 20);
			objMobileDevice3.recordContact("1421396827", 340, 13);
			objMobileDevice3.recordContact("-221149134", 337, 26);
			MobileDevice objMobileDevice4=new MobileDevice("testfiles\\main\\devices\\device4.properties",objGovernment);
			objMobileDevice4.recordContact("1545486612", 346, 17);
			objMobileDevice4.recordContact("1421396827", 346, 14);
			objMobileDevice4.recordContact("305621101", 337, 26);
			
			assertEquals("Test 3a:::Recording device 1 in government",false,objMobileDevice1.synchronize());
			assertEquals("Test 3b:::Recording device 2 in government",false,objMobileDevice2.synchronize());
			assertEquals("Test 3c:::Recording device 3 in government",false,objMobileDevice3.synchronize());
			assertEquals("Test 3d:::Recording device 4 in government",false,objMobileDevice4.synchronize());
			
		} catch (ApplicationValidationException e) {
		}
		
		
	}

	
	
	@Test()
	public void test4() {
		
		try {
			Government objGovernment=new Government("testfiles\\database\\controlflow.properties");
			
			MobileDevice objMobileDevice1=new MobileDevice("testfiles\\main\\devices\\device1.txt",objGovernment);
			objMobileDevice1.recordContact("1421396827", 320, 45);
			objMobileDevice1.recordContact("305621101", 321, 20);
			objMobileDevice1.recordContact("-221149134", 318, 17);
			MobileDevice objMobileDevice2=new MobileDevice("testfiles\\main\\devices\\device2.properties",objGovernment);
			objMobileDevice2.recordContact("1545486612", 320, 45);
			objMobileDevice2.recordContact("305621101", 340, 13);
			objMobileDevice2.recordContact("-221149134", 346, 14);
			MobileDevice objMobileDevice3=new MobileDevice("testfiles\\main\\devices\\device3.txt",objGovernment);
			objMobileDevice3.recordContact("1545486612", 321, 20);
			objMobileDevice3.recordContact("1421396827", 340, 13);
			objMobileDevice3.recordContact("-221149134", 337, 26);
			MobileDevice objMobileDevice4=new MobileDevice("testfiles\\main\\devices\\device4.properties",objGovernment);
			objMobileDevice4.recordContact("1545486612", 318, 17);
			objMobileDevice4.recordContact("1421396827", 346, 14);
			objMobileDevice4.recordContact("305621101", 337, 26);
			
			assertEquals("Test 4a:::Recording device 1 in government",false,objMobileDevice1.synchronize());
			assertEquals("Test 4b:::Recording device 2 in government",false,objMobileDevice2.synchronize());
			assertEquals("Test 4c:::Recording device 3 in government",false,objMobileDevice3.synchronize());
			assertEquals("Test 4d:::Recording device 4 in government",false,objMobileDevice4.synchronize());
			
		} catch (ApplicationValidationException e) {
		}
		
		
	}
	
	@Test()
	public void test5() {
		
		try {
			Government objGovernment=new Government("testfiles\\database\\controlflow.properties");
			objGovernment.recordTestResult("TEST##HASH##DEV1", 341, true); //updating test in the government
			
			MobileDevice objMobileDevice1=new MobileDevice("testfiles\\main\\devices\\device1.txt",objGovernment);
			objMobileDevice1.recordContact("1421396827", 345, 45);
			objMobileDevice1.recordContact("305621101", 343, 20);
			objMobileDevice1.recordContact("-221149134", 346, 17);
			MobileDevice objMobileDevice2=new MobileDevice("testfiles\\main\\devices\\device2.properties",objGovernment);
			objMobileDevice2.recordContact("1545486612", 345, 45);
			objMobileDevice2.recordContact("305621101", 340, 13);
			objMobileDevice2.recordContact("-221149134", 346, 14);
			MobileDevice objMobileDevice3=new MobileDevice("testfiles\\main\\devices\\device3.txt",objGovernment);
			objMobileDevice3.recordContact("1545486612", 343, 20);
			objMobileDevice3.recordContact("1421396827", 340, 13);
			objMobileDevice3.recordContact("-221149134", 337, 26);
			MobileDevice objMobileDevice4=new MobileDevice("testfiles\\main\\devices\\device4.properties",objGovernment);
			objMobileDevice4.recordContact("1545486612", 346, 17);
			objMobileDevice4.recordContact("1421396827", 346, 14);
			objMobileDevice4.recordContact("305621101", 337, 26);
			
			assertEquals("Test 5a:::Recording device 1 in government",false,objMobileDevice1.synchronize());
			assertEquals("Test 5b:::Recording device 2 in government",false,objMobileDevice2.synchronize());
			assertEquals("Test 5c:::Recording device 3 in government",false,objMobileDevice3.synchronize());
			assertEquals("Test 5d:::Recording device 4 in government",false,objMobileDevice4.synchronize());
			
		} catch (ApplicationValidationException e) {
		}
		
		
	}
	
	@Test()
	public void test6() {
		
		try {
			Government objGovernment=new Government("testfiles\\database\\controlflow.properties");
			
			MobileDevice objMobileDevice1=new MobileDevice("testfiles\\main\\devices\\device1.txt",objGovernment);
			objMobileDevice1.recordContact("1421396827", 345, 45);
			objMobileDevice1.recordContact("305621101", 343, 20);
			objMobileDevice1.recordContact("-221149134", 346, 17);
			objMobileDevice1.positiveTest("TEST##HASH##DEV1");
			MobileDevice objMobileDevice2=new MobileDevice("testfiles\\main\\devices\\device2.properties",objGovernment);
			objMobileDevice2.recordContact("1545486612", 345, 45);
			objMobileDevice2.recordContact("305621101", 340, 13);
			objMobileDevice2.recordContact("-221149134", 346, 14);
			MobileDevice objMobileDevice3=new MobileDevice("testfiles\\main\\devices\\device3.txt",objGovernment);
			objMobileDevice3.recordContact("1545486612", 343, 20);
			objMobileDevice3.recordContact("1421396827", 340, 13);
			objMobileDevice3.recordContact("-221149134", 337, 26);
			MobileDevice objMobileDevice4=new MobileDevice("testfiles\\main\\devices\\device4.properties",objGovernment);
			objMobileDevice4.recordContact("1545486612", 346, 17);
			objMobileDevice4.recordContact("1421396827", 346, 14);
			objMobileDevice4.recordContact("305621101", 337, 26);
			
			assertEquals("Test 6a:::Recording device 1 in government",false,objMobileDevice1.synchronize());
			assertEquals("Test 6b:::Recording device 2 in government",true,objMobileDevice2.synchronize());
			assertEquals("Test 6c:::Recording device 3 in government",true,objMobileDevice3.synchronize());
			assertEquals("Test 6d:::Recording device 4 in government",true,objMobileDevice4.synchronize());
			
		} catch (ApplicationValidationException e) {
		}
		
		
	}
	
	
}
