package com.grid.tests;

import java.net.URL;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class BaseTest {

    protected WebDriver driver;

    @BeforeMethod
    public void setUp() throws Exception {

        // Read execution mode from Maven / Jenkins
        // Default = local (safe fallback)
        String executionMode = System.getProperty("execution", "local");
        System.out.println("Execution mode = " + executionMode);

        if (executionMode.equalsIgnoreCase("grid")) {

            // Grid host (Jenkins / Docker / Local)
            String gridHost = System.getProperty("grid.host", "localhost");
            String gridUrl = "http://" + gridHost + ":4444/wd/hub";

            System.out.println("Connecting to Selenium Grid at: " + gridUrl);

            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setBrowserName("chrome");

            driver = new RemoteWebDriver(new URL(gridUrl), capabilities);

        } else {

            System.out.println("Starting local Chrome browser");
            driver = new ChromeDriver();
        }

        driver.manage().window().maximize();
        driver.get("https://www.google.com");   // simple, reliable test URL
    }

    @AfterMethod
    public void tearDown() {

        if (driver != null) {
            driver.quit();
        }
    }
}
