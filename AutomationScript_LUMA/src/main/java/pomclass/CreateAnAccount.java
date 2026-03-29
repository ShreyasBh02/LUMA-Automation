/**
 * Author: Shreyas Bhagat
 * Date: 8 Jun 2025
 * Description: 
 */
package pomclass;

import java.time.Duration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import actiondriver.actionclass;
import baseclass.BaseClass;
import utilityclass.LoggerUtil;

public class CreateAnAccount extends BaseClass {
	actionclass action=new actionclass();
	private WebDriverWait wait;	

	private static final Logger logger = LogManager.getLogger(LoggerUtil.class.getClass());

	//CustomerLogin page
	@FindBy(xpath = "//input[@id='firstname']") 									private WebElement enterFirstName;
	@FindBy(xpath = "//input[@id='lastname']") 										private WebElement enterLastName;
	@FindBy(xpath = "//input[@id='email_address']") 								private WebElement enterEmail;
	@FindBy(xpath = "//input[@id='password']") 										private WebElement enterPassword;
	@FindBy(xpath = "//input[@id='password-confirmation']") 						private WebElement enterConfirmPassword;
	@FindBy(xpath = "//button[@class='action submit primary']") 					private WebElement clickCreateAccountBtn;
	


	public CreateAnAccount() {
		this.wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));
		PageFactory.initElements(getDriver(), this);
	}

	public void enterFirstName(String firstName) throws Throwable {
		wait.until(ExpectedConditions.elementToBeClickable(enterFirstName));
		enterFirstName.clear();
		enterFirstName.sendKeys(firstName);
		logger.info("First name entered successfully: " + firstName);
	}

	// Method to enter the last name
	public void enterLastName(String lastName) throws Throwable {
		wait.until(ExpectedConditions.elementToBeClickable(enterLastName));
		enterLastName.clear();
		enterLastName.sendKeys(lastName);
		logger.info("Last name entered successfully: " + lastName);
	}

	// Method to enter the email address
	public void enterEmail(String email) throws Throwable {
		wait.until(ExpectedConditions.elementToBeClickable(enterEmail));
		enterEmail.clear();
		enterEmail.sendKeys(email);
		logger.info("Email entered successfully: " + email);
	}

	// Method to enter the password
	public void enterPassword(String Password) throws Throwable {
		wait.until(ExpectedConditions.elementToBeClickable(enterPassword));
		enterPassword.clear();
		enterPassword
		.sendKeys(Password);
		logger.info("Confirm password entered successfully: " + Password);
	}
	
	// Method to enter the confirm password
	public void enterConfirmPassword(String confirmPassword) throws Throwable {
		wait.until(ExpectedConditions.elementToBeClickable(enterConfirmPassword));
		enterConfirmPassword.clear();
		enterConfirmPassword.sendKeys(confirmPassword);
		logger.info("Confirm password entered successfully: " + confirmPassword);
	}

	// Method to click the create account button
	public void clickCreateAccount() throws Throwable {
		wait.until(ExpectedConditions.elementToBeClickable(clickCreateAccountBtn));
		clickCreateAccountBtn.click();
		logger.info("Create account button clicked successfully.");
	}
	
	

}
