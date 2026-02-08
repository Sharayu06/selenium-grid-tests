package com.grid.tests;

import java.net.URL;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;

public class BaseTest {

    protected WebDriver driver;

    protected String execution;
    protected String browser;
    protected String env;
    protected String baseUrl;
    protected String gridHost;

    @Parameters({"browser"})
    @BeforeMethod
    public void setUp(String browser) throws Exception {

        // ---------- READ VALUES (System props → Jenkins → defaults) ----------
        this.execution = System.getProperty("execution", "local");
        this.env = System.getProperty("env", "qa");
        this.gridHost = System.getProperty("grid.host", "localhost");
        this.browser = browser;

        // ---------- ENV → BASE URL ----------
        if (env.equalsIgnoreCase("qa")) {
            baseUrl = "https://www.google.com";
        } else if (env.equalsIgnoreCase("stage")) {
            baseUrl = "https://www.google.com";
        } else {
            throw new RuntimeException("Invalid env value: " + env);
        }

        System.out.println("=================================");
        System.out.println("Execution mode = " + execution);
        System.out.println("Browser        = " + browser);
        System.out.println("Environment    = " + env);
        System.out.println("Grid Host      = " + gridHost);
        System.out.println("=================================");

        // ---------- DRIVER CREATION ----------
        if (execution.equalsIgnoreCase("grid")) {

            URL gridUrl = new URL("http://" + gridHost + ":4444/wd/hub");

            if (browser.equalsIgnoreCase("chrome")) {
                ChromeOptions options = new ChromeOptions();
                driver = new RemoteWebDriver(gridUrl, options);

            } else if (browser.equalsIgnoreCase("firefox")) {
                FirefoxOptions options = new FirefoxOptions();
                driver = new RemoteWebDriver(gridUrl, options);

            } else {
                throw new RuntimeException("Unsupported browser for grid: " + browser);
            }

        } else if (execution.equalsIgnoreCase("local")) {

            if (browser.equalsIgnoreCase("chrome")) {
                driver = new ChromeDriver();

            } else if (browser.equalsIgnoreCase("firefox")) {
                driver = new FirefoxDriver();

            } else {
                throw new RuntimeException("Unsupported browser for local: " + browser);
            }

        } else {
            throw new RuntimeException("Invalid execution mode: " + execution);
        }

        driver.manage().window().maximize();
        driver.get(baseUrl);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
