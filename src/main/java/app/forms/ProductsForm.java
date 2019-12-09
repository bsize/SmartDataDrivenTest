package app.forms;

import org.openqa.selenium.By;
import web_driver.elements.Button;

public class ProductsForm {

    private Button btnDownload;

    public void btnDownloadClick(String nameOfProduct) {
        btnDownload = new Button(By.xpath(String.format("//div[.//div[contains(text(), '%s')] and contains(@data-at-selector, 'downloadApplicationCard')]" +
                "//button[contains(text(), 'Скачать')]", nameOfProduct)), "Download");
        btnDownload.clickWithExecutor();
    }
}
