package com.bigbasket.framework.lab;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class JavaScriptExecutor {
	
	WebDriver browser;
	WebElement currentElement;
	WebDriverWait wait;
	
	@BeforeClass
	public void connectURL(){
		browser = new FirefoxDriver();
		browser.get("http://www.bigbasket.com");
		browser.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		browser.manage().window().maximize();
	}
	
	@Test
	public void clickButton(){
		currentElement = browser.findElement(By.xpath("//*[@id='skip_explore']"));
		wait = new WebDriverWait(browser, 30);
		wait.until(ExpectedConditions.visibilityOf(currentElement));
		((JavascriptExecutor)browser).executeScript("arguments[0].click()",currentElement);
	}
	
	@Test
	public void scrollToBottom(){
		((JavascriptExecutor)browser).executeScript("window.scrollTo(0, document.body.scrollHeight)");
	}
	
	@AfterClass
	public void quitBrowser(){
		browser.quit();
	}

}
