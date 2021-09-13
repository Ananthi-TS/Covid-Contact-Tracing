package com.covid.main;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ MobileConstructorTest.class,MobileRecordContactTest.class,MobilePositiveTest.class,
	GovernmentConstructorTest.class,FindGatheringsTest.class,GovernmentRecordTest.class,SynchronizeAndControlFlowTest.class})
public class AllTests {

	//Test suite to run all the Test cases
	
}
