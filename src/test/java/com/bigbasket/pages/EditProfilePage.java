package com.bigbasket.pages;

import java.util.Hashtable;

import org.openqa.selenium.Alert;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.bigbasket.framework.util.Constants;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class EditProfilePage {
	
	WebDriver browser;
	
	@FindBy(id="id_submit_edit_form")
	WebElement btnSaveChanges;
	@FindBy(xpath="//*[@id='frmuprof']/div[4]/a")
	WebElement btnCancel;
	
	@FindBy(xpath="//*[@id='id_news_status']")
	WebElement chkNewsStatus;
	
	@FindBy(xpath="//*[@id='datepicker']")
	WebElement dpDOB;
	
	@FindBy(xpath="//*[@id='frmuprof']/fieldset[1]/div[2]/span[2]")
	WebElement lblSalutationAlert;
	@FindBy(xpath="//*[@id='frmuprof']/fieldset[1]/div[3]/span[2]")
	WebElement lblFirstNameAlert;
	@FindBy(xpath="//*[@id='frmuprof']/fieldset[1]/div[4]/span[2]")
	WebElement lblLastNameAlert;
	
	@FindBy(xpath="//*[@id='dk_container_id_salutation']/a/span")
	WebElement selSalutation;
	
	@FindBy(linkText="Select")
	WebElement salutationSelect;
	@FindBy(linkText="Mr.")
	WebElement salutationMr;
	@FindBy(linkText="Mrs.")
	WebElement salutationMrs;
	@FindBy(linkText="Ms.")
	WebElement salutationMs;
	
	@FindBy(xpath="//*[@id='id_first_name']")
	WebElement txtFirstName;
	@FindBy(xpath="//*[@id='id_last_name']")
	WebElement txtLastName;
	@FindBy(xpath="//*[@id='id_email']")
	WebElement txtEmail;
	@FindBy(xpath="//*[@id='id_mobile_no']")
	WebElement txtMobileNo;
	@FindBy(xpath="//*[@id='id_phone_no']")
	WebElement txtLandLineNo;
	
	public EditProfilePage editProfileDetails(WebDriver browser, Hashtable<String,String> testDataRecord, ExtentTest stepReporter, String stepName, String stepDesc){
		try{
			this.browser=browser;
			switch(testDataRecord.get("Salutation")){
			case "Mr.":
				selSalutation.click();
				salutationMr.click();
			break;
			case "Mrs.":
				selSalutation.click();
				salutationMr.click();
			break;
			case "Ms.":
				selSalutation.click();
				salutationMs.click();
			break;
			default:
			}
			txtFirstName.clear();
			txtFirstName.sendKeys(testDataRecord.get("First Name"));
			txtLastName.clear();
			txtLastName.sendKeys(testDataRecord.get("Last Name"));
			txtEmail.clear();
			txtEmail.sendKeys(testDataRecord.get("New Email Address"));
			dpDOB.clear();
			dpDOB.sendKeys(testDataRecord.get("DOB"));
			txtMobileNo.clear();
			txtMobileNo.sendKeys(testDataRecord.get("Mobile Number"));
			txtLandLineNo.clear();
			txtLandLineNo.sendKeys(testDataRecord.get("Landline Number"));
			if(testDataRecord.get("News Update").equalsIgnoreCase(Constants.NO)){
				if(chkNewsStatus.isSelected()){
					chkNewsStatus.click();
				}
			}
			if(testDataRecord.get("Action").equalsIgnoreCase(Constants.SAVE)){
				btnSaveChanges.sendKeys(Keys.ENTER);
				stepReporter.log(LogStatus.PASS, stepName, stepDesc);
				return PageFactory.initElements(browser, EditProfilePage.class);
			}else if(testDataRecord.get("Action").equalsIgnoreCase(Constants.CANCEL)){
				stepReporter.log(LogStatus.PASS, stepName, stepDesc);
				return PageFactory.initElements(browser, EditProfilePage.class);
			}
			return null;
		}catch(Exception e){
			stepReporter.log(LogStatus.ERROR, stepName, e.getMessage());
			return PageFactory.initElements(browser, EditProfilePage.class);
		}
	}
	
	public String verifyProfileChangesMade(WebDriver browser, Hashtable <String, String> testDataRecord){
		try{
			this.browser=browser;
			switch(testDataRecord.get("Case")){
			case "CASE_INVALID_MOBILE":
				Alert alert = browser.switchTo().alert();
				String actualAlertMessage = alert.getText();
				if(actualAlertMessage.equals(testDataRecord.get("Alert Message"))){
					alert.accept();
					return "Update profile details restricted because of an expected alert message "
							+ actualAlertMessage + " - " + Constants.PASS;
				}else{
					return "Update profile details restricted but expected alert message is " +
							testDataRecord.get("Alert Message") + " but actual alert message displayed was " +
							actualAlertMessage + " - " + Constants.FAIL;
				}
			}
		}catch(Exception e){
			return Constants.ERROR + " - " +e.getMessage();
		}
		return null;
	}

}
