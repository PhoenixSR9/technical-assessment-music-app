package com.example.musicdemo.utility;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class IsrcGenerator
{
	public static String generateIsrc(String registrantCode, String designationCode, String locale)
	{
		// If US, use QM
		// Reference: https://en.wikipedia.org/wiki/International_Standard_Recording_Code#Format
		if ("US".equals(locale) || null == locale)
		{
			locale = "QM";
		}
		
		String formattedYear = new SimpleDateFormat("yy")
				.format(Calendar.getInstance().getTime());
		
		String isrcToSave = String.join("", 
				locale, 
				registrantCode, 
				formattedYear, 
				designationCode.toString());
		
		return isrcToSave;
	}
}
