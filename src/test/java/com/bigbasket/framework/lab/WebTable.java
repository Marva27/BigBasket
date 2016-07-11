package com.bigbasket.framework.lab;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.Test;

public class WebTable {
	
	@FindAll(@FindBy(xpath="[@id='content']/table/thead/tr/th"))
	List<WebElement> tableHeaders;
	
	@Test
	public void testTables(){
		try{
			System.setProperty("webdriver.chrome.driver", System.getenv("user.dir")+"//src//main//resources//chromedriver.exe");
			WebDriver browser = new ChromeDriver();
			browser.get("http://toolsqa.com/automation-practice-table/");
			PageFactory.initElements(browser, this);
			for(WebElement eachHeader:tableHeaders){
				System.out.println(eachHeader.getText());
			}
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
	}

}
