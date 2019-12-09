package app.menu;

import org.openqa.selenium.By;
import web_driver.elements.Button;

public class OSMenu {

    private Button btnTypeOfOS;

    public void btnTypeOfOSClick(String os) {
        btnTypeOfOS = new Button(By.xpath(String.format("//div[@data-at-selector='osName'][contains(text(),'%s')]", os)), os+"OS");
        btnTypeOfOS.click();
    }
}
