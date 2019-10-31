package com.stadiumgoods.pagefactory;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import org.stadiumgoods.driver.Driver;
import com.stadiumgoods.listeners.*;
import com.stadiumgood.reports.*;
import com.stadiumgood.utils.*;


public class LoginPage {
	
	@FindBy(name="username")
	WebElement txtbox_username;
	
	@FindBy(name="password")
	WebElement txtbox_password;
	
	@FindBy(xpath="//*[text()='LOGIN']")
	WebElement btn_login;

	public LoginPage() {
		PageFactory.initElements(Driver.driver, this);
	}
	public  HomePage login() {
		System.out.println(TestUtil.getCellContent("TestData", ListenerClass.TestcaseName, "username"));
		SeleniumUtils.sendkeys(txtbox_username,TestUtil.getCellContent("TestData", ListenerClass.TestcaseName, "username"));
		SeleniumUtils.sendkeys(txtbox_password,TestUtil.getCellContent("TestData", ListenerClass.TestcaseName, "password"));
		SeleniumUtils.click(btn_login);
		return new HomePage();
	}
}
