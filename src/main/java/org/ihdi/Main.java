package org.ihdi;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Main {
    public static void main(String[] args) throws  InterruptedException {
        final String url = "https://www.tokopedia.com/p/handphone-tablet/handphone/";
        final int totalAllAds = 100;
        int numOfAds = 0;

        System.setProperty("webdriver.chrome.driver", "/home/recharge/IdeaProjects/TokpedScrap/chromedriver_linux64/chromedriver");

        Map<String, String> mobileEmulation = new HashMap<>();
        mobileEmulation.put("deviceName", "Galaxy S5");

        ChromeOptions options = new ChromeOptions();
        options.addArguments("incognito");
        options.setExperimentalOption("useAutomationExtension", false);
        options.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
        options.setExperimentalOption("mobileEmulation", mobileEmulation);


        WebDriver driver = new ChromeDriver(options);

        driver.manage().window().setSize(new Dimension(400, 600));

        driver.get(url);
        Thread.sleep(1000);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0, 500);");
        Thread.sleep(1000);

        //check overlay
        List<WebElement> overlayElmnt = driver.findElements(By.className("unf-overlay"));
        if (!overlayElmnt.isEmpty()) {
            driver.findElement(By.xpath("//article/div/div/div/h1/following-sibling::*[2]/button")).click();
        }
        Thread.sleep(1000);
        
        driver.close();
    }
}
