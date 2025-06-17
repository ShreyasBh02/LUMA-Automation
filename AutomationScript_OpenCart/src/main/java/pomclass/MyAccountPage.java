/**
 * Author: Shreyas Bhagat
 * Date: 9 Jun 2025
 * Description: 
 */
package pomclass;

import java.time.Duration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import actiondriver.actionclass;
import baseclass.BaseClass;
import utilityclass.LoggerUtil;

public class MyAccountPage extends BaseClass {
	actionclass action=new actionclass();
	Actions act = new Actions(getDriver());
	private WebDriverWait wait;	

	private static final Logger logger = LogManager.getLogger(LoggerUtil.class.getClass());

	// Buttons
	@FindBy(xpath = "//div[@class= 'box box-information']//a[@class ='action edit']") 									private WebElement editBtn;
	@FindBy(xpath = "//div[@class= 'box box-information']//a[@class ='action change-password']") 												private WebElement changePasswordBtn;	


	//CustomerLogin page
	@FindBy(xpath = "//div[contains(@class,'message-success')]//div") 											private WebElement enterEmailID;
	@FindBy(xpath = "//input[@name='login[password]']") 								private WebElement enterPassword;	
	@FindBy(xpath = "//button[@id='send2' and @fdprocessedid='bb2r']") 					private WebElement clickSignInBtn;
	@FindBy(xpath = "//a[@class='action remind']") 										private WebElement clickForgotpass;
	@FindBy(xpath = "//a[@class='action create primary']") 								private WebElement clickCreateAccountBtn;

	
	
	public MyAccountPage() {
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
	public void ClickUserAccountOptions(WebDriver driver, String Option) throws Throwable {
		WebElement arrowBtn = driver.findElement(By.xpath("//button[@class='action switch']"));
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOf(arrowBtn));
		arrowBtn.click();
		WebElement userOptionsDropdown = driver.findElement(By.xpath("//div[@class= 'customer-menu']/ul"));
		wait.until(ExpectedConditions.visibilityOf(userOptionsDropdown));
		WebElement selectedOption = null; 
		try {
			switch (Option) {
			case "MyAccount":
				selectedOption = userOptionsDropdown.findElement(By.xpath(".//a[contains(text(),'My Account')]"));
				break;
			case "MyWishList":
				selectedOption = userOptionsDropdown.findElement(By.xpath(".//a[contains(text(),'My Wish List')]"));
				break;
			case "SignOut":
				selectedOption = userOptionsDropdown.findElement(By.xpath(".//a[contains(text(),'Sign Out')]"));
				break;
			default:
				logger.error("Invalid option provided: " + Option);
				return;
			}

			clickOption(selectedOption, Option);
			
		} catch (Exception e) {
			logger.error("Error occurred while clicking '" + Option + "': " + e.getMessage(), e);
		}
	}
	
	
	
	// Method to click the edit button
	public void clickEditBtn() throws Throwable {
		wait.until(ExpectedConditions.elementToBeClickable(editBtn));
		editBtn.click();
		logger.info("Clicked on the Edit button for user account information. ");
	}
	
	// Method to click the change password button
	public void clickChangePasswordBtn() throws Throwable {
		wait.until(ExpectedConditions.elementToBeClickable(changePasswordBtn));
		changePasswordBtn.click();
		logger.info("Clicked on the Change Password button for user account information. ");
	}

	// Method to get the title of the My Account page
	public String getThePageTitle(String userName) throws Throwable {
		WebElement pageTitle = getDriver().findElement(By.xpath("//h1[@class='page-title']//span"));
		wait.until(ExpectedConditions.elementToBeClickable(pageTitle));
		return pageTitle.getText();
	}

	public String getSuccessMsg(String userName) throws Throwable {
		WebElement successMsg = getDriver().findElement(By.xpath("//div[contains(@class,'message-success')]//div"));
		wait.until(ExpectedConditions.elementToBeClickable(successMsg));
		return successMsg.getText();
	}



}
