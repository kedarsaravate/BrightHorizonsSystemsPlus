package reporting;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentManager {
	public static ExtentSparkReporter sparkReporter;
	public static ExtentReports extent;
	public static ExtentTest test;

	public static void setExtentReport() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss"); 
		String timestamp = dateFormat.format(new Date()); 

		sparkReporter = new ExtentSparkReporter(System.getProperty("user.dir")+ "/reports/MyExtentReport_"+timestamp+".html");
		
		sparkReporter.config().setDocumentTitle("Automation Report");
		sparkReporter.config().setReportName("BrightHorizon Testing");

		extent = new ExtentReports();
		extent.attachReporter(sparkReporter);
		
        extent.setSystemInfo("OS", System.getProperty("os.name"));
        extent.setSystemInfo("Environment", "QA");
        extent.setSystemInfo("Java Version", System.getProperty("java.runtime.version"));
        extent.setSystemInfo("User Name", "Kedar Saravate");

	}

	public static void endExtentReport() {
		extent.flush();
	}
}
