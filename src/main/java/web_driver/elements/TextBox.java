package web_driver.elements;

import org.openqa.selenium.By;
import web_driver.logger.Logger;
import web_driver.entity.BaseElement;

public class TextBox extends BaseElement {

    private static Logger logger = Logger.getInstance();

    public TextBox(By locator, String name) {
        super(locator, name);
    }

    public void sendKeys(String key) {
        waitForIsElementPresent();
        getElement().sendKeys(key);
        logger.info(logger.getLoc("loc.set.value") + key + " in field " + name);
    }
}