package com.bigbasket.pages;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.bigbasket.framework.util.Constants;

public class ResetPasswordPage {
	
	@FindBy(xpath="//*[@id='ResetPassword']/fieldset/div[1]")
	WebElement lblResetPassword;
	
	@FindBy(xpath="//*[@id='success_msg']")
	WebElement lblSuccessMessage;
	
	public String verifyCodeSent(Logger log, String emailAddress){
		String expectedMessage = "Verification code has been sent to your email "+ emailAddress +" and registered mobile number";
		try{
			if(expectedMessage.equals(lblSuccessMessage.getText())){
				return Constants.PASS;
			}else{
				return Constants.FAIL;
			}
		}catch(Exception e){
			return Constants.ERROR;
		}
	}

}
