package com.demo.test;

import static io.restassured.RestAssured.given;


import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.testng.Reporter;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.automation.accelerator.ActionEngine;
import com.automation.report.ExtentTestManager;
import com.automation.report.ReporterConstants;
import com.automation.utilities.TestUtil;
import com.relevantcodes.extentreports.LogStatus;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class FirstProgram extends ActionEngine{
	
	private static final Logger LOG = Logger.getLogger(FirstProgram.class);
	
	@Test(dataProvider="Type1WebCallDP")
	public void Type1WebCall(Hashtable<String, String> data) {
		try {

			RestAssured.baseURI = data.get("BaseURI");
			Response response = null;
			RequestSpecification reqSpecification = given().log().all().
				param(data.get("Param1"),data.get("ParamValue1")).
				param(data.get("Param2"),data.get("ParamValue2")).
				param(data.get("Param3"),ReporterConstants.API_KEY);
			response = reqSpecification.when().
				get(data.get("ResourcePath"));
			//Print Header
			List<Header> headerList = getHeaderAsList(response);
			for (Header header : headerList) {
				ExtentTestManager.getTest().log(LogStatus.INFO, "Response Header", header.toString());
			}
			// Cookies
			Map<String, String> coockiesMap =  getCoockiesAsMap(response);
			for (Map.Entry<String, String> entry : coockiesMap.entrySet()) {
				ExtentTestManager.getTest().log(LogStatus.INFO, "Response Cookies", entry.getKey() + " = " + entry.getValue());
			}
			//Body
			JsonPath path = getJsonPath(response);
			int count = getCountOfItemOfObjectPath(response, "items");
			for (int i = 0; i < count; i++) {
				ExtentTestManager.getTest().log(LogStatus.INFO, "Item - " + i + " Name :" ,  path.getString("items[" +i+ "].name"));
				ExtentTestManager.getTest().log(LogStatus.INFO, "Item - " + i + " MRP :" ,  path.getString("items[" +i+ "].msrp"));
			}
			ArrayList<Map<String, ?>>  upcList = getListOfItemOfObjectPath(response, "items.upc");
			for (int i = 0; i < upcList.size(); i++) {
				ExtentTestManager.getTest().log(LogStatus.INFO, "Item UPC " + i + " : " , upcList.get(i) + "");
			}
		} catch (Exception e) {
			LOG.debug("Excep[tion Occured | Type1WebCall " , e);
		}
	}
	
	@Test(dataProvider="Type2WebCallDP")
	public void Type2WebCall(Hashtable<String, String> data) {
		Response res = RestAssured.get(data.get("EndPointURL"));
		assertValues("Status Code" , getStringFrom(data.get("StatusCode")), getStringFrom(res.getStatusCode()));
		assertValues("Content Type", data.get("ContentType"), getStringFrom(res.getContentType()));
	}
	
	@Test(dataProvider="Type3WebCallDP")
	public void Type3WebCall(Hashtable<String, String> data) {
	RequestSpecification requestSpec = new RequestSpecBuilder().addParam("query","ipod").
															  and().addParam("format","json").
															  and().addParam("apiKey","xyc52sk8fnkc954nvcpspb52").build();
		given().
			spec(requestSpec).
		when().
			get("/v1/search").
		then().assertThat().contentType(ContentType.JSON);
		Reporter.log("Inside " + getClass().toString());
	}
	
	@DataProvider
	public Object[][] Type1WebCallDP() {
		return TestUtil.getData("Type1WebCallDP", dataPath, "WebService");
	}
	@DataProvider
	public Object[][] Type2WebCallDP() {
		return TestUtil.getData("Type2WebCallDP", dataPath, "WebService");
	}
	@DataProvider
	public Object[][] Type3WebCallDP() {
		return TestUtil.getData("Type3WebCallDP", dataPath, "WebService");
	}
}
