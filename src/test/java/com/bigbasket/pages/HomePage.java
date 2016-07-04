package com.bigbasket.pages;

import java.util.Hashtable;
import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.bigbasket.framework.util.Constants;
import com.bigbasket.framework.util.UtilFunctions;

public class HomePage {
	
	@FindBy(xpath="//*[@id='skip_explore']")
	WebElement btnSkipAndExplore;
	
	@FindBy(xpath="html/body/div[18]/div[1]/div[2]/div[2]/span[2]/p")
	WebElement lblCustomerName;
	
	@FindBy(linkText="LOGIN")
	WebElement linkLOGIN;
	@FindBy(linkText="SIGN UP")
	WebElement linkSIGNUP;
	
	@FindBy(xpath="//*[@id='member_account']")
	WebElement mnuMyAccount;
	
	@FindAll({@FindBy(xpath=".//*[@id='basket_menu']/ul/li/a")})
	List<WebElement> mnuMainCategory;
	
	@FindAll({@FindBy(xpath="//*[contains(@id,'menu-')][not(contains(@style,'display:none'))]/div/div/ul/li/a")})
	List<WebElement> mnuSubCategory1;
	
	public String clickSkipAndExplore(Logger log){
		UtilFunctions.writeLog(log, "Starting function clickSkipAndExplore in HomePage.class");
		try{
			if(btnSkipAndExplore.isDisplayed()){
				btnSkipAndExplore.click();
				UtilFunctions.writeLog(log, "Ending function clickSkipAndExplore in HomePage.class with "+Constants.PASS);
				return Constants.PASS;
			}
		}catch(Exception e){
			UtilFunctions.writeLog(log, "Ending function clickSkipAndExplore in HomePage.class with "+Constants.ERROR+" - "+e.getMessage());
			return Constants.ERROR+" - "+e.getMessage();
		}
		return null;
	}
	
	public String clickLOGINLink(Logger log){
		UtilFunctions.writeLog(log, "Starting function clickLOGINLink in HomePage.class");
		try{
			linkLOGIN.click();
			UtilFunctions.writeLog(log, "Ending function clickLOGINLink in HomePage.class with "+Constants.PASS);
			return Constants.PASS;
		}catch(Exception e){
			UtilFunctions.writeLog(log, "Ending function clickLOGINLink in HomePage.class with "+Constants.ERROR+" - "+e.getMessage());
			return Constants.ERROR+" - "+e.getMessage();
		}
	}
	
	public String clickSIGNUPLink(Logger log){
		UtilFunctions.writeLog(log, "Starting function clickSIGNUPLink in HomePage.class");
		try{
			linkSIGNUP.click();
			UtilFunctions.writeLog(log, "Ending function clickSIGNUPLink in HomePage.class with "+Constants.PASS);
			return Constants.PASS;
		}catch(Exception e){
			UtilFunctions.writeLog(log, "Ending function clickSIGNUPLink in HomePage.class with "+Constants.ERROR+" - "+e.getMessage());
			return Constants.ERROR+" - "+e.getMessage();
		}
	}
	
	public String verifyIfLoginSuccess(Logger log, WebDriver browser, String customerName){
		try{
			UtilFunctions.writeLog(log, "Starting function verifyIfLoginSuccess in HomePage.class");
			if(browser.getCurrentUrl().contains("login")){
				SignInPage signInPage = PageFactory.initElements(browser, SignInPage.class);
				if(signInPage.isInvalidCredentials()){
					UtilFunctions.writeLog(log, "Ending function verifyIfLoginSuccess in HomePage.class "+Constants.FAIL+" - Incorrect credentials");
					return Constants.FAIL+" - Incorrect credentials";
				}else{
					if(lblCustomerName.getText().equalsIgnoreCase(customerName)){
						UtilFunctions.writeLog(log, "Ending function verifyIfLoginSuccess in HomePage.class "+Constants.PASS);
						return " - " + Constants.PASS;
					}else{
						UtilFunctions.writeLog(log, "Ending function verifyIfLoginSuccess in HomePage.class "+Constants.FAIL+" - Expected Customer Name is "+customerName+" Actual Customer Name displayed was "+lblCustomerName.getText());
						return Constants.FAIL+" - Expected Customer Name is "+customerName+" Actual Customer Name displayed was "+lblCustomerName.getText(); 
					}
				}	
			}else{
				if(lblCustomerName.getText().equalsIgnoreCase(customerName)){
					UtilFunctions.writeLog(log, "Ending function verifyIfLoginSuccess in HomePage.class "+Constants.PASS);
					return " - " + Constants.PASS;
				}else{
					UtilFunctions.writeLog(log, "Ending function verifyIfLoginSuccess in HomePage.class "+Constants.FAIL+" - Expected Customer Name is "+customerName+" Actual Customer Name displayed was "+lblCustomerName.getText());
					return Constants.FAIL+" - Expected Customer Name is "+customerName+" Actual Customer Name displayed was "+lblCustomerName.getText(); 
				}
			}
		}catch(Exception e){
			UtilFunctions.writeLog(log, "Ending function verifyIfLoginSuccess in HomePage.class "+Constants.ERROR+"-"+e.getMessage());
			return Constants.ERROR+" - "+e.getMessage();
		}
	}
	
	public MyAccountPage selectMyAccount(Logger log, WebDriver browser){
		UtilFunctions.writeLog(log, "Starting function selectMyAccount in MyAccountPage.class");
		try{
			Actions actions = new Actions(browser);
			actions.moveToElement(lblCustomerName).perform();
			actions.moveToElement(mnuMyAccount).click().build().perform();
			UtilFunctions.writeLog(log, "Ending function selectMyAccount in MyAccountPage.class");
			return PageFactory.initElements(browser, MyAccountPage.class);
		}catch(Exception e){
			UtilFunctions.writeLog(log, "Ending function selectMyAccount in MyAccountPage.class with "+Constants.ERROR+"-"+e.getMessage());
			return null;
		}
	}
	
	public void selectCategory(Logger log, WebDriver browser, Hashtable<String, String> testDataRecord){
		try{
			Actions actions = new Actions(browser);
			for(WebElement mainMenuElement: mnuMainCategory){
				if(testDataRecord.get("MainCategory").equals(mainMenuElement.getText())){
					actions.moveToElement(mainMenuElement).build().perform();
					PageFactory.initElements(browser, HomePage.class);
					System.out.println(mnuSubCategory1.size());
					for(WebElement subMenu1Element: mnuSubCategory1){
						System.out.println(subMenu1Element.getText());
					}
				}
			}
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
	}

}
