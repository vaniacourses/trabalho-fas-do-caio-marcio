package net.originmobi.pdv.system;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertTrue;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SegurancaTest {
    private WebDriver driver;

    @Before
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "/usr/lib/chromium-browser/chromedriver");
        driver = new ChromeDriver();
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void testAcessoSemLogin() {
        driver.get("http://localhost:8080/pessoa/form");
        new WebDriverWait(driver, 10)
                .until(ExpectedConditions.urlContains("/login"));
        assertTrue(driver.getCurrentUrl().contains("/login"));
    }

    @Test
    public void testAcessoComPerfilIncorreto() {
        driver.get("http://localhost:8080/login");
        WebElement usuario = new WebDriverWait(driver, 10)
                .until(ExpectedConditions.visibilityOfElementLocated(By.name("username")));
        WebElement senha = driver.findElement(By.name("password"));
        WebElement btnLogin = driver.findElement(By.id("btn-login"));

        usuario.clear();
        usuario.sendKeys("usuario_comum");
        senha.clear();
        senha.sendKeys("123");
        btnLogin.click();

        driver.get("http://localhost:8080/pessoa/form");

        new WebDriverWait(driver, 10)
                .until(ExpectedConditions.urlContains("/login"));

        assertTrue(driver.getCurrentUrl().contains("/login"));
    }
}
