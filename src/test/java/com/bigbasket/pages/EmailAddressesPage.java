package com.bigbasket.pages;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.bigbasket.framework.util.Constants;
import com.bigbasket.framework.util.UtilFunctions;

public class EmailAddressesPage {
	
	Date date = new Date();
	
	@FindBy(xpath="//*[@id='alert-msg']")
	WebElement alertEmailSuccess;
	
	@FindBy(xpath="html/body/div[20]/div[5]/div[2]/div[1]/div[2]/a")
	WebElement btnAddEmailAddress;
	@FindBy(xpath="//*[@id='add-email']/div/div/div[3]/div[1]/input")
	WebElement btnSaveChanges;
	@FindBy(xpath="//*[@id='add-email']/div/div/div[3]/div[2]/a")
	WebElement btnCancel;
	@FindBy(xpath="//*[@id='ok_button']")
	WebElement btnOK;
	@FindBy(xpath="//*[@id='cancel_button']")
	WebElement btnAlertCancel;
	
	@FindBy(xpath="html/body/div[20]/div[5]/div[2]/div[1]/div[1]")
	WebElement h1EmailAddresses;
	
	@FindBy(xpath="//*[@id='email_error']")
	WebElement lblEmailError;
	
	@FindBy(xpath="//*[@id='new-email-id']")
	WebElement txtEmailAddress;
	
	@FindAll({@FindBy(xpath="//*[contains(@id,'_address')]")})
	List<WebElement> lblEmailAddresses;
	@FindAll({@FindBy(xpath="//*[contains(@id,'row')]/td[4]/a")})
	List<WebElement> btnRemove;
	
	WebElement lblCurrentEmail;
	
	public String verifyEmailAddressesPage(Logger log){
		try{
			if(h1EmailAddresses.isDisplayed()){
				return Constants.PASS;
			}else{
				return Constants.FAIL+" - Email Addresses page NOT displayed";
			}
		}catch(Exception e){
			return Constants.ERROR+" - "+e.getMessage();
		}
	}
	
	public void addEmailAddress(Logger log, String emailAddress){
		UtilFunctions.writeLog(log, "Starting function addEmailAddress in EmailAddressesPage.class");
		try{
			btnAddEmailAddress.click();
			txtEmailAddress.sendKeys(emailAddress);
		}catch(Exception e){
			UtilFunctions.writeLog(log, e.getMessage());
		}
		UtilFunctions.writeLog(log, "Ending function addEmailAddress in EmailAddressesPage.class");
	}
	
	public EmailAddressesPage clickSave(Logger log, WebDriver browser){
		UtilFunctions.writeLog(log, "Starting function clickSave in EmailAddressesPage.class");
		try{
			btnSaveChanges.click();
			Thread.sleep(1000);
			SimpleDateFormat formatter = new SimpleDateFormat("MM dd, yyyy hh:mm a");
			TimeZone tzAsiaCalcutta = TimeZone.getTimeZone("Asia/Calcutta");
			formatter.setTimeZone(tzAsiaCalcutta);
		}catch(Exception e){
			UtilFunctions.writeLog(log, e.getMessage());
		}
		UtilFunctions.writeLog(log, "Ending function clickSave in EmailAddressesPage.class");
		return PageFactory.initElements(browser, EmailAddressesPage.class);
	}
	
	public String verifyAlertForEmail(Logger log, String alertMessage){
		UtilFunctions.writeLog(log, "Starting verifyAlertForEmail function in EmailAddressesPage.class");
		try{
			String actualText = lblEmailError.getText();
			if(actualText.equals(alertMessage)){
				UtilFunctions.writeLog(log, "Ending verifyAlertForEmail function in EmailAddressesPage.class with "+Constants.PASS);
				return Constants.PASS;
			}else{
				UtilFunctions.writeLog(log, "Ending verifyAlertForEmail function in EmailAddressesPage.class with "+Constants.FAIL);
				return Constants.FAIL +" - "+"Expected alert message is "+alertMessage+
						" Actual alert message displayed was "+actualText;
			}
		}catch(Exception e){
			UtilFunctions.writeLog(log, "Ending verifyAlertForEmail function in EmailAddressesPage.class with "+Constants.ERROR+"-"+e.getMessage());
			return Constants.ERROR+" - Please check logs";
		}
	}
	
