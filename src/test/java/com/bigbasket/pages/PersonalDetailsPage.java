package com.bigbasket.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class PersonalDetailsPage {
	
	@FindBy(xpath="html/body/div[19]/div[5]/div[2]/div[3]/div[2]/span/a")
	WebElement btnFundWallet;
	
	@FindBy(xpath="html/body/div[19]/div[5]/div[2]/div[1]/div")
	WebElement lblProfileDetails;
	
	@FindBy(xpath="html/body/div[19]/div[5]/div[2]/div[2]/div/div/span[2]/a")
	WebElement linkChangePassword;

}
