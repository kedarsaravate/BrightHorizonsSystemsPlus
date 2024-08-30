package reporting;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class ExtentReportManager extends ExtentManager implements  ITestListener{
	
	public static void logStepInfo(String stepInfo) {
		test.log(Status.INFO, "<b>"+stepInfo+"</b>");
	}
	
	public static void logStepInfo(String stepInfo,String stepStatus) {
		if(stepStatus.equals("PASS")) {
			test.log(Status.PASS, "<b>"+stepInfo+"</b>");	
		}else if(stepStatus.equals("FAIL")) {
			test.log(Status.FAIL, "<b>"+stepInfo+"</b>");	
		}
	}

	public void onTestStart(ITestResult result) {
		test = extent.createTest(result.getName());	
		test.info(result.getMethod().getDescription());
		test.info(Arrays.toString(result.getMethod().getGroups()));
	}

	public void onTestSuccess(ITestResult result) {
		if (result.getStatus() == ITestResult.SUCCESS) {
			test.log(Status.PASS, "Test Case PASSED is :" + result.getName());
		}
	}

	public void onTestFailure(ITestResult result) {
		if (result.getStatus() == ITestResult.FAILURE) {
		     test.log(Status.FAIL,MarkupHelper.createLabel(result.getName() + " - Test Case Failed", ExtentColor.RED));
		     test.log(Status.FAIL,MarkupHelper.createLabel(result.getThrowable() + " - Test Case Failed", ExtentColor.RED));
		}
	}

	public void onTestSkipped(ITestResult result) {
		if (result.getStatus() == ITestResult.SKIP) {
			test.info(result.getMethod().getDescription());
			test.info(Arrays.toString(result.getMethod().getGroups()));
		    test.log(Status.SKIP, "Skipped Test case is: " + result.getName());
		}
	}

	public void onFinish(ITestContext context) {
		extent.flush();
		try {
		      Map<String, Object> testResult = new HashMap<>();
		      testResult.put("TotalTestCaseCount", context.getAllTestMethods().length);
		      testResult.put("PassedTestCaseCount", context.getPassedTests().size());
		      testResult.put("FailedTestCaseCount", context.getFailedTests().size());
		      testResult.put("SkippedTestCaseCount", context.getSkippedTests().size());

		      ObjectMapper mapper = new ObjectMapper();
		      mapper.enable(SerializationFeature.INDENT_OUTPUT);
		      String filePath = "test-output/ExtentReport/TestExecutionReport.json";
		      mapper.writeValue(new File(filePath), testResult);
		    } catch (IOException e) {
		    	throw new RuntimeException("Error occurred while writing to TestExecutionReport.json file: ",e);
		    }
	}
}
