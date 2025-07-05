package baseclass;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import actiondriver.actionclass;
import io.github.bonigarcia.wdm.WebDriverManager;
import utilityclass.ExtentManager;

/**
 * Author: Shreyas Bhagat Date: 24/05/2025 BaseClass provides necessary
 * functionalities for handling WebDriver actions and loading configurations.
 */

public class BaseClass {
	public static Properties prop;

	// Thread-safe WebDriver
	public static ThreadLocal<RemoteWebDriver> driver = new ThreadLocal<>();

	/**
	 * Loads user configurations and initializes the Extent Reports before the test
	 * suite starts.
	 */
	@BeforeSuite
	public void loadUser() throws Throwable {
		ExtentManager.setExtent();
		// DOMConfigurator.configure("log4j.xml");
		try {
			prop = new Properties();
			FileInputStream ip = new FileInputStream(
					System.getProperty("user.dir") + "\\Configuration\\config.properties");
			prop.load(ip);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new Exception("Configuration file not found");
		} catch (IOException e) {
			e.printStackTrace();
			throw new Exception("Error loading configuration file");
		}
	}

	public static WebDriver getDriver() {
		return driver.get();
	}

	/**
	 * Launches the web application in the specified browser and navigates to the
	 * URL defined in the properties file.
	 * 
	 * @param browserName The name of the browser to launch (e.g., Chrome, Firefox,
	 *                    Edge).
	 * @param URL         The URL of the web application to launch.
	 * @throws Throwable if there is an error launching the browser or navigating to
	 *                   the URL.
	 */
	public void launchWebApp(String browserName, String URL) throws Throwable {
		if (browserName.equalsIgnoreCase("Chrome")) {

			/*	WebDriverManager.chromedriver().setup();
			driver.set(new ChromeDriver());
			*/
			ChromeOptions options = new ChromeOptions();
			File crxFile = new File(System.getProperty("user.dir") + "/src/test/resources/extensions/ublock-origin.crx");
			System.out.println("CRX file exists: " + crxFile.exists());
			System.out.println("CRX file path: " + crxFile.getAbsolutePath());

			if (crxFile.exists()) {
			    try {
			        options.addExtensions(crxFile);
			    } catch (Exception e) {
			        System.out.println("Error loading CRX extension: " + e.getMessage());
			        e.printStackTrace();
			    }
			} else {
			    System.out.println("Skipping CRX extension – file not found.");
			}

			// Add other Chrome arguments
			options.addArguments("--disable-notifications");
			options.addArguments("--disable-popup-blocking");
			// Launch browser
			driver.set(new ChromeDriver(options));
		
		
		} else if (browserName.equalsIgnoreCase("Firefox")) {
			WebDriverManager.firefoxdriver().setup();
			driver.set(new FirefoxDriver());
		} else if (browserName.equalsIgnoreCase("Edge")) {
			WebDriverManager.edgedriver().setup();
			driver.set(new EdgeDriver());
		} else {
			throw new Exception("Browser is not correct");
		}
		// Maximize window
		getDriver().manage().window().maximize();
		getDriver().manage().deleteAllCookies();

		// Set timeouts using Duration
		getDriver().manage().timeouts()
		.implicitlyWait(Duration.ofSeconds(Integer.parseInt(prop.getProperty("implicitWait", "30"))));

		getDriver().manage().timeouts()
		.pageLoadTimeout(Duration.ofSeconds(Integer.parseInt(prop.getProperty("pageLoadTimeOut", "60"))));

		// Launch URL
		getDriver().get(URL);


		System.out.println("Launching " + browserName + " with URL: " + URL);

		// Wait for the page to load completely
		actionclass action = new actionclass();
		int timeout = Integer.parseInt(prop.getProperty("pageLoadTimeOut", "30"));
		action.waitForPageLoad(getDriver(), timeout);



	}

	/**
	 * Closes the browser and cleans up resources after the test suite finishes.
	 */
	@AfterSuite
	public void afterSuite() {
		if (getDriver() != null) {
			getDriver().quit(); // Close the browser and clean up resources
		}
		ExtentManager.endReport();
	}
}
