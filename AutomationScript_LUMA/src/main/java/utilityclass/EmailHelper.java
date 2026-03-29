/**
 * Author: Shreyas Bhagat
 * Date: 8 Jun 2025
 * Description: 
 */
package utilityclass;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONObject;

import org.openqa.selenium.By;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import baseclass.BaseClass;

public class EmailHelper extends BaseClass {

	private WebDriver driver;
	private static WebDriverWait wait;

	public EmailHelper(WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	}

	public static Boolean openPutsbox(WebDriver driver) throws InterruptedException {
		try {
			((JavascriptExecutor) driver).executeScript("window.open('about:blank','_blank');");
			Thread.sleep(1000);

			// Switch to new tab
			String originalHandle = driver.getWindowHandles().iterator().next();
			for (String handle : driver.getWindowHandles()) {
				if (!handle.equals(originalHandle)) {
					driver.switchTo().window(handle);
					break;
				}
			}

			driver.get("https://www.putsbox.com/");
			wait.until(ExpectedConditions.titleContains("Putsbox"));
			return true; 
		}
		catch (Exception e) {
			System.out.println("Error opening Putsbox: " + e.getMessage());
		}
		return false; 
	}

	public static String createNewInbox(WebDriver driver) throws Exception {
		try {
			WebElement newInboxButton = driver.findElement(By.xpath("//button[contains(text(),'Create a PutsBox')]"));
			wait.until(ExpectedConditions.elementToBeClickable(newInboxButton));
			newInboxButton.click();
			

			WebElement emailBox = driver.findElement(By.id("putsbox-token-input"));
			wait.until(ExpectedConditions.visibilityOf(emailBox));
			String emailID = emailBox.getAttribute("value"); 

			System.out.println("Generated email Id: " + emailID);
			return emailID;
		}
		catch (Exception e) {
			System.out.println("Error creating new inbox: " + e.getMessage());
			throw new Exception("Failed to create new inbox.");
		}
	}

	public static String waitForEmail(String inbox, String subject, int timeoutSeconds) throws Exception {
		String putsBoxUrl = "https://api.putsbox.com/v1/boxes/" + inbox + "/messages";
		int waited = 0;

		while (waited < timeoutSeconds) {
			HttpResponse<String> response = Unirest.get(putsBoxUrl).asString();
			JSONArray messages = new JSONArray(response.getBody());

			for (int i = 0; i < messages.length(); i++) {
				JSONObject message = messages.getJSONObject(i);
				if (message.getString("subject").contains(subject)) {
					return message.getString("id");
				}
			}

			Thread.sleep(3000);
			waited += 3;
		}

		throw new Exception("Email with subject [" + subject + "] not found in " + timeoutSeconds + " seconds.");
	}

	public static String getEmailContent(String inbox, String emailId) throws Exception {
		String putsBoxUrl = "https://api.putsbox.com/v1/boxes/" + inbox + "/messages/" + emailId;
		HttpResponse<String> response = Unirest.get(putsBoxUrl).asString();
		JSONObject message = new JSONObject(response.getBody());
		return message.getString("content");
	}

	public static String getEmailBody(String inbox, String messageId) throws Exception {
		String emailUrl = "https://api.putsbox.com/v1/boxes/" + inbox + "/messages/" + messageId;
		HttpResponse<String> response = Unirest.get(emailUrl).asString();

		JSONObject messageDetails = new JSONObject(response.getBody());
		return messageDetails.getString("html_body"); // or "text_body"
	}
	public static String extractVerificationLink(String htmlContent) {
		Pattern pattern = Pattern.compile("https?://[\\w\\-\\.]+/verify\\?token=[\\w\\-]+");
		Matcher matcher = pattern.matcher(htmlContent);
		if (matcher.find()) {
			return matcher.group();
		}
		return null;
	}

	public static String extractOTP(String body) {
		Pattern pattern = Pattern.compile("\\b\\d{4,8}\\b");
		Matcher matcher = pattern.matcher(body);
		if (matcher.find()) {
			return matcher.group();
		}
		return null;
	}
	
	public static String extractName(String emailId) {
	    if (emailId != null && !emailId.isEmpty()) {
	        String username = emailId.split("@")[0]; // Extract the part before '@'
	        String firstName = username.contains("_") ? username.split("_")[0] : username;
	        return firstName;
	    }
	    return null; // Return null if emailId is invalid
	}

	public static Boolean ClosePutsbox(WebDriver driver) {
		try {
			String originalHandle = driver.getWindowHandles().iterator().next();
			for (String handle : driver.getWindowHandles()) {
				if (!handle.equals(originalHandle)) {
					driver.switchTo().window(handle);
					driver.close();
					break;
				}
			}
			driver.switchTo().window(originalHandle);
			return true;
		} catch (Exception e) {
			System.out.println("Error closing Putsbox: " + e.getMessage());
		}
		return false;
	}

}
