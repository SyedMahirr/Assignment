package com.stadiumgoods.API;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class pokemonAPIValidation {
	public static void main(String[] args) throws IOException, JSONException {
		
		List<String> ret = new ArrayList<String>();
		List<String> ret1 = new ArrayList<String>();
		FileInputStream fInputStream = new FileInputStream("C:\\Users\\Ajay\\Desktop\\Freelancer\\Contests\\RestAPI automation Tester\\Input.xlsx");
		Workbook excelWookBook = new XSSFWorkbook(fInputStream);
		Sheet employeeSheet = excelWookBook.getSheetAt(0);		
		int firstRowNum = employeeSheet.getFirstRowNum();
		int lastRowNum = employeeSheet.getLastRowNum();
		DataFormatter formatter = new DataFormatter();
		for(int i=firstRowNum+1; i<lastRowNum+1; i++)
		{
			Row row = employeeSheet.getRow(i);
			
			int firstCellNum = row.getFirstCellNum();
			int lastCellNum = row.getLastCellNum();
			List<String> rowDataList = new ArrayList<String>();
			for(int j = firstCellNum; j < lastCellNum; j++)
			{
				String cellValue = formatter.formatCellValue(employeeSheet.getRow(i).getCell(j));
				if (cellValue.equals("null")) {
					cellValue = "";
				}
				rowDataList.add(cellValue);
			}
			ret.addAll(rowDataList);
	}		
		
		for(int i = 0; i < ret.size(); i=i+5) {
	        String temp = ret.get(i);//0,5,10,15,20,25,30
	        String temp1 = ret.get(i+1);
	        String temp2 = ret.get(i+2);
	        String temp3 = ret.get(i+3);
	        String temp4 = ret.get(i+4);//4,9,14,19,24,29,34        
	        
	        RestAssured.baseURI = "https://pokeapi.co/api/v2/pokemon";
	        RequestSpecification httpRequest = RestAssured.given();
	        Response response = httpRequest.request(Method.GET, "/"+temp);
	        String responseBody = response.getBody().asString();        
	        ObjectMapper mapper = new ObjectMapper();
	        Object json = mapper.readValue(responseBody, Object.class);
	        String indented = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(json);          
	        JSONObject jsr = new JSONObject(indented); // JSON object with above data
	        JSONArray content = jsr.getJSONArray("abilities");// get CONTENT which is Json array inside Demo
	       
	        for (int j = 0; j < content.length(); j++) { // iterate over array to get inner JSON objects and extract values inside
	            JSONObject record = content.getJSONObject(j); // each item of Array is a JSON object
	            boolean hiddenValue = record.getBoolean("is_hidden");
	            int slotValue = record.getInt("slot");
	            String nameValue = record.getJSONObject("ability").getString("name");
	            String urlValue = record.getJSONObject("ability").getString("url");
	            String hiddenValueVariable = String.valueOf(hiddenValue).toUpperCase();
	            String slotValueVariable = String.valueOf(slotValue);
	            if(nameValue.equals(temp1)) {
	            ret1.add(nameValue);
	            ret1.add(urlValue);
	            ret1.add(hiddenValueVariable);
	            ret1.add(slotValueVariable);
	            }
	            }            
	           
	        }  	

		int j=0;
		for(int i = 0; i < ret.size(); i=i+5) {
			if(ret.get(i+1).equals(ret1.get(j))) {				
            	System.out.println("Does Tag:\"abilities.ability.name\" value match with database value : " + ret.get(i+1).equals(ret1.get(j)) + " | Value from DB : " + ret.get(i+1) + " & Value from API :" + ret1.get(j)) ;	           
		      }
			else {
				System.out.println("Does Tag:\"abilities.ability.name\" value match with database value : " + ret.get(i+1).equals(ret1.get(j)) + " | Value from DB : " + ret.get(i+1) + " & Value from API :" + ret1.get(j)) ;
			}
			
			if(ret.get(i+2).equals(ret1.get(j+1)) ) {				
            	System.out.println("Does Tag:\"abilities.ability.url\" value match with database value : " + ret.get(i+2).equals(ret1.get(j+1)) + " | Value from DB : " + ret.get(i+2) + " & Value from API :" + ret1.get(j+1)) ;	           
		      }
			else {
				System.out.println("Does Tag:\"abilities.ability.url\" value match with database value : " + ret.get(i+2).equals(ret1.get(j+1)) + " | Value from DB : " + ret.get(i+2) + " & Value from API :" + ret1.get(j+1)) ;
			}
			
			if(ret.get(i+3).equals(ret1.get(j+2)) ) {				
            	System.out.println("Does Tag:\"abilities.ability.is_hidden\" value match with database value : " + ret.get(i+3).equals(ret1.get(j+2)) + " | Value from DB : " + ret.get(i+3) + " & Value from API :" + ret1.get(j+2)) ;	           
		      }
			else {
				System.out.println("Does Tag:\"abilities.ability.is_hidden\" value match with database value : " + ret.get(i+3).equals(ret1.get(j+2)) + " | Value from DB : " + ret.get(i+3) + " & Value from API ::" + ret1.get(j+2)) ;
			}
			
			if(ret.get(i+4).equals(ret1.get(j+3)) ) {				
            	System.out.println("Does Tag:\"abilities.ability.slot\" value match with database value : " + ret.get(i+4).equals(ret1.get(j+3)) + " | Value from DB : " + ret.get(i+4) + " & Value from API :" + ret1.get(j+3)) ;	           
		      }
			else {
				System.out.println("Does Tag:\"abilities.ability.slot\" value match with database value : " + ret.get(i+4).equals(ret1.get(j+3)) + " | Value from DB : " + ret.get(i+4) + " & Value from API :" + ret1.get(j+3)) ;
			}

            System.out.println("-------------------------------------------------------------------------");
			j = j+4;
			
				}
		
		
		}
		
	}
      
	        

		

/*
 * 
 * System.out.println(ret.size());
for(int i = 0; i < ret.size(); i++) {
			
			System.out.println(ret.get(i));
					
				}
 * 
  System.out.println(nameValue+" | "+urlValue+" | "+slotValue+" | "+hiddenValue+" | ");
	            if(nameValue.equals(temp1) && urlValue.equals(temp2) && slotValueVariable.equals(temp4) && hiddenValueVariable.equals(temp3)) {
		            System.out.println("-------------------------------------------------------------------------");
	            	System.out.println("Does Tag:\"abilities.ability.name\" value match with database value : " + nameValue.equals(temp1));
		            System.out.println("Does Tag:\"abilities.ability.url\" value match with database value : " + urlValue.equals(temp2));
		            System.out.println("Does Tag:\"abilities.slot\" value match with database value : " + slotValueVariable.equals(temp3));
		            System.out.println("Does Tag:\"abilities.slot\" value match with database value : " + hiddenValueVariable.equals(temp4));
		            System.out.println("-------------------------------------------------------------------------");
 */
 
