package com.grid.tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.net.URL;

public class GridTest {

    WebDriver driver;

    @Parameters("browser")
    @Test
    public void openGoogleOnGrid(String browser) throws Exception {

        // Read execution mode: local or grid
        String executionMode = System.getProperty("execution", "local");
        System.out.println("Execution mode = " + executionMode);

        if ("grid".equalsIgnoreCase(executionMode)) {

            // Grid host (Jenkins / Docker / Local)
            String gridHost = System.getProperty("grid.host", "localhost");
            URL gridUrl = new URL("http://" + gridHost + ":4444/wd/hub");

            if (browser.equalsIgnoreCase("chrome")) {
                ChromeOptions options = new ChromeOptions();
                options.setCapability("browserName", "chrome");
                driver = new RemoteWebDriver(gridUrl, options);

            } else if (browser.equalsIgnoreCase("firefox")) {
                FirefoxOptions options = new FirefoxOptions();
                options.setCapability("browserName", "firefox");
                driver = new RemoteWebDriver(gridUrl, options);
            }

        } else {
            throw new RuntimeException(
                "Local execution is disabled in GridTest. Use -Dexecution=grid"
            );
        }

        driver.get("https://www.google.com");
        System.out.println("Title: " + driver.getTitle());
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
