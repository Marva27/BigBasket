package com.bigbasket.framework.lab;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class FirefoxTest {

	public static void main(String[] args) {
		WebDriver browser = new FirefoxDriver();
		browser.get("http://www.google.co.in");
		browser.quit();

	}

}
