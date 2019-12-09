package web_driver.utils;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import web_driver.Browser;
import web_driver.logger.Logger;

public class Waiters {

    private static final int TIMEOUT_FOR_CONDITIONS = Browser.getTimeoutForCondition();
    private static final Long TIMEOUT_FOR_PAGE_LOAD = Long.parseLong(Browser.getTimeoutForPageLoad());
    private static Logger logger = Logger.getInstance();


    public static void waitToBeClickable(By locator, String name) {
        try {
            logger.info(String.format("Wait until element '%s' to be clickable", name));
            WebDriverWait wait = new WebDriverWait(Browser.getInstance().getDriver(), TIMEOUT_FOR_CONDITIONS);
            wait.until(ExpectedConditions.elementToBeClickable(locator));
        } catch (TimeoutException e) {
            logger.warn(logger.getLoc("loc.element.isnt.clickable") + " " + TIMEOUT_FOR_CONDITIONS + " seconds.");
        }
    }

    public static void waitToBePresented(By locator) {
        try {
            logger.info("Wait until element to be presented");
            WebDriverWait wait = new WebDriverWait(Browser.getInstance().getDriver(), TIMEOUT_FOR_CONDITIONS);
            wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        } catch (TimeoutException e) {
            logger.warn(logger.getLoc("loc.isnt.present") + " " + TIMEOUT_FOR_CONDITIONS + " seconds.");
        }
    }

    public static void waitToBeVisible(WebElement element) {
        try {
            logger.info("Wait until element to be visible");
            WebDriverWait wait = new WebDriverWait(Browser.getInstance().getDriver(), TIMEOUT_FOR_CONDITIONS);
            wait.until(ExpectedConditions.visibilityOf(element));
        } catch (TimeoutException e) {
            logger.warn(logger.getLoc("loc.isnt.present") + " " + TIMEOUT_FOR_CONDITIONS + " seconds.");
        }
    }

    public static void waitToBeVisibleElementLocated(By locator, int waitTime) {
        try {
            logger.info("Wait until element to be visible");
            new WebDriverWait(Browser.getInstance().getDriver(), waitTime).
                    until(ExpectedConditions.visibilityOfElementLocated(locator));
        } catch (TimeoutException e) {
            logger.warn("Element isn't present");
        }
    }

    public static void waitToBeInVisibleElementLocated(By locator, int waitTime) {
        try {
            logger.info("Wait until element to be visible");
            new WebDriverWait(Browser.getInstance().getDriver(), waitTime).
                    until(ExpectedConditions.invisibilityOfElementLocated(locator));
        } catch (TimeoutException e) {
            logger.warn("Element isn't present");
        }
    }

    public static void waitElement() {
        try {
            logger.info("Wait until element to be presented");
            (new WebDriverWait(Browser.getInstance().getDriver(), TIMEOUT_FOR_CONDITIONS)).until((element) -> false);
        } catch (TimeoutException e) {
            logger.warn("Element isn't present");
        }
    }

    public static void waitForPageToLoad() {
        logger.info("Wait until for page to load");
        WebDriverWait wait = new WebDriverWait(Browser.getInstance().getDriver(), TIMEOUT_FOR_PAGE_LOAD);
        try {
            wait.until(d -> {
                if (!(d instanceof JavascriptExecutor)) {
                    return true;
                }
                Object result = ((JavascriptExecutor) d)
                        .executeScript("return document['readyState'] ? 'complete' == document.readyState : true");
                if (result != null && result instanceof Boolean && (Boolean) result) {
                    return true;
                }
                return false;
            });
        } catch (Exception e) {
            logger.error(logger.getLoc("loc.browser.page.timeout"));
        }
    }
}
