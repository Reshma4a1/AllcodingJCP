package com.driver;

import org.apache.log4j.Logger;

import com.logconfig.LogConfig;



public class DriverInit extends DriverBase {

	static Logger consoleLog = LogConfig.getLogger(DriverInit.class);
	
	public static void setupDriver(String param){
		switch(param){
			case "web":
				//KillWindowsProcess.clearThreadFromTaskmanager();
				initWebDriver();
				break;
			case "mobile":
				//AppiumDriverBase appiumDriverBase = new AppiumDriverBase();
				//appiumDriverBase.startAppiumServer();
				//initMobileDriver();
				consoleLog.info("Remote driver initialised");
				break;
			case "api":
				//initAPI();
				consoleLog.info("All API level service initialized");
				break;
			case "desktop":
				//TO-DO
				break;
			default:
				consoleLog.info("Invalid option");
				break;
		}
	}


}
