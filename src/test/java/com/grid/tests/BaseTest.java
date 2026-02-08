package com.grid.tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;

import java.net.URL;

public class BaseTest {

    protected WebDriver driver;

    @Parameters("browser")
    @BeforeMethod
    public void setUp(String browser) throws Exception {

        String execution = System.getProperty("execution", "local");
        String gridHost = System.getProperty("grid.host", "localhost");

        System.out.println("Execution mode = " + execution);
        System.out.println("Browser = " + browser);

        if (execution.equalsIgnoreCase("grid")) {

            URL gridUrl = new URL("http://" + gridHost + ":4444/wd/hub");

            if (browser.equalsIgnoreCase("chrome")) {
                ChromeOptions options = new ChromeOptions();
                driver = new RemoteWebDriver(gridUrl, options);
            } 
            else if (browser.equalsIgnoreCase("firefox")) {
                FirefoxOptions options = new FirefoxOptions();
                driver = new RemoteWebDriver(gridUrl, options);
            }

        } else {
            throw new RuntimeException("Local execution disabled for Grid project");
        }
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
