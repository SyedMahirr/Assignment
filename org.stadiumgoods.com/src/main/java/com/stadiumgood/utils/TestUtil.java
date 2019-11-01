package com.stadiumgood.utils;

import java.io.*;
import java.lang.*;
import java.util.*;
import java.util.concurrent.*;

import javax.mail.*;

import org.apache.commons.io.FileUtils;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.apache.commons.mail.MultiPartEmail;
import org.apache.commons.mail.SimpleEmail;
import org.apache.poi.util.ArrayUtil;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.apache.poi.ss.usermodel.*;

import org.stadiumgoods.driver.Driver;
import com.stadiumgoods.listeners.ListenerClass;
import com.stadiumgood.reports.ExtentReport;
import com.stadiumgood.reports.LogStatus;

/*
 * All the utilities needed for the framework is placed in this class including excel utilities, screenshot capture.
 * We have used method overloading concept in getCellContent Method.
 */
public class TestUtil extends Driver {

	public static FileInputStream fs;
	public static XSSFWorkbook workbook;
	public static XSSFSheet sheet;
	public static List<String> testCases = new ArrayList<String>();
	public static List<String> runStatus = new ArrayList<String>();
	public static List<String> testDescription = new ArrayList<String>();
	public static List<String> invocationCount = new ArrayList<String>();
	public static List<String> priority = new ArrayList<String>();
	public static HashMap<Integer, String> rowAndTestCaseMap = new HashMap<Integer, String>();
	public static String screenshotPath = ReadPropertyFile.get("ScreenshotPath");
	public static WebDriver driver;
	public static DataFormatter formatter = new DataFormatter();
	/*
	 * Reads the data from the excel sheet and store the values in respective lists
	 * which will be used in annotation transformer class
	 */

	public TestUtil(WebDriver driver) {
		this.driver = driver;
	}

