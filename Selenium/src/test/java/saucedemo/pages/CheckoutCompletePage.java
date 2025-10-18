package saucedemo.pages;

import io.qameta.allure.Step;
import saucedemo.core.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CheckoutCompletePage extends BasePage {
    private final By thankYouHeader = By.cssSelector(".complete-header");
    private final By backHomeBtn = By.id("back-to-products");

    public CheckoutCompletePage(WebDriver driver) {
        super(driver);
    }

    @Step("Read Thank You header")
    public String getHeaderText() {
        return el(thankYouHeader).getText();
    }

    @Step("Back to products")
    public saucedemo.pages.ProductsPage backHome() {
        click(backHomeBtn);
        return new saucedemo.pages.ProductsPage(driver);
    }
}
