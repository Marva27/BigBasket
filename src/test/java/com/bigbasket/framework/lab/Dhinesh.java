package com.bigbasket.framework.lab;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

public class Dhinesh {

	public static void main(String[] args) {
		List<WebElement> allLinks;
		WebDriver browser = new FirefoxDriver();
		browser.get("http://www.amazon.in");
		allLinks = browser.findElements(By.xpath("//*[@id='nav-flyout-shopAll']/div[2]/span/span"));
		for(int i=0;i<allLinks.size();i++){
			System.out.println(allLinks.get(i).getAttribute("textContent"));
		}
		browser.quit();

	}

}
