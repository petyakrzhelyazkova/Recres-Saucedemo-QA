package com.saucedemo.tests;

import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Test;
import saucedemo.pages.SortOption;

@Epic("SauceDemo")
@Feature("Sorting")
public class Scenario2Test extends BaseTest {

    @Severity(SeverityLevel.NORMAL)
    @Story("Version 1 - Scenario 2")
    @Test(groups = {"regression"})
    public void sort_high_to_low() {

        products = login.login(user(), pass());
        products.selectSort(SortOption.PRICE_HIGH_TO_LOW);
        Assert.assertTrue(products.isSortedHighToLow(), "Products are sorted high to low");
        products.logout();
    }
}
