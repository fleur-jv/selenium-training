package ru.testexample;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertFalse;

public class StickerTest {

    private WebDriver driver;


    boolean isElementPresent(By locator) {
        try {
            driver.findElement(locator);
            return true;
        } catch (NoSuchElementException ex) {
            return false;
        }
    }


    @Before
    public void start() {
        driver = new ChromeDriver();
    }


    @Test
    public void myStickerTest() {
        driver.get("http://litecart.loc/");

        List<String> blockList = new ArrayList<>();
        blockList.add("#box-most-popular");
        blockList.add("#box-campaigns");
        blockList.add("#box-latest-products");

        for (String item : blockList) {

            if (isElementPresent(By.cssSelector(item))) {

                WebElement blockItem = driver.findElement(By.cssSelector(item));
                List<WebElement> productList = blockItem.findElements(By.cssSelector(".product"));

                for (WebElement product : productList) {

                    List<WebElement> stickers = product.findElements(By.cssSelector(".sticker"));
                    assertFalse(stickers.size() != 1);
                }
            }
        }
    }


    @After
    public void stop() {
        driver.quit();
        driver = null;
    }

}
