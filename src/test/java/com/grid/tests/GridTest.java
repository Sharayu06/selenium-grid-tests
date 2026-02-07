package com.grid.tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
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
    	
    	System.out.println("===== SYSTEM PROPERTIES DUMP =====");
    	System.getProperties().forEach((k, v) ->
    	        System.out.println(k + " = " + v)
    	);
    	System.out.println("=================================");


        String executionMode = System.getProperty("execution");

        // 🔍 DEBUG (VERY IMPORTANT)
        System.out.println("Execution mode = " + executionMode);

        if ("grid".equalsIgnoreCase(executionMode)) {

            String gridHost = System.getProperty("grid.host", "localhost");
            System.out.println("Grid host = " + gridHost);

            URL gridUrl = new URL("http://" + gridHost + ":4444/wd/hub");

            if (browser.equalsIgnoreCase("chrome")) {
                driver = new RemoteWebDriver(gridUrl, new ChromeOptions());
            } else if (browser.equalsIgnoreCase("firefox")) {
                driver = new RemoteWebDriver(gridUrl, new FirefoxOptions());
            }

        } else {
            // ✅ LOCAL execution
            if (browser.equalsIgnoreCase("chrome")) {
                driver = new ChromeDriver();
            } else if (browser.equalsIgnoreCase("firefox")) {
                driver = new FirefoxDriver();
            }
        }

        driver.get("https://www.google.com");
        System.out.println("Title: " + driver.getTitle());
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
