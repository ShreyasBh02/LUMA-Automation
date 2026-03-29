package utilityclass;

import java.io.IOException;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;


/**
 * Author: Shreyas Bhagat
 * Date: 24/05/2025
 * ExtentManager manages the configuration and generation of Extent Reports for test execution.
 */

public class ExtentManager {

	private static ExtentSparkReporter htmlReporter;
	private static ExtentSparkReporter htmlReporterFAIL;
	protected  static ExtentReports extent;
	protected  static ExtentTest test;



	public static void setExtent() throws Throwable {
		// Initialize the main report
		htmlReporter= new ExtentSparkReporter(System.getProperty("user.dir")+"/test-output/ExtentReport/"+"Report_LUMA"+".html");
		
		// Initialize the failure report
		htmlReporterFAIL = new ExtentSparkReporter(System.getProperty("user.dir")+"/test-output/ExtentReport/"+"ReportFAIL_LUMA"+".html")
				.filter().statusFilter() .as(new Status[] { Status.FAIL }).apply();

		htmlReporter.loadXMLConfig(System.getProperty("user.dir")+"/extent_CONFIG.xml");
		htmlReporter.config().setTheme(Theme.DARK);

		extent = new ExtentReports();
		extent.attachReporter(htmlReporter,htmlReporterFAIL);

		//	extent.setSystemInfo("HostName", "MyHost");
		extent.setSystemInfo("ProjectName", "LUMA");
		extent.setSystemInfo("Tester", "Shreyasrb");
		extent.setSystemInfo("OS", "Win10");
		extent.setSystemInfo("Browser", System.getProperty("browser"));
	}
	
	
	/**
     * Finalizes the report and writes it to the output.
     */
    public static void endReport() {
        extent.flush();
    }
    
    /**
     * Creates a new test case in the report.
     * 
     * @param testName The name of the test case.
     * @return The ExtentTest object for logging.
     */
    public static ExtentTest createTest(String testName) {
        test = extent.createTest(testName);
        return test;
    }
    /**
     * LoggerUtil a message with the specified status.
     * 
     * @param message The message to log.
     * @param status  The status of the log (INFO, PASS, FAIL, etc.).
     */
    public static void log(String message, Status status) {
        if (test != null) {
            test.log(status, message);
        } else {
            System.err.println("Test not initialized. Please create a test case first.");
        }
    }
    
    
}
