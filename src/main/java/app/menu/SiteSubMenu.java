package app.menu;

import org.openqa.selenium.By;
import web_driver.elements.Button;

public class SiteSubMenu {

    private Button btnSiteSubMenu;

    public void btnSiteSubMenuClick(String value) {
        btnSiteSubMenu = new Button(By.xpath(String.format("//div[starts-with(@class, 'w-menu') and contains(text(), '%s')]", value)), "Site sub menu");
        btnSiteSubMenu.click();
    }


}
