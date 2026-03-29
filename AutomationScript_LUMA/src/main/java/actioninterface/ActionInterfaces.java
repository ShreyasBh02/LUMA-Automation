package actioninterface;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Author: Shreyas Bhagat
 * Date: 24/05/2025
 * This class provides necessary functionalities for handling actions in Selenium WebDriver.
 */
public interface ActionInterfaces {

	public void scrollBy(WebDriver driver, WebElement scroll);
	public void click(WebDriver ldriver, WebElement element);
	public boolean findElement(WebDriver driver, WebElement element);
	public boolean isElementdsiplay(WebDriver ldriver, WebElement element);
	public boolean isElementDisplayed(WebDriver ldriver, WebElement element);
	public boolean isElementSelected(WebDriver driver, WebElement element);
	public boolean isElementEnabled(WebDriver driver, WebElement element);
	public boolean selectByValue(WebElement element, String value);
	public boolean selectByVisibleText(String visibletext, WebElement element);
	public boolean switchToFrameByName(WebDriver driver, String nameValue);
	public boolean switchToDefaultFrame(WebDriver driver);
	public void mouseOverElement(WebDriver driver, WebElement element);
	public boolean moveToElement(WebDriver driver, WebElement element);
	public boolean mouseover(WebDriver driver, WebElement element);
	public boolean draggable(WebDriver driver, WebElement source, int x, int y);
	public boolean draganddrop(WebDriver driver, WebElement source, WebElement target);
	public boolean rightclick(WebDriver driver, WebElement element);
	public boolean switchWindowByTitle(WebDriver driver, String windowTitle, int count);
	public boolean switchToNewWindow(WebDriver driver);
	public boolean switchToOldWindow(WebDriver driver);
	public int getColumncount(WebElement row);
	public int getRowCount(WebElement table);
	public boolean launchUrl(WebDriver driver, String url);
	public boolean isAlertPresent(WebDriver driver);
	public String getTitle(WebDriver driver);
	public String getCurrentURL(WebDriver driver);
	public void fluentWait(WebDriver driver, WebElement element, int timeOut);
	public void implicitWait(WebDriver driver, int timeOut);
	public void explicitWait(WebDriver driver, WebElement element, Duration timeOut);
	public void pageLoadTimeOut(WebDriver driver, int timeOut);
	public String screenShot(WebDriver driver, String filename);
	public String getCurrentTime();
	public boolean type(WebElement element, String text);
	public boolean selectBySendkeys(String value,WebElement element);
	public boolean JSClick(WebDriver driver, WebElement element);
//	public boolean popup(WebElement driver, WebElement ele);
	public void waitTillElement(WebDriver driver, WebElement element, int timeOut);
	public void waitForElementToBeClickable(WebDriver driver, WebElement element, Duration timeOut);
	 public String getElementText(WebElement element);
	 public void waitForPageLoad(WebDriver driver,int timeoutInSeconds);
	 public void  handleAdPopupIfPresent(WebDriver driver);
	 
}
