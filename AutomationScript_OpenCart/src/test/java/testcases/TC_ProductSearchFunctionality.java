package testcases;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;

import actiondriver.actionclass;
import dataprovider.DataProviders;
import baseclass.BaseClass;
import dataprovider.DataProviders;
import pomclass.CreateAnAccount;
import pomclass.CustomerLogin;
import pomclass.HeaderOptions;
import pomclass.HomePage;
import pomclass.ProductListingPage;
import utilityclass.EmailHelper;
import utilityclass.ExtentManager;
import utilityclass.LoggerUtil;


public class TC_ProductSearchFunctionality extends BaseClass {

	CustomerLogin login;
	HomePage 	  homePage;
	CreateAnAccount createAnAccount;
	EmailHelper emailHelper;
	HeaderOptions headerOptions;
	ProductListingPage productListingPage;
	
	WebDriverWait wait;	
	private String mainWindowHandle;
	
	private static final Logger Logs = LogManager.getLogger(LoggerUtil.class.getClass());
	actionclass action = new actionclass();
	
	@BeforeMethod
	public void websetup() throws Throwable {
		String browser =prop.getProperty("Browser");
		String URL =prop.getProperty("URL");
		launchWebApp(browser, URL); 
		initialize();
	}

	

	@Test(dataProvider = "Product Data Provider", dataProviderClass = DataProviders.class)
	public void SearchFunctionality(HashMap<String,String> hashMapvalue)  throws Throwable {
		mainWindowHandle = getDriver().getWindowHandle();
		wait= new WebDriverWait(getDriver(), Duration.ofSeconds(10));
		try {
			
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='search']")));

			// Handle Google Ad pop-up if it appears
		    try {
		        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));
		        Thread.sleep(2000); 
		        WebElement googleAdPopup = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='ad_position_box']")));
				if (googleAdPopup.isDisplayed()) {
					WebElement cancelButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@id='dismiss-button']"))); 
					cancelButton.click();
					System.out.println("Closed the Google Ad pop-up.");
					
				} 
				
		    } catch (Exception e) {
		        System.out.println("No Google Ad pop-up appeared.");
		    }
		    
			homePage.searchProduct(hashMapvalue.get("SearchKeyword1"));
			Logs.info("Product searched successfully: " + hashMapvalue.get("SearchKeyword1"));
			ExtentManager.log("Searched for product: " + hashMapvalue.get("SearchKeyword1"), Status.INFO);

			//Verify the URL
			action.waitForPageLoad(getDriver(), 10);
			String currentUrl = getDriver().getCurrentUrl();
			Assert.assertTrue(currentUrl.contains("/catalogsearch/result"), "URL does not contain 'catalogsearch/result'");
			
			//Collect titles through pagination
			List<String> allTitles = homePage.goThroughPagination(
					getDriver(),
					By.xpath("//dl[@class='block']//following-sibling::div//a[@title='Next']"), // next button
					driver -> homePage.getProductTitles(driver, By.xpath("//strong[contains(@class,'product-item-name')]//a"))
				);

			//Validate each product title contains the keyword
			String searchKeyword = hashMapvalue.get("SearchKeyword1").toLowerCase();
			List<String> unMatchedTitles = new ArrayList<>();
			for (String title : allTitles) {
				Assert.assertFalse(title.isEmpty(), "Title should not be empty");
				if(!title.toLowerCase().contains(searchKeyword))
				{
					unMatchedTitles.add(title);
				} 
				
			}
			
			Assert.assertTrue(unMatchedTitles.isEmpty(), 
				    "❌ The following titles do not match keyword '" + searchKeyword + "':\n" + String.join("\n", unMatchedTitles));
			
			ExtentManager.log("Re-login verified on Home Page", Status.PASS);
		}
		catch(Exception e) {
			Logs.error("An error occurred during the test execution: " + e.getMessage(), e);
			ExtentManager.log("Test failed due to an error: " + e.getMessage(), Status.FAIL);
		}
	}


	@Test(dataProvider = "Product Data Provider", dataProviderClass = DataProviders.class)
	public void FilterByCategory(HashMap<String,String> hashMapvalue)  throws Throwable {
		mainWindowHandle = getDriver().getWindowHandle();
		wait= new WebDriverWait(getDriver(), Duration.ofSeconds(10));
		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='search']")));
			Thread.sleep(2000);
			headerOptions.ClickMainOptions(getDriver(), "Men");
			headerOptions.ClickMenOptions(getDriver(), "Tops");
			headerOptions.ClickMenSubOptions(getDriver(), "Tees");
			Thread.sleep(2000);
			
			//action.handleAdPopupIfPresent(getDriver());
			//Verify the URL
			action.waitForPageLoad(getDriver(), 30);
			String currentUrl = getDriver().getCurrentUrl();
			Assert.assertTrue(currentUrl.contains("/men/tops-men/tees-men"), "URL does not contain 'catalogsearch/result'");
			productListingPage.selectFilterOption("Size", "XS");
			productListingPage.selectFilterOption("Color", "Black"); 
			
			
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
        headerOptions = new HeaderOptions();
        productListingPage = new ProductListingPage();
	}
}
