package net.originmobi.pdv.system;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import static org.junit.Assert.*;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class LoginUsuarioTest {
    private WebDriver driver;

    @Before
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
        driver = new ChromeDriver();
        driver.get("http://localhost:8080");
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    private void realizarLogin() {
        driver.get("http://localhost:8080/login");
        WebElement usuario = new WebDriverWait(driver, 10)
            .until(ExpectedConditions.visibilityOfElementLocated(By.name("username")));
        WebElement senha = driver.findElement(By.name("password"));
        WebElement btnLogin = driver.findElement(By.id("btn-login"));
        usuario.clear();
        usuario.sendKeys("gerente");
        senha.clear();
        senha.sendKeys("123");
        btnLogin.click();
        new WebDriverWait(driver, 10)
            .until(ExpectedConditions.not(
                ExpectedConditions.urlContains("/login")
            ));
    }

    @Test
    public void testLoginUsuario() {
        realizarLogin();
        assertTrue(driver.getPageSource().contains("Usuário: gerente"));
    }
}
