package com.bigbasket.pages;

import java.util.Hashtable;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.bigbasket.framework.util.Constants;
import com.bigbasket.framework.util.UtilFunctions;

public class SignInPage {
	
	public HomePage homePage;
	
	@FindBy(xpath="html/body/div[19]/div[4]/div[3]/div[1]/div[1]/a[1]")
	WebElement btnFacebookLogin;
	@FindBy(xpath="html/body/div[19]/div[4]/div[3]/div[1]/div[1]/a[2]")
	WebElement btnGoogleLogin;
	@FindBy(xpath="//*[@id='uiv2-loginform']/button")
	WebElement btnLOGIN;
	
	@FindBy(xpath="html/body/div[19]/div[4]/div[1]/p/a[1]")
	WebElement h1LOGIN;
	
	@FindBy(xpath="//*[@id='uiv2-loginform']/div[2]/span/label")
	WebElement lblEnterValidEmailAddress;
	@FindBy(xpath="//*[@id='id_login_page_error']/span")
	WebElement lblEnterEmail;
	@FindBy(xpath="//*[@id='uiv2-loginform']/div[3]/label[2]")
	WebElement lblEnterValidPassword;
	@FindBy(xpath="//*[@id='id_login_page_error']/span")
	WebElement lblInvalidCredentials;
	
	@FindBy(xpath="//*[@id='uiv2-loginform']/a")
	WebElement linkForgotPassword;
	
	@FindBy(xpath="//*[@id='uiv2-loginform']/div[2]/span/input")
	WebElement txtEmailAddress;
	@FindBy(xpath="//*[@id='password']")
	WebElement txtPassword;
	
	public String enterEmailAddress(Logger log, String emailAddress){
		UtilFunctions.writeLog(log, "Starting function enterEmailAddress in SignInPage.class");
		try{
			txtEmailAddress.sendKeys(emailAddress);
		}catch(Exception e){
			UtilFunctions.writeLog(log, "Ending function enterEmailAddress in SignInPage.class"+"-"+e.getMessage());
			return Constants.ERROR;
		}
		UtilFunctions.writeLog(log, "Ending function enterEmailAddress in SignInPage.class");
		return Constants.PASS;
	}
	
	public void enterPassword(String password){
		try{
			txtPassword.sendKeys(password);
		}catch(Exception e){
		}
	}
	
	public boolean isInvalidCredentials(){
		try{
			if(lblInvalidCredentials.isDisplayed()){
				return true;
			}else{
				return false;
			}
		}catch(Exception e){
			return false;
		}
	}
	
	public SignInPage clickLOGIN(WebDriver browser){
		try{
			btnLOGIN.click();
			return PageFactory.initElements(browser, SignInPage.class);
		}catch(Exception e){
		}
		return null;
	}
	
	public void clickLoginWithFacebook(Logger log){
		UtilFunctions.writeLog(log, "Starting function clickLoginWithFacebook in SignInPage.class");
		try{		
			btnFacebookLogin.click();
		}catch(Exception e){
			UtilFunctions.writeLog(log, Constants.ERROR+"-"+e.getMessage());
		}
		UtilFunctions.writeLog(log, "Ending function clickLoginWithFacebook in SignInPage.class");
	}
	
	public GmailLoginPage1 clickLoginWithGoogle(Logger log, WebDriver browser){
		UtilFunctions.writeLog(log, "Starting function clickLoginWithGoogle in SignInPage.class");
		try{
			btnGoogleLogin.click();
		}catch(Exception e){
			UtilFunctions.writeLog(log, "Ending function clickLoginWithGoogle in SignInPage.class with "+Constants.ERROR+"-"+e.getMessage());
			return null;
		}
		UtilFunctions.writeLog(log, "Ending function clickLoginWithGoogle in SignInPage.class");
		return PageFactory.initElements(browser, GmailLoginPage1.class);
	}
	
	public void doLogin(Logger log, Hashtable <String, String> testDataRecord){
		try{
			UtilFunctions.writeLog(log, "Starting doLogin function in SignInPage.class");
			txtEmailAddress.sendKeys(testDataRecord.get("Email Address"));
			txtPassword.sendKeys(testDataRecord.get("Password"));
			btnLOGIN.click();
			UtilFunctions.writeLog(log, "Ending doLogin function in SignInPage.class");
		}catch(Exception e){
			UtilFunctions.writeLog(log, "Ending doLogin function in SignInPage.class with "+Constants.ERROR+"-"+e.getMessage());
		}
	}
	
