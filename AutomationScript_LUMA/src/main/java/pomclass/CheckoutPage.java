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

public class CheckoutPage extends BaseClass {
    actionclass action = new actionclass();
    private WebDriverWait wait;
    private static final Logger logger = LogManager.getLogger(LoggerUtil.class.getClass());

    // Shipping Elements
    @FindBy(xpath = "//div[@id='shipping-method-buttons-container']//button")
    private WebElement nextButton;
    
    @FindBy(name = "ko_unique_1") // First shipping method radio button
    private WebElement shippingMethodRadioBtn;

    // Payment Elements
    @FindBy(xpath = "//button[@title='Place Order']")
    private WebElement placeOrderBtn;
    
    @FindBy(xpath = "//div[contains(@class,'payment-method _active')]//button[@title='Place Order']")
    private WebElement activePlaceOrderBtn;

    public CheckoutPage() {
        this.wait = new WebDriverWait(getDriver(), Duration.ofSeconds(20));
        PageFactory.initElements(getDriver(), this);
    }

    public void selectShippingMethod() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(shippingMethodRadioBtn));
            if (!shippingMethodRadioBtn.isSelected()) {
                shippingMethodRadioBtn.click();
            }
            logger.info("Selected Shipping Method");
        } catch(Exception e) {
            logger.info("Shipping method already selected or not needed");
        }
    }

    public void clickNextButton() {
        wait.until(ExpectedConditions.elementToBeClickable(nextButton));
        nextButton.click();
        logger.info("Clicked Next button on Shipping page");
    }

    public OrderConfirmationPage clickPlaceOrder() throws InterruptedException {
        Thread.sleep(3000); // Synchronization wait for payment section loading overlay
        wait.until(ExpectedConditions.elementToBeClickable(activePlaceOrderBtn));
        activePlaceOrderBtn.click();
        logger.info("Clicked on Place Order button");
        return new OrderConfirmationPage();
    }
}
