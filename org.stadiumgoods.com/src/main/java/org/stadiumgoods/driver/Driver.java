package org.stadiumgoods.driver;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import java.beans.EventHandler;

import org.stadiumgoods.driver.Driver;
import com.relevantcodes.extentreports.LogStatus;
import com.stadiumgood.utils.*;
import com.stadiumgood.reports.*;

public class Driver {

	public static WebDriver driver = null;

	public Driver() {
		
	}
	

	public static void initialize() {
		String browser = ReadPropertyFile.get("Browser");
		try {
			if (browser.equalsIgnoreCase("chrome")) {
				System.setProperty("webdriver.chrome.driver", ".//src/test/resources/chromedriver.exe");
				ChromeOptions options = new ChromeOptions();
				options.addArguments("--incognito");
				DesiredCapabilities capabilities = new DesiredCapabilities();
				capabilities.setCapability(ChromeOptions.CAPABILITY, options);
				driver = new ChromeDriver();
			} else if (browser.equalsIgnoreCase("firefox")) {
				System.setProperty("webdriver.gecko.driver", ".//src/test/resources/geckodriver.exe");
				FirefoxOptions FFoptions = new FirefoxOptions();
				FFoptions.addArguments("--incognito");
				driver = new FirefoxDriver(FFoptions);
			}
		} catch (Exception e) {
			System.out.println("Broweser did not invoke");
		}
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Integer.parseInt(ReadPropertyFile.get("WaitTime")), TimeUnit.SECONDS);
		EventHandlerInit();
		driver.get(ReadPropertyFile.get("url"));
		driver.manage().deleteAllCookies();
	}


	public void wrapUp() {
		Driver.driver.close();
	}

	public static void quit() {
		if (driver != null) {
			driver.quit();
		}
	}

	private static void EventHandlerInit() {
		EventFiringWebDriver eventhandle = new EventFiringWebDriver(driver);
		EventCapture capture = new EventCapture();
		eventhandle.register(capture);
		driver = eventhandle;
	}

}
