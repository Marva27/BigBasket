package com.bigbasket.framework.util;

import com.relevantcodes.extentreports.DisplayOrder;
import com.relevantcodes.extentreports.ExtentReports;

public class SimpleReportFactory {
	
	private static ExtentReports reporter;
	
	public static synchronized ExtentReports getReporter(){
		if(reporter==null){
			reporter = new ExtentReports("HTML Reports//index.html",true,DisplayOrder.OLDEST_FIRST);
		}
		return reporter;
	}
	
	public static synchronized void closeReporter(){
		reporter.flush();
		reporter.close();
	}

}
