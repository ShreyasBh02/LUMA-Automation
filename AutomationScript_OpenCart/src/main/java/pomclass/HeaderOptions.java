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

public class HeaderOptions extends BaseClass {
	actionclass action=new actionclass();
	private WebDriverWait wait;	
	Actions act = new Actions(getDriver());



	private static final Logger logger = LogManager.getLogger(LoggerUtil.class.getClass());

	// XPath
	//HeaderOptions page
	@FindBy(xpath = "//header[@class='page-header']//li[@class='authorization-link']//a") 									private WebElement clickSignInBtn;

	// Constructor to initialize the WebDriverWait and PageFactory elements
	public HeaderOptions() {
		this.wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));
		PageFactory.initElements(getDriver(), this);
	}

	//Method/ Functions
	public void clickOption(WebElement option, String selectedOption) throws Throwable {

		act.moveToElement(option).build().perform();
		wait.until(ExpectedConditions.elementToBeClickable(option));
		act.moveToElement(option).build().perform();
		option.click();
		logger.info("Clicked on the option: " + selectedOption);
	}


	public void ClickMainOptions(WebDriver driver, String Option) throws Throwable {
		WebElement optionRow = driver.findElement(By.xpath("//div[@id='store.menu']"));
		WebElement selectedOption = null; 

		try {
			switch (Option) {
			case "What's New":
				selectedOption = optionRow.findElement(By.xpath(".//a[@id='ui-id-3']"));
				break;
			case "Women":
				selectedOption = optionRow.findElement(By.xpath(".//a[@id='ui-id-4']"));
				break;
			case "Men":
				selectedOption = optionRow.findElement(By.xpath(".//a[@id='ui-id-5']"));
				break;
			case "Gear":
				selectedOption = optionRow.findElement(By.xpath(".//a[@id='ui-id-6']"));
				break;
			case "Training":
				selectedOption = optionRow.findElement(By.xpath(".//a[@id='ui-id-7']"));
				break;
			case "Sale":
				selectedOption = optionRow.findElement(By.xpath(".//a[@id='ui-id-8']"));
				break;
			default:
				logger.error("Invalid option provided: " + Option);
				return; 
			}
			//clickOption(selectedOption, Option);
			act.moveToElement(selectedOption).build().perform();
			wait.until(ExpectedConditions.elementToBeClickable(selectedOption));
			logger.info("Mouse hover over on the option: " + Option);

		} catch (Exception e) {
			logger.error("Error occurred while clicking '" + Option + "': " + e.getMessage(), e);
		}

	}

	public void ClickWomenOptions(WebDriver driver, String Option) throws Throwable {
		WebElement optionBlock = driver.findElement(By.xpath("//a[@id='ui-id-4']/following-sibling::ul"));
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOf(optionBlock));
		WebElement selectedOption = null; 
		try {
			switch (Option) {
			case "Tops":
				selectedOption = optionBlock.findElement(By.xpath(".//a[@id='ui-id-9']"));
				break;
			case "Bottoms":
				selectedOption = optionBlock.findElement(By.xpath(".//a[@id='ui-id-10']"));
				break;
			default:
				logger.error("Invalid option provided: " + Option);
				return;
			}

			clickOption(selectedOption, Option);
			/*act.moveToElement(selectedOption).build().perform();
			wait.until(ExpectedConditions.elementToBeClickable(selectedOption));
			logger.info("Mouse hover over on the option: " + Option);
			 */

		} catch (Exception e) {
			logger.error("Error occurred while clicking '" + Option + "': " + e.getMessage(), e);
		}
	}

	// Need to updated xpath
	public void ClickMenOptions(WebDriver driver, String Option) throws Throwable {
		WebElement optionBlock = driver.findElement(By.xpath("//a[@id='ui-id-5']/following-sibling::ul"));
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOf(optionBlock));
		WebElement selectedOption = null; 
		try {
			switch (Option) {
			case "Tops":
				selectedOption = optionBlock.findElement(By.xpath(".//a[@id='ui-id-17']"));
				break;
			case "Bottoms":
				selectedOption = optionBlock.findElement(By.xpath(".//a[@id='ui-id-18']"));
				break;
			default:
				logger.error("Invalid option provided: " + Option);
				return;
			}

			//clickOption(selectedOption, Option);
			act.moveToElement(selectedOption).build().perform();
			wait.until(ExpectedConditions.elementToBeClickable(selectedOption));
			logger.info("Mouse hover over on the option: " + Option);

		} catch (Exception e) {
			logger.error("Error occurred while clicking '" + Option + "': " + e.getMessage(), e);
		}
	}

	/**
	 * Clicks on a specified sub-option under the "Men" category in the menu.
	 *
	 * @param driver The WebDriver instance used to interact with the browser.
	 * @param option The text of the sub-option to be clicked (e.g., "Jackets", "Hoodies & Sweatshirts", "Tees", "Tanks").
	 * @throws Throwable If an error occurs during the execution of the method.
	 */

	public void ClickMenSubOptions(WebDriver driver, String Option) throws Throwable {
		WebElement optionBlock = driver.findElement(By.xpath("//a[@id='ui-id-5']/following-sibling::ul"));
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOf(optionBlock));
		WebElement selectedOption = null; 
		try {
			selectedOption = optionBlock.findElement(By.xpath(".//a[span[contains(text(),'" + Option + "')]]"));
			clickOption(selectedOption, Option);

		} catch (Exception e) {
			logger.error("Error occurred while clicking '" + Option + "': " + e.getMessage(), e);
		}
	}



}

