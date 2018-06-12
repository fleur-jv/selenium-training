package ru.testexample;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertFalse;

public class SortingTest {

    private WebDriver driver;

    public List<WebElement> getElements(String link, String mainSelector, String subSelector) {

        driver.get(link);
        WebElement mainElement = driver.findElement(By.cssSelector(mainSelector));
        List<WebElement> elements = mainElement.findElements(By.cssSelector(subSelector));
        return elements;
    }

    @Before
    public void start() {
        driver = new ChromeDriver();
    }

    @Test
    public void mySortingTest() {
        driver.get("http://litecart.loc/admin/");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();

        List<WebElement> countryRows = getElements("http://litecart.loc/admin/?app=countries&doc=countries",
                ".dataTable",
                ".row");

        List<Country> countryList = new ArrayList<>();

        for (WebElement item: countryRows) {
            String name = item.findElement(By.cssSelector("td:nth-child(5) a")).getAttribute("textContent");
            String link = item.findElement(By.cssSelector("td:nth-child(5) a")).getAttribute("href");
            String zoneCount = item.findElement(By.cssSelector("td:nth-child(6)")).getText();

            Country country = new Country(name, link, Integer.parseInt(zoneCount));
            countryList.add(country);
        }

        String prevName = null;

        for (Country item : countryList) {

            String currentName = item.getName();

            if (prevName != null) {
                assertFalse(currentName.compareTo(prevName) < 0);
            }

            prevName = currentName;
        }

        for (Country item : countryList) {

            if (item.getZoneCount() > 0) {
                checkZoneSorting(item);
            }
        }

        List<WebElement> zonedCountryRows = getElements("http://litecart.loc/admin/?app=geo_zones&doc=geo_zones",
                ".dataTable", ".row td:nth-child(3) a");

        List<String> linksList = new ArrayList<>();

        for (WebElement item: zonedCountryRows) {
            linksList.add(item.getAttribute("href"));
        }

        for (String zone : linksList) {
            List<WebElement> zonesList = getElements(zone, "#table-zones",
                    "select[name*=zone_code] option");

            List<String> zoneList = new ArrayList<>();

            for (WebElement item : zonesList) {

                if (item.getAttribute("selected") != null) {
                    zoneList.add(item.getAttribute("textContent"));
                }
            }

            String prevZoneName = null;

            for (String item : zoneList) {

                String currentZoneName = item;

                if (prevZoneName != null) {
                    assertFalse(currentZoneName.compareTo(prevZoneName) < 0);
                }

                prevZoneName = currentZoneName;
            }
        }

    }


    public void checkZoneSorting(Country country) {

        List<WebElement> zonesList = getElements(country.getLink(), "#table-zones",
                "td:nth-child(3) input");

        String prevZoneName = null;

        for (WebElement item : zonesList) {

            String currentZoneName = item.getText();

            if (prevZoneName != null) {
                assertFalse(currentZoneName.compareTo(prevZoneName) < 0);
            }

            prevZoneName = currentZoneName;
        }
    }


    @After
    public void stop() {
        driver.quit();
        driver = null;
    }

}
