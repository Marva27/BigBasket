package com.bigbasket.framework.util;

import java.lang.reflect.Method;

import org.testng.annotations.DataProvider;



public class TestCaseDataProvider {
	
	@DataProvider(name="getDataForCustomerSuite")
	public static Object[][] getDataForCustomerSuite(Method m){
		String testCase = m.getName();
		Xls_Reader xls = new Xls_Reader(Constants.SUITECUSTOMER_XLS_PATH);
		return Utility.getData(testCase, xls);	
	}
	
	@DataProvider(name="getDataForMyAccountSuite")
	public static Object[][] getDataForMyAccountSuite(Method m){
		String testCase = m.getName();
		Xls_Reader xls = new Xls_Reader(Constants.SUITEMYACCOUNT_XLS_PATH);
		return Utility.getData(testCase, xls);	
	}
	
	@DataProvider(name="getDataForProductsSuite")
	public static Object[][] getDataForProductsSuite(Method m){
		String testCase = m.getName();
		Xls_Reader xls = new Xls_Reader(Constants.SUITEPRODUCTS_XLS_PATH);
		return Utility.getData(testCase, xls);
	}

}
