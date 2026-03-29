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

public class CartPage extends BaseClass {
    actionclass action = new actionclass();
    private WebDriverWait wait;
    private static final Logger logger = LogManager.getLogger(LoggerUtil.class.getClass());

    @FindBy(xpath = "//button[@data-role='proceed-to-checkout']")
    private WebElement proceedToCheckoutBtn;

    public CartPage() {
        this.wait = new WebDriverWait(getDriver(), Duration.ofSeconds(15));
        PageFactory.initElements(getDriver(), this);
    }

    public CheckoutPage clickProceedToCheckout() {
        wait.until(ExpectedConditions.elementToBeClickable(proceedToCheckoutBtn));
        proceedToCheckoutBtn.click();
        logger.info("Clicked on Proceed to Checkout button");
        return new CheckoutPage();
    }
}
