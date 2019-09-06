package com.driver;

import java.io.File;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;



import org.apache.log4j.Logger;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import com.logconfig.LogConfig;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.repository.FileRepository;
import com.util.PropertyReader;
import com.wrapper.DriverWait;

public class DriverBase extends DriverProperty {
	// Sleep parameters
	public static final long SMALL_SLEEP = (long) Integer.parseInt(GLOBAL_PROP.get("smallSleep"));
	public static final long MEDIUM_SLEEP = (long) Integer.parseInt(GLOBAL_PROP.get("mediumSleep"));
	public static final long LARGE_SLEEP = (long) Integer.parseInt(GLOBAL_PROP.get("largeSleep"));
	public static final int SMALL_SLEEP_SECONDS = ((int)SMALL_SLEEP / 1000);
	public static final int MEDIUM_SLEEP_SECONDS = ((int)MEDIUM_SLEEP / 1000);
	public static final int LARGE_SLEEP_SECONDS = ((int)LARGE_SLEEP / 1000); 
	
	// Webdriver instance
	public static WebDriver driver=null;
	
	// Browser Type
	private static final String BROWSER_TYPE =GLOBAL_PROP.get("Browser");
	private static ChromeOptions chromeOption = null;
	private static DesiredCapabilities desiredCapabilities = null;
	protected static FirefoxProfile firefoxProfile = null;


	
	// Log instance
	private static Logger consoleLog = LogConfig.getLogger(DriverBase.class);
	
	// Extent Report
	public static ExtentReports extent;
	public static ExtentTest htmlTestReport;
	
	/**
	 * Before suite : initialize jobs
	 *  1. Extent Report
	 *  
	 */
	@BeforeSuite
	public void beforeSuite(){
		//extent = new ExtentReports(FileRepository.HTML_REPORT_FILE_PATH);
		//extent.loadConfig(new File(FileRepository.EXTENT_CONFIG_FILE_PATH));
	}
	
	/**
	 * After suite : Clean jobs
	 * 1. Extent Report
	 * 
	 */
	@AfterSuite
	public void AfterSuite(){
		extent.flush();
		closeBrowser();
	}
	
	/**
	 * 1. Check If driver instance is null.
	 * 2. If YES
	 * 3.		Read the global property adn get browser type.
	 * 4.		Initialize webdriver property.
	 * 5. Return the driver object.
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("deprecation")
	protected static synchronized WebDriver initWebDriver(){
		if(driver==null){
			try{
				switch (BROWSER_TYPE) {
				case "chrome":
					setDriverProperty("webdriver.chrome.driver",FileRepository.CHROME_DRIVER_PATH);				
					DriverWait.sleep(1); //Give Time To Selenium Server to Re-Vamp, minimum 25 sec if deploy in web container
					consoleLog.info("Initializing Chrome Driver....Please wait");
					driver = new ChromeDriver(setChromeProfile());
					break;
				case "firefox":
					setDriverProperty("webdriver.gecko.driver",FileRepository.GECKO_DRIVER_PATH);
					driver = new FirefoxDriver((Capabilities) setFFProfile());
					break;
				case "InternetExplorer":
					setDriverProperty("webdriver.ie.driver",FileRepository.INTERNETEXPLORER_DRIVER_PATH);
					driver = new InternetExplorerDriver();
					break;
				case "ghost":
					File file = new File("phantomjs.exe");				
					setDriverProperty("phantomjs.binary.path", file.getAbsolutePath());		
			        //driver = new PhantomJSDriver();
					/**
					 * set chrome.setHeadLess(true);
					 */
					//TO-DO
					break;
				default:
					setDriverProperty("webdriver.chrome.driver",FileRepository.CHROME_DRIVER_PATH);
					driver = new ChromeDriver(setChromeProfile());
					break;
				}	
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);	
		DriverWait.sleep(5);
		driver.manage().window().maximize();
		consoleLog.info(BROWSER_TYPE + " Browser Loaded successfully");
		return driver;
	}
	
	/**
	 * Description : quite will invoke to dispose and will close all browser windows session safely
	 */
	public static void closeBrowser(){
		driver.quit();
	}
	

	
	/**
	 * 
	 * @param driverSysProperty
	 * @param driverPath
	 */
	private static void setDriverProperty(String driverSysProperty,String driverPath){
		System.setProperty(driverSysProperty, driverPath);
	}
	
	/**
	 * 1. Create instance for chrome Desired capabilities
	 * @return
	 * @throws Exception
	 */
	private static final DesiredCapabilities setChromeProfile() throws Exception {
	    String pathtoDownloads = FileRepository.SERVER_DOWNLOAD_FILE_PATH;
	    consoleLog.info("Path to Downloads folder :  "+pathtoDownloads);   
	    Map<String, Object> chromePrefs = new HashMap<String, Object>();
	    chromePrefs.put("profile.default_content_settings.popups", 0);
	    chromePrefs.put("download.default_directory", pathtoDownloads);
	    chromeOption = new ChromeOptions();
	    chromeOption.setExperimentalOption("prefs", chromePrefs);
	    desiredCapabilities = DesiredCapabilities.chrome();
	    desiredCapabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
	    desiredCapabilities.setCapability(ChromeOptions.CAPABILITY, chromeOption);
	    return desiredCapabilities;
	}
	
	/**
	 * 1. Create instance for FireFox profile
	 * @return
	 * @throws Exception
	 */
	private static final FirefoxProfile setFFProfile() throws Exception {
		String pathtoDownloads = FileRepository.SERVER_DOWNLOAD_FILE_PATH;
	    firefoxProfile = new FirefoxProfile();
	    consoleLog.info("Path to Downloads folder :  "+pathtoDownloads);
	    firefoxProfile.setPreference("browser.download.folderList", 2);
	    firefoxProfile.setPreference("browser.download.manager.showWhenStarting", false);
	    firefoxProfile.setPreference("browser.helperApps.neverAsk.saveToDisk","application/xml,text/plain,text/xml,text/csv,image/jpeg,application/vnd.openxmlformats-officedocument.spreadsheetml.sheet,application/x-shockwave-flash,application/futuresplash");
	    firefoxProfile.setPreference("browser.download.dir", pathtoDownloads);
	    firefoxProfile.setPreference("intl.accept_languages", PropertyReader.getData(FileRepository.SERVER_GLOBAL_PROPERTY).get("Language").trim());
	    return firefoxProfile;
	}

}
