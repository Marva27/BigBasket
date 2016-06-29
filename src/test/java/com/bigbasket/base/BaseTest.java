package com.bigbasket.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;

import com.bigbasket.framework.util.SimpleReportFactory;
import com.bigbasket.framework.util.UtilFunctions;
import com.relevantcodes.extentreports.ExtentReports;


public class BaseTest {
	
	protected ExtentReports reporter = SimpleReportFactory.getReporter();
	public Properties project;
	public UtilFunctions utilFunctions;
	
	@BeforeSuite
	public void clearLogsAndReports() throws IOException{
		File logs = new File(System.getProperty("user.dir")+"//executioninfo//logs");
		FileUtils.cleanDirectory(logs);
		File htmlReports = new File(System.getProperty("user.dir")+"//HTML Reports");
		FileUtils.cleanDirectory(htmlReports);
		FileUtils.cleanDirectory(new File(System.getProperty("user.dir")+"//executioninfo//screenshots"));
	}
	
	@BeforeClass
	public void loadProperties() throws IOException{
		FileInputStream fis = new FileInputStream(System.getProperty("user.dir")+"//src//main//resources//project.properties");
		project = new Properties();
		project.load(fis);
	}
		
	@AfterSuite
	public void afterSuite(){
		SimpleReportFactory.closeReporter();
	}
}
