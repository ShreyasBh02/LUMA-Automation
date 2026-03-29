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

public class OrderConfirmationPage extends BaseClass {
    actionclass action = new actionclass();
    private WebDriverWait wait;
    private static final Logger logger = LogManager.getLogger(LoggerUtil.class.getClass());

    @FindBy(xpath = "//h1[@class='page-title']/span")
    private WebElement orderSuccessMessage;

    @FindBy(xpath = "//a[@class='order-number']/strong | //div[@class='checkout-success']/p/a/strong | //div[@class='checkout-success']/p/span")
    private WebElement orderId;

    @FindBy(xpath = "//a[contains(@class,'continue')]")
    private WebElement continueShoppingBtn;

    public OrderConfirmationPage() {
        this.wait = new WebDriverWait(getDriver(), Duration.ofSeconds(20));
        PageFactory.initElements(getDriver(), this);
    }

    public String getSuccessMessage() {
        wait.until(ExpectedConditions.visibilityOf(orderSuccessMessage));
        String msg = orderSuccessMessage.getText();
        logger.info("Order success message: " + msg);
        return msg;
    }

    public String getOrderId() {
        wait.until(ExpectedConditions.visibilityOf(orderId));
        String id = orderId.getText();
        logger.info("Order ID generated: " + id);
        return id;
    }

    public HomePage clickContinueShopping() {
        wait.until(ExpectedConditions.elementToBeClickable(continueShoppingBtn));
        continueShoppingBtn.click();
        logger.info("Clicked Continue Shopping");
        return new HomePage();
    }
}
