package saucedemo.components;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import saucedemo.core.BasePage;

public class Header extends BasePage {
    private final By burgerMenu = By.id("react-burger-menu-btn");
    private final By logoutLink = By.id("logout_sidebar_link");
    private final By cartBadge = By.cssSelector(".shopping_cart_badge");

    public Header(WebDriver driver) { super(driver); }

    @Step("Open burger menu")
    public void openMenu() { click(burgerMenu); }

    @Step("Logout via header")
    public void logout() { openMenu(); click(logoutLink); }

    @Step("Get cart badge count")
    public int cartCount() {
        try { return Integer.parseInt(driver.findElement(cartBadge).getText().trim()); }
        catch (Exception e) { return 0; }
    }
}
