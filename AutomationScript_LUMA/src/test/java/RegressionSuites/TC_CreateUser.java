package RegressionSuites;

import java.time.Duration;
import java.util.HashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;

import dataprovider.DataProviders;
import baseclass.BaseClass;
import dataprovider.DataProviders;
import pomclass.CreateAnAccount;
import pomclass.CustomerLogin;
import pomclass.HomePage;
import pomclass.MyAccountPage;
import utilityclass.EmailHelper;
import utilityclass.ExtentManager;
import utilityclass.LoggerUtil;


public class TC_CreateUser extends BaseClass {

	CustomerLogin login;
	HomePage 	  homePage;
	CreateAnAccount createAnAccount;
	EmailHelper emailHelper;
	MyAccountPage myAccountPage;


	WebDriverWait wait;	
	private String mainWindowHandle;

	private static final Logger Logs = LogManager.getLogger(LoggerUtil.class.getClass());


	@BeforeMethod
	public void websetup() throws Throwable {
		String browser =prop.getProperty("Browser");
		String URL =prop.getProperty("URL");
		launchWebApp(browser, URL); 
		initialize();
		ExtentManager.setExtent();
	}



	@Test(priority = 1,dataProvider = "Combined Data Provider", dataProviderClass = DataProviders.class)
	public void CreateNewUser(HashMap<String,String> hashMapvalue)  throws Throwable {
		mainWindowHandle = getDriver().getWindowHandle();
		wait= new WebDriverWait(getDriver(), Duration.ofSeconds(10));
		try {
			homePage.clickSignIn();
			Logs.info("Navigated to the Customer Login page.");
			ExtentManager.log("Clicked Sign In on Home Page", Status.INFO);

			login.clickCreateAccount();
			Logs.info("Navigated to the Customer Login page.");
			ExtentManager.log("Clicked Create Account link", Status.INFO);
			String currentURL = getDriver().getCurrentUrl();
			Assert.assertTrue(currentURL.contains("create"), "Create Account page is not displayed.");


			emailHelper.openPutsbox(getDriver());
			Logs.info("Opened Putsbox to create a new inbox.");
			String emailId= emailHelper.createNewInbox(getDriver());
			emailHelper.ClosePutsbox(getDriver());
			String FirstName= emailHelper.extractName(emailId);
			Logs.info("New inbox created successfully: " + emailId);
			ExtentManager.log("Opened Putsbox for temp email", Status.INFO);

			createAnAccount.enterFirstName(FirstName);
			createAnAccount.enterLastName(hashMapvalue.get("LastName"));
			createAnAccount.enterEmail(emailId);
			createAnAccount.enterPassword(hashMapvalue.get("Password"));
			createAnAccount.enterConfirmPassword(hashMapvalue.get("Password"));
			createAnAccount.clickCreateAccount();
			Logs.info("Filled in the account creation form with provided data.");
			ExtentManager.log("Filled in account creation form", Status.INFO);

			//Signout the User
			Thread.sleep(2000);
			myAccountPage.ClickUserAccountOptions(getDriver(), "SignOut");
			Logs.info("User signed out successfully.");
			getDriver().navigate().refresh();
			String getSignoutText = getDriver().findElement(By.xpath("//h1[@class='page-title']/span[@class='base']")).getText();
			Assert.assertEquals(getSignoutText.trim(), "You are signed out", "Expected sign-out message not found.");
			wait.until(ExpectedConditions.not(ExpectedConditions.urlToBe("https://magento.softwaretestingboard.com/customer/account/logoutSuccess/")));

			//Relogin into system with the created user
			Thread.sleep(500);
			homePage.clickSignIn();
			Logs.info("Navigated to the Customer Login page.");
			login.enterEmailID(emailId);
			login.enterPassword(hashMapvalue.get("Password"));
			login.clickSignIn();
			ExtentManager.log("User logged in with newly created account", Status.INFO);

			String getSignInText = getDriver().findElement(By.xpath("//h1[@class='page-title']/span[@class='base']")).getText();
			Assert.assertEquals(getSignInText.trim(), "Home Page", "Expected sign-In message not found.");
			ExtentManager.log("Re-login verified on Home Page", Status.PASS);
		}
		catch(Exception e) {
			Logs.error("An error occurred during the test execution: " + e.getMessage(), e);
			ExtentManager.log("Test failed due to an error: " + e.getMessage(), Status.FAIL);
		}
	}


	public void initialize() {
		login = new CustomerLogin();
		homePage = new HomePage();
		createAnAccount = new CreateAnAccount();
		emailHelper = new EmailHelper(getDriver());
		myAccountPage = new MyAccountPage();


	}
}
