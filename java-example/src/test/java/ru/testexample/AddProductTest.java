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

import java.nio.file.Paths;

import static java.lang.Thread.sleep;

import static org.junit.Assert.assertFalse;

public class AddProductTest {

    private WebDriver driver;


    @Before
    public void start() {
        driver = new ChromeDriver();
    }


    public void waiting(int s) {
        try {
            sleep(s);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public void findAndClick(String mainSelector, String subSelector) {
        WebElement main = driver.findElement(By.cssSelector(mainSelector));
        WebElement sub = main.findElement(By.cssSelector(subSelector));
        sub.click();
    }


    @Test
    public void myAddProductTest() {
        driver.get("http://litecart.loc/admin/");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();

        findAndClick("#box-apps-menu", "li:nth-child(2) a");
        findAndClick("#content", "div a:nth-child(2)");

        String name = "Pink Duck";

        WebElement tabGeneral = driver.findElement(By.id("tab-general"));
        WebElement productStatus = tabGeneral.findElement(By.name("status"));
        productStatus.click();
        WebElement productName = tabGeneral.findElement(By.name("name[en]"));
        productName.sendKeys(name);
        WebElement productCode = tabGeneral.findElement(By.name("code"));
        productCode.sendKeys("101001000");
        WebElement productGender = tabGeneral.findElement(By.cssSelector("input[name*=product_groups]"));
        productGender.click();
        WebElement productQuantity = tabGeneral.findElement(By.name("quantity"));
        productQuantity.clear();
        productQuantity.sendKeys("7,00");
        WebElement productImage = tabGeneral.findElement(By.cssSelector("[type=file]"));
        productImage.sendKeys(Paths.get("assets/new_duck.jpg").toAbsolutePath().toString());
        WebElement productDateFrom = tabGeneral.findElement(By.name("date_valid_from"));
        productDateFrom.sendKeys("01092010");
        WebElement productDateTo = tabGeneral.findElement(By.name("date_valid_to"));
        productDateTo.sendKeys("01092020");

        findAndClick(".index", "li:nth-child(2)");
        waiting(1000);

        WebElement tabInfo = driver.findElement(By.id("tab-information"));
        Select productManufacturer = new Select(tabInfo.findElement(By.cssSelector("select[name=manufacturer_id]")));
        productManufacturer.selectByValue("1");
        WebElement productKeywords = tabInfo.findElement(By.name("keywords"));
        productKeywords.sendKeys("pink duck");
        WebElement productShortDesc = tabInfo.findElement(By.name("short_description[en]"));
        productShortDesc.sendKeys("Pretty little duck");
        WebElement productDesc = tabInfo.findElement(By.cssSelector(".trumbowyg-editor"));
        productDesc.sendKeys("This is pretty little duck.");
        WebElement productTitle = tabInfo.findElement(By.name("head_title[en]"));
        productTitle.sendKeys("Pink Duck");
        WebElement productMetaDesc = tabInfo.findElement(By.name("meta_description[en]"));
        productMetaDesc.sendKeys("duck, pink duck");

        findAndClick(".index", "li:nth-child(4)");
        waiting(1000);

        WebElement tabPrices = driver.findElement(By.id("tab-prices"));
        WebElement productPurPrice = tabPrices.findElement(By.name("purchase_price"));
        productPurPrice.clear();
        productPurPrice.sendKeys("100,00");
        Select productCur = new Select(tabPrices.findElement(By.cssSelector("select[name=purchase_price_currency_code]")));
        productCur.selectByValue("EUR");
        WebElement productUSD = tabPrices.findElement(By.name("prices[USD]"));
        productUSD.sendKeys("116");
        WebElement productEUR = tabPrices.findElement(By.name("prices[EUR]"));
        productEUR.sendKeys("100");

        findAndClick(".button-set","button:first-child");

        try {
            driver.findElement(By.cssSelector(".dataTable")).findElement(By.linkText(name));
            System.out.println("Product successfully added!");
        } catch (NoSuchElementException ex) {
            assertFalse("Oops! Something went wrong...", true);
        }
    }


    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}
