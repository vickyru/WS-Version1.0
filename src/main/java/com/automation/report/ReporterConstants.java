package com.automation.report;

import com.automation.utilities.ConfigFileReadWrite;

/**
 * @author prash
 *
 */
public interface ReporterConstants {
  String configReporterFile =System.getProperty("user.dir")+"\\src\\main\\resources\\resources.properties";
  
  // Automation Report File Location
  String AUTOMATION_REPORTS_FOLDER = ConfigFileReadWrite.read(configReporterFile, "automation_report_folder");
  String TESTDATA_REPORTS_FOLDER = ConfigFileReadWrite.read(configReporterFile, "test_data_file_location");
  String API_KEY = ConfigFileReadWrite.read(configReporterFile, "api_key");
  String BASE_URI = ConfigFileReadWrite.read(configReporterFile, "base-uri");
  String JIRA_PORT = ConfigFileReadWrite.read(configReporterFile, "jira_port");
  
  
}