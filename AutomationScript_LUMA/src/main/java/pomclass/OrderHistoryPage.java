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

public class OrderHistoryPage extends BaseClass {
    actionclass action = new actionclass();
    private WebDriverWait wait;
    private static final Logger logger = LogManager.getLogger(LoggerUtil.class.getClass());

    @FindBy(xpath = "//table[@id='my-orders-table']//tbody//tr[1]//td[@class='col id']")
    private WebElement latestOrderId;

    @FindBy(xpath = "//table[@id='my-orders-table']//tbody//tr[1]//td[@class='col actions']/a[@class='action view']")
    private WebElement viewLatestOrderObj;

    public OrderHistoryPage() {
        this.wait = new WebDriverWait(getDriver(), Duration.ofSeconds(15));
        PageFactory.initElements(getDriver(), this);
    }

    public String getLatestOrderId() {
        wait.until(ExpectedConditions.visibilityOf(latestOrderId));
        String idText = latestOrderId.getText();
        logger.info("Latest Order ID in History: " + idText);
        return idText;
    }

    public void clickViewLatestOrder() {
        wait.until(ExpectedConditions.elementToBeClickable(viewLatestOrderObj));
        viewLatestOrderObj.click();
        logger.info("Clicked to view latest order details");
    }
}
