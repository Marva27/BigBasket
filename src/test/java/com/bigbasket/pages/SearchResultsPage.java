package com.bigbasket.pages;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.bigbasket.framework.util.Constants;
import com.bigbasket.framework.util.UtilFunctions;

public class SearchResultsPage {
	
	@FindBy(xpath="//*[@id='id_q']")
	WebElement txtSearchBox;
	
	@FindBy(xpath="//*[@id='filter_brands']")
	WebElement txtSearchBrand;
	
	@FindBy(xpath="//*[@id='auto_search']/div/form/input[2]")
	WebElement btnSearch;
	
	@FindBy(xpath="//*[@id='products-container']/div[1]")
	WebElement frmProductsContainer;
	
	@FindAll({@FindBy(xpath="//*[@id='filter_brands_list'][not(contains(@style,'display: none;'))]/div/div[1]/li/label")})
	List<WebElement> chkBrands;
	
	@FindAll({@FindBy(xpath="//*[@id='filter_prices_list']/li/label")})
	List<WebElement> chkPriceRange;
	
	@FindAll({@FindBy(xpath=".//*[@id='facetsContainer']/div[5]/ul/li/label")})
	List<WebElement> chkDiscounts;
	
	@FindAll({@FindBy(xpath="//*[contains(@id,'products-page')]")})
	List<WebElement> h3Pages;
	
	@FindBy(xpath="//*[@id='facetsContainer']/div[4]/h4")
	WebElement h4Price;
	
	@FindBy(xpath="//*[@id='facetsContainer']/div[5]/h4")
	WebElement h4Discounts;
	
	@FindAll({@FindBy(xpath="//*[contains(@id,'product')][not(contains(@style,'display:none'))]/div[4]/div[1]/div")})
	List<WebElement> lblPrice;
	
	@FindAll({@FindBy(xpath="//*[contains(@id,'product')][not(contains(@style,'display:none'))]/div[5]/div[1]/div[1]/span")})
	List<WebElement> lblOriginalPrice;
	
	@FindAll({@FindBy(xpath="//*[contains(@id,'product')][not(contains(@style,'display:none'))]/div[5]/div[1]/div[2]")})
	List<WebElement> lblOfferPrice;
	
	@FindAll({@FindBy(xpath="//*[contains(@id,'product')][not(contains(@style,'display:none'))]/div/span[2]/a")})
	List<WebElement> lblProductName;
	
	@FindAll({@FindBy(xpath="//*[contains(@id,'product')][not(contains(@style,'display:none'))]/div/span[2]/a/span")})
	List<WebElement> lblBrandName;
	
	@FindAll({@FindBy(className=".uiv2-tool-tip-hover ")})
	List<WebElement> ttProductName;
	
	public SearchResultsPage searchProduct(Logger log, WebDriver browser, String productName){
		UtilFunctions.writeLog(log, "Starting function searchProduct in SearchResultsPage.class");
		try{
			txtSearchBox.clear();
			txtSearchBox.sendKeys(productName);
			btnSearch.click();
			UtilFunctions.writeLog(log, "Ending function searchProduct in SearchResultsPage.class");
			return PageFactory.initElements(browser, SearchResultsPage.class);
		}catch(Exception e){
			UtilFunctions.writeLog(log, "Ending function searchProduct in SearchResultsPage.class with "+Constants.ERROR+"-"+e.getMessage());
			return null;
		}
	}
	
	/*public String verifySearchResults(WebDriver browser, String productName){
		System.out.println(lblProductName.size());
		try{
			Actions actions = new Actions(browser);
			for(WebElement eachElement:lblProductName){
				System.out.println(eachElement.getAttribute("href"));
				actions.moveToElement(eachElement).perform();
				//Actions action = new Actions(driver);
				actions.moveToElement(eachElement).build().perform();
				WebElement toolTipElement = browser.findElement(By.cssSelector(".uiv2-tool-tip-hover "));
				System.out.println("Tooltip text is "+toolTipElement.getAttribute("textContent"));
			}
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		return productName;	
	}*/
	
