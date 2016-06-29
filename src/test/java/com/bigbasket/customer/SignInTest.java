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
import com.bigbasket.pages.FacebookLoginPage;
import com.bigbasket.pages.GmailLoginPage1;
import com.bigbasket.pages.GmailLoginPage2;
import com.bigbasket.pages.HomePage;
import com.bigbasket.pages.SignInPage;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class SignInTest extends BaseTest{
	
	public UtilFunctions utilFunctions;
	public ExtentTest testReporter;
	public WebDriver browser;
	
	//Required web pages declaration
	public HomePage homePage;
	public SignInPage signInPage;
	public FacebookLoginPage facebookLoginPage;
	public GmailLoginPage1 gmailLoginPage1;
	public GmailLoginPage2 gmailLoginPage2;
	
	@Test(dataProviderClass=TestCaseDataProvider.class,dataProvider="getDataForCustomerSuite")
	public void signInTest(Hashtable <String, String> testDataRecord) throws IOException{
		
		String testName = "SignInTest";
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
		
		//Login to account
		signInPage = PageFactory.initElements(browser, SignInPage.class);
		switch(testDataRecord.get("Case")){
		case "CASE_OK":
			signInPage.doLogin(log, testDataRecord);
			homePage = PageFactory.initElements(browser, HomePage.class);
			utilFunctions.writeHTMLResult(currentTestName, browser, reporter, testReporter, "Step 5",
					"Verify if the user is able to login upon providing valid credentials", homePage.verifyIfLoginSuccess(log, browser, testDataRecord.get("Customer Name")));
			break;
		case "CASE_INVALID_EMAIL":
			signInPage.doLogin(log, testDataRecord);
			UtilFunctions.writeLog(log, "Starting function verifyAlertForInvalidEmail in SignInPage.class");
			stepResult = signInPage.verifyAlertForInvalidEmail(testDataRecord.get("Alert Message"));
			UtilFunctions.writeLog(log, "Ending function verifyAlertForInvalidEmail in SignInPage.class "+stepResult);
			utilFunctions.writeHTMLResult(currentTestName, browser, reporter, testReporter, "Step 5", 
					"Verify if the user is restricted to login upon providing invalid email address", stepResult);
			break;
		case "CASE_INVALID_PASSWORD":
			signInPage.doLogin(log, testDataRecord);
			UtilFunctions.writeLog(log, "Starting function verifyAlertForInvalidPassword in SignInPage.class");
			stepResult = signInPage.verifyAlertForInvalidPassword(browser, testDataRecord.get("Alert Message"));
			UtilFunctions.writeLog(log, "Ending function verifyAlertForInvalidPassword in SignInPage.class - "+stepResult);
			utilFunctions.writeHTMLResult(currentTestName, browser, reporter, testReporter, "Step 5", 
					"Verify if the user is restricted to login upon providing invalid password", stepResult);
			break;
		case "CASE_INVALID_CREDENTIALS":
			signInPage.doLogin(log, testDataRecord);
			UtilFunctions.writeLog(log, "Starting function verifyAlertForInvalidCredentials in SignInPage.class");
			stepResult = signInPage.verifyAlertForInvalidCredentials(browser, testDataRecord.get("Alert Message"));
			UtilFunctions.writeLog(log, "Ending function verifyAlertForInvalidCredentials in SignInPage.class - "+stepResult);
			utilFunctions.writeHTMLResult(currentTestName, browser, reporter, testReporter, "Step 5", 
					"Verify if the user is restricted to login upon providing invalid email or password", stepResult);
			break;
		case "CASE_FACEBOOK_LOGIN":
			signInPage.clickLoginWithFacebook(log);
			facebookLoginPage = PageFactory.initElements(browser, FacebookLoginPage.class);
			facebookLoginPage.enterEmailAddress(log, testDataRecord.get("Email Address"));
			facebookLoginPage.enterPassword(log, testDataRecord.get("Password"));
			homePage = facebookLoginPage.clickLogin(log, browser);
			utilFunctions.writeHTMLResult(currentTestName, browser, reporter, testReporter, "Step 5",
					"Verify if the user is able to login using his/her Facebook account", homePage.verifyIfLoginSuccess(log, browser, testDataRecord.get("Customer Name")));
			break;
		case "CASE_GOOGLE_LOGIN":
			gmailLoginPage1 = signInPage.clickLoginWithGoogle(log, browser);
			gmailLoginPage1.enterEmailAndClickNext(log, testDataRecord.get("Email Address"));
			gmailLoginPage2 = PageFactory.initElements(browser, GmailLoginPage2.class);
			homePage = gmailLoginPage2.enterPasswordAndClickNext(log, browser, testDataRecord.get("Password"));
			utilFunctions.writeHTMLResult(currentTestName, browser, reporter, testReporter, "Step 5",
					"Verify if the user is able to login using his/her Google account", homePage.verifyIfLoginSuccess(log, browser, testDataRecord.get("Customer Name")));
			break;
		}
		
		//Ending the test
		UtilFunctions.writeLog(log, "Ending "+currentTestName);
		reporter.endTest(testReporter);
	}

}
