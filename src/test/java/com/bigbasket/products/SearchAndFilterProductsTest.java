package com.bigbasket.products;

import java.io.IOException;
import java.util.Hashtable;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.Test;

import com.bigbasket.base.BaseTest;
import com.bigbasket.framework.util.Constants;
import com.bigbasket.framework.util.TestCaseDataProvider;
import com.bigbasket.framework.util.UtilFunctions;
import com.bigbasket.framework.util.Utility;
import com.bigbasket.framework.util.Xls_Reader;
import com.bigbasket.pages.HomePage;
import com.bigbasket.pages.SearchResultsPage;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class SearchAndFilterProductsTest extends BaseTest{
	
	public UtilFunctions utilFunctions;
	public ExtentTest testReporter;
	public WebDriver browser;
	
	public HomePage homePage;
	public SearchResultsPage searchResultsPage;
	
	@Test(dataProviderClass=TestCaseDataProvider.class,dataProvider="getDataForProductsSuite")
	public void searchProductAndFilterByPriceTest(Hashtable<String, String> testDataRecord) throws IOException, InterruptedException{
		String testName = "SearchProductAndFilterByPriceTest";
		
		//Checking the Run Modes of a given test suite, test cases within the suite and test data records of the test cases
		Xls_Reader xls = new Xls_Reader(Constants.SUITEPRODUCTS_XLS_PATH);
		int rowNum = xls.getCellRowNum(Constants.TEST_CASES_SHEET, Constants.TEST_CASE_ID_COL, testName);
		String currentTestName = testName+"_"+testDataRecord.get(Constants.CASE_COL)+"_"+testDataRecord.get(Constants.ITERATION_COL);
		String testDescription = xls.getCellData(Constants.TEST_CASES_SHEET, Constants.TEST_DESCRIPTION_COL, rowNum);
		testReporter = reporter.startTest(currentTestName, testDescription);
		Utility.checkRunModes("Products", testName, testDataRecord, xls, reporter, testReporter);
		
		//Logs
		Logger log = Utility.initLogs(currentTestName);
		log.debug("Starting "+currentTestName);
						
		utilFunctions = UtilFunctions.getInstance(testName);
		utilFunctions.setLogger(log);
						
		log.debug("Executing "+currentTestName+" using "+testDataRecord.toString());
				
		//Executing the test case if the run mode is found to be YES
		testReporter.log(LogStatus.INFO, "Test Data", "Starting the test "+currentTestName+" using test data "+testDataRecord.toString());
				
		//Opening the required browser
		browser = utilFunctions.openBrowser(testDataRecord.get("Browser"),project.getProperty("platform"));
			if(browser!=null){
				utilFunctions.writeHTMLResult(currentTestName, browser, reporter, testReporter, "Step 1", "Launch the required browser", Constants.PASS);
			}else{
				utilFunctions.writeHTMLResult(currentTestName, browser, reporter, testReporter, "Step 1", "Launch the required browser", Constants.ERROR);
			}
				
		//Connecting to test site URL		
		String stepResult = utilFunctions.connectURL(project.getProperty("testSiteURL"));
		utilFunctions.writeHTMLResult(currentTestName, browser, reporter, testReporter, "Step 2", "Connect to http://www.bigbasket.com", stepResult);
				
		//Click Skip and Explore button
		homePage = PageFactory.initElements(browser, HomePage.class);
		utilFunctions.writeHTMLResult(currentTestName, browser, reporter, testReporter, "Step 3", "Click the Skip and Explore button", homePage.clickSkipAndExplore(log));
		
		//Search a product and verify the Search Results filter by price range and verify the filter results
		searchResultsPage = PageFactory.initElements(browser, SearchResultsPage.class);
		searchResultsPage = searchResultsPage.searchProduct(log, browser, testDataRecord.get("Product Name"));
		//utilFunctions.writeHTMLResult(currentTestName, browser, reporter, testReporter, "Step 4", "Search a Product and verify the Search Results", searchResultsPage.verifySearchResults(testDataRecord.get("Product Name")));
		
		//Filter by price range and verify the filter results
		searchResultsPage = searchResultsPage.filterByPriceRange(log, browser, testDataRecord.get("Price Range"));
		switch(testDataRecord.get("Case")){
		case "< Rs 20":
			utilFunctions.writeHTMLResult(currentTestName, browser, reporter, testReporter, "Step 5", "Filter by Price Range and verify if the Search Results are within the Price Range selected", searchResultsPage.verifySearchResultsByPriceRange(log, browser, 0, 20));
			break;
		case "Rs 21 to Rs 50":
			utilFunctions.writeHTMLResult(currentTestName, browser, reporter, testReporter, "Step 5", "Filter by Price Range and verify if the Search Results are within the Price Range selected", searchResultsPage.verifySearchResultsByPriceRange(log, browser, 21, 50));
			break;
		case "Rs 51 to Rs 100":
			utilFunctions.writeHTMLResult(currentTestName, browser, reporter, testReporter, "Step 5", "Filter by Price Range and verify if the Search Results are within the Price Range selected", searchResultsPage.verifySearchResultsByPriceRange(log, browser, 51, 100));
			break;
		}
		
		//Ending the test
		log.debug("Ending "+currentTestName);
		reporter.endTest(testReporter);
	}
	
	@Test(dataProviderClass=TestCaseDataProvider.class,dataProvider="getDataForProductsSuite")
	public void searchProductAndFilterByDiscountTest(Hashtable<String, String> testDataRecord) throws IOException, InterruptedException{
		String testName = "SearchProductAndFilterByDiscountTest";
		
		//Checking the Run Modes of a given test suite, test cases within the suite and test data records of the test cases
		Xls_Reader xls = new Xls_Reader(Constants.SUITEPRODUCTS_XLS_PATH);
		int rowNum = xls.getCellRowNum(Constants.TEST_CASES_SHEET, Constants.TEST_CASE_ID_COL, testName);
		String currentTestName = testName+"_"+testDataRecord.get(Constants.CASE_COL)+"_"+testDataRecord.get(Constants.ITERATION_COL);
		String testDescription = xls.getCellData(Constants.TEST_CASES_SHEET, Constants.TEST_DESCRIPTION_COL, rowNum);
		testReporter = reporter.startTest(currentTestName, testDescription);
		Utility.checkRunModes("Products", testName, testDataRecord, xls, reporter, testReporter);
		
		//Logs
		Logger log = Utility.initLogs(currentTestName);
		log.debug("Starting "+currentTestName);
								
		utilFunctions = UtilFunctions.getInstance(testName);
		utilFunctions.setLogger(log);
								
		log.debug("Executing "+currentTestName+" using "+testDataRecord.toString());
						
		//Executing the test case if the run mode is found to be YES
		testReporter.log(LogStatus.INFO, "Test Data", "Starting the test "+currentTestName+" using test data "+testDataRecord.toString());
				
		//Opening the required browser
		browser = utilFunctions.openBrowser(testDataRecord.get("Browser"),project.getProperty("platform"));
			if(browser!=null){
				utilFunctions.writeHTMLResult(currentTestName, browser, reporter, testReporter, "Step 1", "Launch the required browser", Constants.PASS);
			}else{
				utilFunctions.writeHTMLResult(currentTestName, browser, reporter, testReporter, "Step 1", "Launch the required browser", Constants.ERROR);
			}
				
		//Connecting to test site URL		
		String stepResult = utilFunctions.connectURL(project.getProperty("testSiteURL"));
		utilFunctions.writeHTMLResult(currentTestName, browser, reporter, testReporter, "Step 2", "Connect to http://www.bigbasket.com", stepResult);
				
		//Click Skip and Explore button
		homePage = PageFactory.initElements(browser, HomePage.class);
		utilFunctions.writeHTMLResult(currentTestName, browser, reporter, testReporter, "Step 3", "Click the Skip and Explore button", homePage.clickSkipAndExplore(log));
		
		//Search a product and verify the Search Results filter by Discounts range and verify the filter results
		searchResultsPage = PageFactory.initElements(browser, SearchResultsPage.class);
		searchResultsPage = searchResultsPage.searchProduct(log, browser, testDataRecord.get("Product Name"));
		
		//Filter by price range and verify the filter results
		searchResultsPage = searchResultsPage.filterByDiscounts(browser, testDataRecord.get("Discount Range"));
		//searchResultsPage.verifySearchResultsByDiscountsRange(5);
		switch(testDataRecord.get("Case")){
		case "UPTO 5%":
			utilFunctions.writeHTMLResult(currentTestName, browser, reporter, testReporter, "Step 5", "Filter by Discount Range and verify if the Search Results are within the Discount Range selected", searchResultsPage.verifySearchResultsByDiscountsRange(browser, 0, 5));
			break;
		case "5% - 10%":
			utilFunctions.writeHTMLResult(currentTestName, browser, reporter, testReporter, "Step 5", "Filter by Discount Range and verify if the Search Results are within the Discount Range selected", searchResultsPage.verifySearchResultsByDiscountsRange(browser, 5, 10));
			break;
		case "10% - 15%":
			utilFunctions.writeHTMLResult(currentTestName, browser, reporter, testReporter, "Step 5", "Filter by Discount Range and verify if the Search Results are within the Discount Range selected", searchResultsPage.verifySearchResultsByDiscountsRange(browser, 10, 15));
			break;
		case "15% - 25%":
			utilFunctions.writeHTMLResult(currentTestName, browser, reporter, testReporter, "Step 5", "Filter by Discount Range and verify if the Search Results are within the Discount Range selected", searchResultsPage.verifySearchResultsByDiscountsRange(browser, 15, 25));
			break;
		case "MORE THAN 25%":
			utilFunctions.writeHTMLResult(currentTestName, browser, reporter, testReporter, "Step 5", "Filter by Discount Range and verify if the Search Results are within the Discount Range selected", searchResultsPage.verifySearchResultsByDiscountsRange(browser, 25, 99));
			break;
		}
		
		//Ending the test
		log.debug("Ending "+currentTestName);
		reporter.endTest(testReporter);
	}
	
	@Test(dataProviderClass=TestCaseDataProvider.class,dataProvider="getDataForProductsSuite")
	public void SearchProductAndFilterByBrandTest(Hashtable<String, String> testDataRecord) throws IOException, InterruptedException{
		String testName = "SearchProductAndFilterByBrandTest";
		
		//Checking the Run Modes of a given test suite, test cases within the suite and test data records of the test cases
		Xls_Reader xls = new Xls_Reader(Constants.SUITEPRODUCTS_XLS_PATH);
		int rowNum = xls.getCellRowNum(Constants.TEST_CASES_SHEET, Constants.TEST_CASE_ID_COL, testName);
		String currentTestName = testName+"_"+testDataRecord.get(Constants.CASE_COL)+"_"+testDataRecord.get(Constants.ITERATION_COL);
		String testDescription = xls.getCellData(Constants.TEST_CASES_SHEET, Constants.TEST_DESCRIPTION_COL, rowNum);
		testReporter = reporter.startTest(currentTestName, testDescription);
		Utility.checkRunModes("Products", testName, testDataRecord, xls, reporter, testReporter);
		
		//Logs
		Logger log = Utility.initLogs(currentTestName);
		log.debug("Starting "+currentTestName);
										
		utilFunctions = UtilFunctions.getInstance(testName);
		utilFunctions.setLogger(log);
										
		log.debug("Executing "+currentTestName+" using "+testDataRecord.toString());
								
		//Executing the test case if the run mode is found to be YES
		testReporter.log(LogStatus.INFO, "Test Data", "Starting the test "+currentTestName+" using test data "+testDataRecord.toString());
				
		//Opening the required browser
		browser = utilFunctions.openBrowser(testDataRecord.get("Browser"),project.getProperty("platform"));
			if(browser!=null){
				utilFunctions.writeHTMLResult(currentTestName, browser, reporter, testReporter, "Step 1", "Launch the required browser", Constants.PASS);
			}else{
				utilFunctions.writeHTMLResult(currentTestName, browser, reporter, testReporter, "Step 1", "Launch the required browser", Constants.ERROR);
			}
				
		//Connecting to test site URL		
		String stepResult = utilFunctions.connectURL(project.getProperty("testSiteURL"));
		utilFunctions.writeHTMLResult(currentTestName, browser, reporter, testReporter, "Step 2", "Connect to http://www.bigbasket.com", stepResult);
				
		//Click Skip and Explore button
		homePage = PageFactory.initElements(browser, HomePage.class);
		utilFunctions.writeHTMLResult(currentTestName, browser, reporter, testReporter, "Step 3", "Click the Skip and Explore button", homePage.clickSkipAndExplore(log));
		
		//Search a product and verify the Search Results
		searchResultsPage = PageFactory.initElements(browser, SearchResultsPage.class);
		searchResultsPage = searchResultsPage.searchProduct(log, browser, testDataRecord.get("Product Name"));
		utilFunctions.writeHTMLResult(currentTestName, browser, reporter, testReporter, "Step 4", "Search a Product and verify the Search Results", searchResultsPage.verifySearchResults(testDataRecord.get("Product Name")));
		
		//Filter by brands and verify the filter results
		utilFunctions.writeHTMLResult(currentTestName, browser, reporter, testReporter, "Step 5", "Filter the products by Brands", searchResultsPage.filterByBrands(browser, testDataRecord.get("Brands")));
		utilFunctions.writeHTMLResult(currentTestName, browser, reporter, testReporter, "Step 5", "Verify the filter results are within the Brands selected",searchResultsPage.verifyFilterByBrands(testDataRecord.get("Brands")));
		
		//Ending the test
		log.debug("Ending "+currentTestName);
		reporter.endTest(testReporter);
	}

}
