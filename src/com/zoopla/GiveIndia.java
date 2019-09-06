package com.zoopla;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class GiveIndia {
	
	public static void main(String[] agrs)
	{
		System.setProperty("webdriver.chrome.driver", "C:\\Softwares\\chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		driver.get("https://en.wikipedia.org/wiki/Selenium");
		driver.findElement(By.xpath("//span[@class='toctext' and text()='External links']")).click();
		driver.findElement(By.xpath("//a[@title='Oxygen']")).click();
		
		
		
		
	}

}
