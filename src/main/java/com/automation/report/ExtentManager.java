package com.automation.report;

import java.io.File;

import com.automation.utilities.DateUtils;
import com.relevantcodes.extentreports.ExtentReports;

public class ExtentManager {
    static ExtentReports extent;
    final static String filePath = System.getProperty("user.dir") +"\\" + new File(ReporterConstants.AUTOMATION_REPORTS_FOLDER)  +"\\" + DateUtils.getDate() + "\\" + "report.html";
    
    public synchronized static ExtentReports getReporter() {
        if (extent == null) {
            extent = new ExtentReports(filePath, true);
        }
        
        return extent;
    }
}