package com.stadiumgood.utils;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.stadiumgoods.driver.Driver;
import com.stadiumgood.*;
import com.stadiumgood.reports.*;
import com.stadiumgood.utils.*;

import cucumber.api.Scenario;

public class CommonUtil extends Driver {

	Driver driver; 
	
	public static void highlightElement(WebElement element) {
		((JavascriptExecutor)Driver.driver).executeScript("arguments[0].style.border='3px solid red'", element);
	}

	public static boolean isVisible(WebElement element) {
		boolean flag=false;
		Driver.driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		try
		{
			if(element.isDisplayed())
			{
				flag=true;
			}
		}
		catch (Exception e) {

		}
		return flag;
	}

	
	
	
}
