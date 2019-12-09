package web_driver;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import web_driver.Browser.Browsers;
import web_driver.exceptions.InvalidBrowserException;
import web_driver.logger.Logger;
import java.util.HashMap;

public class BrowserFactory {

    private static Logger logger = Logger.getInstance();

    public static WebDriver setUp(final Browsers type) throws InvalidBrowserException {
        WebDriver driver;
        switch (type) {
            case CHROME:
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver(setChromePreferences());
                break;
            case FIREFOX:
                WebDriverManager.firefoxdriver().setup();
                driver = new FirefoxDriver(setFireFoxPreferences());
                break;
            default:
                logger.error("Selected type of browser not support");
                throw new InvalidBrowserException();
        }
        return driver;
    }

    public static WebDriver setUp(final String type) throws InvalidBrowserException {
        for (Browsers t : Browsers.values()) {
            if (t.toString().equalsIgnoreCase(type)) {
                return setUp(t);
            }
        }
        throw new InvalidBrowserException();
    }

    private static FirefoxOptions setFireFoxPreferences() {
        WebDriverManager.firefoxdriver().setup();
        FirefoxProfile fprofile = new FirefoxProfile();
        fprofile.setPreference("intl.accept_languages", "ru");
        fprofile.setPreference("browser.download.dir", System.getProperty("user.dir"));
        fprofile.setPreference("browser.download.folderList", 2);
        fprofile.setPreference("browser.helperApps.neverAsk.saveToDisk", "application/x-debian-package,application/octet-stream");
        FirefoxOptions options = new FirefoxOptions();
        options.setProfile(fprofile);
        return options;
    }

    private static ChromeOptions setChromePreferences() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        HashMap<String, Object> chromePrefs = new HashMap<>();
        chromePrefs.put("download.default_directory", System.getProperty("user.dir"));
        chromePrefs.put("download.prompt_for_download", false);
        chromePrefs.put("download.directory_upgrade", true);
        chromePrefs.put("safebrowsing.enabled", true);
        chromePrefs.put("intl.accept_languages", "ru");
        options.setExperimentalOption("prefs", chromePrefs);
        return options;
    }
}