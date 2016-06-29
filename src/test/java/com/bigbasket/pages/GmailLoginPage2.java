package com.bigbasket.pages;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.bigbasket.framework.util.Constants;
import com.bigbasket.framework.util.UtilFunctions;


public class GmailLoginPage2 {
	
	@FindBy(xpath="//*[@id='signIn']")
	WebElement btnSignIn;
	
	@FindBy(xpath="//*[@id='Passwd']")
	WebElement txtPassword;
	
	public HomePage enterPasswordAndClickNext(Logger log, WebDriver browser, String password){
		try{
			UtilFunctions.writeLog(log, "Starting function enterPasswordAndClickNext in GmailLoginPage2.class");
			txtPassword.sendKeys(password);
			btnSignIn.click();
			UtilFunctions.writeLog(log, "Ending function enterPasswordAndClickNext in GmailLoginPage2.class");
			return PageFactory.initElements(browser, HomePage.class);
		}catch(Exception e){
			UtilFunctions.writeLog(log, "Ending function enterPasswordAndClickNext in GmailLoginPage2.class with "+Constants.ERROR+"-"+e.getMessage());
			return null;
		}
	}
}
