package ru.testexample;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.HashMap;

import static org.junit.Assert.assertFalse;

public class ProductCompareTest {

    private WebDriver driver;


    @Before
    public void start() {
        driver = new ChromeDriver();
    }


    public void checkRegColors(String colorString) {
        String[] colors = colorString.substring(5, colorString.length()-1).split(", ");
        assertFalse(colors[0].compareTo(colors[1]) !=0 |
                colors[1].compareTo(colors[2]) !=0 |
                colors[0].compareTo(colors[2]) !=0);
    }


    public void checkSpecColors(String colorString) {
        String[] colors = colorString.substring(5, colorString.length()-1).split(", ");
        assertFalse(Integer.parseInt(colors[1]) != 0 | Integer.parseInt(colors[2]) !=0);
    }


    public void checkSize(String sizeStr1, String sizeStr2) {
        double size1 = Double.parseDouble(sizeStr1.substring(0,sizeStr1.length()-2));
        double size2 = Double.parseDouble(sizeStr2.substring(0, sizeStr2.length()-2));
        assertFalse(size2 < size1);
    }


    public HashMap<String, String> getPropertiesMP() {
        driver.get("http://litecart.loc/");
        WebElement productBlockMP = driver.findElement(By.cssSelector("#box-campaigns"));

        HashMap<String, String> propertiesMap = new HashMap<>();
        propertiesMap.put("name", productBlockMP.findElement(By.cssSelector(".product div.name")).
                getAttribute("textContent"));
        propertiesMap.put("regPrice", productBlockMP.findElement(By.cssSelector(".product s.regular-price")).
                getAttribute("textContent"));
        propertiesMap.put("specPrice", productBlockMP.findElement(By.cssSelector(".product strong.campaign-price")).
                getAttribute("textContent"));
        propertiesMap.put("regTag", productBlockMP.findElement(By.cssSelector(".product s.regular-price")).
                getTagName());
        propertiesMap.put("specTag", productBlockMP.findElement(By.cssSelector(".product strong.campaign-price")).
                getTagName());
        propertiesMap.put("regColor", productBlockMP.findElement(By.cssSelector(".product s.regular-price")).
                getCssValue("color"));
        propertiesMap.put("specColor", productBlockMP.findElement(By.cssSelector(".product strong.campaign-price")).
                getCssValue("color"));
        propertiesMap.put("regSize", productBlockMP.findElement(By.cssSelector(".product s.regular-price")).
                getCssValue("font-size"));
        propertiesMap.put("specSize", productBlockMP.findElement(By.cssSelector(".product strong.campaign-price")).
                getCssValue("font-size"));
        propertiesMap.put("link", productBlockMP.findElement(By.cssSelector(".product a")).getAttribute("href"));
        return propertiesMap;
    }


    public HashMap<String, String> getProperties(String link) {
        driver.get(link);
        WebElement productBlock = driver.findElement(By.cssSelector("#box-product"));

        HashMap<String, String> propertiesMap = new HashMap<>();
        propertiesMap.put("name", productBlock.findElement(By.cssSelector("h1")).getAttribute("textContent"));
        propertiesMap.put("regPrice", productBlock.findElement(By.cssSelector(".price-wrapper s.regular-price")).
                getAttribute("textContent"));
        propertiesMap.put("specPrice", productBlock.findElement(By.cssSelector(".price-wrapper strong.campaign-price")).
                getAttribute("textContent"));
        propertiesMap.put("regTag", productBlock.findElement(By.cssSelector(".price-wrapper s.regular-price")).
                getTagName());
        propertiesMap.put("specTag", productBlock.findElement(By.cssSelector(".price-wrapper strong.campaign-price")).
                getTagName());
        propertiesMap.put("regColor", productBlock.findElement(By.cssSelector(".price-wrapper s.regular-price")).
                getCssValue("color"));
        propertiesMap.put("specColor", productBlock.findElement(By.cssSelector(".price-wrapper strong.campaign-price")).
                getCssValue("color"));
        propertiesMap.put("regSize", productBlock.findElement(By.cssSelector(".price-wrapper s.regular-price")).
                getCssValue("font-size"));
        propertiesMap.put("specSize", productBlock.findElement(By.cssSelector(".price-wrapper strong.campaign-price")).
                getCssValue("font-size"));
        return propertiesMap;
    }


    @Test
    public void myProductCompareTest() {

        HashMap<String, String> valuesMP = getPropertiesMP();
        HashMap<String, String> values = getProperties(valuesMP.get("link"));

        //compare names
        assertFalse(values.get("name").compareTo(valuesMP.get("name")) != 0);
        //compare regular prices
        assertFalse(values.get("regPrice").compareTo(valuesMP.get("regPrice")) != 0);
        //compare special prices
        assertFalse(values.get("specPrice").compareTo(valuesMP.get("specPrice")) != 0);
        //check <s>
        assertFalse(values.get("regTag").compareTo("s") != 0 |
                valuesMP.get("regTag").compareTo("s") != 0);
        //check <strong>
        assertFalse(values.get("specTag").compareTo("strong") != 0 |
                valuesMP.get("specTag").compareTo("strong") != 0);
        //compare font-sizes
        checkSize(values.get("regSize"), values.get("specSize"));
        checkSize(valuesMP.get("regSize"), valuesMP.get("specSize"));
        //compare regular colors
        checkRegColors(valuesMP.get("regColor"));
        checkRegColors(values.get("regColor"));
        //compare special colors
        checkSpecColors(valuesMP.get("specColor"));
        checkSpecColors(values.get("specColor"));
    }


    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}
