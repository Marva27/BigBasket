package com.bigbasket.framework.lab;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.Test;

public class JavaScriptExecutor {
	
	@FindBy(xpath="//*[@id='skip_explore']")
	WebElement btnSkipAndExplore;
	
	@FindBy(xpath="html/body/footer/div[1]/div[1]/h4")
	WebElement h4BigBasket;
	
	@Test
	public void javaScriptExecutor() throws MalformedURLException{
		WebDriver browser;
		DesiredCapabilities cap = null;
		cap = DesiredCapabilities.chrome();
		cap.setBrowserName("chrome");
		cap.setPlatform(org.openqa.selenium.Platform.MAC);
		browser = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), cap);
		browser.get("http://www.bigbasket.com");
		browser.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		browser.manage().window().maximize();
		PageFactory.initElements(browser, this);
		try{
			((JavascriptExecutor)browser).executeScript("arguments[0].click();",btnSkipAndExplore);
			((JavascriptExecutor)browser).executeScript("arguments[0].scrollIntoView();", h4BigBasket);
			((JavascriptExecutor)browser).executeScript("window.scrollBy(0,-250)", "");
			((JavascriptExecutor)browser).executeScript("window.scrollTo(0,document.body.scrollHeight)");
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		browser.quit();
	}

}
