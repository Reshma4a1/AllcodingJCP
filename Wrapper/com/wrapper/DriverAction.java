package com.wrapper;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import com.logconfig.LogConfig;


public class DriverAction {
	static Logger consoleLog = LogConfig.getLogger(DriverAction.class);
	
	/**
	 * @Description : Method designed for web application load
	 * @param : driver
	 * @param : application url
	 */
	
	public static void launchApplication(String applicationUrl,WebDriver driver){
		try{
		driver.get(applicationUrl);
		DriverWait.pageToLoad(driver);
	}catch(Exception e){
		consoleLog.info(e.getMessage());
	}
		
		
}
	
	/**
	  * Description : method designed for right click and open in new tab
	  * @param driver
	  * @param locator
	  * @param logtext
	  * @param takeScreenShot
	  * @param shallThisStepExecute
	  * @return
	  */
	 public static boolean rightClickAndOpenInNewTab(WebDriver driver, String locator,String logtext,boolean takeScreenShot,boolean shallThisStepExecute){
	    	consoleLog.info("DriverAction Class >> Right Click and Open in New Tab");
	    	boolean flag = false;
	    	if(shallThisStepExecute){
	    		try{
	    			DriverWait.sleep(2);
	    			WebElement element = DriverLocator.getWebElement(driver, locator);
	        		if(!element.isDisplayed()) {
	        			scrollPage(driver,DriverScrollPage.DOWN);
	    	        }
	        		DriverWait.fluentwait(driver, locator, 20, 5, NoSuchElementException.class);
	        		Actions builder = new Actions(driver);
	                builder.contextClick(element).perform();
	                DriverWait.sleep(1);
	                builder.sendKeys(Keys.SPACE).perform();
	                DriverWait.sleep(1);
	                builder.sendKeys(Keys.CONTROL).perform();
	                DriverWait.sleep(1);
	                builder.sendKeys(Keys.ARROW_DOWN).perform();
	                DriverWait.sleep(1);
	                builder.sendKeys(Keys.ENTER).perform();
	                DriverWait.sleep(3);
	                consoleLog.info(logtext);
	            	takeScreenShot(driver,takeScreenShot,"newTab.png");
	            	flag = true;
	    		}catch(Exception e){
	    			consoleLog.info("NO Search result Found");
	    			e.printStackTrace();
	    			DriverWait.sleep(3);
	    		}
	    	}
	    	return flag;
	    }
	 /**
	  * Description : method designed to switch to next tab
	  * @param driver
	  * @param logtext
	  * @param takeScreenShot
	  * @param shallThisStepExecute
	  */
	 public static void switchToTab(WebDriver driver,String logtext,boolean takeScreenShot,boolean shallThisStepExecute){
	    	consoleLog.info("DriverAction Class >> Switch To New Tab");
	    	if(shallThisStepExecute){
	    		Set<String> tab_handles = driver.getWindowHandles();
	            int number_of_tabs = tab_handles.size();
	            int new_tab_index = number_of_tabs-1;
	            driver.switchTo().window(tab_handles.toArray()[new_tab_index].toString());
	            DriverWait.pageToLoad(driver);
	            consoleLog.info(logtext);
	        	takeScreenShot(driver,takeScreenShot,"navigateTonewTab.png");
	            DriverWait.sleep(3);
	    	}
	    }
	 /**
	  * Description : Overloaded method for window switch
	  * @param driver
	  * @param windowName
	  * @param logtext
	  * @param takeScreenShot
	  * @param shallThisStepExecute
	  */
	 public static void switchToTab(WebDriver driver,String windowName,String logtext,boolean takeScreenShot,boolean shallThisStepExecute){
		 consoleLog.info("DriverAction Class >> Switch To New Existing Tab");
		 if(shallThisStepExecute){
			 driver.switchTo().window(windowName);
			 DriverWait.pageToLoad(driver);
			 consoleLog.info(logtext);
			 takeScreenShot(driver,takeScreenShot,"navigateTonewTab.png");
	         DriverWait.sleep(3);
		 }
	 }

	


/**
 * Description : method designed for clearing the element
 * @param driver
 * @param locator
 * @param logtext
 * @param takeScreenShot
 * @param shallThisStepExecute
 */
public static void clear(WebDriver driver,String locator,String logtext,boolean takeScreenShot,boolean shallThisStepExecute){
	 DriverWait.fluentwait(driver, locator, 20, 5, NoSuchElementException.class);
	 DriverWait.sleep(2);
	 consoleLog.info("DriverAction Class >> Clear");
	 if(shallThisStepExecute){
       	WebElement element = DriverLocator.getWebElement(driver,locator);
       	if(!element.isDisplayed()) {
       		scrollPage(driver,DriverScrollPage.DOWN);
	        }
       	DriverWait.fluentwait(driver, locator, 20, 5, NoSuchElementException.class);
       	element.clear();
       	consoleLog.info("Element Cleared");
       	DriverWait.sleep(2);
       	
       	consoleLog.info(logtext);
       	takeScreenShot(driver,takeScreenShot,"clear.png");
       }
}

/**
 * Description : method designed for send key
 * @param driver
 * @param locator
 * @param data
 * @param logtext
 * @param takeScreenShot
 * @param shallThisStepExecute
 */
public static void sendKey(WebDriver driver,String locator,String data,String logtext,boolean takeScreenShot,boolean shallThisStepExecute){
   	consoleLog.info("DriverAction Class >> SendKey");
   	if(shallThisStepExecute){
       	WebElement element = DriverLocator.getWebElement(driver, locator);
       	if(!element.isDisplayed()) {
       		scrollPage(driver,DriverScrollPage.DOWN);
	        }
       	DriverWait.fluentwait(driver, locator, 20, 5, NoSuchElementException.class);
       	clear(driver, locator,"Element Cleared >> Invoked from sendKey method", takeScreenShot, shallThisStepExecute);
       	
       	DriverWait.fluentwait(driver, locator, 20, 5, NoSuchElementException.class);
       	element.sendKeys(data);
       	consoleLog.info("Enteted Data in Element : " + data);
       	DriverWait.sleep(2);
       	consoleLog.info(logtext);
       	takeScreenShot(driver,takeScreenShot,"sendKey.png");
       }
}

/**
* Description : method designed for click on button
* @param driver
* @param locator
* @param logtext
* @param takeScreenShot
* @param shallThisStepExecute
*/
public static void buttonClick(WebDriver driver, String locator,String logtext,boolean takeScreenShot,boolean shallThisStepExecute){
	 	DriverWait.sleep(5);
   	consoleLog.info("DriverAction Class >> Button Click");	
   	if(shallThisStepExecute){
   			DriverWait.fluentwait(driver, locator, 30, 5, NoSuchElementException.class);
           	WebElement element = DriverLocator.getWebElement(driver, locator);
           	if(!element.isDisplayed()) {
           		scrollToElement(driver, locator);
   	        }
           	DriverWait.fluentwait(driver, locator, 20, 5, NoSuchElementException.class);
           	element.click();
           	consoleLog.info("Clicked on Element");
           	DriverWait.sleep(2);
           	consoleLog.info(logtext);
           	takeScreenShot(driver,takeScreenShot,"buttonClick.png");
           }
   	
   }

/**
 * Description : method designed for radio button click
 * @param driver
 * @param locator
 * @param logtext
 * @param takeScreenShot
 * @param shallThisStepExecute
 */
public static void radioButtonClick(WebDriver driver, String locator,String logtext,boolean takeScreenShot,boolean shallThisStepExecute){
   		consoleLog.info("DriverAction Class >> Radio Button Click");
   		if(shallThisStepExecute){
           	WebElement element = DriverLocator.getWebElement(driver,locator);
           	if(!element.isDisplayed()) {
           		scrollPage(driver,DriverScrollPage.DOWN);
   	        }
           	DriverWait.fluentwait(driver, locator, 20, 5, NoSuchElementException.class);
           	if(!isElementSelected(driver, locator, takeScreenShot, shallThisStepExecute)){
           		element.click();
               	consoleLog.info("Clicked on Element");
               	DriverWait.sleep(2);	
           	}
           	consoleLog.info(logtext);
           	takeScreenShot(driver,takeScreenShot,"radioButtonClick.png");
           }
   	
   }





/**
 * Description : method designed for getting the attribute
 * @param driver
 * @param locator
 * @param attribute
 * @param logtext
 * @param takeScreenShot
 * @param shallThisStepExecute
 * @return
 */
public static String getAttribute(WebDriver driver,String locator,String attribute,String logtext,boolean takeScreenShot,boolean shallThisStepExecute){
   	consoleLog.info("DriverAction Class >> Get Attribute");
   	String attributeText = "";
   	if(shallThisStepExecute){
   		DriverWait.fluentwait(driver, locator, 20, 5, NoSuchElementException.class);
   		WebElement element = DriverLocator.getWebElement(driver,locator);
   		if(!element.isDisplayed()) {
       		scrollPage(driver,DriverScrollPage.DOWN);
	        }
   		attributeText =  element.getAttribute(attribute);
   		consoleLog.info(logtext + " : "+attributeText);
        	takeScreenShot(driver,takeScreenShot,"getAttribute.png");
   		DriverWait.sleep(3);
        	return attributeText;
   	}
   	return attributeText;
   }

/**
 * Description : method designed for getting text
 * @param driver
 * @param locator
 * @param logtext
 * @param takeScreenShot
 * @param shallThisStepExecute
 * @return
 */
public static String getText(WebDriver driver,String locator,String logtext,boolean takeScreenShot,boolean shallThisStepExecute){
   	consoleLog.info("DriverAction Class >> Get Text");
   	String fetchText = "";
   	if(shallThisStepExecute){
   		DriverWait.fluentwait(driver, locator, 20, 5, NoSuchElementException.class);
   		WebElement element = DriverLocator.getWebElement(driver,locator);
   		if(!element.isDisplayed()) {
       		scrollPage(driver,DriverScrollPage.DOWN);
	        }
   		fetchText =  element.getText();
   		consoleLog.info(logtext + " : "+fetchText);
        	takeScreenShot(driver,takeScreenShot,"getText.png");
   		DriverWait.sleep(3);
        	return fetchText;
   	}
   	return fetchText;
   }

/**
 * 
 * @param driver
 * @param locator
 * @param takeScreenShot
 * @param shallThisStepExecute
 * @return
 */
public static boolean isElementDisplayed(WebDriver driver,String locator,boolean takeScreenShot,boolean shallThisStepExecute){
	 consoleLog.info("DriverAction Class >> IS ELEMENT DISPLAYED");
	 boolean flag = false;
	 try{
		 if(shallThisStepExecute){
			 DriverWait.fluentwait(driver, locator, 20, 5, NoSuchElementException.class);
			 WebElement element = DriverLocator.getWebElement(driver,locator);
			 flag = element.isDisplayed();
		 }
	 }catch(Exception e){
		 flag = false;
		 consoleLog.info("Element not visible");
	 }
	 return flag;
}

/**
 * 
 * @param driver
 * @param locator
 * @param takeScreenShot
 * @param shallThisStepExecute
 * @return
 */
public static boolean isElementEnabled(WebDriver driver,String locator,boolean takeScreenShot,boolean shallThisStepExecute){
	 consoleLog.info("DriverAction Class >> IS ELEMENT ENABLED");
	 boolean flag = false;
	 try{
		 if(shallThisStepExecute){
			 DriverWait.fluentwait(driver, locator, 20, 5, NoSuchElementException.class);
			 WebElement element = DriverLocator.getWebElement(driver,locator);
			 flag = element.isEnabled();
		 }
	 }catch(Exception e){
		 flag = false;
		 consoleLog.info("Element not Enabled");
	 }
	 return flag;
}

/**
 * 
 * @param driver
 * @param locator
 * @param takeScreenShot
 * @param shallThisStepExecute
 * @return
 */
public static boolean isElementSelected(WebDriver driver,String locator,boolean takeScreenShot,boolean shallThisStepExecute){
	 consoleLog.info("DriverAction Class >> IS ELEMENT SELECTED");
	 boolean flag = false;
	 try{
		 if(shallThisStepExecute){
			 DriverWait.fluentwait(driver, locator, 20, 5, NoSuchElementException.class);
			 WebElement element = DriverLocator.getWebElement(driver,locator);
			 flag = element.isSelected();
		 }
	 }catch(Exception e){
		 flag = false;
		 consoleLog.info("Element not Selected");
	 }
	 return flag;
}
/**
 * Description : method designed for taking screen shot
 * @param driver
 * @param takeScreenShot
 * @param screenShotName
 */
public static void takeScreenShot(WebDriver driver,boolean takeScreenShot,String screenShotName){
	//TO-DO Error exist --> Need to revamp
  	consoleLog.info("DriverAction Class >> Take Screen Shot");
   	if(takeScreenShot){
   		try{
   			String currentScreenShotFolder = DateTimeFormatter.ofPattern("MM-dd-yyyy-HH-mm-ss").format(LocalDate.now())+"_ScreenShot";
   	    	File outputFile = new File(currentScreenShotFolder);
   			if (!outputFile.exists()) {
   				outputFile.mkdir();
   			}
       		File src= ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
       		FileUtils.copyFile(src, new File("screenShot\\"+currentScreenShotFolder+"\\"+screenShotName));	
       	}catch(Exception e){
       		e.printStackTrace();
       	}
   	}
   }

/**
* Description : method used to scroll a page up/down
* @param driver
* @param scroll
*/
public static void scrollPage(WebDriver driver,DriverScrollPage scroll){
		JavascriptExecutor jse = (JavascriptExecutor)driver;
		switch(scroll){
			case UP:
				jse.executeScript("scroll(0, -250);");
				consoleLog.info("Page Scrolled UP");
				DriverWait.sleep(2);
				break;
			case DOWN:
				jse.executeScript("window.scrollTo(0, document.body.scrollHeight)");
				consoleLog.info("Page Scrolled DOWN");
				DriverWait.sleep(2);
				break;
			default:
				consoleLog.error("Invalid Option Selected");
				break;
		}
		
}

/**
* Description : method used to scroll a page up/down
* @param driver
* @param scroll
*/
public static void scrollToElement(WebDriver driver,String locator){
	WebElement element = DriverLocator.getWebElement(driver, locator);
	Actions actions = new Actions(driver);
	actions.moveToElement(element);
	actions.perform();
}
	
}
