package saucedemo.core;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;

import java.util.HashMap;
import java.util.Map;

public class DriverFactory {
    public static WebDriver create(String browser, String resolution) {
        if (browser == null || browser.isBlank()) browser = "chrome";
        WebDriver driver;
        switch (browser.toLowerCase()) {
            case "firefox" -> {
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions fo = new FirefoxOptions();
                FirefoxProfile profile = new FirefoxProfile();
                profile.setPreference("signon.rememberSignons", false);
                profile.setPreference("signon.autofillForms", false);
                profile.setPreference("extensions.formautofill.creditCards.enabled", false);

                fo.setProfile(profile);
                if (isHeadless(resolution)) fo.addArguments("-headless");
                driver = new FirefoxDriver(fo);
            }
            default -> {
                WebDriverManager.chromedriver().setup();
                ChromeOptions co = new ChromeOptions();
                Map<String, Object> prefs = new HashMap<>();
                prefs.put("credentials_enable_service", false);
                prefs.put("profile.password_manager_enabled", false);
                prefs.put("autofill.profile_enabled", false);
                prefs.put("autofill.credit_card_enabled", false);
                co.setExperimentalOption("prefs", prefs);

                co.addArguments("--disable-save-password-bubble");
                co.addArguments("--disable-features=PasswordLeakDetection,PasswordManagerOnboarding,AutofillServerCommunication");
                co.addArguments("--incognito");
                co.addArguments("--disable-notifications");
                if (isHeadless(resolution)) co.addArguments("--headless=new");
                driver = new ChromeDriver(co);
            }
        }
        if (resolution != null && !resolution.isBlank() && !resolution.equalsIgnoreCase("headless")) {
            Dimension dim = parseResolution(resolution);
            if (dim != null) driver.manage().window().setSize(dim);
            else driver.manage().window().maximize();
        } else if (!isHeadless(resolution)) {
            driver.manage().window().maximize();
        }
        return driver;
    }

    private static boolean isHeadless(String resolution) {
        return resolution != null && resolution.equalsIgnoreCase("headless");
    }

    private static Dimension parseResolution(String resolution) {
        try {
            if (resolution.contains("x")) {
                String[] parts = resolution.toLowerCase().split("x");
                return new Dimension(Integer.parseInt(parts[0].trim()), Integer.parseInt(parts[1].trim()));
            }
        } catch (Exception ignored) {}
        return null;
    }
}
