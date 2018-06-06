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


public class MenuTest {

    private WebDriver driver;


    boolean isElementPresent(By locator) {
        try {
            driver.findElement(locator);
            return true;
        } catch (NoSuchElementException ex) {
            return false;
        }
    }


    public List<String> getMenuLinks(String menuSelector, String linkTag) {

        List<String> result = new ArrayList<>();

        if (isElementPresent(By.cssSelector(menuSelector))) {
            WebElement menuList = driver.findElement(By.cssSelector(menuSelector));
            List<WebElement> menuLinks = menuList.findElements(By.tagName(linkTag));

            for (WebElement link : menuLinks) {
                result.add(link.getAttribute("href"));
            }
        }
        return result;
    }


    @Before
    public void start() {
        driver = new ChromeDriver();
    }


    @Test
    public void myFirstTest() {
        driver.get("http://litecart.loc/admin/");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();

        List<String> itemsList = getMenuLinks("#box-apps-menu", "a");

        for (String item : itemsList) {
            driver.get(item);
            driver.findElement(By.tagName("h1"));

            List<String> childItems = getMenuLinks("#box-apps-menu .docs", "a");

            for (String subItem : childItems) {
                driver.get(subItem);
                driver.findElement(By.tagName("h1"));
            }
        }
    }


    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}