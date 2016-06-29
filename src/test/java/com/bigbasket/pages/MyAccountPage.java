package com.bigbasket.pages;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.bigbasket.framework.util.Constants;
import com.bigbasket.framework.util.UtilFunctions;


public class MyAccountPage {
	
	@FindBy(xpath="html/body/div[17]/div[1]/div[2]/div[2]/span[2]/p")
	WebElement lblCustomerName;
	
	@FindBy(xpath="//*[@id='member_logout']/span")
	WebElement linkLogout;
	@FindBy(xpath="//*[@id='link-personal-details']")
	WebElement linkPersonalDetails;
	@FindBy(xpath="//*[@id='link-edit-profile']")
	WebElement linkEditProfile;
	@FindBy(xpath="//*[@id='link-delivery-addresses']")
	WebElement linkDeliveryAddresses;
	@FindBy(xpath="//*[@id='link-change-passwd']")
	WebElement linkChangePassword;
	@FindBy(xpath="//*[@id='email-address']")
	WebElement linkEmailAddresses;
	@FindBy(xpath="//*[@id='link-my-orders']")
	WebElement linkMyOrders;
	@FindBy(xpath="//*[@id='link-my-wallet']")
	WebElement linkMyWallet;
	@FindBy(linkText="Refer Friends to BigBasket")
	WebElement linkReferFriends;
	@FindBy(linkText="Locate On Map")
	WebElement linkLocateOnMap;
	@FindBy(xpath="//*[@id='link-view-notifications']")
	WebElement linkAlertsAndNotifications;
	
	public EditProfilePage clickEditProfileLink(WebDriver browser){
		try{
			linkEditProfile.click();
			return PageFactory.initElements(browser, EditProfilePage.class);
		}catch(Exception e){
			return null;
		}
	}
	
	public HomePage logoutFromAccount(Logger log, WebDriver browser){
		UtilFunctions.writeLog(log, "Starting function logoutFromAccount in MyAccountPage.class");
		try{
			Actions actions = new Actions(browser);
			actions.moveToElement(lblCustomerName).perform();
			Thread.sleep(1000);
			actions.moveToElement(linkLogout).click().build().perform();
			UtilFunctions.writeLog(log, "Ending function logoutFromAccount in MyAccountPage.class");
			return PageFactory.initElements(browser, HomePage.class);
		}catch(Exception e){
			UtilFunctions.writeLog(log, "Ending function logoutFromAccount in MyAccountPage.class with "+Constants.ERROR+"-"+e.getMessage());
			return null;
		}
	}
	
	public ChangePasswordPage clickChangePasswordLink(Logger log, WebDriver browser){
		UtilFunctions.writeLog(log, "Starting function clickChangePasswordLink in ChangePasswordPage.class");
		try{
			linkChangePassword.click();
			UtilFunctions.writeLog(log, "Ending function clickChangePasswordLink in ChangePasswordPage.class");
			return PageFactory.initElements(browser, ChangePasswordPage.class);
		}catch(Exception e){
			UtilFunctions.writeLog(log, "Ending function clickChangePasswordLink in ChangePasswordPage.class with "+Constants.ERROR+"-"+e.getMessage());
			return null;
		}
	}
	
	public EmailAddressesPage clickEmailAddressesLink(Logger log, WebDriver browser){
		UtilFunctions.writeLog(log, "Starting function clickEmailAddressesLink in ChangePasswordPage.class");
		try{
			linkEmailAddresses.click();
			UtilFunctions.writeLog(log, "Ending function clickEmailAddressesLink in ChangePasswordPage.class");
			return PageFactory.initElements(browser, EmailAddressesPage.class);
		}catch(Exception e){
			UtilFunctions.writeLog(log, "Ending function clickEmailAddressesLink in ChangePasswordPage.class with "+Constants.ERROR+"-"+e.getMessage());
			return null;
		}
	}
}