	public String verifyAlertForEmailSuccess(Logger log, String alertMessage){
		UtilFunctions.writeLog(log, "Starting function verifyAlertForEmailSuccess in EmailAddressesPage.class");
		try{
			String actualText = alertEmailSuccess.getText();
			if(actualText.equals(alertMessage)){
				UtilFunctions.writeLog(log, "Ending function verifyAlertForEmailSuccess in EmailAddressesPage.class with "+Constants.PASS);
				return Constants.PASS;
			}else{
				UtilFunctions.writeLog(log, "Ending function verifyAlertForEmailSuccess in EmailAddressesPage.class with "+Constants.FAIL);
				return Constants.FAIL +" - "+"Expected alert message is "+alertMessage+
						" Actual alert message displayed was "+actualText;
			}
			}catch(Exception e){
				UtilFunctions.writeLog(log, "Ending function verifyAlertForEmailSuccess in EmailAddressesPage.class with "+Constants.ERROR+"-"+e.getMessage());
				return Constants.ERROR+" - Please check logs";
		}
	}
	
	public boolean locateEmailAddress(Logger log, String emailAddress){
		UtilFunctions.writeLog(log, "Starting function locateEmailAddress in EmailAddressesPage.class");
		boolean emailFound = false;
		try{
			for(WebElement lblEmail: lblEmailAddresses){
				if(lblEmail.getText()!=""){
					if(lblEmail.getText().equals(emailAddress)){
						lblCurrentEmail = lblEmail;
						emailFound = true;
						break;
					}
				}				
			}
		}catch(Exception e){
			UtilFunctions.writeLog(log, "Ending function locateEmailAddress in EmailAddressesPage.class with "+Constants.ERROR+"-"+e.getMessage());
		}
		UtilFunctions.writeLog(log, "Ending function locateEmailAddress in EmailAddressesPage.class");
		return emailFound;
	}
	
	public String verifyNewEmailAddress(Logger log, String emailAddress){
		UtilFunctions.writeLog(log, "Starting function verifyNewEmailAddress in EmailAddressesPage.class");
		if(locateEmailAddress(log, emailAddress)){
			UtilFunctions.writeLog(log, "Ending function verifyNewEmailAddress in EmailAddressesPage.class with "+Constants.PASS);
			return Constants.PASS;
		}else{
			UtilFunctions.writeLog(log, "Ending function verifyNewEmailAddress in EmailAddressesPage.class with "+Constants.FAIL);
			return Constants.FAIL;
		}
	}

	public EmailAddressesPage clickOK(Logger log, WebDriver browser) {
		UtilFunctions.writeLog(log, "Starting function clickOK in EmailAddressesPage.class");
		try{
			btnOK.click();
			UtilFunctions.writeLog(log, "Ending function clickOK in EmailAddressesPage.class");
			return PageFactory.initElements(browser, EmailAddressesPage.class);
		}catch(Exception e){
			UtilFunctions.writeLog(log, "Ending function clickOK in EmailAddressesPage.class with "+Constants.ERROR+"-"+e.getMessage());
			return null;
		}
	}
	
	public String removeEmailAddress(Logger log, WebDriver browser, String decision){
		UtilFunctions.writeLog(log, "Starting function removeEmailAddress in EmailAddressesPage.class");
		try{
			btnRemove.get(0).click();
			if(decision.equals("OK")){
				btnOK.click();
				Thread.sleep(1000);
				PageFactory.initElements(browser, EmailAddressesPage.class);
				if(lblEmailAddresses.size()<=1){
					return Constants.PASS;
				}else{
					return Constants.FAIL;
				}
			}else if(decision.equals("CANCEL")){
				btnAlertCancel.click();
				PageFactory.initElements(browser, EmailAddressesPage.class);
				if(lblEmailAddresses.size()>1){
					return Constants.PASS;
				}else{
					return Constants.FAIL;
				}
			}
		}catch(Exception e){
			System.out.println(e.getMessage());
			UtilFunctions.writeLog(log, "Ending function removeEmailAddress in EmailAddressesPage.class with "+Constants.ERROR+"-"+e.getMessage());
		}
		return Constants.FAIL;
	}
}