	public String verifySearchResults(String productName){
		boolean incorrectSearchResult = false;
		String incorrectSearchResultText = null;
		try{
			for(WebElement eachElement:lblProductName){
				if(!eachElement.getAttribute("href").contains(productName.toLowerCase())){
					incorrectSearchResult = true;
					incorrectSearchResultText=eachElement.getText();
					break;
				}
			}
		}catch(Exception e){
			return Constants.ERROR;
		}
		if(incorrectSearchResult){
			return Constants.FAIL+"- Incorrect search result "+incorrectSearchResultText;
		}else{
			return Constants.PASS;
		}
	}
	
	public String filterByBrands(WebDriver browser, String brands){
		String[] listOfBrands = brands.split(",");
		boolean brandNotFound = true;
		Actions actions = new Actions(browser);
		try{
			for(int i=0;i<listOfBrands.length;i++){
				actions.moveToElement(txtSearchBrand).click().build().perform();
				txtSearchBrand.clear();
				txtSearchBrand.sendKeys(listOfBrands[i]);
				System.out.println("No. of brands is "+chkBrands.size());
				for(WebElement eachElement:chkBrands){
					System.out.println("Current brand name is "+eachElement.getAttribute("textContent").trim());
					if(eachElement.getAttribute("textContent").trim().equals(listOfBrands[i])){
						brandNotFound = false;
						eachElement.click();
						Thread.sleep(2000);
						PageFactory.initElements(browser, this);
						break;
					}
					brandNotFound = true;
				}
				if(brandNotFound){
					return Constants.ERROR+"-"+listOfBrands[i]+" brand NOT found";
				}
			}
			return Constants.PASS;
		}catch(Exception e){
		}
		return brands;
	}
	
	public String verifyFilterByBrands(String brands){
		boolean filterWorks = true;
		String currentBrandName = null;
		try{
			for(WebElement eachElement: lblBrandName){
				currentBrandName = eachElement.getText();
				if(!brands.toLowerCase().contains(currentBrandName.toLowerCase())){
					filterWorks = false;
					break;
				}
			}
			if(filterWorks){
				return Constants.PASS;
			}else{
				return Constants.FAIL+"- Brand "+currentBrandName+" is out of filter range";
			}
		}catch(Exception e){
			return Constants.ERROR;
		}
	}
	
	public SearchResultsPage filterByPriceRange(Logger log, WebDriver browser, String priceRange){
		UtilFunctions.writeLog(log, "Starting function filterByPriceRange in SearchResultsPage.class");
		try{
			for(WebElement eachElement:chkPriceRange){
				UtilFunctions.writeLog(log, "Product price is "+eachElement.getText());
				if(eachElement.getText().contains(priceRange)){
					Actions actions = new Actions(browser);
					actions.moveToElement(h4Price).perform();
					actions.moveToElement(eachElement).perform();
					try{
						eachElement.click();
					}catch(Exception e){
						if(!eachElement.isSelected()){
							eachElement.click();
						}
					}
					WebDriverWait wait = new WebDriverWait(browser, 30);
					wait.until(ExpectedConditions.elementToBeClickable(frmProductsContainer));
					Thread.sleep(1000);
					browser.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
					UtilFunctions.writeLog(log, "Ending function filterByPriceRange in SearchResultsPage.class");
					return PageFactory.initElements(browser, SearchResultsPage.class);
				}
			}
		}catch(Exception e){
			UtilFunctions.writeLog(log, "Ending function filterByPriceRange in SearchResultsPage.class with "+Constants.ERROR+"-"+e.getMessage());
		}
		return null;
	}
	
