package com.bigbakset.customer.myaccount;

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
import com.bigbasket.pages.EmailAddressesPage;
import com.bigbasket.pages.HomePage;
import com.bigbasket.pages.MyAccountPage;
import com.bigbasket.pages.SignInPage;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class EmailAddressesTest extends BaseTest{

	public UtilFunctions utilFunctions;
	public ExtentTest testReporter;
	public WebDriver browser;
	
	//Web pages declarations
	public HomePage homePage;
	public SignInPage signInPage;
	public MyAccountPage myAccountPage;
	public EmailAddressesPage emailAddressesPage;
	@Test(dataProviderClass=TestCaseDataProvider.class,dataProvider="getDataForMyAccountSuite",priority=1)
	public void addEmailAddressTest(Hashtable <String, String> testDataRecord) throws IOException{
		
		String testName = "AddEmailAddressTest";
		
		String currentTestName = testName+"_"+testDataRecord.get(Constants.CASE_COL)+"_"+testDataRecord.get(Constants.ITERATION_COL);
		
		//Using an utility function to start a test
		utilFunctions = UtilFunctions.getInstance(testName);
		testReporter = utilFunctions.startTest(Constants.SUITEMYACCOUNT_XLS_PATH, "MyAccount", testName, testDataRecord, reporter, testReporter);
		
		//Logs
		Logger log = Utility.initLogs(currentTestName);
		UtilFunctions.writeLog(log, "Starting "+currentTestName);
						
		utilFunctions.setLogger(log);
						
		UtilFunctions.writeLog(log, "Executing "+currentTestName+" using "+testDataRecord.toString());
				
		//Executing the test case if the run mode is found to be YES
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

				
		//Login to user account
		homePage = PageFactory.initElements(browser, HomePage.class);
		homePage.clickSkipAndExplore(log);
		homePage.clickLOGINLink(log);
				
		signInPage = PageFactory.initElements(browser, SignInPage.class);
		String loginResult = signInPage.doLogin(log, browser, project.getProperty("userName"),project.getProperty("password"));
		utilFunctions.writeHTMLResult(currentTestName, browser, reporter, testReporter, "Step 3", "Login to Customer account", loginResult);
				
		//Select the MY ACCOUNT menu option and click Email Addresses link
		myAccountPage = homePage.selectMyAccount(log, browser);
		emailAddressesPage = myAccountPage.clickEmailAddressesLink(log, browser);
		stepResult = emailAddressesPage.verifyEmailAddressesPage(log);
		UtilFunctions.writeLog(log, "Ending function verifyEmailAddressesPage in EmailAddressesPage.class "+stepResult);
		utilFunctions.writeHTMLResult(currentTestName, browser, reporter, testReporter, "Step 4", "Select MY ACCOUNT menu option and click the Email Addresses link", stepResult);
		
		//Add Email Address
		emailAddressesPage.addEmailAddress(log, testDataRecord.get("Email Address"));
		emailAddressesPage = emailAddressesPage.clickSave(log, browser);
		switch(testDataRecord.get("Case")){
			case "CASE_MISSING_EMAIL":
				stepResult = emailAddressesPage.verifyAlertForEmail(log, testDataRecord.get("Alert Message"));
				utilFunctions.writeHTMLResult(currentTestName, browser, reporter, testReporter, "Step 5", "Verify if the Add new email address restricted because of null value", stepResult);
				break;
			case "CASE_INVALID_EMAIL":
				stepResult = emailAddressesPage.verifyAlertForEmail(log, testDataRecord.get("Alert Message"));
				utilFunctions.writeHTMLResult(currentTestName, browser, reporter, testReporter, "Step 5", "Verify if the Add new email address restricted because of invalid email address", stepResult);
				break;
			case "CASE_EXISTING_EMAIL":
				stepResult = emailAddressesPage.verifyAlertForEmail(log, testDataRecord.get("Alert Message"));
				utilFunctions.writeHTMLResult(currentTestName, browser, reporter, testReporter, "Step 5", "Verify if the Add new email address restricted because of entered email address already exists", stepResult);
				break;
			case "CASE_OK":
				String successMessage = "The email address "+testDataRecord.get("Email Address")+" has been added successfully. Please check your email and validate this address";
				stepResult = emailAddressesPage.verifyAlertForEmailSuccess(log, successMessage);
				emailAddressesPage = emailAddressesPage.clickOK(log, browser);
				utilFunctions.writeHTMLResult(currentTestName, browser, reporter, testReporter, "Step 5", "Verify if the new email address can be added to the customer account", stepResult);
					
				//Verify the newly added email address
				stepResult = emailAddressesPage.verifyNewEmailAddress(log, testDataRecord.get("Email Address"));
				utilFunctions.writeHTMLResult(currentTestName, browser, reporter, testReporter, "Step 6", "Verify if the "+testDataRecord.get("Email Address")+" is added to the Customer account", stepResult);
				break;
			}
		
		//Ending the test
		UtilFunctions.writeLog(log, "Ending "+currentTestName);
		reporter.endTest(testReporter);
	}
	
	@Test(dataProviderClass=TestCaseDataProvider.class,dataProvider="getDataForMyAccountSuite", priority=2)
	public void removeEmailAddressTest(Hashtable <String, String> testDataRecord) throws IOException{
		
		String testName = "RemoveEmailAddressTest";
		
		String currentTestName = testName+"_"+testDataRecord.get(Constants.CASE_COL)+"_"+testDataRecord.get(Constants.ITERATION_COL);
		
		//Using an utility function to start a test
		utilFunctions = UtilFunctions.getInstance(testName);
		testReporter = utilFunctions.startTest(Constants.SUITEMYACCOUNT_XLS_PATH, "MyAccount", testName, testDataRecord, reporter, testReporter);
		
		//Logs
		Logger log = Utility.initLogs(currentTestName);
		UtilFunctions.writeLog(log, "Starting "+currentTestName);
						
		utilFunctions.setLogger(log);
						
		UtilFunctions.writeLog(log, "Executing "+currentTestName+" using "+testDataRecord.toString());
				
		//Executing the test case if the run mode is found to be YES
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

		//Login to user account
		homePage = PageFactory.initElements(browser, HomePage.class);
		homePage.clickSkipAndExplore(log);
		homePage.clickLOGINLink(log);
				
		signInPage = PageFactory.initElements(browser, SignInPage.class);
		String loginResult = signInPage.doLogin(log, browser, project.getProperty("userName"),project.getProperty("password"));
		utilFunctions.writeHTMLResult(currentTestName, browser, reporter, testReporter, "Step 3", "Login to Customer account", loginResult);
				
		//Select the MY ACCOUNT menu option and click Email Addresses link
		myAccountPage = homePage.selectMyAccount(log, browser);
		emailAddressesPage = myAccountPage.clickEmailAddressesLink(log, browser);
		stepResult = emailAddressesPage.verifyEmailAddressesPage(log);
		UtilFunctions.writeLog(log, "Ending function verifyEmailAddressesPage in EmailAddressesPage.class "+stepResult);
		utilFunctions.writeHTMLResult(currentTestName, browser, reporter, testReporter, "Step 4", "Select MY ACCOUNT menu option and click the Email Addresses link", stepResult);
		
		//Remove the Email Address
		switch(testDataRecord.get("Case")){
		case "CASE_OK":
			stepResult = emailAddressesPage.removeEmailAddress(log, browser, "OK");
			utilFunctions.writeHTMLResult(currentTestName, browser, reporter, testReporter, "Step 5", "Click the Remove button, click the OK button and verify if the Email Address is removed", stepResult);
			break;
		case "CASE_CANCEL":
			stepResult = emailAddressesPage.removeEmailAddress(log, browser, "CANCEL");
			utilFunctions.writeHTMLResult(currentTestName, browser, reporter, testReporter, "Step 5", "Click the Remove button, click the CANCEL button and verify if the Email Address is NOT removed", stepResult);
			break;
		}
		
		//Ending the test
		UtilFunctions.writeLog(log, "Ending "+currentTestName);
		reporter.endTest(testReporter);
	}
}
