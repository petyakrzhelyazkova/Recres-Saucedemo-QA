package saucedemo.core;

import io.qameta.allure.Allure;
import org.openqa.selenium.*;
import org.testng.*;

import java.io.ByteArrayInputStream;
import java.nio.file.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TestListener implements ITestListener {

    private static final String DEFAULT_SCREENSHOT_DIR = "target/screenshots";
    private static final SimpleDateFormat DATE_FORMAT =
            new SimpleDateFormat("yyyyMMdd_HHmmss_SSS");

    private static Path buildScreenshotPath(ITestResult result) throws Exception {
        String baseDir = System.getProperty("shot.dir", DEFAULT_SCREENSHOT_DIR);
        String className = result.getTestClass().getRealClass().getSimpleName();
        String methodName = result.getMethod().getMethodName();
        String timestamp = DATE_FORMAT.format(new Date());

        Path classDir = Paths.get(baseDir, className);
        Files.createDirectories(classDir);

        return classDir.resolve(methodName + "_" + timestamp + ".png");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        Object instance = result.getInstance();
        if (!(instance instanceof HasDriver)) {
            System.out.println("[SCREENSHOT] Test instance does not implement HasDriver.");
            return;
        }

        WebDriver driver = ((HasDriver) instance).getDriver();
        if (driver == null) {
            System.out.println("[SCREENSHOT] WebDriver is null.");
            return;
        }

        try {
            byte[] screenshotBytes = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);

            Path outputPath = buildScreenshotPath(result);
            Files.write(outputPath, screenshotBytes);

            Allure.addAttachment("Failure screenshot", new ByteArrayInputStream(screenshotBytes));

            System.out.println("[SCREENSHOT] Saved to: " + outputPath.toAbsolutePath());
        } catch (Exception e) {
            System.out.println("[SCREENSHOT] Could not capture screenshot: " + e.getMessage());
        }
    }

    @Override public void onTestStart(ITestResult result) {}
    @Override public void onTestSuccess(ITestResult result) {}
    @Override public void onTestSkipped(ITestResult result) {}
    @Override public void onTestFailedButWithinSuccessPercentage(ITestResult result) {}
    @Override public void onStart(ITestContext context) {}
    @Override public void onFinish(ITestContext context) {}
}
