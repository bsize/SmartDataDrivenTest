import app.utils.Steps;
import web_driver.utils.CheckMailAPI;
import web_driver.logger.Logger;
import web_driver.Browser;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import web_driver.utils.Waiters;

public abstract class BaseEntity {
    protected static Logger logger = Logger.getInstance();
    private static Browser browser;
    public Steps steps = new Steps();

    @BeforeClass
    public void before() {
        logger.logTestName(this.getClass().getName());
        CheckMailAPI.deleteMessageBeforeTest();
        browser = Browser.getInstance();
        browser.windowMaximise();
        browser.navigate(Browser.getUrl());
        Waiters.waitForPageToLoad();
    }

    @AfterClass
    public void after() {
        browser.exit();
    }
}
