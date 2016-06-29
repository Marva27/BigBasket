package com.bigbasket.pages;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.bigbasket.framework.util.Constants;
import com.bigbasket.framework.util.UtilFunctions;

public class ChangePasswordPage {
	
	@FindBy(xpath="//*[@id='update_passwd']")
	WebElement btnUpdate;
	@FindBy(xpath="//*[@id='change_passwd']/div[2]/div[2]/a")
	WebElement btnCancel;
	
	@FindBy(xpath="//*[@id='change_passwd']/fieldset/div[1]")
	WebElement h1ChangePassword;
	
	@FindBy(xpath="//*[@id='change_passwd']/fieldset/div[2]/div/span[3]")
	WebElement lblIncorrectPassword;
	@FindBy(xpath="//*[@id='change_passwd']/fieldset/div[4]/div/span[1]/label")
	WebElement lblFieldRequired;
	@FindBy(xpath="//*[@id='change_passwd']/fieldset/div[3]/div/div/div[1]")
	WebElement lblPasswordRequirement;
	@FindBy(xpath="//*[@id='change_passwd']/fieldset/div[4]/div/span[3]")
	WebElement lblPasswordMismatch;
	@FindBy(xpath="html/body/div[19]/div[3]/div/ul/li")
	WebElement lblUpdatePassword;

	@FindBy(xpath="//*[@id='id_old_password']")
	WebElement txtOldPassword;
	@FindBy(xpath="//*[@id='id_new_password']")
	WebElement txtNewPassword;
	@FindBy(xpath="//*[@id='id_password2']")
	WebElement txtConfirmPassword;
	
	public void enterOldPassword(String oldPassword){
		try{
			txtOldPassword.clear();
			txtOldPassword.sendKeys(oldPassword);
		}catch(Exception e){
		}
	}
	
	public void enterNewPassword(String newPassword){
		try{
			txtNewPassword.sendKeys(newPassword);
		}catch(Exception e){
		}
	}
	
	public void enterConfirmPassword(String confirmPassword){
		try{
			txtConfirmPassword.sendKeys(confirmPassword);
		}catch(Exception e){
		}
	}
	
	public String verifyChangePasswordPage(Logger log){
		UtilFunctions.writeLog(log, "Starting function verifyChangePasswordPage in ChangePasswordPage.class");
		try{
			if(h1ChangePassword.isDisplayed()){
				return Constants.PASS;
			}else{
				return Constants.FAIL+" - Change Password page NOT displayed";
			}
		}catch(Exception e){
			return Constants.ERROR+"-"+e.getMessage();
		}
	}
	
	public ChangePasswordPage updatePassword(Logger log, WebDriver browser, String oldPassword, String newPassword, String confirmPassword){
		UtilFunctions.writeLog(log, "Starting function updatePassword in ChangePasswordPage.class");
		try{
			enterOldPassword(oldPassword);
			enterNewPassword(newPassword);
			enterConfirmPassword(confirmPassword);
		}catch(Exception e){
			UtilFunctions.writeLog(log, "Ending function updatePassword in ChangePasswordPage.class with "+Constants.ERROR+"-"+e.getMessage());
			return null;
		}
		UtilFunctions.writeLog(log, "Ending function updatePassword in ChangePasswordPage.class");
		return clickUpdateButton(log, browser);
	}
	
	public ChangePasswordPage clickUpdateButton(Logger log, WebDriver browser){
		UtilFunctions.writeLog(log, "Starting function clickUpdateButton in ChangePasswordPage.class");
		try{
			btnUpdate.click();
			UtilFunctions.writeLog(log, "Ending function clickUpdateButton in ChangePasswordPage.class");
			return PageFactory.initElements(browser, ChangePasswordPage.class);
		}catch(Exception e){
			UtilFunctions.writeLog(log, "Ending function clickUpdateButton in ChangePasswordPage.class with "+Constants.ERROR+"-"+e.getMessage());
			return null;
		}
	}
	
	public String verifyAlertForFieldRequired(String alertMessage){
		try{
			if(lblFieldRequired.getText().equals(alertMessage)){
				return " restricted with expected alert message "+alertMessage+" - "+Constants.PASS;
			}else{
				return Constants.FAIL+" - Expected alert message is "+alertMessage+" Actual alert message displayed was "+lblFieldRequired.getText();
			}
		}catch(Exception e){
			return Constants.ERROR+" - "+e.getMessage();
		}
	}

	public String verifyAlertForPasswordRequirement(String alertMessage) {
		try{
			if(lblPasswordRequirement.getText().equals(alertMessage)){
				return " restricted with expected alert message "+alertMessage+" - "+Constants.PASS;
			}else{
				return Constants.FAIL+" - Expected alert message is "+alertMessage+" Actual alert message displayed was "+lblPasswordRequirement.getText();
			}
		}catch(Exception e){
			return Constants.ERROR+" - "+e.getMessage();
		}
	}

	public String verifyAlertForPasswordMismatch(String alertMessage) {
		try{
			String actualText = lblPasswordMismatch.getText().trim();
			if(actualText.equals(alertMessage)){
				return " restricted with expected alert message "+alertMessage+" - "+Constants.PASS;
			}else{
				return Constants.FAIL+" - Expected alert message is "+alertMessage+" Actual alert message displayed was "+lblPasswordMismatch.getText();
			}
		}catch(Exception e){
			return Constants.ERROR+" - "+e.getMessage();
		}
	}

	public String verifyAlertForPasswordChange(String alertMessage) {
		try{
			String actualText = lblUpdatePassword.getText().trim();
			if(actualText.equals(alertMessage)){
				return " - "+Constants.PASS;
			}else{
				return Constants.FAIL;
			}
		}catch(Exception e){
			return Constants.ERROR+" - "+e.getMessage();
		}
	}

}
