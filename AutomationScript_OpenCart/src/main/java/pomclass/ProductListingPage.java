/**
* Author: Shreyas Bhagat
* Date: 8 Jun 2025
* Description: 
*/
package pomclass;

import java.time.Duration;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import actiondriver.actionclass;
import baseclass.BaseClass;
import utilityclass.LoggerUtil;

public class ProductListingPage extends BaseClass {
	actionclass action=new actionclass();
	private WebDriverWait wait;	
	Actions act = new Actions(getDriver());
	
	
	
	private static final Logger logger = LogManager.getLogger(LoggerUtil.class.getClass());
	
	// XPath
	
	// Filter options
	@FindBy(xpath = "//header[@class='page-header']//li[@class='authorization-link']//a") 									private WebElement clickSignInBtn;

	
	public ProductListingPage() {
		this.wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));
		PageFactory.initElements(getDriver(), this);
	}

	//Method/ Functions
	public void selectFilterOption(String filterTitle, String itemText) throws Exception {
        // Locate the filter option title and click to expand it
        WebElement filterOptionTitle = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//div[@data-role='title' and text()='" + filterTitle + "']")));
        filterOptionTitle.click();
        logger.info("Clicked on filter option: " + filterTitle);
        
        // Wait for the content to be visible
        WebElement filterContent = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[contains(@class,'filter-options-item allow active')]//div[@data-role='content' and @aria-hidden='false']")));
        
        // Locate the filter item based on the provided item text
        List<WebElement> filterItems = filterContent.findElements(By.xpath(".//div[@class='swatch-attribute-options clearfix']//div"));
        for (WebElement item : filterItems) {
            if (item.getText().trim().equalsIgnoreCase(itemText)) {
                item.click(); // Click the filter item
                break;
            }
        }
        logger.info("Selected filter value: " + itemText);
    }
}
