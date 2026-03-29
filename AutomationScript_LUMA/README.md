# LUMA Test Automation Framework

This project is a robust, highly scalable **Test Automation Framework** built completely from scratch using **Java, Selenium WebDriver, and TestNG**. It handles UI test automation specifically targeted at the famous [Magento 2 Luma E-Commerce theme](https://magento.softwaretestingboard.com/).

## 🛠️ Technology Stack
* **Programming Language:** Java 8+
* **Automation Engine:** Selenium WebDriver (v4.14.1)
* **Testing Framework:** TestNG (v7.4.0)
* **Build Tool:** Maven
* **Design Pattern:** Page Object Model (POM) & Data-Driven
* **Data Reading:** Apache POI (Excel `.xlsx`)
* **Logging Engine:** Log4j2
* **Reporting Output:** ExtentReports 5

---

## 🏗️ Framework Architecture
The framework follows the **Page Object Model (POM)** structure to tightly maintain UI WebElements segregated from the Test flows:

*   **`src/main/java`**
    *   **`pomclass`**: Contains centralized Locators and Page Action logic (e.g. `CheckoutPage.java`, `CartPage.java`, `ProductDetailPage.java`).
    *   **`baseclass`**: Core execution handles configurations, WebDriver setups natively via WebDriverManager, and environment parameters before running any `@Test`.
    *   **`dataprovider`**: Provides multi-dimensional iteration fetching complex `.xlsx` scenarios matching tests using `DataProviders.java`.
    *   **`actiondriver`**: Holds heavily customized Selenium Wrapper logic like waits, hover, scroll via `actionclass.java`.
    *   **`utilityclass`**: Helper bundles including Custom Listeners, ExtentManager rendering engine, Log4j formatters, and Excel Reading logic via `NewExcelLibrary.java`.

*   **`src/test/java`**
    *   **`testcases` & `RegressionSuites`**: Organized containers isolating real Test Execution endpoints (`TC_EndToEndCheckout.java`, `TC_ProductSearchFunctionality.java`, `TC_CreateUser.java`). Only logic interacting with DataProviders and Validation `Assert` methods dwell here.

*   **`Configuration/`**
    *   **`config.properties`**: Externalized environment controls (browser definitions, Base URL, wait timeouts, etc.).

---

## 🎯 Key Test Cases Automations implemented:
1.  **TC_EndToEndCheckout**: Maps the complete user purchasing journey. Logs in (optionally), Searches for an item, selects custom product properties (Size & Color), adds it to the Magento cart, proceeds through the dynamically injected shipping phase, and places a verified order capturing its ID. 
2.  **TC_CreateUser**: Generates unique disposable inboxes utilizing `Putsbox`/`Mailosaur` API, captures extracted names, registers them flawlessly on Magento, signs out, and seamlessly handles session re-login assertions.
3.  **TC_ProductSearchFunctionality**: Handles keyword scraping via Data Providers, closes Google Ads automatically, iterates gracefully through dynamic product catalog pagination grids mapping keyword assertions inside UI nodes.

---

## 🚀 Execution Guide

### Prerequisites
Make sure your system contains: 
*   **Java Development Kit (JDK) 8+** configured on PATH environment variables.  
*   **Apache Maven** installed. 
*   **Google Chrome** installed (WebDriver execution dynamically matches bindings via `webdrivermanager`).

### Running via TestNG XML
If using an IDE like Eclipse or IntelliJ, right-click on the `testng.xml` inside the root tree structure or within `TestNG_Suite` and select **Run As -> TestNG Suite**.

### Running via Maven CLI
Execute the entire regression pack natively via terminal:
```bash
mvn clean test
```

---

## 📊 Reporting & Logs Engine
*   **Test Logs**: Trace detailed step execution debugs via dynamically captured log trails stored systematically in `LUMAAutomation_Logs\`.
*   **Extent Reports**: Fully formatted dynamic HTML Extent GUI reports are mapped inside ExtentReports framework directory output visualizing PASS/FAIL steps alongside captured failure screenshots. Setup configurations for report bounds mapping lives exclusively inside `extent_CONFIG.xml`.
