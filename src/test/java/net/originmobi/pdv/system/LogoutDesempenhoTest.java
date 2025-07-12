package net.originmobi.pdv.system;

import org.junit.After;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LogoutDesempenhoTest {

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
            .until(ExpectedConditions.not(ExpectedConditions.urlContains("/login")));
    }

    @Test
    public void testLogoutDesempenho() {
        realizarLogin();

        long startTime = System.currentTimeMillis();

        WebElement btnSair = new WebDriverWait(driver, 10)
            .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[contains(., 'Sair')]")));
        btnSair.click();

        new WebDriverWait(driver, 10)
            .until(ExpectedConditions.or(
                ExpectedConditions.urlContains("/login"),
                ExpectedConditions.textToBePresentInElementLocated(By.tagName("body"), "login")
            ));

        long endTime = System.currentTimeMillis();
        long tempoLogout = endTime - startTime;

        System.out.println("Tempo de logout: " + tempoLogout + " ms");

        assertTrue("Logout demorou mais que o esperado", tempoLogout < 2000);
    }
}
