package saucedemo.pages;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import saucedemo.core.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import io.qameta.allure.Step;
import saucedemo.components.Header;

public class ProductsPage extends BasePage {
    private final Header header;
    private final By title = By.cssSelector(".title");
    private final By inventoryItems = By.cssSelector(".inventory_item");
    private final By cartBadge = By.cssSelector(".shopping_cart_badge");
    private final By cartLink = By.cssSelector(".shopping_cart_link");
    private final By sortDropdown = By.cssSelector("select[data-test='product-sort-container']");
    private final By items = By.cssSelector(".inventory_list .inventory_item");
    private final By itemName = By.cssSelector(".inventory_item_name");
    private final By addBtn = By.cssSelector("button[data-test^='add-to-cart']");
    private final By removeBtn = By.cssSelector("button[data-test^='remove']");

    public ProductsPage(WebDriver driver) {
        super(driver);
        this.header = new saucedemo.components.Header(driver);
        el(title);
    }

    public int itemCount() {
        return driver.findElements(items).size();
    }

    public String getItemName(int index) {
        WebElement card = driver.findElements(items).get(index);
        return card.findElement(itemName).getText().trim();
    }

    public double getItemPrice(int index) {
        WebElement item = driver.findElements(inventoryItems).get(index);
        String priceText = item.findElement(By.className("inventory_item_price")).getText().replace("$", "");
        return Double.parseDouble(priceText);
    }

    public void addItemToCart(int index) {
        int before = cartCount();

        WebElement card = driver.findElements(items).get(index);
        List<WebElement> addButtons = card.findElements(addBtn);
        if (!addButtons.isEmpty()) {
            addButtons.get(0).click();
            new WebDriverWait(driver, Duration.ofSeconds(3))
                    .until(d -> !card.findElements(removeBtn).isEmpty());
        }

        new WebDriverWait(driver, Duration.ofSeconds(3))
                .until(d -> cartCount() == before + 1);
    }

    public void removeItemFromCart(int index) {
        driver.findElements(inventoryItems).get(index).findElement(By.tagName("button")).click();
    }

    public int cartCount() {
        return header.cartCount();
    }

    public CartPage openCart() {
        click(cartLink);
        return new CartPage(driver);
    }

    @Step("Select sort")
    public void selectSort(String value) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        WebElement selectEl = wait.until(ExpectedConditions.visibilityOfElementLocated(sortDropdown));
        wait.until(d -> selectEl.isEnabled());

        String beforeFirst = driver.findElements(items).get(0).findElement(itemName).getText();

        new org.openqa.selenium.support.ui.Select(selectEl).selectByValue(value);

        wait.until(drv -> {
            List<WebElement> list = drv.findElements(items);
            if (list.isEmpty()) return true;
            String now = list.get(0).findElement(itemName).getText();
            return !now.equals(beforeFirst);
        });
    }

    @Step("Verify prices sorted High→Low")
    public boolean isSortedHighToLow() {
        List<Double> prices = new ArrayList<>();
        for (int i = 0; i < itemCount(); i++) prices.add(getItemPrice(i));
        List<Double> sorted = new ArrayList<>(prices);
        sorted.sort(Comparator.reverseOrder());
        return prices.equals(sorted);
    }

    @Step("Select sort")
    public void selectSort(saucedemo.pages.SortOption option) {
        selectSort(option.value());
    }

    public void logout() {
        header.logout();
    }
}
