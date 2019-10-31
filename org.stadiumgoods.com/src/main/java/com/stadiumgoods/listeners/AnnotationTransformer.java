package com.stadiumgoods.listeners;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import org.testng.IAnnotationTransformer;
import org.testng.annotations.ITestAnnotation;

import com.stadiumgood.utils.TestUtil;

public class AnnotationTransformer implements IAnnotationTransformer{
	int count=0;

	@Override
	public void transform(ITestAnnotation annotation, Class testClass, Constructor testConstructor, Method testMethod) {

		
		try {
			if(count==0) {
				TestUtil.getRunStatus();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		for(int i=0;i<TestUtil.testCases.size();i++) {
			if(testMethod.getName().equalsIgnoreCase(TestUtil.testCases.get(i)))
			{	
				annotation.setDataProvider("dataProviderForIterations");								//sets the dataprovider to all the test methods
				annotation.setDataProviderClass(TestUtil.class);
				annotation.setRetryAnalyzer(RetryFailedTestCases.class); 								//sets the retry analyser for all the test cases
				annotation.setPriority(Integer.parseInt(TestUtil.priority.get(i)));					//sets the priority for all the test cases based on the excel sheet input
				annotation.setDescription(TestUtil.testDescription.get(i)); 							//sets the description for all the test cases based on the excel sheet input
				annotation.setInvocationCount(Integer.parseInt(TestUtil.invocationCount.get(i)));		//sets the invocation count for all the test cases based on the excel sheet input
				if(TestUtil.runStatus.get(i).equalsIgnoreCase("no")) {
					annotation.setEnabled(false);														//sets the enabled parameter for all the test cases based on the excel sheet input
					break;
				}
			} 
		}

		count++;
	}
}



