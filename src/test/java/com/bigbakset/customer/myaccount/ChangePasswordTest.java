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
import com.bigbasket.pages.ChangePasswordPage;
import com.bigbasket.pages.HomePage;
import com.bigbasket.pages.MyAccountPage;
import com.bigbasket.pages.SignInPage;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class ChangePasswordTest extends BaseTest{
	
	public UtilFunctions utilFunctions;
	public ExtentTest testReporter;
	public WebDriver browser;
	
	//Web pages declarations
	public HomePage homePage;
	public SignInPage signInPage;
	public MyAccountPage myAccountPage;
	public ChangePasswordPage changePasswordPage;
	
	@Test(dataProviderClass=TestCaseDataProvider.class,dataProvider="getDataForMyAccountSuite")
	public void changePasswordTest(Hashtable <String, String> testDataRecord) throws IOException{
		
		String testName = "ChangePasswordTest";
		
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
				
		//Select the MY ACCOUNT menu option and click Change Password link
		myAccountPage = homePage.selectMyAccount(log, browser);
		changePasswordPage = myAccountPage.clickChangePasswordLink(log, browser);
		stepResult = changePasswordPage.verifyChangePasswordPage(log);
		UtilFunctions.writeLog(log, "Ending function changePasswordPage in ChangePasswordPage.class "+stepResult);
		utilFunctions.writeHTMLResult(currentTestName, browser, reporter, testReporter, "Step 4", "Select MY ACCOUNT menu option and click the Change Password link", stepResult);
				
		//Change the Password
		changePasswordPage = changePasswordPage.updatePassword(log, browser, testDataRecord.get("Old Password"), testDataRecord.get("New Password"), testDataRecord.get("Confirm Password"));
		switch(testDataRecord.get("Case")){
			case "CASE_FIELD_REQUIRED":
				UtilFunctions.writeLog(log, "Starting function verifyAlertForFieldRequired in ChangePasswordPage.class");
				stepResult = changePasswordPage.verifyAlertForFieldRequired(testDataRecord.get("Alert Message"));
				UtilFunctions.writeLog(log, "Ending function verifyAlertForFieldRequired in ChangePasswordPage.class with "+stepResult);
				utilFunctions.writeHTMLResult(currentTestName, browser, reporter, testReporter, "Step 5", "Password change is restricted as the Old Password not provided", stepResult);
				break;
			case "CASE_PASSWORD_REQUIREMENT":
				UtilFunctions.writeLog(log, "Starting function verifyAlertForPasswordRequirement in ChangePasswordPage.class");
				stepResult = changePasswordPage.verifyAlertForPasswordRequirement(testDataRecord.get("Alert Message"));
				UtilFunctions.writeLog(log, "Ending function verifyAlertForPasswordRequirement in ChangePasswordPage.class with "+stepResult);
				utilFunctions.writeHTMLResult(currentTestName, browser, reporter, testReporter, "Step 5", "Password change is restricted as the Password is not of length at least 8 characters", stepResult);
				break;
			case "CASE_PASSWORD_MISMATCH":
				UtilFunctions.writeLog(log, "Starting function verifyAlertForPasswordMismatch in ChangePasswordPage.class");
				stepResult = changePasswordPage.verifyAlertForPasswordMismatch(testDataRecord.get("Alert Message"));
				UtilFunctions.writeLog(log, "Ending function verifyAlertForPasswordMismatch in ChangePasswordPage.class with "+stepResult);
				utilFunctions.writeHTMLResult(currentTestName, browser, reporter, testReporter, "Step 5", "Password change is restricted as the New Password and Confirm Password do not match", stepResult);
				break;
			case "CASE_OK":
				UtilFunctions.writeLog(log, "Starting function verifyAlertForPasswordChange in ChangePasswordPage.class");
				stepResult = changePasswordPage.verifyAlertForPasswordChange(testDataRecord.get("Alert Message"));
				UtilFunctions.writeLog(log, "Ending function verifyAlertForPasswordChange in ChangePasswordPage.class with "+stepResult);
				utilFunctions.writeHTMLResult(currentTestName, browser, reporter, testReporter, "Step 5", "Password changed successfully", stepResult);
					
			//Verify the Password Change
			myAccountPage=PageFactory.initElements(browser, MyAccountPage.class);
			homePage = myAccountPage.logoutFromAccount(log, browser);
			homePage.clickLOGINLink(log);
			signInPage = PageFactory.initElements(browser, SignInPage.class);
			loginResult = signInPage.doLogin(log, browser, project.getProperty("userName"),testDataRecord.get("New Password"));
			utilFunctions.writeHTMLResult(currentTestName, browser, reporter, testReporter, "Step 6", "Login to Customer account after successful password change", loginResult);	
			break;
		}
				
		//Ending the test
		UtilFunctions.writeLog(log, "Ending "+currentTestName);
		reporter.endTest(testReporter);
	}

}
