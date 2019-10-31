package org.stadiumgoods.com.testrunner;


import cucumber.api.CucumberOptions;

import cucumber.api.CucumberOptions;
import cucumber.api.testng.AbstractTestNGCucumberTests;


@CucumberOptions(
		features = "features", //the path of the feature files
		glue={"com.stadiumgood.stepdefination"}, //the path of the step definition files
		tags = {"@invokeBrowser"},	
		
		format= {"pretty","html:test-outout", "json:json_output/cucumber.json", "junit:junit_xml/cucumber.xml"}, //to generate different types of reporting
		monochrome = true, //display the console output in a proper readable format
		strict = true, //it will check if any step is not defined in step definition file
		dryRun = false //to check the mapping is proper between feature file and step def file
		//tags = {"~@SmokeTest" , "~@RegressionTest", "~@End2End"}		
        )

public class RunCucumberTest extends AbstractTestNGCucumberTests{}





	



