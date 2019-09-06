package com.logconfig;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import com.repository.FileRepository;

public class LogConfig {

static Logger logger;
	
	private static void getLogProperties(String filePath){
		PropertyConfigurator.configure(filePath);
	}
	
	public static Logger getLogger(Class<?> className){
		getLogProperties(FileRepository.SERVER_LOG4J_FILE_PATH);
		logger = Logger.getLogger(className);
		return logger ;
	}
}
