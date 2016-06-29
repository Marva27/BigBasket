package com.bigbasket.customer;

import java.io.IOException;
import java.util.Hashtable;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.Test;

import com.bigbasket.base.BaseTest;
import com.bigbasket.framework.util.Constants;
import com.bigbasket.framework.util.TestCaseDataProvider;
import com.bigbasket.framework.util.UtilFunctions;
import com.bigbasket.framework.util.Utility;
import com.bigbasket.pages.HomePage;
import com.bigbasket.pages.ResetPasswordPage;
import com.bigbasket.pages.SignInPage;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class ForgotPasswordTest extends BaseTest{
	
	public UtilFunctions utilFunctions;
	public ExtentTest testReporter;
	public WebDriver browser;
	
	//Required web pages declaration
	public HomePage homePage;
	public SignInPage signInPage;
	public ResetPasswordPage resetPasswordPage;
	
	@Test(dataProviderClass=TestCaseDataProvider.class,dataProvider="getDataForCustomerSuite")
	public void forgotPasswordTest(Hashtable <String, String> testDataRecord) throws IOException, InterruptedException{
		
		String testName = "ForgotPasswordTest";
		String currentTestName = testName+"_"+testDataRecord.get(Constants.CASE_COL)+"_"+testDataRecord.get(Constants.ITERATION_COL);
		
		//Using an utility function to start a test
		utilFunctions = UtilFunctions.getInstance(testName);
		testReporter = utilFunctions.startTest(Constants.SUITECUSTOMER_XLS_PATH, "Customer", testName, testDataRecord, reporter, testReporter);
		
		//Logs
		Logger log = Utility.initLogs(currentTestName);
		UtilFunctions.writeLog(log, "Starting "+currentTestName);
		utilFunctions.setLogger(log);
		
		UtilFunctions.writeLog(log, "Executing "+currentTestName+" using "+testDataRecord.toString());
		
		//Executing the test case if the run mode is found to be YES
		System.out.println(testReporter.toString());
		testReporter.log(LogStatus.INFO, "Test Data", "Starting the test "+currentTestName+" using test data "+testDataRecord.toString());
		
		//Opening the required browser
		browser = utilFunctions.openBrowser(testDataRecord.get("Browser"),project.getProperty("platform"));
		if(browser!=null){
			utilFunctions.writeHTMLResult(currentTestName, browser, reporter, testReporter, "Step 1", "Launch the required browser", Constants.PASS);
		}else{
			utilFunctions.writeHTMLResult(currentTestName, browser, reporter, testReporter, "Step 1", "Launch the required browser", Constants.ERROR);
		}
		
		//Connecting to test site URL
		UtilFunctions.writeLog(log, "Starting connectURL function trying to connect "+project.getProperty("testSiteURL"));
		String stepResult = utilFunctions.connectURL(project.getProperty("testSiteURL"));
		UtilFunctions.writeLog(log, "Ending connectURL function "+stepResult);
		utilFunctions.writeHTMLResult(currentTestName, browser, reporter, testReporter, "Step 2", "Connect to http://www.bigbasket.com", stepResult);
		
		//Click Skip and Explore button
		homePage = PageFactory.initElements(browser, HomePage.class);
		stepResult = homePage.clickSkipAndExplore(log);
		utilFunctions.writeHTMLResult(currentTestName, browser, reporter, testReporter, "Step 3", "Click the Skip and Explore button", stepResult);
		
		//Click the LOGIN link
		stepResult = homePage.clickLOGINLink(log);
		utilFunctions.writeHTMLResult(currentTestName, browser, reporter, testReporter, "Step 4", "Click the LOGIN link", stepResult);
		
		//Enter Email Address and click Forgot Password link
		signInPage = PageFactory.initElements(browser, SignInPage.class);
		signInPage.enterEmailAddress(log, testDataRecord.get("Email Address"));
		resetPasswordPage = signInPage.clickForgotPassword(log, browser);
		
		//Retrieve Forgotten Password
		switch(testDataRecord.get("Case")){
		case "CASE_OK":
			stepResult = resetPasswordPage.verifyCodeSent(log, testDataRecord.get("Email Address"));
			utilFunctions.writeHTMLResult(currentTestName, browser, reporter, testReporter, "Step 5", "Verify if the OTP Code is sent to registered email address and mobile number upon providing valid email address, clicking the Forgot Password link", stepResult);
			break;
		case "CASE_INVALID_EMAIL":
			Thread.sleep(1000);
			signInPage = PageFactory.initElements(browser, SignInPage.class);
			stepResult = signInPage.verifyCodeNotSent(log, testDataRecord.get("Alert Message"));
			UtilFunctions.writeLog(log, "Ending function verifyCodeNotSent in SignInPage.class with "+stepResult);
			utilFunctions.writeHTMLResult(currentTestName, browser, reporter, testReporter, "Step 5", "Verify if the OTP Code is NOT sent when an invalid email is entered", stepResult);
			break;
		case "CASE_MISSING_EMAIL":
			signInPage = PageFactory.initElements(browser, SignInPage.class);
			stepResult = signInPage.verifyCodeNotSent(log, testDataRecord.get("Alert Message"));
			UtilFunctions.writeLog(log, "Ending function verifyCodeNotSent in SignInPage.class with "+stepResult);
			utilFunctions.writeHTMLResult(currentTestName, browser, reporter, testReporter, "Step 5", "Verify if the OTP Code is NOT sent when an invalid email is entered", stepResult);
			break;
		}
		
		//Ending the test
		UtilFunctions.writeLog(log, "Ending "+currentTestName);
		reporter.endTest(testReporter);
	}

}
