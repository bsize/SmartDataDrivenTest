package app.pages;

import org.openqa.selenium.By;
import web_driver.elements.Button;

public class HomePage {

    private Button btnSignIn = new Button(By.xpath("//button[contains(text(), 'Войти')]"), "SignIn");

    public HomePage() {
        super();
    }

    public void btnSignInClick() {
        btnSignIn.click();
    }
}
