package ru.testexample;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

import java.util.Random;

public class RegistrationTest {

    private WebDriver driver;


    @Before
    public void start() {
        driver = new ChromeDriver();
    }


    public String genEmail() {
        Random myRandom = new Random();
        String str = "";
        for(int i = 0; i < 10; i++){
            str = str + String.valueOf(myRandom.nextInt(10));
        }
        return str;
    }


    public void logOut() {
        WebElement blockAccount = driver.findElement(By.id("box-account"));
        WebElement logoutLink = blockAccount.findElement(By.cssSelector("li:nth-child(4) a"));
        logoutLink.click();
    }


    @Test
    public void myRegistrationTest() {

        driver.get("http://litecart.loc/");
        WebElement loginFormCreate = driver.findElement(By.name("login_form"));
        WebElement newLink = loginFormCreate.findElement(By.tagName("a"));
        newLink.click();

        WebElement customerForm = driver.findElement(By.name("customer_form"));
        WebElement firstName = customerForm.findElement(By.name("firstname"));
        firstName.sendKeys("Имя");
        WebElement lastName = customerForm.findElement(By.name("lastname"));
        lastName.sendKeys("Фамилия");
        WebElement address = customerForm.findElement(By.name("address1"));
        address.sendKeys("First st., 1");
        WebElement postcode = customerForm.findElement(By.name("postcode"));
        postcode.sendKeys("90210");
        WebElement city = customerForm.findElement(By.name("city"));
        city.sendKeys("Beverly Hills");
        Select country = new Select(customerForm.findElement(By.name("country_code")));
        country.selectByValue("US");
        Select zone = new Select(customerForm.findElement(By.cssSelector("select[name=zone_code]")));
        zone.selectByValue("CA");
        String tmpEmail = genEmail();
        WebElement email = customerForm.findElement(By.name("email"));
        email.sendKeys(tmpEmail + "@mail.ru");
        WebElement phone = customerForm.findElement(By.name("phone"));
        phone.sendKeys("+71111111111");
        WebElement desPass = customerForm.findElement(By.name("password"));
        desPass.sendKeys("qwerty");
        WebElement conPass = customerForm.findElement(By.name("confirmed_password"));
        conPass.sendKeys("qwerty");
        WebElement createAccount = customerForm.findElement(By.name("create_account"));
        createAccount.click();

        logOut();

        WebElement loginForm = driver.findElement(By.name("login_form"));
        WebElement eMail = loginForm.findElement(By.name("email"));
        eMail.sendKeys(tmpEmail + "@mail.ru");
        WebElement pass = loginForm.findElement(By.name("password"));
        pass.sendKeys("qwerty");
        WebElement logIn = loginForm.findElement(By.name("login"));
        logIn.click();

        logOut();
    }


    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}
