package com.bigbasket.framework.util;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class UtilFunctions {
	
	public static Logger Application_Log;
	public WebDriver browser;
	public HashMap<String,WebDriver> browserMap;
	
	static HashMap<String,UtilFunctions> instanceMap = new HashMap<String,UtilFunctions>();
	
	public UtilFunctions() throws IOException{
		//Initialize the map
		browserMap = new HashMap<String,WebDriver>();
		browserMap.put(Constants.MOZILLA, null);
		browserMap.put(Constants.CHROME, null);
		browserMap.put(Constants.INTERNETEXPLORER, null);
	}
	
	public void writeHTMLResult(String currentTestIteration, WebDriver browser, ExtentReports reporter, ExtentTest testReporter, String stepName, String stepDesc, String stepStatus) throws IOException{
		try{
			if(stepStatus.contains(Constants.PASS)){
				testReporter.log(LogStatus.PASS, stepName, stepDesc);
			}else if(stepStatus.contains(Constants.ERROR)){
				testReporter.log(LogStatus.ERROR, stepName, stepDesc+" - "+Constants.ERROR+" - Please check logs");
			}else if(stepStatus.contains(Constants.FAIL)){
				testReporter.log(LogStatus.FAIL, stepName, stepDesc+" - "+stepStatus);
				String fileName = currentTestIteration+"_"+stepName;
				File defectScreenshot = Utility.captureScreenshot(browser, fileName);
				testReporter.log(LogStatus.INFO, defectScreenshot.getName(), testReporter.addScreenCapture(defectScreenshot.getAbsolutePath()));
				reporter.endTest(testReporter);
				Assert.fail(stepStatus);	
			}
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
	}

	/*
	 * Function Name: openBrowser
	 * Parameters: browserType
	 * Date Created: 04/01/2016  Date Modified: 04/01/2016
	 * Changes Made: Initial Version
	 * Version: V1.0
	 */
	public WebDriver openBrowser(String browserType, String platform){
		writeLog("Starting function openBrowser - "+ browserType);
		try{
			/* Grid start */
			 DesiredCapabilities cap = null;
			 writeLog(browserMap.toString());
			 if(browserMap.get(browserType)==null){ // browser not opened
				writeLog("Opening fresh browser - "+browserType);
				if(browserType.equalsIgnoreCase(Constants.MOZILLA))
				{
				  cap = DesiredCapabilities.firefox();
				  cap.setBrowserName("firefox");
				}
				
				if(browserType.equalsIgnoreCase(Constants.CHROME))
				{
				  cap = DesiredCapabilities.chrome();
				  cap.setBrowserName("chrome");
				}
				
				if(browserType.equalsIgnoreCase(Constants.INTERNETEXPLORER))
				{
				  cap = DesiredCapabilities.internetExplorer();
				  cap.setBrowserName("internet explorer");
				  cap.setPlatform(org.openqa.selenium.Platform.WINDOWS);
				}
				
				if(platform.equals("MAC")){
					 cap.setPlatform(org.openqa.selenium.Platform.MAC);
				}else if(platform.equals("WINDOWS")){
					cap.setPlatform(org.openqa.selenium.Platform.WINDOWS);
				}
				
				try {
					browser = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), cap);
					browserMap.put(browserType, browser);
				} catch (MalformedURLException e) {
					writeLog(Constants.ERROR+"-"+e.getMessage());
				}
			 }else{
				 writeLog("Using existing browser - "+browserType);
				 browser = browserMap.get(browserType);
			 }
			 /* Grid end */
			browser.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
			browser.manage().window().maximize();
		}catch(Exception e){ //error
			writeLog(Constants.ERROR+"-"+e.getMessage());
			return null;
		}
		return browser;
	}
	
	public String connectURL(String url) {
		browser.manage().deleteAllCookies();
		browser.get(url);
		browser.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		if(browser.getTitle().contains("Big")){
			return Constants.PASS;
		}else{
			return Constants.FAIL+" - Failed to connect test website. Please check URL or Internet Connection.";
		}
	}
	
	public synchronized void setLogger(Logger log){
		Application_Log = log;
	}
	
	public synchronized void writeLog(String message){
		System.out.println(message);
		Application_Log.debug(message);
	}
	
	public synchronized static void writeLog(Logger log, String message){
		System.out.println(log.getName()+"<--"+message);
		log.debug(message);	
	}
	
	public void reportFailureAndStop(WebDriver browser, String currentTestCaseName, String currentStepName, String failureMessage, ExtentReports reporter, ExtentTest stepLogger) throws IOException {
		try{
			System.out.println(browser.toString());
			String fileName = currentTestCaseName+"_"+currentStepName;
			File defectScreenshot = Utility.captureScreenshot(browser, fileName);
			stepLogger.log(LogStatus.INFO, defectScreenshot.getName(), stepLogger.addScreenCapture(defectScreenshot.getAbsolutePath()));
			//writeLog(failureMessage);
			reporter.endTest(stepLogger);
			Assert.fail(failureMessage);
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
				
	}
	
	public static UtilFunctions getInstance(String instanceName) throws IOException {
		if(instanceMap.get(instanceName) == null){
			instanceMap.put(instanceName, new UtilFunctions());
		}
		return instanceMap.get(instanceName);
	}

	public ExtentTest startTest(String suiteXLSPath, String suiteName, String testName,
			Hashtable<String, String> testDataRecord, ExtentReports reporter, ExtentTest testReporter) {
		Xls_Reader xls = new Xls_Reader(suiteXLSPath);
		int rowNum = xls.getCellRowNum(Constants.TEST_CASES_SHEET, Constants.TEST_CASE_NAME_COL, testName);
		String testDescription = xls.getCellData(Constants.TEST_CASES_SHEET, Constants.TEST_DESCRIPTION_COL, rowNum);
		String currentTestName = testName+"_"+testDataRecord.get(Constants.CASE_COL)+"_"+testDataRecord.get(Constants.ITERATION_COL);
		testReporter = reporter.startTest(currentTestName, testDescription);
		Utility.checkRunModes(suiteName, testName, testDataRecord, xls, reporter, testReporter);
		return testReporter;
		
	}

}

