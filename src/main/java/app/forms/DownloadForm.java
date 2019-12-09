package app.forms;

import org.openqa.selenium.By;
import web_driver.elements.Button;

public class DownloadForm {

    private Button btnSendByEmail = new Button(By.xpath("//span[contains(text(),'Отправить по почте')]"), "Send by email");

    public void btnSendByEmailClick() {
        btnSendByEmail.click();
    }
}
