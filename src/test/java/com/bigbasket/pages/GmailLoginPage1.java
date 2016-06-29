package com.bigbasket.pages;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.bigbasket.framework.util.Constants;
import com.bigbasket.framework.util.UtilFunctions;


public class GmailLoginPage1 {
	
	@FindBy(xpath="//*[@id='next']")
	WebElement btnNext;
	
	@FindBy(xpath="//*[@id='Email']")
	WebElement txtEmail;
	
	public void enterEmailAndClickNext(Logger log, String emailAddress){
		UtilFunctions.writeLog(log, "Starting function enterEmailAndClickNext in GmailLoginPage1.class");
		try{
			txtEmail.sendKeys(emailAddress);
			btnNext.click();
		}catch(Exception e){
			UtilFunctions.writeLog(log, "Ending function enterEmailAndClickNext in GmailLoginPage1.class with "+Constants.ERROR+"-"+e.getMessage());
		}
		UtilFunctions.writeLog(log, "Ending function enterEmailAndClickNext in GmailLoginPage1.class");
	}
	

}
