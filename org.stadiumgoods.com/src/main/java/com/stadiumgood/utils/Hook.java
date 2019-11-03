package com.stadiumgood.utils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriverException;
import org.stadiumgoods.driver.Driver;

import cucumber.api.Scenario;
import cucumber.api.java.Before;

public class Hook extends Driver {
	
	Driver driver;
	@Before
	// Gets the name of the scenario
	public void beforeScenario(Scenario scenario) {
		scenario.getName();
	}
	
	public void embedScreenshot(Scenario scenario) {
		try {
			
			byte[] screenshot = ((TakesScreenshot) Driver.driver).getScreenshotAs(OutputType.BYTES);
			scenario.embed(screenshot, "img/png");
		}
		catch (WebDriverException noscreenshotSupport) {
			// logger 
			
		}
	}
	
	//public void afterScenario
	
	

}
