package web_driver.entity;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.MoveTargetOutOfBoundsException;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import web_driver.Browser;
import web_driver.logger.Logger;
import web_driver.utils.Waiters;
import java.util.List;
import java.util.concurrent.TimeUnit;

public abstract class BaseElement {

    protected String name;
    protected By locator;
    protected WebElement element;
    protected Actions action;
    private static final int TIMEOUT_WAIT = 0;
    private static final int TIMEOUT_FOR_CONDITIONS = Browser.getTimeoutForCondition();
    private static Logger logger = Logger.getInstance();

    protected BaseElement(final By locator, final String name) {
        this.locator = locator;
        this.name = name;
        this.element = Browser.getInstance().getDriver().findElement(locator);
    }

    public BaseElement(By locator) {
        this.locator = locator;
    }

    public BaseElement(String string, String name) {

    }

    public WebElement getElement() {
        logger.info(logger.getLoc("loc.element.getelement") + name);
        return element;
    }

    public void click() {
        Waiters.waitToBeClickable(locator, name);
        action = new Actions(Browser.getInstance().getDriver());
        try {
            action.click(getElement()).build().perform();
            logger.info(logger.getLoc("loc.clicking") + name);
        } catch (MoveTargetOutOfBoundsException e) {
            JavascriptExecutor js = (JavascriptExecutor) Browser.getInstance().getDriver();
            js.executeScript("arguments[0].scrollIntoView();", getElement());
            action.click(getElement()).build().perform();
            logger.info(logger.getLoc("loc.clicking") + name);
        }
    }

    public void clickWithExecutor() {
        waitForIsElementPresent();
        JavascriptExecutor js = (JavascriptExecutor) Browser.getInstance().getDriver();
        js.executeScript("arguments[0].click();", getElement());
        logger.info(logger.getLoc("loc.clicking") + name);
    }

    public void moveToElement() {
        action = new Actions(Browser.getInstance().getDriver());
        try {
            action.moveToElement(getElement()).build().perform();
            logger.info(logger.getLoc("loc.moveto") + name);
        } catch (MoveTargetOutOfBoundsException e) {
            JavascriptExecutor js = (JavascriptExecutor) Browser.getInstance().getDriver();
            js.executeScript("arguments[0].scrollIntoView();", getElement());
            action.moveToElement(getElement()).build().perform();
            logger.info(logger.getLoc("loc.moveto") + name);
        }
    }

    public String getText() {
        waitForIsElementPresent();
        logger.info(logger.getLoc("loc.getting.text") + name);
        return element.getText();
    }

    public boolean isDisplayed(){
        waitForIsElementPresent();
        logger.info(name+" "+logger.getLoc("loc.is.present"));
        return element.isDisplayed();
    }

    public boolean isPresent() {
        boolean isPresent = isPresent(TIMEOUT_WAIT);
        logger.info(name+" "+logger.getLoc("loc.is.present"));
        return isPresent;
    }

    public String getAttribute(String key) {
        logger.info(logger.getLoc("loc.element.getattribute" + key + " of ") + name);
        return element.getAttribute(key);
    }

    public String getAttributeValue() {
        logger.info("Getting attribute " + name);
        return element.getAttribute("value");
    }

    public void waitForIsElementPresent() {
        logger.info(String.format("Wait until element '%s' to be present", name));
        isPresent(TIMEOUT_FOR_CONDITIONS);
        Assert.assertTrue(element.isDisplayed());
    }

    public boolean isPresent(int timeout) {
        WebDriverWait wait = new WebDriverWait(Browser.getInstance().getDriver(), timeout);
        Browser.getInstance().getDriver().manage().timeouts().implicitlyWait(TIMEOUT_WAIT, TimeUnit.SECONDS);
        try {
            wait.until(driver -> {
                try {
                    List<WebElement> list = driver.findElements(locator);
                    for (WebElement el : list) {
                        if (el instanceof WebElement && el.isDisplayed()) {
                            element = (WebElement) el;
                            return element.isDisplayed();
                        }
                    }
                    element = (WebElement) driver.findElement(locator);
                } catch (Exception e) {
                    logger.error("Exception" + e);
                    return false;
                }
                return element.isDisplayed();
            });
        } catch (Exception e) {
            logger.error("Exception" + e);
            return false;
        }
        try {
            Browser.getInstance().getDriver().manage().timeouts().implicitlyWait(TIMEOUT_FOR_CONDITIONS, TimeUnit.SECONDS);
            return element.isDisplayed();
        } catch (Exception e) {
            logger.error("Exception" + e);
        }
        return false;
    }
}
