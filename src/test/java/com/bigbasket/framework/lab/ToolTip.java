package com.bigbasket.framework.lab;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.Test;

public class ToolTip {
	
	WebDriver browser;
	
	@FindBy(xpath="//*[@id='skip_explore']")
	WebElement btnSkipAndExplore;
	
	@FindBy(xpath="//*[@id='id_q']")
	WebElement txtSearch;
	
	@FindBy(xpath="//*[@id='auto_search']/div/form/input[2]")
	WebElement btnSearch;
	
	@FindAll({@FindBy(xpath="//*[contains(@id,'product')][not(contains(@style,'display:none'))]/div/span[2]/span")})
	List<WebElement> lblProductToolTip;
	
	@Test
	public void printToolTipText(){
		browser = new FirefoxDriver();
		browser.get("http://www.bigbasket.com");
		browser.manage().window().maximize();
		browser.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		PageFactory.initElements(browser, this);
		btnSkipAndExplore.click();
		txtSearch.sendKeys("Fruits");
		btnSearch.click();
		browser.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		PageFactory.initElements(browser, this);
		
		for(WebElement currentElement:lblProductToolTip){
			System.out.println(currentElement.getAttribute("textContent"));
		}
		
		browser.quit();
	}

}
