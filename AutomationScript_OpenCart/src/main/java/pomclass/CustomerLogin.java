package pomclass;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.search.SubjectTerm;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.mailosaur.MailosaurClient;
import com.mailosaur.MailosaurException;
import com.mailosaur.models.Link;
import com.mailosaur.models.MessageSearchParams;
import com.mailosaur.models.SearchCriteria;

import actiondriver.actionclass;
import baseclass.BaseClass;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import utilityclass.LoggerUtil;

/**
 * Author: Shreyas Bhagat
 * Date: 24/05/2025
 * It provides necessary functionalities for handling actions.
 */

public class CustomerLogin extends BaseClass {

	actionclass action=new actionclass();
	private WebDriverWait wait;	
	
	private static final Logger logger = LogManager.getLogger(LoggerUtil.class.getClass());

	
	//CustomerLogin page
	@FindBy(xpath = "//input[@id='email']") 											private WebElement enterEmailID;
	@FindBy(xpath = "//input[@name='login[password]']") 								private WebElement enterPassword;	
	@FindBy(xpath = "//button[@id='send2']") 					private WebElement clickSignInBtn;
	@FindBy(xpath = "//a[@class='action remind']") 										private WebElement clickForgotpass;
	@FindBy(xpath = "//a[@class='action create primary']") 								private WebElement clickCreateAccountBtn;

	public CustomerLogin() {
		this.wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));
		PageFactory.initElements(getDriver(), this);
	}

	// Method to enter the username
    public void enterEmailID(String userName) throws Throwable {
        wait.until(ExpectedConditions.elementToBeClickable(enterEmailID));
        enterEmailID.clear(); 
        enterEmailID.sendKeys(userName); 
        logger.info("Username entered successfully: " + userName);
    }
    
    // Method to enter the password
    public void enterPassword(String password) throws Throwable {
        wait.until(ExpectedConditions.elementToBeClickable(enterPassword));
        enterPassword.clear();
        enterPassword.sendKeys(password); 
        logger.info("Password entered successfully: " + password);
    }
    
    // Method to click the sign-in button
    public void clickSignIn() throws Throwable {
        wait.until(ExpectedConditions.elementToBeClickable(clickSignInBtn));
        clickSignInBtn.click(); 
        logger.info("Sign-in button clicked successfully.");
    }
    
    // Method to click the "Forgot Password" link
    public void clickForgotPassword() throws Throwable {
        wait.until(ExpectedConditions.elementToBeClickable(clickForgotpass));
        clickForgotpass.click(); 
        logger.info("Forgot Password link clicked successfully.");
    }
    
    // Method to click the "Create Account" button
    public void clickCreateAccount() throws Throwable {
        wait.until(ExpectedConditions.elementToBeClickable(clickCreateAccountBtn));
        clickCreateAccountBtn.click();
        logger.info("Create Account button clicked successfully.");
    }
}
