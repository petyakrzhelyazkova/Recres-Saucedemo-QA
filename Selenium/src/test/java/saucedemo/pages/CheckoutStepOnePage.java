package saucedemo.pages;

import io.qameta.allure.Step;
import saucedemo.core.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CheckoutStepOnePage extends BasePage {
    private final By firstName = By.id("first-name");
    private final By lastName = By.id("last-name");
    private final By postalCode = By.id("postal-code");
    private final By continueBtn = By.id("continue");

    public CheckoutStepOnePage(WebDriver driver) {
        super(driver);
    }

    @Step("Fill address and continue")
    public CheckoutStepTwoPage fillAndContinue(String f, String l, String zip) {
        type(firstName, f);
        type(lastName, l);
        type(postalCode, zip);
        click(continueBtn);
        return new CheckoutStepTwoPage(driver);
    }
}
