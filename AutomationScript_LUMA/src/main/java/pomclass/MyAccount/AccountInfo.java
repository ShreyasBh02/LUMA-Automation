/**
* Author: Shreyas Bhagat
* Date: 10 Jun 2025
* Description: 
*/
package pomclass.MyAccount;

import java.time.Duration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import actiondriver.actionclass;
import baseclass.BaseClass;
import utilityclass.LoggerUtil;

public class AccountInfo extends BaseClass {

	actionclass action=new actionclass();
	private WebDriverWait wait;	
	Actions act = new Actions(getDriver());
	
	
	
	private static final Logger logger = LogManager.getLogger(LoggerUtil.class.getClass());
	
	// XPath
	//HeaderOptions page
	@FindBy(xpath = "//header[@class='page-header']//li[@class='authorization-link']//a") 									private WebElement clickSignInBtn;

	// Constructor to initialize the WebDriverWait and PageFactory elements
	public AccountInfo() {
		this.wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));
		PageFactory.initElements(getDriver(), this);
	}

	//Method/ Functions
	public void clickOption(WebElement option, String selectedOption) throws Throwable {
		
		act.moveToElement(option).build().perform();
		wait.until(ExpectedConditions.elementToBeClickable(option));
		option.click();
		logger.info("Clicked on the option: " + selectedOption);
	}
}
