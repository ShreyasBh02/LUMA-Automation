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

public class ProductDetailPage extends BaseClass {
    actionclass action = new actionclass();
    private WebDriverWait wait;
    private static final Logger logger = LogManager.getLogger(LoggerUtil.class.getClass());

    @FindBy(xpath = "//div[contains(@class,'swatch-option text')][1]")
    private WebElement firstSizeOption;

    @FindBy(xpath = "//div[contains(@class,'swatch-option color')][1]")
    private WebElement firstColorOption;

    @FindBy(id = "qty")
    private WebElement quantityInput;

    @FindBy(id = "product-addtocart-button")
    private WebElement addToCartBtn;

    @FindBy(xpath = "//div[@data-ui-id='message-success']")
    private WebElement successMessage;
    
    @FindBy(xpath = "//a[text()='shopping cart']")
    private WebElement shoppingCartLink;

    public ProductDetailPage() {
        this.wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));
        PageFactory.initElements(getDriver(), this);
    }

    public void selectFirstSize() {
        wait.until(ExpectedConditions.elementToBeClickable(firstSizeOption));
        firstSizeOption.click();
        logger.info("Selected first available size");
    }

    public void selectFirstColor() {
        wait.until(ExpectedConditions.elementToBeClickable(firstColorOption));
        firstColorOption.click();
        logger.info("Selected first available color");
    }

    public void enterQuantity(String qty) {
        wait.until(ExpectedConditions.visibilityOf(quantityInput));
        quantityInput.clear();
        quantityInput.sendKeys(qty);
        logger.info("Entered quantity: " + qty);
    }

    public void clickAddToCart() {
        wait.until(ExpectedConditions.elementToBeClickable(addToCartBtn));
        addToCartBtn.click();
        logger.info("Clicked on Add to Cart button");
    }

    public boolean isSuccessMessageDisplayed() {
        wait.until(ExpectedConditions.visibilityOf(successMessage));
        logger.info("Success message displayed: " + successMessage.getText());
        return successMessage.isDisplayed();
    }
    
    public CartPage clickShoppingCartLink() {
        wait.until(ExpectedConditions.elementToBeClickable(shoppingCartLink));
        shoppingCartLink.click();
        logger.info("Clicked on shopping cart link from success message");
        return new CartPage();
    }
}
