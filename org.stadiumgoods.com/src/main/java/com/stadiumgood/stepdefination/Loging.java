package com.stadiumgood.stepdefination;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.stadiumgoods.driver.Driver;


import com.stadiumgood.utils.*;
import com.stadiumgoods.listeners.ListenerClass;
import com.stadiumgood.utils.*;
import com.stadiumgood.utils.*;
import com.stadiumgood.utils.TestUtil;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class Loging extends Driver {

	@Given("^User Launches Browser$")
	public void user_Launches_Browser() throws Throwable {
	   Driver.initialize();
	   
	}
	
	@When("^User Enters User Name$")
	public void user_Enters_User_Name() throws Throwable {
		System.out.println(TestUtil.getCellContent("TestData", ListenerClass.TestcaseName, "username"));
	WebElement ele = 	driver.findElement(By.name("username"));
	SeleniumUtils.sendkeys(ele ,TestUtil.getCellContent("TestData", ListenerClass.TestcaseName, "username"));
	
	}

	@When("^User Enters Password$")
	public void user_Enters_Password() throws Throwable {
	   
	}

	@When("^User Clicks Login Button$")
	public void user_Clicks_Login_Button() throws Throwable {
	   
	}

	@Then("^User Successfully lands on the Home Page$")
	public void user_Successfully_lands_on_the_Home_Page() throws Throwable {
	    
	}
}
