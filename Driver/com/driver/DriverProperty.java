package com.driver;

import java.util.Map;

import com.repository.FileRepository;
import com.util.PropertyReader;



public class DriverProperty {
	// Global Property File
	private static final String GLOBAL_PROP_FILE = FileRepository.SERVER_GLOBAL_PROPERTY;
	public static Map<String,String> GLOBAL_PROP = getDriverProperty();
	
	/**
	 * Description : method designed to get the global property and make it available to the framework
	 * @return
	 */
	private static final Map<String,String> getDriverProperty(){
		if(GLOBAL_PROP==null){
			GLOBAL_PROP = PropertyReader.getData(GLOBAL_PROP_FILE);
		}
		return GLOBAL_PROP;
	}
}
