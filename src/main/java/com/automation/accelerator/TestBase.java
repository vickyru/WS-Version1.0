package com.automation.accelerator;

import java.lang.reflect.Method;

import org.apache.log4j.PropertyConfigurator;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;

import com.automation.report.ExtentManager;
import com.automation.report.ExtentTestManager;
import com.automation.report.ReporterConstants;
import com.automation.utilities.TestDataReader;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.ExtentTestInterruptedException;
import com.relevantcodes.extentreports.LogStatus;

import io.restassured.RestAssured;

public class TestBase {
	
	public static ExtentReports extent;
	public static ExtentTest test;
	public static ExtentTestInterruptedException testexception;
	public TestDataReader  dataPath = new TestDataReader(System.getProperty("user.dir") + "/testdata/TestData.xlsx");
	
	@BeforeSuite
	public void beforeSuite() 
	{
		//Report Directory and Report Name
		PropertyConfigurator.configure(System.getProperty("user.dir") + "\\src\\main\\resources\\Log.properties");
		RestAssured.baseURI = ReporterConstants.BASE_URI;
		RestAssured.port = Integer.valueOf(ReporterConstants.JIRA_PORT);
		System.out.println("Before Suite ");
	}
	
	@BeforeTest
	public void beforeTest()
	{
		System.out.println("Before Test ");
	}
	
	//Messages on Categories Section of HTML Report
	
	@BeforeMethod 
	public  void beforeMethod(Method method) 
	{
		System.out.println("Before Method ");
		ExtentTestManager.startTest(method.getName());
	}
	
	//Test Case Reporting Ends Here
	@AfterMethod
	public void afterMethod(ITestResult result) 
	{
		System.out.println("After Method ");
		 if (result.getStatus() == ITestResult.FAILURE) {
	            ExtentTestManager.getTest().log(LogStatus.FAIL, result.getThrowable());
	        } else if (result.getStatus() == ITestResult.SKIP) {
	            ExtentTestManager.getTest().log(LogStatus.SKIP, "Test skipped " + result.getThrowable());
	        } else {
	            ExtentTestManager.getTest().log(LogStatus.PASS, "Test passed");
	        }
	        
	        ExtentManager.getReporter().endTest(ExtentTestManager.getTest());        
	        ExtentManager.getReporter().flush();
	}
	   	
	@AfterTest
	public void afterTest()
	{
		System.out.println("After Test");
	}
		
	@AfterSuite
	public void afterSuite() 
	{
		System.out.println("AfterSuite");
	}

}
