package com.wrapper;

import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.logconfig.LogConfig;

public class DriverLocator {
	static Logger consoleLog = LogConfig.getLogger(DriverLocator.class);
	
	/**
	 * @Description : Method to get webelement
	 * @param : driver
	 * @param : locator
	 * @return webelement
	 * @locator : xpath,name ,classname,linktext,partiallinktext,tagname,id,css
	 */
	
	public static WebElement getWebElement(WebDriver driver, String locator){
		WebElement webelement;
		if (locator.startsWith("css=")) {
			webelement = driver.findElement(By.cssSelector(locator.substring(4,locator.length())));
			consoleLog.info(" css locator used for : "+locator);
		} else if ((locator.startsWith("xpath=") || (locator.startsWith("//")))) {
			if (locator.startsWith("xpath=")) {
				webelement = driver.findElement(By.xpath(locator.substring(6,locator.length())));
			} else {
				webelement = driver.findElement(By.xpath(locator));
			}
			consoleLog.info(" xpath locator used for :     "+locator);
		} else if ((locator.startsWith("class="))) {
			webelement = driver.findElement(By.className(locator.substring(6,locator.length())));
			consoleLog.info(" class name locator used for :     "+locator);
		} else if ((locator.startsWith("name="))) {
			webelement = driver.findElement(By.name(locator.substring(5,locator.length())));
			consoleLog.info(" name locator used for :     "+locator);
		} else if ((locator.startsWith("tag="))) {
			webelement = driver.findElement(By.tagName(locator.substring(4,locator.length())));
			consoleLog.info(" tag locator used for :     "+locator);
		} else if ((locator.startsWith("link="))) {
			webelement = driver.findElement(By.linkText(locator.substring(5,locator.length())));
			consoleLog.info(" link locator used for :     "+locator);
		} else if ((locator.startsWith("plink="))) {
			webelement = driver.findElement(By.partialLinkText(locator.substring(5, locator.length())));
			consoleLog.info(" partial link locator used for :     "+locator);
		} else {
			webelement = driver.findElement(By.id(locator));
			consoleLog.info(" id locator used for :     "+locator);
		}

		// scroll element into view if it is not visible or obscured
		if (!webelement.isDisplayed()) {
			consoleLog.info("***getWebElement: Element " + locator +" is not visible.  Scrolling into view...");
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true)", webelement);
		}
		return webelement;
	}
	
	/**
	 * Description : method designed to find list of web elements
	 * @param driver
	 * @param locator
	 * @return
	 */
	public static List<WebElement> getWebElements(WebDriver driver, String locator) {
		List<WebElement> webElementList;

        if(locator.startsWith("css=")){
        	webElementList=driver.findElements(By.cssSelector(locator.substring(4, locator.length())));
        	consoleLog.info(" css locator used for : "+locator);
        } else if((locator.startsWith("xpath=")||(locator.startsWith("//")))){
            if(locator.startsWith("xpath=")){
            	webElementList=driver.findElements(By.xpath(locator.substring(6, locator.length())));
            } else{
            	webElementList=driver.findElements(By.xpath(locator));
            }
            consoleLog.info(" xpath locator used for :     "+locator);
        } else if((locator.startsWith("class="))){
        	webElementList=driver.findElements(By.className(locator.substring(6, locator.length())));
        	consoleLog.info(" class name locator used for :     "+locator);
        } else if((locator.startsWith("name="))){
        	webElementList=driver.findElements(By.name(locator.substring(5, locator.length())));
        	consoleLog.info(" name locator used for :     "+locator);
        } else if((locator.startsWith("tag="))){
        	webElementList=driver.findElements(By.tagName(locator.substring(4, locator.length())));
        	consoleLog.info(" tag locator used for :     "+locator);
        } else if((locator.startsWith("link="))){
        	webElementList=driver.findElements(By.linkText(locator.substring(5, locator.length())));
        	consoleLog.info(" link locator used for :     "+locator);
        } else if((locator.startsWith("plink="))){
        	webElementList=driver.findElements(By.partialLinkText(locator.substring(5, locator.length())));
        	consoleLog.info(" partial link locator used for :     "+locator);
        } else{
        	webElementList=driver.findElements(By.id(locator));
        	consoleLog.info(" id locator used for :     "+locator);
        }

    return webElementList;
	}
}

