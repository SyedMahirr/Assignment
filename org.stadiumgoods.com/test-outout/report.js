$(document).ready(function() {var formatter = new CucumberHTML.DOMFormatter($('.cucumber-report'));formatter.uri("BrowserInvoke.feature");
formatter.feature({
  "line": 1,
  "name": "Login into the Application",
  "description": "",
  "id": "login-into-the-application",
  "keyword": "Feature"
});
formatter.scenario({
  "line": 3,
  "name": "launch Browser",
  "description": "",
  "id": "login-into-the-application;launch-browser",
  "type": "scenario",
  "keyword": "Scenario",
  "tags": [
    {
      "line": 2,
      "name": "@invokeBrowser"
    }
  ]
});
formatter.step({
  "line": 5,
  "name": "User Launches Browser",
  "keyword": "Given "
});
formatter.step({
  "line": 6,
  "name": "User Enters User Name",
  "keyword": "When "
});
formatter.step({
  "line": 7,
  "name": "User Enters Password",
  "keyword": "And "
});
formatter.step({
  "line": 8,
  "name": "User Clicks Login Button",
  "keyword": "And "
});
formatter.step({
  "line": 9,
  "name": "User Successfully lands on the Home Page",
  "keyword": "Then "
});
formatter.match({
  "location": "Loging.user_Launches_Browser()"
});
formatter.result({
  "duration": 12598355500,
  "status": "passed"
});
formatter.match({
  "location": "Loging.user_Enters_User_Name()"
});
formatter.result({
  "duration": 111314900,
  "status": "passed"
});
formatter.match({
  "location": "Loging.user_Enters_Password()"
});
formatter.result({
  "duration": 20000,
  "status": "passed"
});
formatter.match({
  "location": "Loging.user_Clicks_Login_Button()"
});
formatter.result({
  "duration": 40600,
  "status": "passed"
});
formatter.match({
  "location": "Loging.user_Successfully_lands_on_the_Home_Page()"
});
formatter.result({
  "duration": 46000,
  "status": "passed"
});
});