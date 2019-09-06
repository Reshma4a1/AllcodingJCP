package com.zoopla;



import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.Test;

import com.gargoylesoftware.htmlunit.javascript.host.intl.DateTimeFormat;

public class RentProperty {
	static WebDriver driver ;
	
	
	
@Test()
public static void SaleRent() throws InterruptedException{
	System.setProperty("webdriver.chrome.driver", "C:\\Softwares\\chromedriver.exe");
	driver = new ChromeDriver();
	
	//Application url
	driver.get("https://www.makemytrip.com");
	
	//make sure application is in homepage
    //code to do image validation
	WebElement ImageFile = driver.findElement(By.xpath("//img[@alt='Make My Trip']"));
    
    Boolean ImagePresent = (Boolean) ((JavascriptExecutor)driver).executeScript("return arguments[0].complete && typeof arguments[0].naturalWidth != \"undefined\" && arguments[0].naturalWidth > 0", ImageFile);
    if (!ImagePresent)
    {
         System.out.println("Image not displayed.");
    }
    else
    {
        System.out.println("Image displayed.");
    }
	//click on flights ->round trip->from->to->select depature and return
	
    driver.findElement(By.xpath("//span[text()='Flights']")).click();
    driver.findElement(By.xpath("//li[text()='Round Trip']")).click();
    driver.findElement(By.xpath("//span[text()='From']")).click();
    
    //from location
    driver.findElement(By.xpath("//input[@placeholder='From']")).sendKeys("Bangalore");
    Thread.sleep(2000);
	WebElement mousehoverfrom = driver.findElement(By.xpath("//div[@class='react-autosuggest__section-container react-autosuggest__section-container--first']//li[@role='option']"));
	Actions action = new Actions(driver);
	action.moveToElement(mousehoverfrom).click().build().perform();		
			
		
	//to location
	driver.findElement(By.xpath("//span[text()='To']")).click();
	driver.findElement(By.xpath("//input[@placeholder='To']")).sendKeys("Delhi");
	Thread.sleep(2000);
	WebElement mousehoverto = driver.findElement(By.xpath("//div[@class='react-autosuggest__section-container react-autosuggest__section-container--first']//li[@role='option']"));
	Actions action1 = new Actions(driver);
	action1.moveToElement(mousehoverto).click().build().perform();		
			
	//depature
	driver.findElement(By.xpath("//span[text()='DEPARTURE']")).click();
	Thread.sleep(2000);
	/*String date = "2019/04/26";
	
	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy"); 
	LocalDateTime now = LocalDateTime.now();
	System.out.println(dtf.format(now));
	String currentdate = dtf.format(now);http://marketplace.eclipse.org/marketplace-client-intro?mpc_install=3153377
	
	
	
	DateTimeFormatter dtf1 = DateTimeFormatter.ofPattern("MM/dd/yyyy");  
	LocalDateTime now1 = LocalDateTime.now(); 
	System.out.println(dtf.format(now1.plusDays(1)));
	String tomorrowdate = dtf.format(now1.plusDays(1));
			
	if(date.equals(currentdate) )
	{
		driver.findElement(By.xpath("//div[@class='DayPicker-Day DayPicker-Day--start DayPicker-Day--selected DayPicker-Day--today']")).click();
	}
	else if(date.equals(tomorrowdate)){
		driver.findElement(By.xpath("//div[@class='DayPicker-Day DayPicker-Day--end DayPicker-Day--selected']")).click();
	}
			*/
			
	//FROM TO
	String from = "Wed May 15 2019";//Fri May 03 2019
	String to = "Thu May 16 2019";
	driver.findElement(By.xpath("//div[@class='DayPicker-Day' and @aria-label='"+from+"']")).click();
	Thread.sleep(2000);
	driver.findElement(By.xpath("//div[@class='DayPicker-Day' and @aria-label='"+to+"']")).click();		
	
	driver.findElement(By.xpath("//a[text()='Search']")).click();
			
			
			
			
			
			
			
}
	
}
