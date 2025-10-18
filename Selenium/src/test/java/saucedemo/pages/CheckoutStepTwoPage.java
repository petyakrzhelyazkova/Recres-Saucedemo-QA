package saucedemo.pages;

import io.qameta.allure.Step;
import saucedemo.core.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CheckoutStepTwoPage extends BasePage {
    private final By finishBtn = By.id("finish");

    public CheckoutStepTwoPage(WebDriver driver) {
        super(driver);
    }

    @Step("Finish checkout")
    public saucedemo.pages.CheckoutCompletePage finish() {
        click(finishBtn);
        return new saucedemo.pages.CheckoutCompletePage(driver);
    }
}
