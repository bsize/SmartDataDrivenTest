package app.forms;

import org.openqa.selenium.By;
import web_driver.elements.Button;

public class ConfirmationForm {

    private Button btnConfirmation = new Button(By.xpath("//button[contains(text(),'OK')]"), "Confirmation");

    public void btnConfirmationClick(){
        btnConfirmation.click();
    }
}
