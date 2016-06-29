package com.bigbasket.framework.util;

public class Constants {
	
	//Paths
	public static final String TESTSUITE_XLS_PATH = System.getProperty("user.dir")+"//executioninfo//input-data//TestSuite.xlsx";
	public static final String SUITECUSTOMER_XLS_PATH = System.getProperty("user.dir")+"//executioninfo//input-data//Customer.xlsx";
	public static final String SUITEMYACCOUNT_XLS_PATH = System.getProperty("user.dir")+"//executioninfo//input-data//MyAccount.xlsx";
	public static final String SUITEPRODUCTS_XLS_PATH = System.getProperty("user.dir")+"//executioninfo//input-data//Products.xlsx";
	public static final String PROPERTIES_FILE_PATH = System.getProperty("user.dir")+"//src//main//resources//project.properties";
	
	//Sheet Names
	public static final String TESTSUITE_SHEET = "TestSuite";
	public static final String TEST_DATA_SHEET = "TestData";
	public static final String TEST_CASES_SHEET = "TestCases";
	public static final String TEST_STEPS_SHEET = "TestSteps";
	
	//Run Modes
	public static final String RUNMODE_YES="Y";
	public static final String RUNMODE_NO="N";
	
	//Column Names
	public static final String SUITENAME_COL = "SuiteName";
	public static final String RUNMODE_COL = "Runmode";
	public static final String TEST_CASE_NAME_COL = "TestCaseName";
	public static final String ITERATION_COL = "Iteration";
	public static final String TEST_CASE_ID_COL = "TCID";
	public static final String KEYWORD_COL = "Keyword";
	public static final String OBJECT_COL = "Object";
	public static final String DATA_COL = "Data";
	public static final String CASE_COL = "Case";
	public static final String ALERTMESSAGE_COL = "AlertMessage";
	public static final String BROWSER_COL = "Browser";
	public static final String STEP_DESCRIPTION_COL = "Description";
	public static final String STEP_NAME_COL = "Step Name";
	public static final String TEST_DESCRIPTION_COL = "Description";
	
	//Run Status
	public static final String PASS = "PASS";
	public static final String FAIL = "FAIL";
	public static final String SKIP = "SKIP";
	public static final String ERROR = "ERROR";
	public static final String INFO = "INFO";
	
	//Browser Types
	public static final String MOZILLA = "Mozilla";
	public static final String CHROME = "Chrome";
	public static final String INTERNETEXPLORER = "IE";
	public static final String SAFARI = "Safari";
	
	//Locators
	public static final String XPATH = "XPath";
	public static final String ID = "ID";
	public static final String CSS = "CSS";
	
	//Error Messages
	public static final String ERROR_OPENBROWSER = "Error - Failed to open browser - ";
	public static final String ERROR_MISSINGELEMENT = "Error - Failed to locate element - ";
	public static final String OPENBROWSER_ERROR = "ERROR - FAILED TO OPEN BROWSER - ";
	
	//Values
	public static final String YES = "Yes";
	public static final String NO = "No";
	public static final String WEBSITE_TITLE = "BigBasket";
	
	//Execution Status
	public static final String KEYWORD_PASS = "Pass";
	public static final String KEYWORD_FAIL = "Fail";
	
	//Action
	public static final String SAVE = "Save";
	public static final String CANCEL = "Cancel";

}
