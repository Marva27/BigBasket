package com.bigbasket.pages;

import java.util.Hashtable;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.bigbasket.framework.util.Constants;
import com.bigbasket.framework.util.UtilFunctions;

public class SignUpPage {
	
	@FindBy(xpath="//*[@id='sign_up']")
	WebElement btnSignUp;
	@FindBy(xpath="html/body/div[19]/div[4]/div[4]/div[1]/div[1]/a[1]")
	WebElement btnFacebookSignUp;
	@FindBy(xpath="html/body/div[19]/div[4]/div[4]/div[1]/div[1]/a[2]")
	WebElement btnGoogleSignUp;
	
	@FindBy(xpath=".//*[@id='uiv2-signupform']/div[2]/div/span[1]/label")
	public WebElement lblMissingFirstName;
	@FindBy(xpath=".//*[@id='uiv2-signupform']/div[2]/div/span[2]/label")
	public WebElement lblMissingLastName;
	@FindBy(xpath=".//*[@id='uiv2-signupform']/div[3]/label[2]")
	public WebElement lblMissingEmailAddress;
	@FindBy(xpath="//*[@id='uiv2-signupform']/div[4]/div/div[1]")
	public WebElement lblMissingPassword;
	@FindBy(xpath=".//*[@id='uiv2-signupform']/div[7]/div/label/label")
	public WebElement lblMissingCaptcha;
	
	@FindBy(xpath="//*[@id='id_firstname']")
	WebElement txtFirstName;
	@FindBy(xpath="//*[@id='id_lastname']")
	WebElement txtLastName;
	@FindBy(xpath="//*[@id='id_email']")
	WebElement txtEmail;
	@FindBy(xpath="//*[@id='id_password']")
	WebElement txtPassword;
	
	public String enterSignUpDetails(Logger log, Hashtable<String, String> testDataRecord){
		try{
			UtilFunctions.writeLog(log, "Starting function enterSignUpDetails in SignUpPage.class");
			txtFirstName.sendKeys(testDataRecord.get("First Name"));
			txtLastName.sendKeys(testDataRecord.get("Last Name"));
			txtEmail.sendKeys(testDataRecord.get("Email Address"));
			txtPassword.sendKeys(testDataRecord.get("Password"));
			btnSignUp.click();
			UtilFunctions.writeLog(log, "Ending function enterSignUpDetails in SignUpPage.class with "+Constants.PASS);
			return Constants.PASS;
		}catch(Exception e){
			UtilFunctions.writeLog(log, "Ending function enterSignUpDetails in SignUpPage.class with "+Constants.ERROR+"-"+e.getMessage());
			return Constants.ERROR;
		}
	}
	
	public String verifySignUpFailure(Logger log, WebElement lblAlert, String expectedMessage){
		try{
			UtilFunctions.writeLog(log, "Starting function verifySignUpFailure in SignUpPage.class");
			String actualMessage = lblAlert.getAttribute("textContent");
			if(actualMessage.trim().equals(expectedMessage.trim())){
				UtilFunctions.writeLog(log, "Ending function verifySignUpFailure in SignUpPage.class with "+Constants.PASS);
				return Constants.PASS;
			}else{
				UtilFunctions.writeLog(log, "Ending function verifySignUpFailure in SignUpPage.class with "+Constants.FAIL);
				return Constants.FAIL+" - Expected alert message is "+expectedMessage+" But the actual alert message displayed was "+actualMessage;
			}
		}catch(Exception e){
			UtilFunctions.writeLog(log, "Ending function verifySignUpFailure in SignUpPage.class with "+Constants.ERROR+" - "+e.getMessage());
			return Constants.ERROR;
		}
	}
}
