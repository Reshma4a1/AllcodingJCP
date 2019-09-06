package com.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class KillWindowsProcess {
	private static final String TASKLIST = "tasklist";
	private static final String KILL = "taskkill /F /IM ";
	
	public static void clearThreadFromTaskmanager(){
		String processName = "chromedriver.exe";
		//String processName = "chrome.exe";
		if (isProcessRunning(processName)) {
			killProcess(processName);
		}
	}

	private static void killProcess(String processName) {
		try {
			Runtime.getRuntime().exec(KILL + processName);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static boolean isProcessRunning(String processName) {
		try{
		Process p = Runtime.getRuntime().exec(TASKLIST);
		 BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
		 String line;
		 while ((line = reader.readLine()) != null) {
		  if (line.contains(processName)) {
		   return true;
		  }
		 }
		}catch(Exception e){
			e.printStackTrace();
		}
		 return false;
	}
	public static void main(String a[]){
		clearThreadFromTaskmanager();
	}
}
