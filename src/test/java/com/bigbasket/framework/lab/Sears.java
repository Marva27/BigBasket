package com.bigbasket.framework.lab;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Sears {

	public static void main(String[] args) {
		WebDriver browser = new FirefoxDriver();
		browser.get("http://www.sears.com/en_us.html");
		browser.manage().window().maximize();
		browser.findElement(By.xpath("//*[@id='gnf_01_tree_item_5']/span/a")).click();
		WebElement currentElement = browser.findElement(By.xpath("//*[@id='myProfiles']/div"));
		Actions actions = new Actions(browser);
		actions.moveToElement(currentElement).build().perform();
		browser.findElement(By.xpath("//*[@id='subnavDD_myProfile']/ul/li[1]/p/a")).click();
		
		try{
			WebElement formElement = browser.findElement(By.id("loginFormDisplay"));
			currentElement = formElement.findElement(By.xpath("//*[@id='email']"));
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		WebDriverWait wait = new WebDriverWait(browser, 30);
		wait.until(ExpectedConditions.visibilityOf(currentElement));
		currentElement.sendKeys("srinimarva@gmail.com");
		browser.quit();

	}

}
