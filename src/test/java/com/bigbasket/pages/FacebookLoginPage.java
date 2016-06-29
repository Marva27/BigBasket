package com.bigbasket.pages;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.bigbasket.framework.util.Constants;
import com.bigbasket.framework.util.UtilFunctions;

public class FacebookLoginPage {
	
	@FindBy(xpath="//*[@id='loginbutton']")
	WebElement btnLogin;
	
	@FindBy(xpath="//*[@id='email']")
	WebElement txtEmailAddress;
	@FindBy(xpath="//*[@id='pass']")
	WebElement txtPassword;
	
	public void enterEmailAddress(Logger log, String emailAddress){
		UtilFunctions.writeLog(log, "Starting function enterEmailAddress in FacebookLoginPage.class");
		try{
			txtEmailAddress.sendKeys(emailAddress);
		}catch(Exception e){
			UtilFunctions.writeLog(log, Constants.ERROR+"-"+e.getMessage());
		}
		UtilFunctions.writeLog(log, "Ending function enterEmailAddress in FacebookLoginPage.class");
	}
	
	public void enterPassword(Logger log, String password){
		UtilFunctions.writeLog(log, "Starting function enterPassword in FacebookLoginPage.class");
		try{
			txtPassword.sendKeys(password);
		}catch(Exception e){
			UtilFunctions.writeLog(log, Constants.ERROR+"-"+e.getMessage());
		}
		UtilFunctions.writeLog(log, "Ending function enterPassword in FacebookLoginPage.class");
	}
	
	public HomePage clickLogin(Logger log, WebDriver browser){
		UtilFunctions.writeLog(log, "Starting function clickLogin in FacebookLoginPage.class");
		try{
			btnLogin.click();
			UtilFunctions.writeLog(log, "Ending function clickLogin in FacebookLoginPage.class");
			return PageFactory.initElements(browser, HomePage.class);
		}catch(Exception e){
			UtilFunctions.writeLog(log, "Ending function clickLogin in FacebookLoginPage.class with "+Constants.ERROR+"-"+e.getMessage());
			return null;
		}
	}

}