	public String verifySearchResultsByPriceRange(Logger log, WebDriver browser, double lowPrice, double highPrice) throws InterruptedException{
		boolean filterWorks = false;
		double convertedProductPrice = 0.0;
		Thread.sleep(1000);
		UtilFunctions.writeLog(log, "Starting function verifySearchResultsByPriceRange in SearchResultsPage.class");
		try{
			for(WebElement eachElement:lblPrice){
				Actions actions = new Actions(browser);
				actions.moveToElement(eachElement).build().perform();
				String productPrice = eachElement.getText();
				if(productPrice.length()!=0){
					productPrice = productPrice.replace("Rs. ", "");
					convertedProductPrice = Double.parseDouble(productPrice);
					UtilFunctions.writeLog(log, "Converted product price is "+convertedProductPrice);
					if(convertedProductPrice>=lowPrice && convertedProductPrice<=highPrice){
						filterWorks = true;
					}else{
						filterWorks = false;
						break;
					}
				}
			}
			if(!filterWorks){
				UtilFunctions.writeLog(log, "Ending function verifySearchResultsByPriceRange in SearchResultsPage.class with "+Constants.FAIL);
				return Constants.FAIL+" - "+convertedProductPrice+" is out of range";
			}else{
				UtilFunctions.writeLog(log, "Ending function verifySearchResultsByPriceRange in SearchResultsPage.class with "+Constants.PASS);
				return Constants.PASS;
			}
		}catch(Exception e){
			UtilFunctions.writeLog(log, "Ending function verifySearchResultsByPriceRange in SearchResultsPage.class with "+Constants.ERROR+"-"+e.getMessage());
			return Constants.ERROR;
		}
	}
	
	public SearchResultsPage filterByDiscounts(Logger log, WebDriver browser,  String discountRange) throws InterruptedException{
		UtilFunctions.writeLog(log, "Starting function filterByDiscounts in SearchResultsPage.class");
		try{
			((JavascriptExecutor)browser).executeScript("arguments[0].scrollIntoView();", h4Discounts);
			h4Discounts.click();
			for(WebElement eachElement:chkDiscounts){
				String currentDiscount = eachElement.getText();
				if(currentDiscount.contains(discountRange)){
					eachElement.click();
					browser.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
					UtilFunctions.writeLog(log, "Selected "+currentDiscount);
					break;
				}
			}
		}catch(Exception e){
			UtilFunctions.writeLog(log, "Ending function filterByDiscounts in SearchResultsPage.class with "+Constants.ERROR+"-"+e.getMessage());
		}
		UtilFunctions.writeLog(log, "Ending function filterByDiscounts in SearchResultsPage.class");
		return PageFactory.initElements(browser, SearchResultsPage.class);
	}
	
	public String verifySearchResultsByDiscountsRange(Logger log, WebDriver browser, double lowerDiscountPercentage, double higherDiscountPercentage) throws InterruptedException{
		UtilFunctions.writeLog(log, "Starting function verifySearchResultsByDiscountsRange in SearchResultsPage.class");
		boolean filterWorks = false;
		double convertedOriginalProductPrice = 0.0;
		double convertedOfferProductPrice = 0.0;
		double percentageDiscounted = 0.0;
		String productPrice = null;
		String productName = null;
		Thread.sleep(1000);
		Actions actions = new Actions(browser);
		try{
			for(int i=0;i<lblOriginalPrice.size();i++){
				actions.moveToElement(lblOriginalPrice.get(i)).perform();
				productName = lblProductName.get(i).getText();
				UtilFunctions.writeLog(log, "Original Product Price is "+lblOriginalPrice.get(i).getText());
				productPrice = lblOriginalPrice.get(i).getText().replace("Rs. ", "");
				convertedOriginalProductPrice = Double.parseDouble(productPrice);
				UtilFunctions.writeLog(log, "Discount Product Price is "+lblOfferPrice.get(i).getText());
				productPrice = lblOfferPrice.get(i).getText().replace("Rs. ", "");
				convertedOfferProductPrice = Double.parseDouble(productPrice);
				percentageDiscounted = Math.round(100-((convertedOfferProductPrice/convertedOriginalProductPrice)*100));
				UtilFunctions.writeLog(log, "% discounted is: "+percentageDiscounted);
				if(percentageDiscounted>=lowerDiscountPercentage && percentageDiscounted<=higherDiscountPercentage){
					filterWorks = true;
				}else{
					filterWorks = false;
					break;
				}
			}
			if(!filterWorks){
				UtilFunctions.writeLog(log, Constants.FAIL+" - "+productName+" is out of range");
				return Constants.FAIL+" - "+productName+" is out of range";
			}else{
				UtilFunctions.writeLog(log, Constants.PASS);
				return Constants.PASS;
			}
		}catch(Exception e){
			UtilFunctions.writeLog(log, Constants.ERROR+" - "+e.getMessage());
			return Constants.ERROR;
		}
	}

}
