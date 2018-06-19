package ru.testexample;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

import static org.openqa.selenium.support.ui.ExpectedConditions.textToBePresentInElement;
import static org.openqa.selenium.support.ui.ExpectedConditions.stalenessOf;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOf;

public class CartTest {

    private WebDriver driver;
    private WebDriverWait wait;

    @Before
    public void start() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 10);
    }


    boolean isElementPresent(By locator) {
        try {
            driver.findElement(locator);
            return true;
        } catch (NoSuchElementException ex) {
            return false;
        }
    }


    public void addToCart() {
        driver.get("http://litecart.loc/");
        WebElement productList = driver.findElement(By.id("box-most-popular"));
        WebElement product = productList.findElement(By.cssSelector("a.link"));
        product.click();

        WebElement buyNow = driver.findElement(By.name("buy_now_form"));
        if (isElementPresent(By.name("options[Size]"))) {
            Select size = new Select(buyNow.findElement(By.name("options[Size]")));
            size.selectByValue("Small");
        }

        WebElement cartCounter = driver.findElement(By.id("cart"));
        WebElement counter = cartCounter.findElement(By.cssSelector("span.quantity"));
        int count = Integer.parseInt(counter.getText());

        WebElement addToCart = buyNow.findElement(By.name("add_cart_product"));
        addToCart.click();

        wait.until(textToBePresentInElement(counter, String.valueOf(count + 1)));
    }


    @Test
    public void myCartTest() {

        for (int i=1; i<4; i=i+1) {
            addToCart();
        }

        WebElement checkout = driver.findElement(By.id("cart"));
        WebElement ckeckoutLink = checkout.findElement(By.cssSelector("a.link"));
        ckeckoutLink.click();

        List<WebElement> orderItems;

        do {
            WebElement table = driver.findElement(By.id("order_confirmation-wrapper"));
            List<WebElement> rows = table.findElements(By.cssSelector("tr:not(.header):not(.footer)"));
            WebElement orderSummary = driver.findElement(By.id("box-checkout-cart"));
            orderItems = orderSummary.findElements(By.name("remove_cart_item"));
            wait.until(visibilityOf(orderItems.get(0)));
            orderItems.get(0).click();
            wait.until(stalenessOf(rows.get(0)));
        } while (orderItems.size() > 1);
    }


    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}
