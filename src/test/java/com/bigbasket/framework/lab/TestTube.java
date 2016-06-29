package com.bigbasket.framework.lab;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

public class TestTube {

	public static void main(String[] args) {
		WebDriver browser = new FirefoxDriver();
		browser.navigate().to("http://www.bigbasket.com");
		try{
			browser.findElement(By.xpath("//*[@id='basket_menu']/ul/li[1]/a")).click();
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		try{
			List<WebElement> productResults = browser.findElements(By.xpath("//*[contains(@id,'product')][not(contains(@style,'display:none'))]/div[2]/span[2]/a"));
			List<WebElement> qtyResults = browser.findElements(By.xpath("//*[contains(@id,'product')][not(contains(@style,'display:none'))]/div[4]/div[2]/div/div[3]"));
			
			System.out.println(productResults.size());
			System.out.println(qtyResults.size());
			for(int i=0;i<productResults.size();i++){
				System.out.println(productResults.get(i).getText());
				System.out.println(qtyResults.get(i).getText());
				System.out.println(qtyResults.get(i).toString());
				//qtyResults.get(i).clear();
				//qtyResults.get(i).sendKeys("2");
			}
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		
		
		browser.quit();
	}

}
