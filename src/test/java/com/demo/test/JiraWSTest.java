package com.demo.test;

import static io.restassured.RestAssured.given;

import java.util.Hashtable;

import org.apache.log4j.Logger;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.automation.accelerator.ActionEngine;
import com.automation.report.ExtentTestManager;
import com.automation.utilities.TestUtil;
import com.demo.pojo.CreateIssuePOJO;
import com.relevantcodes.extentreports.LogStatus;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class JiraWSTest extends ActionEngine {
	
	private static final Logger LOG = Logger.getLogger(JiraWSTest.class);
	
	protected CreateIssuePOJO createIssuePOJO ;
	
	@Test(dataProvider="JiraTest1DP")
	public void TC_01_CreateIssueTest(Hashtable<String, String> data) {
		Response responseObj = null;
		RequestSpecification req = null;
		String issueId = null;
		String issueKey = null;
		String issueURL = null;

		try {
			req = given().
				header(data.get("Param1"), data.get("ParamValue1")).
				header("cookie", data.get("Param2")+ "=" + getSeesonKey(data)).
				body(data.get("CreateIssueRequestBody")).log().all();
			responseObj= req.when().
				post(data.get("CreateIssueResourcePath")).
			then().
				statusCode(201).log().all().extract().response();
			
			createIssuePOJO = responseObj.as(CreateIssuePOJO.class);
			
			LOG.debug("Request : " + req.log().all().toString());
			LOG.debug("Response : " + responseObj.asString());
			issueId = getItemFromReponse(responseObj, "id").toString();
			issueKey = getItemFromReponse(responseObj, "key").toString();
			issueURL =  getItemFromReponse(responseObj, "self").toString();
			
			ExtentTestManager.getTest().log(LogStatus.PASS, "Id of the issue created", issueId);
			ExtentTestManager.getTest().log(LogStatus.PASS, "Key of the issue created", issueKey);
			ExtentTestManager.getTest().log(LogStatus.PASS, "URL of the issue created", issueURL);
			
		} catch (Exception e) {
			LOG.debug("Exception Occured | Type1WebCall " , e);
		}
	}
	
	@Test(dataProvider="TC_02_AssignIssueTestDP")
	public void TC_02_AssignIssueTest(Hashtable<String, String> data) {
		Response reponse = null;
		 try {
			 reponse = given().
						header(data.get("Param1"), data.get("ParamValue1")).
						header(data.get("Param2"), getSeesonKey(data)).
					 	body(data.get("RequestBodyAssign")).
					  when().
					  	put("rest/api/2/issue/" + createIssuePOJO.getId() + "/assignee").
					  then().
					  	extract().response();
			 if(!assertValues("Status Code", 204, reponse.getStatusCode())) {
				 ExtentTestManager.getTest().log(LogStatus.FAIL , "Unable to updated the Issue " + createIssuePOJO.getId());
			 }else {
				 ExtentTestManager.getTest().log(LogStatus.PASS , "Successfully updated the issue " + createIssuePOJO.getId());
			 }
		} catch (Exception e) {
			LOG.debug("Exception Occured | TC_02_AssignIssueTest " , e);
		}
	}
	
	@Test(dataProvider="JiraTest1DP")
	public void TC_03_JsonObjectDeserialization(Hashtable<String, String> data) {
		Response responseObj = null;
		RequestSpecification req = null;
		 try {
			 req = given().
						header(data.get("Param1"), data.get("ParamValue1")).
						header("cookie", data.get("Param2")+ "=" + getSeesonKey(data)).
						body(data.get("CreateIssueRequestBody")).log().all();
			 responseObj= req.when().
						post(data.get("CreateIssueResourcePath")).
					then().
						statusCode(201).log().all().extract().response();
					
					createIssuePOJO = responseObj.as(CreateIssuePOJO.class);

					ExtentTestManager.getTest().log(LogStatus.PASS, "Id of the issue created from POJO ", createIssuePOJO.getId());
					ExtentTestManager.getTest().log(LogStatus.PASS, "Key of the issue created from POJO ", createIssuePOJO.getKey()+"");
					ExtentTestManager.getTest().log(LogStatus.PASS, "URL of the issue created from POJO ", createIssuePOJO.getSelf());
		} catch (Exception e) {
			LOG.debug("Exception Occured | TC_03_JsonObjectDeserialization " , e);
		}
	}
	
	@DataProvider
	public Object[][] JiraTest1DP() {
		return TestUtil.getData("JiraTest1DP", dataPath, "Jira");
	}
	
	@DataProvider
	public Object[][] TC_02_AssignIssueTestDP() {
		return TestUtil.getData("TC_02_AssignIssueTestDP", dataPath, "Jira");
	}

}
