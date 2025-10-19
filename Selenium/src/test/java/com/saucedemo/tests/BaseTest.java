package com.saucedemo.tests;

import io.qameta.allure.testng.AllureTestNg;
import org.aeonbits.owner.ConfigFactory;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;
import org.testng.annotations.Listeners;
import saucedemo.config.AppConfig;
import saucedemo.core.DriverFactory;
import saucedemo.core.HasDriver;
import saucedemo.core.TestListener;
import saucedemo.pages.*;

@Listeners({ AllureTestNg.class, TestListener.class })
public abstract class BaseTest implements HasDriver {

    protected WebDriver driver;
    protected AppConfig cfg;

    protected LoginPage login;
    protected ProductsPage products;
    protected CartPage cart;
    protected CheckoutStepOnePage checkout1;
    protected CheckoutStepTwoPage checkout2;
    protected CheckoutCompletePage checkoutComplete;

    @BeforeClass(alwaysRun = true)
    public void setUpClass() {
        cfg = ConfigFactory.create(AppConfig.class, System.getProperties());
    }

    protected String user() {
        String fallback = (cfg.username() == null || cfg.username().isBlank())
                ? "standard_user" : cfg.username();
        return System.getProperty("username", fallback);
    }

    protected String pass() {
        String fallback = (cfg.password() == null || cfg.password().isBlank())
                ? "secret_sauce" : cfg.password();
        return System.getProperty("password", fallback);
    }

    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        driver = DriverFactory.create(
                System.getProperty("browser", "chrome"),
                System.getProperty("resolution", "")
        );
        driver.get(cfg.baseUrl());
        login = new LoginPage(driver);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Override
    public WebDriver getDriver() {
        return driver;
    }
}
