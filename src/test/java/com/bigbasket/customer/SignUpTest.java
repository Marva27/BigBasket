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
import com.bigbasket.pages.SignUpPage;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class SignUpTest extends BaseTest{
	
	public UtilFunctions utilFunctions;
	public ExtentTest testReporter;
	public WebDriver browser;
	
	//Required web pages declaration
	public HomePage homePage;
	public SignUpPage signUpPage;
	
	@Test(dataProviderClass=TestCaseDataProvider.class,dataProvider="getDataForCustomerSuite")
	public void signUpTest(Hashtable <String, String> testDataRecord) throws IOException{
		
		String testName = "SignUpTest";
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
		
		//Click the SIGN UP Link
		stepResult = homePage.clickSIGNUPLink(log);
		utilFunctions.writeHTMLResult(currentTestName, browser, reporter, testReporter, "Step 4", "Click the SIGNUP link", stepResult);
		
		//Enter the required details for Sign Up
		signUpPage = PageFactory.initElements(browser, SignUpPage.class);
		stepResult = signUpPage.enterSignUpDetails(log, testDataRecord);
		utilFunctions.writeHTMLResult(currentTestName, browser, reporter, testReporter, "Step 5", "Enter the required details for SignUp and click the SIGNUP button", stepResult);
		
		//Verify if the SIGNUP works fine upon providing valid details
		signUpPage = PageFactory.initElements(browser, SignUpPage.class);
		switch(testDataRecord.get("Case")){
		case "CASE_MISSING_FIRST_NAME":
			stepResult = signUpPage.verifySignUpFailure(log, signUpPage.lblMissingFirstName, testDataRecord.get("Alert Message"));
			utilFunctions.writeHTMLResult(currentTestName, browser, reporter, testReporter, "Step 6", "Verify if the Sign Up is restricted when the First Name is not entered", stepResult);
			break;
		case "CASE_MISSING_LAST_NAME":
			stepResult = signUpPage.verifySignUpFailure(log, signUpPage.lblMissingLastName, testDataRecord.get("Alert Message"));
			utilFunctions.writeHTMLResult(currentTestName, browser, reporter, testReporter, "Step 6", "Verify if the Sign Up is restricted when the Last Name is not entered", stepResult);
			break;
		case "CASE_MISSING_EMAIL_ADDRESS":
			stepResult = signUpPage.verifySignUpFailure(log, signUpPage.lblMissingEmailAddress, testDataRecord.get("Alert Message"));
			utilFunctions.writeHTMLResult(currentTestName, browser, reporter, testReporter, "Step 6", "Verify if the Sign Up is restricted when the Email Address is not entered", stepResult);
			break;
		case "CASE_MISSING_PASSWORD":
			stepResult = signUpPage.verifySignUpFailure(log, signUpPage.lblMissingPassword, testDataRecord.get("Alert Message"));
			utilFunctions.writeHTMLResult(currentTestName, browser, reporter, testReporter, "Step 6", "Verify if the Sign Up is restricted when the Password is not entered", stepResult);
			break;
		case "CASE_MISSING_CAPTCHA":
			stepResult = signUpPage.verifySignUpFailure(log, signUpPage.lblMissingCaptcha, testDataRecord.get("Alert Message"));
			utilFunctions.writeHTMLResult(currentTestName, browser, reporter, testReporter, "Step 6", "Verify if the Sign Up is restricted when the Captcha is not entered", stepResult);
			break;
		}
		
		//Ending the test
		UtilFunctions.writeLog(log, "Ending "+currentTestName);
		reporter.endTest(testReporter);
	}

}
