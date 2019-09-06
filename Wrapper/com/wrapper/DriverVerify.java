package com.wrapper;

import org.apache.log4j.Logger;
import org.testng.Assert;

import com.logconfig.LogConfig;



public class DriverVerify {
	static Logger consoleLog = LogConfig.getLogger(DriverVerify.class);
	
	/**
	 * @Description : method to verify
	 * @param : actual 
	 * @param : expected
	 */
	
	public boolean verifyEquals(String actual,String expected){
		boolean flag = false;
		try{
			Assert.assertEquals(actual,expected);
			flag = true;
			return flag;
		}catch(Exception e){
			flag = false;
			consoleLog.info("Data Mismatch");
		}
		return flag;
		
	}

}
