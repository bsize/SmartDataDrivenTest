package web_driver;

import web_driver.exceptions.InvalidBrowserException;
import web_driver.logger.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import web_driver.utils.PropertiesReader;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class Browser {

    static Browsers currentBrowser;
    private static Browser instance;
    private static Logger logger = Logger.getInstance();
    private static WebDriver driver;
    private static String timeoutForPageLoad;
    private static String implicitWait;
    private static String timeoutForCondition;
    private static URL confProp = PropertiesReader.class.getClassLoader().getResource("configuration.properties");


    private Browser() {
        logger.info(String.format(logger.getLoc("loc.browser.ready"), currentBrowser.toString()));
    }

    public static String getUrl() {
        PropertiesReader props = new PropertiesReader();
        props.setUrl(confProp);
        return props.getTestProps("url");
    }

    public static Browser getInstance() {
        PropertiesReader props = new PropertiesReader();
        props.setUrl(confProp);
        if (instance == null) {
            initProperties();
            try {
                driver = BrowserFactory.setUp(currentBrowser.toString());
                driver.manage().timeouts().implicitlyWait(Long.parseLong(implicitWait), TimeUnit.SECONDS);
                logger.info(logger.getLoc("loc.browser.constructed"));
            } catch (InvalidBrowserException e) {
                logger.error("Browser driver wasn't constructed");
            }
            instance = new Browser();
        }
        return instance;
    }

    private static void initProperties() {
        logger.info(logger.getLoc("loc.browser.init.properties"));
        PropertiesReader props = new PropertiesReader();
        props.setUrl(confProp);
        try {
            timeoutForCondition = props.getTestProps("defaultConditionTimeout");
            implicitWait = props.getTestProps("implicitWait");
            timeoutForPageLoad = props.getTestProps("pageLoadTimeout");
            currentBrowser = Browsers.valueOf(props.getTestProps("browser").toUpperCase());
        }catch (Exception e){
           logger.error("Exception " + e);
        }
    }

    public static int getTimeoutForCondition() {
        return Integer.parseInt(timeoutForCondition);
    }

    public void exit() {
        try {
            driver.quit();
            logger.info(logger.getLoc("loc.browser.driver.quit"));
            driver = null;
            instance = null;
        } catch (Exception e) {
            logger.error("Driver didn't quit");
        } finally {
            instance = null;
        }
    }

    public static String getTimeoutForPageLoad() {
        return timeoutForPageLoad;
    }

    public void windowMaximise() {
        logger.info("Set window maximise");
        driver.manage().window().maximize();
    }

    public void navigate(final String url) {
        logger.info("Navigate to " + url);
        driver.navigate().to(url);
    }

    public WebDriver getDriver() {
        return driver;
    }

    public enum Browsers {
        FIREFOX("firefox"),
        CHROME("chrome");
        public String value;

        Browsers(final String values) {
            value = values;
        }

        public String toString() {
            return value;
        }
    }
}
