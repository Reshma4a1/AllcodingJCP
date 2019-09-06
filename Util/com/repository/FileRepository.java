package com.repository;

public interface FileRepository {
	
		//*******************************************
		//Global File Repo
		//*******************************************
		
	// global property file path
		public String SERVER_GLOBAL_PROPERTY = "C:\\JarvisWS\\Jarvis\\globalProperties\\global.properties";
		
		// Browser driver File Path
		public String CHROME_DRIVER_PATH = "C:\\JarvisWS\\AutomationChallenge\\drivers\\chrome\\chromedriver.exe";
		public String GECKO_DRIVER_PATH = "C:\\JarvisWS\\Jarvis\\drivers\\firefox\\gechodriver.exe";
		public String INTERNETEXPLORER_DRIVER_PATH = "C:\\JarvisWS\\Jarvis\\drivers\\ie\\internetexplorerdriver.exe";
		
		// extent report config file path
		public String SERVER_EXTENT_CONFIG_FILE_PATH = "C:\\JarvisWS\\Jarvis\\globalProperties\\extent-config.xml";
		
		// html report file path
		public String SERVER_HTML_REPORT_FILE_PATH = "C:\\JarvisWS\\Jarvis\\globalProperties\\TestReport.html";
		
		// log4j config file path
		public String SERVER_LOG4J_FILE_PATH = "C:\\JarvisWS\\AutomationChallenge\\globalproperties\\log4j.properties";
		
		// download FilePath
		public String SERVER_DOWNLOAD_FILE_PATH = "C:\\JarvisWS\\Jarvis\\downloads";

}
