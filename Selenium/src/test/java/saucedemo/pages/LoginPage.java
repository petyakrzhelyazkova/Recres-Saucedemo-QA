package saucedemo.pages;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import saucedemo.core.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.time.Duration;

import io.qameta.allure.Step;

public class LoginPage extends BasePage {
    private final By user = By.id("user-name");
    private final By pass = By.id("password");
    private final By loginBtn = By.id("login-button");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    @Step("Login with provided credentials")
    public saucedemo.pages.ProductsPage login(String username, String password) {
        type(user, username);
        type(pass, password);
        click(loginBtn);
        this.wait.until(ExpectedConditions.urlContains("/inventory.html"));
        this.wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".title")));
        return new saucedemo.pages.ProductsPage(driver);
    }

}
