package app.forms;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import web_driver.logger.Logger;
import web_driver.elements.Button;
import web_driver.elements.Label;
import web_driver.utils.PropertiesReader;
import web_driver.utils.Waiters;
import java.net.URL;

public class SendEmailForm {

    private static Logger logger = Logger.getInstance();
    private static URL confProp = PropertiesReader.class.getClassLoader().getResource("configuration.properties");
    private static By locatorForIframeOne = By.xpath("//iframe[starts-with(@name, 'a-') and starts-with(@src, 'https://www.google.com/recaptcha')]");
    private static By locatorForIframeTwo = By.xpath("//iframe[starts-with(@name, 'c-') and starts-with(@src, 'https://www.google.com/recaptcha')]");
    private Button btnSend = new Button(By.xpath("//span[contains(text(),'ОТПРАВИТЬ')]"), "Send");
    private Label captcha;


    public void checkCaptchaIsPresent() {
        try {
            PropertiesReader propertiesReader = new PropertiesReader();
            propertiesReader.setUrl(confProp);
            int waitForElementToBePresent = Integer.parseInt(propertiesReader.getTestProps("waitForElementToBePresent"));
            int waitForElementToBeInPresent = Integer.parseInt(propertiesReader.getTestProps("waitForElementToBeInPresent"));
            captcha = new Label(locatorForIframeOne, "iframe captcha");
            if(captcha.isPresent(waitForElementToBePresent)){
                Waiters.waitToBeVisibleElementLocated(locatorForIframeTwo, waitForElementToBePresent);
                Waiters.waitToBeInVisibleElementLocated(locatorForIframeTwo, waitForElementToBeInPresent);
            }
        } catch (NoSuchElementException e) {
            logger.info("Element captcha isn't present");
        }
    }

    public void btnSendClick() {
        checkCaptchaIsPresent();
        btnSend.click();
    }
}
