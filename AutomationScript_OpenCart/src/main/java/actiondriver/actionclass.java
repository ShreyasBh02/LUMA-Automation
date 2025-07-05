package actiondriver;

import java.io.File;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import actioninterface.ActionInterfaces;
import baseclass.BaseClass;

/**
 * Author: Shreyas Bhagat Date: 24/05/2025 This class provides necessary
 * functionalities for handling actions in Selenium WebDriver.
 */

public class actionclass extends BaseClass implements ActionInterfaces {

	@Override
	public void scrollBy(WebDriver driver, WebElement scroll) {
		try {
			Actions action = new Actions(driver);
			action.moveToElement(scroll).perform();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void click(WebDriver driver, WebElement ele) {
		try {
			Actions act = new Actions(driver);
			act.moveToElement(ele).click().build().perform();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean findElement(WebDriver driver, WebElement ele) {
		boolean flag = false;
		try {
			ele.isDisplayed();
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
		}
		return flag;
	}

	@Override
	public boolean isElementdsiplay(WebDriver ldriver, WebElement ele) {
		boolean flag = false;
		flag = findElement(ldriver, ele);
		if (flag) {
			flag = ele.isDisplayed();
			if (flag) {
				System.out.println("The Element is displayed");
			} else {
				System.out.println("The Element is not displayed");
			}
		} else {
			System.out.println("The Element is not displayed");
		}
		return flag;
	}

	@Override
	public boolean isElementDisplayed(WebDriver driver, WebElement ele) {
		boolean flag = false;
		flag = findElement(driver, ele);
		if (flag) {
			flag = ele.isDisplayed();
			if (flag) {
				System.out.println("The element is Displayed");
			} else {
				System.out.println("The element is not Displayed");
			}
		} else {
			System.out.println("Not displayed ");
		}
		return flag;
	}

	@Override
	public boolean isElementSelected(WebDriver driver, WebElement ele) {
		boolean flag = false;
		flag = findElement(driver, ele);
		if (flag) {
			flag = ele.isSelected();
			if (flag) {
				System.out.println("The element is Selected");
			} else {
				System.out.println("The element is not Selected");
			}
		} else {
			System.out.println("Not selected ");
		}
		return flag;
	}

	@Override
	public boolean isElementEnabled(WebDriver driver, WebElement ele) {
		boolean flag = false;
		flag = findElement(driver, ele);
		if (flag) {
			flag = ele.isEnabled();
			if (flag) {
				System.out.println("The element is Enabled");
			} else {
				System.out.println("The element is not Enabled");
			}
		} else {
			System.out.println("Not Enabled ");
		}
		return flag;
	}

	/**
	 * Type text at location
	 * 
	 * @param locatorName
	 * @param searchkbox
	 * @return - true/false
	 */

	@Override
	public boolean type(WebElement ele, String text) {
		boolean flag = false;
		try {
			flag = ele.isDisplayed();
			ele.clear();
			ele.sendKeys(text);
			// logger.info("Entered text :"+text);
			flag = true;
		} catch (Exception e) {
			System.out.println("Location Not found");
			e.printStackTrace();
			flag = false;
		}
		return flag;
	}

	@Override
	public boolean selectBySendkeys(String value, WebElement ele) {
		boolean flag = false;
		try {
			ele.sendKeys(value);
			flag = true;
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * select value from DropDown by using selectByIndex
	 * 
	 * @param locator     : Action to be performed on element (Get it from Object
	 *                    repository)
	 * 
	 * @param index       : Index of value wish to select from dropdown list.
	 * 
	 * @param locatorName : Meaningful name to the element (Ex:Year Dropdown, items
	 *                    Listbox etc..)
	 * 
	 */
	/*
	 * @Override public boolean selectByIndex(WebElement element, int index) {
	 * boolean flag = false; try { Select s = new Select(element);
	 * s.selectByIndex(index); flag = true; return true; } catch (Exception e) {
	 * e.printStackTrace(); return false; } }
	 */

	/**
	 * select value from DD by using value
	 * 
	 * @param locator     : Action to be performed on element (Get it from Object
	 *                    repository)
	 * 
	 * @param value       : Value wish to select from dropdown list.
	 * 
	 * @param locatorName : Meaningful name to the element (Ex:Year Dropdown, items
	 *                    Listbox etc..)
	 */

	@Override
	public boolean selectByValue(WebElement element, String value) {
		boolean flag = false;
		try {
			Select s = new Select(element);
			s.selectByValue(value);
			flag = true;
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean selectByVisibleText(String visibletext, WebElement ele) {
		boolean flag = false;
		try {
			Select s = new Select(ele);
			s.selectByVisibleText(visibletext);
			flag = true;
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/*
	 * @Override public boolean mouseHoverByJavaScript(WebElement ele) { boolean
	 * flag = false; try { WebElement mo = ele; String javaScript =
	 * "var evObj = document.createEvent('MouseEvents');" +
	 * "evObj.initMouseEvent(\"mouseover\",true, false, window, 0, 0, 0, 0, 0, false, false, false, false, 0, null);"
	 * + "arguments[0].dispatchEvent(evObj);"; JavascriptExecutor js =
	 * (JavascriptExecutor) driver; js.executeScript(javaScript, mo); flag = true;
	 * return true; } catch (Exception e) { e.printStackTrace(); return false; } }
	 */

	@Override
	public boolean JSClick(WebDriver driver, WebElement ele) {
		boolean flag = false;
		try {
			// WebElement element = driver.findElement(locator);
			JavascriptExecutor executor = (JavascriptExecutor) driver;
			executor.executeScript("arguments[0].click();", ele);
			// driver.executeAsyncScript("arguments[0].click();", element);
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return flag;
	}

	/**
	 * This method switch the to frame using frame ID.
	 * 
	 * @param idValue : Frame ID wish to switch
	 * 
	 */
	/*
	 * @Override public boolean switchToFrameById(WebDriver driver,String idValue) {
	 * boolean flag = false; try { driver.switchTo().frame(idValue); flag = true;
	 * return true; } catch (Exception e) {
	 * 
	 * e.printStackTrace(); return false; } finally { if (flag) {
	 * System.out.println("Frame with Id \"" + idValue + "\" is selected"); } else {
	 * System.out.println("Frame with Id \"" + idValue + "\" is not selected"); } }
	 * }
	 */

	/**
	 * This method switch the to frame using frame Name.
	 * 
	 * @param nameValue : Frame Name wish to switch
	 * 
	 */
	@Override
	public boolean switchToFrameByName(WebDriver driver, String nameValue) {
		boolean flag = false;
		try {
			driver.switchTo().frame(nameValue);
			flag = true;
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean switchToDefaultFrame(WebDriver driver) {
		boolean flag = false;
		try {
			driver.switchTo().defaultContent();
			flag = true;
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public void mouseOverElement(WebDriver driver, WebElement element) {
		boolean flag = false;
		try {
			new Actions(driver).moveToElement(element).build().perform();
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean moveToElement(WebDriver driver, WebElement ele) {
		boolean flag = false;
		try {
			// WebElement element = driver.findElement(locator);
			JavascriptExecutor executor = (JavascriptExecutor) driver;
			executor.executeScript("arguments[0].scrollIntoView(true);", ele);
			Actions actions = new Actions(driver);
			// actions.moveToElement(driver.findElement(locator)).build().perform();
			actions.moveToElement(ele).build().perform();
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public boolean mouseover(WebDriver driver, WebElement ele) {
		boolean flag = false;
		try {
			new Actions(driver).moveToElement(ele).build().perform();
			flag = true;
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean draggable(WebDriver driver, WebElement source, int x, int y) {
		boolean flag = false;
		try {
			new Actions(driver).dragAndDropBy(source, x, y).build().perform();
			Thread.sleep(5000);
			flag = true;
			return true;

		} catch (Exception e) {
			e.printStackTrace();
			return false;

		}
	}

	@Override
	public boolean draganddrop(WebDriver driver, WebElement source, WebElement target) {
		boolean flag = false;
		try {
			new Actions(driver).dragAndDrop(source, target).perform();
			flag = true;
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean rightclick(WebDriver driver, WebElement ele) {
		boolean flag = false;
		try {
			Actions clicker = new Actions(driver);
			clicker.contextClick(ele).perform();
			flag = true;
			return true;
			// driver.findElement(by1).sendKeys(Keys.DOWN);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean switchWindowByTitle(WebDriver driver, String windowTitle, int count) {
		boolean flag = false;
		try {
			Set<String> windowList = driver.getWindowHandles();

			String[] array = windowList.toArray(new String[0]);

			driver.switchTo().window(array[count - 1]);

			if (driver.getTitle().contains(windowTitle)) {
				flag = true;
			} else {
				flag = false;
			}
			return flag;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			if (flag) {
				System.out.println("Navigated to the window with title");
			} else {
				System.out.println("The Window with title is not Selected");
			}
		}
	}

	@Override
	public boolean switchToNewWindow(WebDriver driver) {
		boolean flag = false;
		try {
			Set<String> s = driver.getWindowHandles();
			Object popup[] = s.toArray();
			driver.switchTo().window(popup[1].toString());
			flag = true;
			return flag;
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
			return flag;
		}
	}

	@Override
	public boolean switchToOldWindow(WebDriver driver) {
		boolean flag = false;
		try {
			Set<String> s = driver.getWindowHandles();
			Object popup[] = s.toArray();
			driver.switchTo().window(popup[0].toString());
			flag = true;
			return flag;
		} catch (Exception e) {
			flag = false;
			e.printStackTrace();
			return flag;
		}
	}

	@Override
	public int getColumncount(WebElement row) {
		// Returns the number of columns in the specified row
		List<WebElement> columns = row.findElements(By.tagName("td"));
		int count = columns.size();
		for (WebElement column : columns) {
			System.out.print(column.getText() + "|");
		}
		return count;
	}

	@Override
	public int getRowCount(WebElement table) {
		List<WebElement> rows = table.findElements(By.tagName("tr"));
		return rows.size() - 1;
	}

	/**
	 * Verify alert present or not
	 * 
	 * @return: Boolean (True: If alert preset, False: If no alert)
	 * 
	 */

	@Override
	public boolean launchUrl(WebDriver driver, String url) {
		boolean flag = false;
		try {
			driver.navigate().to(url);
			flag = true;
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean isAlertPresent(WebDriver driver) {
		try {
			driver.switchTo().alert();
			return true;
		} catch (NoAlertPresentException Ex) {
			return false;
		}
	}

	@Override
	public String getTitle(WebDriver driver) {
		boolean flag = false;

		String text = driver.getTitle();
		if (flag) {
			System.out.println("Title of the page is: \"" + text + "\"");
		}
		return text;
	}

	@Override
	public String getCurrentURL(WebDriver driver) {
		boolean flag = false;

		String text = driver.getCurrentUrl();
		if (flag) {
			System.out.println("Current URL is: \"" + text + "\"");
		}
		return text;
	}

	@Override
	public void fluentWait(WebDriver driver, WebElement element, int timeOut) {
		try {
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(timeOut));
			Wait<WebDriver> wait = new FluentWait<WebDriver>((WebDriver) driver)
					.withTimeout(Duration.ofSeconds(timeOut)).pollingEvery(Duration.ofSeconds(500))
					.ignoring(Exception.class);
			wait.until(ExpectedConditions.visibilityOf(element));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void waitTillElement(WebDriver driver, WebElement element, int timeOut) {
		try {
			Wait<WebDriver> wait = new FluentWait<>(driver).withTimeout(Duration.ofSeconds(timeOut))
					.pollingEvery(Duration.ofSeconds(500)).ignoring(NoSuchElementException.class)
					.ignoring(StaleElementReferenceException.class);
			wait.until(ExpectedConditions.visibilityOf(element));
		} catch (TimeoutException e) {
			System.out.println("Element not visible after waiting for " + timeOut + " seconds.");
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void implicitWait(WebDriver driver, int timeOut) {
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(timeOut));
	}

	@Override
	public void explicitWait(WebDriver driver, WebElement element, Duration timeOut) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, timeOut);
			wait.until(ExpectedConditions.visibilityOf(element));
		} catch (TimeoutException e) {
			System.out.println("Explicit wait timed out after " + timeOut.getSeconds() + " seconds.");
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	@Override
	public void pageLoadTimeOut(WebDriver driver, int timeOut) {
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(timeOut));
	}

	@Override
	public String screenShot(WebDriver driver, String filename) {
		String dateName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
		TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
		File source = takesScreenshot.getScreenshotAs(OutputType.FILE);
		String destination = System.getProperty("user.dir") + "\\Screenshots\\" + filename + "_" + dateName + ".png";

		try {
			FileUtils.copyFile(source, new File(destination));
		} catch (Exception e) {
			System.err.println("Error while saving screenshot: " + e.getMessage());
			return null;
		}
		return destination;
	}

	@Override
	public String getCurrentTime() {
		String currentDate = new SimpleDateFormat("yyyy-MM-dd-hhmmss").format(new Date());
		return currentDate;
	}

	// New method to wait for an element to be clickable
	public void waitForElementToBeClickable(WebDriver driver, WebElement element, Duration timeOut) {
		// Waits for the specified element to be clickable
		try {
			WebDriverWait wait = new WebDriverWait(driver, timeOut);
			wait.until(ExpectedConditions.elementToBeClickable(element));
		} catch (TimeoutException e) {
			System.out.println("Element not clickable after waiting for " + timeOut.getSeconds() + " seconds.");
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void waitForPageLoad(WebDriver driver, int timeoutInSeconds) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
		wait.until(webDriver -> 
		((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete")
				);
	}


	// New method to get the text of an element
	public String getElementText(WebElement element) {
		// Returns the text of the specified element
		String text = "";
		try {
			text = element.getText();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return text;
	}
	@Override
	/**
	 * Handles and closes Google Ads or modals if present on the page.
	 */

	public void handleAdPopupIfPresent(WebDriver driver) {
		try {
			List<WebElement> allFrames = driver.findElements(By.tagName("iframe"));
			boolean adFound = false;

			for (WebElement frame : allFrames) 
			{
				driver.switchTo().frame(frame);
				List<WebElement> adIframes = driver.findElements(By.xpath("//div[contains(@id,'aswift_')]//iframe[contains(@style, 'opacity: 1')]"));
				if (!adIframes.isEmpty()) {
					adFound = true; 
					System.out.println("Ad iframe found in frame: " + driver.getTitle());
					driver.switchTo().frame(adIframes.get(0));
					try {
						WebElement closeButton = driver.findElement(By.xpath("//div[@id='ad_position_box']//div[@id='dismiss-button']"));
						if (closeButton.isDisplayed()) {
							closeButton.click();
							System.out.println("Closed the ad popup.");
						}
					} catch (Exception e) {
						System.out.println("Close button not found or not clickable: " + e.getMessage());
					}

					driver.switchTo().parentFrame(); 
					break; 
				}
				driver.switchTo().defaultContent();
			}
			if (!adFound) {
				System.out.println("No ad popup found.");
			}
		} catch (Exception e) {
			System.out.println("Error while handling ad popup: " + e.getMessage());
			driver.switchTo().defaultContent(); 
		}
	}


}
