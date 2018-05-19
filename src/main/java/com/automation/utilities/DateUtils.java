package com.automation.utilities;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
	
	private static final DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
	
	public static String getDate() {
		String dateValue = null;
		try {
			Date date = new Date();
			dateValue = sdf.format(date);
		} catch (Exception e) {
		}
		return dateValue;
	}
	
}
