package com.grid.tests;

import org.testng.annotations.Test;

public class GridTest extends BaseTest {

    @Test
    public void openGoogle() {
        driver.get("https://www.google.com");
        System.out.println("Title = " + driver.getTitle());
    }
}
