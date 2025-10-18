package saucedemo.pages;

import io.qameta.allure.Step;
import saucedemo.core.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.stream.Collectors;

public class CartPage extends BasePage {
    private final By cartItem = By.cssSelector(".cart_item");
    private final By checkoutBtn = By.id("checkout");
    private final By continueShoppingBtn = By.id("continue-shopping");

    public CartPage(WebDriver driver) {
        super(driver);
    }

    @Step("Get cart item names")
    public java.util.List<String> getCartItemNames() {
        return driver.findElements(cartItem).stream()
                .map(e -> e.findElement(By.className("inventory_item_name")).getText())
                .collect(Collectors.toList());
    }

    @Step("Proceed to checkout")
    public saucedemo.pages.CheckoutStepOnePage goToCheckout() {
        click(checkoutBtn);
        return new saucedemo.pages.CheckoutStepOnePage(driver);
    }

    @Step("Continue shopping")
    public saucedemo.pages.ProductsPage continueShopping() {
        click(continueShoppingBtn);
        return new saucedemo.pages.ProductsPage(driver);
    }
}
