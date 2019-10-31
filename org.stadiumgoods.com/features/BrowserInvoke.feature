Feature: Login into the Application 
@invokeBrowser
Scenario: launch Browser

Given User Launches Browser
When User Enters User Name 
And User Enters Password
And User Clicks Login Button 
Then User Successfully lands on the Home Page 