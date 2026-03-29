/**
 * Author: Shreyas Bhagat
 * Date: 8 Jun 2025
 * Description: 
 */
package pomclass;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import actiondriver.actionclass;
import baseclass.BaseClass;
import utilityclass.LoggerUtil;

public class HomePage extends BaseClass {

	actionclass action=new actionclass();
	private WebDriverWait wait;	

	private static final Logger logger = LogManager.getLogger(LoggerUtil.class.getClass());

	//CustomerLogin page
	@FindBy(xpath = "//header[@class='page-header']//li[@class='authorization-link']//a") 									private WebElement clickSignInBtn;
    @FindBy(xpath = "//input[@id='search']")																				private WebElement searchBox;
	
	
	public HomePage() {
		this.wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));
		PageFactory.initElements(getDriver(), this);
	}

	// Method to click the sign-in button
    public void clickSignIn() throws Throwable {
        wait.until(ExpectedConditions.elementToBeClickable(clickSignInBtn));
        clickSignInBtn.click(); 
        logger.info("Sign-in button clicked successfully.");
    }
    
    // Method to search for a product
	public void searchProduct(String productName) throws Throwable {
		wait.until(ExpectedConditions.visibilityOf(searchBox));
		searchBox.sendKeys(productName);
		searchBox.sendKeys(Keys.ENTER);
		logger.info("Product searched: " + productName);
	}

	
	  public  List<String> getProductTitles(WebDriver driver, By titleLocator) {
	        List<String> titles = new ArrayList<>();
	        List<WebElement> elements = driver.findElements(titleLocator);

	        for (WebElement el : elements) {
	        	action.moveToElement(driver, el);
	            String text = el.getText().trim();
	            if (!text.isEmpty()) {
	                titles.add(text);
	                //System.out.println("🔹 " + text);
	            }
	        }

	        return titles;
	    }
	
	    public static List<String> goThroughPagination(WebDriver driver, By nextButtonLocator, DataExtractor dataExtractor) {
	        List<String> allData = new ArrayList<>();
	        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	        int pageCount = 1;

	        do {
	            //System.out.println("Page: " + pageCount);
	            allData.addAll(dataExtractor.extract(driver));

	            List<WebElement> nextButtons = driver.findElements(nextButtonLocator);
	            if (!nextButtons.isEmpty() && nextButtons.get(0).isDisplayed()) {
	                WebElement nextBtn = nextButtons.get(0);
	                nextBtn.click();

	                wait.until(ExpectedConditions.stalenessOf(nextBtn));
	                pageCount++;
	            } else {
	                break; // No more pages
	            }
	        } while (true);

	        return allData;
	    }
	    public interface DataExtractor {
	        List<String> extract(WebDriver driver);
	    }
}
