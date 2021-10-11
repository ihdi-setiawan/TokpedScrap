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

        List<WebElement> elements;
        boolean notCompletedYet = true;
        HashMap<String, List<String>> linkMaps = new HashMap<String, List<String>>();

        while(notCompletedYet) {
            js.executeScript("window.scrollBy(0, 350);");
            elements = driver.findElements(By.xpath("//div[@data-cy='categoryProduct']/div/div/div"));
            for (WebElement element : elements) {
                List<WebElement> ratings = element.findElements(By.xpath(".//a/div[@data-productinfo='true']/div[@data-integrity='true']/i[@aria-label='Rating Star']/following-sibling::*[1]"));
                String rating = "-";
                if (!ratings.isEmpty()) {
                    rating = ratings.get(0).getText();
                }
                if (!ratings.isEmpty() && rating == "5.0") {
                    js.executeScript("arguments[0].remove()", element);
                    continue;
                } else {
                    List<String> productAttr = new ArrayList<>();
                    productAttr.add(rating);
                    productAttr.add(element.findElement(By.xpath(".//a/div/img")).getAttribute("src"));
                    List<WebElement> stores = element.findElements(By.xpath(".//span[@data-testid='linkShopName']"));
                    productAttr.add(stores.get(0).getText());
                    linkMaps.put(element.findElement(By.xpath(".//a")).getAttribute("href"), productAttr);
                    numOfAds++;
                    if (numOfAds == totalAllAds) {
                        notCompletedYet = false;
                        break;
                    }
                    js.executeScript("arguments[0].remove()", element);
                }
            }

            if (!notCompletedYet) break;

            Thread.sleep(1000);
            js.executeScript("window.scrollTo(document.body.scrollHeight, 0)");
            Thread.sleep(1000);
        }

        driver.close();
    }
}
