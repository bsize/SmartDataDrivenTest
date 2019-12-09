package web_driver.elements;

import org.openqa.selenium.By;
import web_driver.entity.BaseElement;

public class Button extends BaseElement {
    public Button(final By locator, String name) {
        super(locator, name);
    }
}
