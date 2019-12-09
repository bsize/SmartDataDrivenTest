package app.utils;

import app.forms.*;
import app.pages.*;
import web_driver.logger.Logger;
import web_driver.utils.CheckMailAPI;
import org.testng.Assert;
import web_driver.utils.RegularExpression;
import javax.mail.MessagingException;
import java.io.IOException;

public class Steps {

    private static HomePage homePage;
    private static SignInForm signInForm;
    private static MainPageWithProduct mainPageWithProduct;
    private static ProductsForm productsForm;
    private static DownloadForm downloadForm;
    private static SendEmailForm sendEmailForm;
    private static ConfirmationForm confirmationForm;
    private static Logger logger = Logger.getInstance();
    private static String regexForSearchInText = "https.*Download";
    private static String searchWord = "Download";
    private SiteSubMenuEnum enums;

    public void btnSignInClick() {
        homePage = new HomePage();
        homePage.btnSignInClick();
    }

    public void authorize(String email, String password) {
        btnSignInClick();
        signInForm = new SignInForm();
        signInForm.typeEmail(email);
        signInForm.typePassword(password);
        signInForm.btnSignInClick();
    }

    public void btnSiteSubMenuClick() {
        mainPageWithProduct = new MainPageWithProduct();
        mainPageWithProduct.sectionMenu.btnSiteSubMenuClick(enums.LOADS.getValue());
    }

    public void btnTypeOfOSClick(String os) {
        mainPageWithProduct.osMenu.btnTypeOfOSClick(os);
    }

    public void btnDownloadClick(String productName) {
        productsForm = new ProductsForm();
        productsForm.btnDownloadClick(productName);
    }

    public void btnSendByEmailClick() {
        downloadForm = new DownloadForm();
        downloadForm.btnSendByEmailClick();
    }

    public void btnSendClick() {
        sendEmailForm = new SendEmailForm();
        sendEmailForm.btnSendClick();
        btnConfirmationClick();
    }

    public void btnConfirmationClick() {
        confirmationForm = new ConfirmationForm();
        confirmationForm.btnConfirmationClick();
    }

    public void checkMessageInMail() throws IOException, MessagingException {
        logger.info(logger.getLoc("loc.check.mail"));
        Assert.assertTrue(RegularExpression.searchRegex(regexForSearchInText, CheckMailAPI.getMessage()).contains(searchWord), "Text in message don't contains necessary word");
    }
}
