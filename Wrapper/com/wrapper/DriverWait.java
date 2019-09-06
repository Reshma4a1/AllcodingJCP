package com.wrapper;

import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import org.apache.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.logconfig.LogConfig;



public class DriverWait {
	static Logger consoleLog = LogConfig.getLogger(DriverWait.class);
	/*
	 * @Description : Method designed for fluent wait for a webelement
	 * @param : Driver
	 * @param : locator
	 * @param : maximum wait time
	 * @param : pooling frequency 
	 */
	
	public static WebElement fluentwait(WebDriver driver,String locator,int max_waitTime_sec,int polling_inEvery_sec,Class<? extends Throwable> exception){
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
				.withTimeout(max_waitTime_sec, TimeUnit.SECONDS)
				.pollingEvery(polling_inEvery_sec, TimeUnit.SECONDS)
				.ignoring(exception);
		WebElement element = wait.until(new Function<WebDriver, WebElement>(){
			@Override
			public WebElement apply(WebDriver t) {
				return DriverLocator.getWebElement(driver, locator);
			}
		});
		return element;
		
	}
	
	/**
	 * @Description : Method designed for static wait
	 * @param : seconds
	 * 
	 */
	public static void sleep(int sec){
		try{
			Thread.sleep(sec * 1000);
			consoleLog.info("Current Thread Sleep for "+sec+" Second");
		}catch(Exception e){
			consoleLog.error(e.getMessage());
		}
	}
	
	/**
	 * @Description : Method designed for page load
	 * @param : driver
	 * 
	 */
	
	public static void pageToLoad(WebDriver driver){
		try{
			JavascriptExecutor js = (JavascriptExecutor) driver;
			if (js.executeScript("return document.readyState").toString().equals("complete")) {
				consoleLog.info("Page Content Loaded successfully");
				return;
			}
		}catch(Exception e){
			System.out.println("PageLoading Error >> "+e.getMessage());
		}
	}

	/**
	 * @Description : static inner class for Explicit wait
	 */
	 static class explicitwait{
			/**
			 * Description : method designed for driver to wait for element to be clickable 
			 * @param driver
			 * @param locator
			 * @param max_waitTime_sec
			 */
			public static void elementToBeClickable(WebDriver driver,String locator,int max_waitTime_sec){
				consoleLog.info("Explicit Wait for Element To be Clickable");
				WebDriverWait wait = new WebDriverWait(driver,max_waitTime_sec);
				wait.until(ExpectedConditions.elementToBeClickable(DriverLocator.getWebElement(driver, locator)));
			}
			
			/**
			 * Description : method designed for driver to wait for element to be selected
			 * @param driver
			 * @param locator
			 * @param max_waitTime_sec
			 */
			public static void elementToBeSelected(WebDriver driver,String locator,int max_waitTime_sec){
				consoleLog.info("Explicit Wait for Element To be selected");
				WebDriverWait wait = new WebDriverWait(driver,max_waitTime_sec);
				wait.until(ExpectedConditions.elementToBeSelected(DriverLocator.getWebElement(driver, locator)));
			}
			
			/**
			 * Description : method designed for driver to wait for element to be visible
			 * @param driver
			 * @param locator
			 * @param max_waitTime_sec
			 */
			public static void visibilityOf(WebDriver driver,String locator,int max_waitTime_sec){
				consoleLog.info("Explicit Wait for Element To be visible");
				WebDriverWait wait = new WebDriverWait(driver,max_waitTime_sec);
				wait.until(ExpectedConditions.visibilityOf(DriverLocator.getWebElement(driver, locator)));
			}
			
		}
		 
		
	}

