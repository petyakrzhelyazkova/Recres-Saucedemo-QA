package com.saucedemo.tests;

import io.qameta.allure.*;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;

@Epic("SauceDemo")
@Feature("Checkout Flow")
public class Scenario1Test extends BaseTest {

    @Severity(SeverityLevel.CRITICAL)
    @Story("Version 1 - Scenario 1")
    @Test(groups = {"smoke","regression"})
    public void endToEnd_checkout_flow() {

        products = login.login(user(), pass());

        int size = products.itemCount();
        String firstItem = products.getItemName(0);
        String lastItem = products.getItemName(size - 1);
        products.addItemToCart(0);
        products.addItemToCart(size - 1);

        new WebDriverWait(driver, Duration.ofSeconds(3))
                .until(d -> products.cartCount() == 2);
        Assert.assertEquals(products.cartCount(), 2, "Cart count after adding first+last");

        cart = products.openCart();
        List<String> names = cart.getCartItemNames();
        Assert.assertTrue(names.contains(firstItem) && names.contains(lastItem), "Cart contains first & last");

        products = cart.continueShopping();
        products.removeItemFromCart(0);
        String prevToLast = products.getItemName(size - 2);
        products.addItemToCart(size - 2);
        Assert.assertEquals(products.cartCount(), 2, "Cart size remains 2");

        cart = products.openCart();
        names = cart.getCartItemNames();
        Assert.assertFalse(names.contains(firstItem), "First removed");
        Assert.assertTrue(names.contains(prevToLast) && names.contains(lastItem), "Has prev-to-last + last");

        checkout1 = cart.goToCheckout();
        checkout2 = checkout1.fillAndContinue("Petya", "Zhelyazkova", "9000");
        checkoutComplete = checkout2.finish();

        Assert.assertTrue(checkoutComplete.getHeaderText().toLowerCase().contains("thank you"), "Order placed");

        products = checkoutComplete.backHome();
        Assert.assertEquals(products.cartCount(), 0, "Cart badge cleared");

        products.logout();
    }
}
