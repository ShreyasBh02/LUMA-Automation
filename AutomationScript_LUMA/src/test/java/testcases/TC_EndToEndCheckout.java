package testcases;

import java.time.Duration;
import java.util.HashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;

import actiondriver.actionclass;
import baseclass.BaseClass;
import dataprovider.DataProviders;
import pomclass.CartPage;
import pomclass.CheckoutPage;
import pomclass.CustomerLogin;
import pomclass.HomePage;
import pomclass.OrderConfirmationPage;
import pomclass.ProductDetailPage;
import pomclass.ProductListingPage;
import utilityclass.ExtentManager;
import utilityclass.LoggerUtil;

public class TC_EndToEndCheckout extends BaseClass {

    HomePage homePage;
    CustomerLogin login;
    ProductListingPage productListingPage;
    ProductDetailPage productDetailPage;
    CartPage cartPage;
    CheckoutPage checkoutPage;
    OrderConfirmationPage orderConfirmationPage;
    
    WebDriverWait wait;
    actionclass action = new actionclass();
    private static final Logger Logs = LogManager.getLogger(LoggerUtil.class.getClass());

    @BeforeMethod
    public void websetup() throws Throwable {
        String browser = prop.getProperty("Browser");
        String URL = prop.getProperty("URL");
        launchWebApp(browser, URL);
        initialize();
    }

    @Test(dataProvider = "Product Data Provider", dataProviderClass = DataProviders.class)
    public void EndToEndCheckout(HashMap<String, String> hashMapvalue) throws Throwable {
        wait = new WebDriverWait(getDriver(), Duration.ofSeconds(15));
        try {
            // Optionally: If checkout requires a logged-in user in your environment, uncomment and set the credentials.
            /*
            homePage.clickSignIn();
            Logs.info("Navigating to Login Page");
            login.enterEmailID("testuser@example.com"); 
            login.enterPassword("Test@1234");
            login.clickSignIn();
            ExtentManager.log("Logged into application", Status.INFO);
            */

            // 1. Search for a Product
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='search']")));
            String searchKeyword = hashMapvalue.get("SearchKeyword1") != null ? hashMapvalue.get("SearchKeyword1") : "shirt";
            homePage.searchProduct(searchKeyword);
            Logs.info("Searched for product: " + searchKeyword);
            ExtentManager.log("Searched for product: " + searchKeyword, Status.INFO);

            // Wait for search results
            action.waitForPageLoad(getDriver(), 15);
            Assert.assertTrue(getDriver().getCurrentUrl().contains("catalogsearch/result"), "Failed to load Search Results");

            // 2. Click the First Product
            WebElement firstProduct = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//a[@class='product-item-link'])[1]")));
            firstProduct.click();
            Logs.info("Clicked on the first product in the search results.");
            ExtentManager.log("Opened Product Detail Page", Status.INFO);

            // 3. Product Detail Page actions
            productDetailPage.selectFirstSize();
            productDetailPage.selectFirstColor();
            productDetailPage.enterQuantity("1");
            productDetailPage.clickAddToCart();
            
            Assert.assertTrue(productDetailPage.isSuccessMessageDisplayed(), "Add to cart success message not displayed!");
            ExtentManager.log("Item added to cart successfully", Status.PASS);

            // 4. Go to Cart
            cartPage = productDetailPage.clickShoppingCartLink();
            ExtentManager.log("Navigated to Cart Page", Status.INFO);

            // 5. Proceed to Checkout
            checkoutPage = cartPage.clickProceedToCheckout();
            ExtentManager.log("Navigated to Checkout Page", Status.INFO);

            // 6. Checkout Flow - Shipping
            action.waitForPageLoad(getDriver(), 15);
            Thread.sleep(5000); // Allow loader to fade out (checkout loading overlay is heavily dynamic in Magento)

            // Select method & proceed
            checkoutPage.selectShippingMethod();
            checkoutPage.clickNextButton();
            ExtentManager.log("Completed Shipping Section", Status.INFO);

            // 7. Checkout Flow - Payment & Place Order
            orderConfirmationPage = checkoutPage.clickPlaceOrder();
            ExtentManager.log("Order Placed", Status.INFO);

            // 8. Order Confirmation validation
            String successMsg = orderConfirmationPage.getSuccessMessage();
            Assert.assertEquals(successMsg, "Thank you for your purchase!", "Order success message mismatch.");
            
            String orderId = orderConfirmationPage.getOrderId();
            Assert.assertNotNull(orderId, "Order ID is null.");
            
            Logs.info("End to End Checkout completed successfully with Order ID: " + orderId);
            ExtentManager.log("Order generated successfully: " + orderId, Status.PASS);
            
            orderConfirmationPage.clickContinueShopping();

        } catch (Exception e) {
            Logs.error("An error occurred during End-to-End Checkout: " + e.getMessage(), e);
            ExtentManager.log("Test failed due to an error: " + e.getMessage(), Status.FAIL);
            Assert.fail("E2E Checkout test failed", e);
        }
    }

    public void initialize() {
        homePage = new HomePage();
        login = new CustomerLogin();
        productListingPage = new ProductListingPage();
        productDetailPage = new ProductDetailPage();
        // Since cartPage, checkoutPage and orderConfirmationPage are instantiated dynamically via POM method returns,
        // we can leave them out of the initialization wrapper.
    }
}
