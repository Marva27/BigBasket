package com.bigbasket.framework.lab;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Hashtable;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.bigbasket.base.BaseTest;
import com.bigbasket.framework.util.Constants;
import com.bigbasket.framework.util.TestCaseDataProvider;
import com.bigbasket.framework.util.UtilFunctions;
import com.bigbasket.framework.util.Utility;
import com.bigbasket.framework.util.Xls_Reader;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class TestNGLab extends BaseTest{
	
	public UtilFunctions utilFunctions;
	public ExtentTest testReporter=null;
	public WebDriver browser;
	public Hashtable <String, String> testDataRecord;
	

	@BeforeMethod
	public void beforeMethod(Method m) throws IOException{
		String testName = m.getName();
		
		//Checking the Run Modes of a given test suite, test cases within the suite and test data records of the test cases
		Xls_Reader xls = new Xls_Reader(Constants.SUITECUSTOMER_XLS_PATH);
		int rowNum = xls.getCellRowNum(Constants.TEST_CASES_SHEET, Constants.TEST_CASE_ID_COL, testName);
		String currentTestName = testName+"_"+testDataRecord.get(Constants.CASE_COL)+"_"+testDataRecord.get(Constants.ITERATION_COL);
		String testDescription = xls.getCellData(Constants.TEST_CASES_SHEET, Constants.TEST_DESCRIPTION_COL, rowNum);
		testReporter = reporter.startTest(currentTestName, testDescription);
		Utility.checkRunModes("Customer", testName, testDataRecord, xls, reporter, testReporter);
		
		//Executing the test when the run mode is found to be YES
		Logger log = Utility.initLogs(currentTestName);
		log.debug("Starting "+currentTestName);
		
		utilFunctions = UtilFunctions.getInstance(testName);
		utilFunctions.setLogger(log);
		
		log.debug("Executing "+currentTestName+" using "+testDataRecord.toString());
		
		testReporter.log(LogStatus.INFO, "Test Data", "Starting the test "+currentTestName+" using test data "+testDataRecord.toString());
	}
	
	@Test(dataProviderClass=TestCaseDataProvider.class,dataProvider="getDataForCustomerSuite")
	public void signInTest(){
		//System.out.println(testDataRecord.toString());
	}
}
