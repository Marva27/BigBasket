package com.bigbasket.framework.util;

import java.io.File;
import java.io.IOException;
import java.util.Hashtable;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.SkipException;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class Utility {
	
	public static Object[][] getData(String testCase,Xls_Reader xls){
				//Find the row number for the test
				int testCaseRowNum = 1;
				while(!xls.getCellData(Constants.TEST_DATA_SHEET, 0, testCaseRowNum).toLowerCase().equals(testCase.toLowerCase())){
					testCaseRowNum++;
				}
				
				//Row for Column and Data
				int colStartRowNum = testCaseRowNum+1;
				int dataStartRowNum = testCaseRowNum+2;
				int rows = 0;
				
				
				//Total rows of data in the test
				while(!xls.getCellData(Constants.TEST_DATA_SHEET, 0, dataStartRowNum+rows).trim().equals("")){
					rows++;
				}
				
				//System.out.println(rows);
				
				//Total columns of a test data record
				int cols = 0;
				while(!xls.getCellData(Constants.TEST_DATA_SHEET, cols, colStartRowNum).trim().equals("")){
					cols++;
				}
				
				//Print the data
				Object testData[][] = new Object[rows][1];
				int i = 0;
				
				//To print the test data records
				for(int rNum=dataStartRowNum;rNum<dataStartRowNum+rows;rNum++){
					Hashtable<String,String> table = new Hashtable<String,String>();
					for(int cNum=0;cNum<cols;cNum++){
						String data = xls.getCellData(Constants.TEST_DATA_SHEET, cNum, rNum);
						String colName = xls.getCellData(Constants.TEST_DATA_SHEET, cNum, colStartRowNum);
						//System.out.println("Column Name is: "+colName);
						//System.out.println("Data is: "+data);
						table.put(colName, data);
					}
					//put the hash table in object Array
					//System.out.println(table.toString());
					testData[i][0]=table;
					i++;
				}
				return testData;
	}
	
	public static boolean isSuiteRunnable(String suiteName){
		Xls_Reader xls = new Xls_Reader(Constants.TESTSUITE_XLS_PATH);
		int rows = xls.getRowCount(Constants.TESTSUITE_SHEET);
		
		for(int rNum=2;rNum<=rows;rNum++){
			String testSuiteName = xls.getCellData(Constants.TESTSUITE_SHEET, Constants.SUITENAME_COL, rNum);
			if(testSuiteName.toLowerCase().equals(suiteName.toLowerCase())){
				String runMode = xls.getCellData(Constants.TESTSUITE_SHEET, Constants.RUNMODE_COL, rNum);
				if(runMode.equals(Constants.RUNMODE_YES)){
					return true;
				}else{
					return false;
				}
			}
			
		}
		return false;
	}

	public static boolean isTestCaseRunnable(String testCaseName, Xls_Reader xls) {
		int rows = xls.getRowCount(Constants.TEST_CASES_SHEET);
		
		for(int rNum=2;rNum<=rows;rNum++){
			String testName = xls.getCellData(Constants.TEST_CASES_SHEET, Constants.TEST_CASE_NAME_COL, rNum);
			if(testName.toLowerCase().equals(testCaseName.toLowerCase())){
				String runMode = xls.getCellData(Constants.TEST_CASES_SHEET, Constants.RUNMODE_COL, rNum);
				if(runMode.equals(Constants.RUNMODE_YES)){
					return true;
				}else{
					return false;
				}
			}
		}
		return false;
	}
	
	public static void checkRunModes(String suiteName, String testCaseName, Hashtable <String, String> testDataRecord, Xls_Reader xls, 
			ExtentReports reporter, ExtentTest testReporter){
				//Checking the Run Modes of a given test suite
				if(!Utility.isSuiteRunnable(suiteName)){
					testReporter.log(LogStatus.SKIP, "Skipping the test "+testCaseName+" as the Runmode of "+suiteName+" Test Suite is found to be NO.");
					reporter.endTest(testReporter);
					throw new SkipException("Skipping the test "+testCaseName+" as the Runmode of Test Suite is found to be NO");
				}
				
				//Checking the Run Modes of individual tests in a given test suite
				if(!Utility.isTestCaseRunnable(testCaseName,xls)){
					//UtilFunctions.writeLog("Skipping the test "+testCaseName+" as the Runmode of Test Case is found to be NO.");
					testReporter.log(LogStatus.SKIP, "Skipping the test "+testCaseName+" as the Runmode of Test Case is found to be NO.");
					reporter.endTest(testReporter);
					throw new SkipException("Skipping the test "+testCaseName+" as the Runmode of Test Case is found to be NO.");
				}
				
				//Checking the Run Modes of individual data set record
				if(testDataRecord.get(Constants.RUNMODE_COL).equals(Constants.RUNMODE_NO)){
					//UtilFunctions.writeLog("Skipping the test "+testCaseName+" as the Runmode of Test Data Set is found to be NO. "+testDataRecord.toString());
					testReporter.log(LogStatus.SKIP, "Skipping the test "+testCaseName+" as the Runmode of Test Data Set is found to be NO. "+testDataRecord.toString());
					reporter.endTest(testReporter);
					throw new SkipException("Skipping the test "+testCaseName+" as the Runmode of Test Data Set is found to be NO.");
				}
	}
	
	/*
	 * Function Name: captureScreenshot
	 * Parameters: WebDriver browser, String currentTestCaseName, String stepName, String currentIteration
	 * Purpose: To capture the application screenshot in case of failure
	 */
	
	public static File captureScreenshot(WebDriver browser, String currentTestCaseName) throws IOException{
		try{
			//System.out.println(browser.toString());
			File srcFile = ((TakesScreenshot)browser).getScreenshotAs(OutputType.FILE);
			String fileName = currentTestCaseName;
			File destFile = new File(System.getProperty("user.dir")+"//executioninfo//screenshots//"+fileName+".png");
			FileUtils.copyFile(srcFile, destFile);
			return destFile;
		}catch(Exception e){
			System.out.println(e.getMessage());
			return null;
		}
		
	}

	
	public static Logger initLogs(String append){
		FileAppender appender = new FileAppender();
		// configure the appender here, with file location, etc
		appender.setFile(System.getProperty("user.dir")+"//executioninfo//logs//"+append+".log");
		appender.setLayout(new PatternLayout("%d %-5p [%c{1}] %m%n"));
		appender.setAppend(false);
		appender.activateOptions();

		
		Logger APPLICATION_LOG = Logger.getLogger(append);
		APPLICATION_LOG.setLevel(Level.DEBUG);
		APPLICATION_LOG.addAppender(appender);
		
		return APPLICATION_LOG;
	}

}
