package com.covid.main;

public class MainDriver {

	//sample method calling all the methods in Government and Mobile
	public static void main(String[] args) throws Exception {
		
		Government objGovernment=new Government("Sample-GovernmentDB.properties");
		objGovernment.recordTestResult("TEST##HASH", 341, true); //updating test in the government
		
		MobileDevice objMobileDevice1=new MobileDevice("testfiles\\main\\devices\\device1.txt",objGovernment);
		objMobileDevice1.recordContact("1421396827", 345, 45);
		objMobileDevice1.recordContact("305621101", 343, 20);
		objMobileDevice1.recordContact("-221149134", 346, 17);
		objMobileDevice1.positiveTest("TEST##HASH");
		MobileDevice objMobileDevice2=new MobileDevice("testfiles\\main\\devices\\device2.properties",objGovernment);
		objMobileDevice2.recordContact("1545486612", 345, 45);
		objMobileDevice2.recordContact("305621101", 340, 13);
		objMobileDevice2.recordContact("-221149134", 346, 14);
		
		
		if(objMobileDevice1.synchronize()) 
			{
			System.out.println("infected");
			}else {
				System.out.println("safe");
			}
		
		if(objMobileDevice2.synchronize()) 
		{
		System.out.println("infected");
		}else {
			System.out.println("safe");
		}
		Government govObj=new Government("testfiles\\database\\findGatherings1.properties");
		System.out.println(govObj.findGatherings(10, 2, 2, 0.1f));
	}

}