	public String doLogin(Logger log, WebDriver browser, String userName, String password){
		UtilFunctions.writeLog(log, "Starting doLogin function in SignInPage.class");
		try{
			txtEmailAddress.sendKeys(userName);
			txtPassword.sendKeys(password);
			btnLOGIN.click();
			HomePage homePage = PageFactory.initElements(browser, HomePage.class);
			if(homePage.lblCustomerName.isDisplayed()){
					UtilFunctions.writeLog(log, "Ending doLogin function in SignInPage.class with "+Constants.PASS);
					return Constants.PASS;
				}else{
					UtilFunctions.writeLog(log, "Ending doLogin function in SignInPage.class with "+Constants.PASS+" - Login failed");
					return Constants.FAIL+" - Login failed";
			}
		}catch(Exception e){
			UtilFunctions.writeLog(log, "Ending doLogin function in SignInPage.class with "+Constants.ERROR+" - "+e.getMessage());
			return Constants.ERROR;
		}
	}
	
	public String verifyAlertForInvalidEmail(String alertMessage){
		try{
			if(lblEnterValidEmailAddress.getText().equals(alertMessage)){
				return " restricted with expected alert message "+alertMessage+" - "+Constants.PASS;
			}else{
				return Constants.FAIL+" - Expected alert message is "+alertMessage+" Actual alert message displayed was "+lblEnterValidEmailAddress.getText();
			}
		}catch(Exception e){
			return Constants.ERROR+" - "+e.getMessage();
		}
	}
	
	public String verifyAlertForInvalidPassword(WebDriver browser, String alertMessage){
		PageFactory.initElements(browser, SignInPage.class);
		try{
			String actualText = lblEnterValidPassword.getText();
			if(actualText.equals(alertMessage)){
				return " restricted with expected alert message "+alertMessage+" - "+Constants.PASS;
			}else{
				return Constants.FAIL+" - Expected alert message is "+alertMessage+" Actual alert message displayed was "+actualText;
			}
		}catch(Exception e){
			return Constants.ERROR+" - "+e.getMessage();
		}
	}
	
	public String verifyAlertForInvalidCredentials(WebDriver browser, String alertMessage){
		PageFactory.initElements(browser, SignInPage.class);
		try{
			String actualText = lblInvalidCredentials.getText();
			if(actualText.equals(alertMessage)){
				return " restricted with expected alert message "+alertMessage+" - "+Constants.PASS;
			}else{
				return Constants.FAIL+" - Expected alert message is "+alertMessage+" Actual alert message displayed was "+actualText;
			}
		}catch(Exception e){
			return Constants.ERROR+" - "+e.getMessage();
		}
	}

	public ResetPasswordPage clickForgotPassword(Logger log, WebDriver browser) {
		try{
			UtilFunctions.writeLog(log, "Starting function clickForgotPassword in ResetPasswordPage.class");
			linkForgotPassword.click();
			UtilFunctions.writeLog(log, "Ending function clickForgotPassword in ResetPasswordPage.class");
			return PageFactory.initElements(browser, ResetPasswordPage.class);
		}catch(Exception e){
			UtilFunctions.writeLog(log, "Ending function clickForgotPassword in ResetPasswordPage.class with "+Constants.ERROR+"-"+e.getMessage());
			return null;
		}
	}
	
	public String verifyCodeNotSent(Logger log, String expectedMessage){
		UtilFunctions.writeLog(log, "Starting function verifyCodeNotSent in SignInPage.class");
		try{
			String actualMessage = lblEnterEmail.getAttribute("textContent");
			if(actualMessage.trim().equals(expectedMessage.trim())){
				return Constants.PASS;
			}else{
				return Constants.FAIL+" - Expected alert message is "+expectedMessage+". But actual alert message displayed was "+actualMessage;
			}
		}catch(Exception e){
			UtilFunctions.writeLog(log, "Ending function verifyCodeNotSent in SignInPage.class with "+Constants.ERROR+"-"+e.getMessage());
		}
		return null;
	}
}