	public static void getRunStatus() throws Exception {
		try {
			fs = new FileInputStream(ReadPropertyFile.get("TestDataLocation"));
			workbook = new XSSFWorkbook(fs);
			sheet = workbook.getSheet("RunManager");
			for (int i = 1; i <= getLastRowNum("RunManager"); i++) {
				// rowAndTestCaseMap.put(i,sheet.getRow(i).getCell(0).getStringCellValue().toString());
				testCases.add(getCellContent("RunManager", i, "TestCaseName"));
				testDescription.add(getCellContent("RunManager", i, "Test Case Description"));
				runStatus.add(getCellContent("RunManager", i, "Execute"));
				invocationCount.add(getCellContent("RunManager", i, "InvocationCount"));
				priority.add(getCellContent("RunManager", i, "Priority"));
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}

	/*
	 * public static Object getRowNumForTestCase(String TestCaseName) { Object
	 * a=null; for(Map.Entry m:rowAndTestCaseMap.entrySet()){
	 * if(m.getValue().toString().equalsIgnoreCase(TestCaseName)) { a= m.getKey(); }
	 * } return a; }
	 */

	/*
	 * Takes rowname and sheetname as parameter return row number based of rowname
	 */
	public static int getRowNumForRowName(String sheetname, String rowName) {
		int rownum = 0;
		sheet = workbook.getSheet(sheetname);
		for (int i = 1; i <= getLastRowNum(sheetname); i++) {
			if (rowName.equalsIgnoreCase(sheet.getRow(i).getCell(0).getStringCellValue())) {
				rownum = i;
				break;
			}
		}

		return rownum;
	}

	/*
	 * Takes columnname and sheetname as parameter return column number based of
	 * columnheader
	 */

	public static int getColumnNumForColumnName(String sheetname, String columnname) {
		int colnum = 0;
		sheet = workbook.getSheet(sheetname);
		for (int i = 0; i < getLastColumnNum(sheetname, 0); i++) {
			if (columnname.equalsIgnoreCase(sheet.getRow(0).getCell(i).getStringCellValue())) {
				colnum = i;
				break;
			}
		}

		return colnum;

	}

	/*
	 * Takes sheetname as parameter return last row number of the sheet
	 */
	public static int getLastRowNum(String sheetname) {
		return workbook.getSheet(sheetname).getLastRowNum();
	}

	/*
	 * Takes sheetname, row number as parameter return last cell number of the row
	 */
	public static int getLastColumnNum(String sheetname, int rownum) {
		return workbook.getSheet(sheetname).getRow(rownum).getLastCellNum();
	}

	/*
	 * Takes sheetname, row number, column number as parameter return cell value
	 */
	public static String getCellContent(String sheetname, int rownum, int colnum) {
		sheet = workbook.getSheet(sheetname);
		return sheet.getRow(rownum).getCell(colnum).getStringCellValue().toString();

	}

	/*
	 * Takes sheetname, row number, column name as parameter return cell value
	 */
	public static String getCellContent(String sheetname, int rownum, String columnname) {
		sheet = workbook.getSheet(sheetname);
		return sheet.getRow(rownum).getCell(getColumnNumForColumnName(sheetname, columnname)).getStringCellValue()
				.toString();

	}

	/*
	 * Takes sheetname, row name, column name as parameter return cell value
	 */
	public static String getCellContent(String sheetname, String rowname, String columnname) {
		sheet = workbook.getSheet(sheetname);
		int rownum = getRowNumForRowName(sheetname, rowname);
		System.out.println(rownum);
		int colnum = getColumnNumForColumnName(sheetname, columnname);
		System.out.println(colnum);
		return sheet.getRow(rownum).getCell(colnum).getStringCellValue().toString();

		
		
	}

	/*
	 * Takes screenshot Make sure parameter ScreenshotsRequired is Yes in
	 * TestRunDetails.properties
	 * 
	 */
	public static void takeScreenshot() {

		if (ReadPropertyFile.get("ScreenshotsRequired").equalsIgnoreCase("yes")) {

			File scrFile = ((TakesScreenshot) Driver.driver).getScreenshotAs(OutputType.FILE);
			try {
				if (screenshotPath.equals("")) {
					FileUtils.copyFile(scrFile, new File(
							"./screenshots/" + ListenerClass.TestCaseName + "/" + System.currentTimeMillis() + ".png"));
				} else {
					FileUtils.copyFile(scrFile, new File(screenshotPath + "/screenshots/" + ListenerClass.TestCaseName
							+ "/" + System.currentTimeMillis() + ".png"));
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	/*
	 * Captures screenshot and returns the screenshot path
	 */
	public static String pullScreenshotPath() {

		String destination = null;
		if (ReadPropertyFile.get("ScreenshotsRequired").equalsIgnoreCase("yes")) {
			File scrFile = ((TakesScreenshot) Driver.driver).getScreenshotAs(OutputType.FILE);
			try {
				if (screenshotPath.equals("")) {

					destination = System.getProperty("user.dir") + "\\screenshots\\" + ListenerClass.TestCaseName + "\\"
							+ System.currentTimeMillis() + ".png";
					FileUtils.copyFile(scrFile, new File(destination));
				} else {
					destination = screenshotPath + "\\screenshots\\" + ListenerClass.TestCaseName.replaceAll(" ", "")
							+ "\\" + System.currentTimeMillis() + ".png";
					FileUtils.copyFile(scrFile, new File(destination));
				}

			} catch (Exception e) {
				e.printStackTrace();

			}

		}
		System.out.println(destination);
		return destination;

	}

	/*
	 * Gives a base64 image which is used to append the screenshots in the extent
	 * report. Converting to base64 format avoids screenshots broken image if sent
	 * the exent report through email.
	 */
	public static String getBase64Image(String screenshotpath) {
		String base64 = null;
		try {
			InputStream is = new FileInputStream(screenshotpath);
			byte[] imageBytes = IOUtils.toByteArray(is);
			base64 = Base64.getEncoder().encodeToString(imageBytes);
		} catch (Exception e) {

		}
		return base64;

	}

	/*
	 * Sends test results to the respective stakeholders Make sure to set the
	 * parameter SendExecutionResultsInEmail to Yes in TestRunDetails.properties
	 */

	public static void sendEmailWithResults() throws Exception {

		if (ReadPropertyFile.get("SendExecutionResultsInEmail").equalsIgnoreCase("yes")) {

			EmailAttachment attachment = new EmailAttachment();
			attachment.setPath(ExtentReport.extentreportpath);
			attachment.setDisposition(EmailAttachment.ATTACHMENT);
			attachment.setDescription("Execution Results");
			attachment.setName("results.html");

			MultiPartEmail email = new MultiPartEmail();
			email.setHostName("smtp.gmail.com");
			email.setSmtpPort(465);
			email.setAuthenticator(
					new DefaultAuthenticator(ReadPropertyFile.get("FromEmail"), ReadPropertyFile.get("EmailPassword")));
			email.setSSLOnConnect(true);
			email.setStartTLSEnabled(true);
			email.setFrom(ReadPropertyFile.get("FromEmail"));
			email.setSubject("Results");
			email.setMsg("Hi Team,\n\n Please find the attached test Automation Execution Results\n\n");
			try {
				email.addTo(getList("ToEmails"));
				email.addCc(getList("CCEmails"));
				email.addBcc(getList("BCCEmails"));
			} catch (Exception e) {

			}
			email.attach(attachment);
			email.send();
			System.out.println("Email sent-->");
		}
	}

	/*
	 * Used to separate email list from the TestRunDetails.properties based on comma
	 * and return them as a String array.
	 */
	public static String[] getList(String maillist) {
		String[] toList = null;
		toList = ReadPropertyFile.get(maillist).split(",");

		return toList;
	}

	/*
	 * Used to return the rownumber of the test cases for multiple iterations.
	 * Suppose if testcase 1 is available in row 4 and 7 is test data , it return
	 * the arraylist with values 4,7
	 */
	private static ArrayList<Integer> getNumberofIterationsForATestCase(String sheetname, String TestCaseName) {
		ArrayList<Integer> a = new ArrayList<Integer>();
		for (int i = 1; i <= getLastRowNum(sheetname); i++) {
			if (TestCaseName.equalsIgnoreCase(getCellContent(sheetname, i, 0))) {
				a.add(i);
			}
		}

		return a;
	}

	public int findCellByString(String sheetname, String text) throws IOException {
		int columnNotFound = 0;
		FileInputStream fis = new FileInputStream("./src/test/resources/testdata.xlsx");
		workbook = new XSSFWorkbook(fis);
		sheet = workbook.getSheet(sheetname);
		for (Row row : sheet) {
			for (Cell cell : row) {
				if (text.equals(cell.getStringCellValue()))
					return cell.getColumnIndex();
			}
		}
		return columnNotFound;
	}

	/*
	 * public HashMap<String, String> loadTestDataModXMLForMessagesFromSPT(String
	 * sheetname, int iCurrentTestCaseID) throws IOException { HashMap<String,
	 * String> testDataSet = new HashMap<String, String>(); FileInputStream fis =
	 * new FileInputStream("./src/test/resources/testdata.xlsx"); workbook = new
	 * XSSFWorkbook(fis); sheet = workbook.getSheet(sheetname);
	 * System.out.println(testDataSet); try { // get testcaseId
	 * testDataSet.put("TestCaseID",
	 * formatter.formatCellValue(sheetname.getRow(iCurrentTestCaseID).getCell(
	 * findCellByString(sheet, "TestCaseID"))));
	 * 
	 * catch (Exception e) { e.printStackTrace(); } return testDataSet;
	 * 
	 * }
	 */

	public static Map<String, String> loadTestDataModXMLCamt035(String sheetname, int iCurrentTestCaseID,
			String coloumn) throws IOException {
		Map<String, String> testDataSet = new HashMap<String, String>();
		FileInputStream fis = new FileInputStream("./src/test/resources/testdata.xlsx");
		workbook = new XSSFWorkbook(fis);
		sheet = workbook.getSheet(sheetname);
		System.out.println(testDataSet);
		// testDataSet.put(sheetname,
		// formatter.formatCellValue(sheet.getRow(iCurrentTestCaseID).getCell(findCellByString(sheet,
		// coloumn))));

		return testDataSet;

	} // end method

}
