package com.chl.studentdb.utils;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class ReadProperties {
	
	private static final String BUNDLE_NAME = "application";
	
	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME);
	
	private ReadProperties(){
		
	}
	public static String getKey(String key) {
		try {
			return RESOURCE_BUNDLE.getString(key);			
		} catch (MissingResourceException e) {
			return "Œ¥’“µΩ" + key + "!";
		}
	}
	

}
