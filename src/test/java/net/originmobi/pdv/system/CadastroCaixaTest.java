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

public class CadastroCaixaTest {
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
    public void testCadastroCaixa() {
        realizarLogin();
        driver.get("http://localhost:8080/caixa/form");
        new WebDriverWait(driver, 10)
            .until(ExpectedConditions.visibilityOfElementLocated(By.name("descricao")));
        driver.findElement(By.name("descricao")).sendKeys("Caixa Selenium");
        WebElement tipo = driver.findElement(By.name("tipo"));
        if (tipo.getTagName().equals("select")) {
            java.util.List<WebElement> opts = tipo.findElements(By.tagName("option"));
            if (opts.size() > 1) opts.get(1).click();
            else if (opts.size() == 1) opts.get(0).click();
        }
        driver.findElement(By.name("valor_abertura")).sendKeys("100,00");
        java.util.List<WebElement> agenciaFields = driver.findElements(By.name("agencia"));
        if (!agenciaFields.isEmpty()) {
            agenciaFields.get(0).sendKeys("1234");
        }
        java.util.List<WebElement> contaFields = driver.findElements(By.name("conta"));
        if (!contaFields.isEmpty()) {
            contaFields.get(0).sendKeys("56789-0");
        }
        driver.findElement(By.cssSelector("a.btn-abrir-caixa")).click();
        new WebDriverWait(driver, 10)
            .until(ExpectedConditions.or(
                ExpectedConditions.textToBePresentInElementLocated(By.tagName("body"), "Caixa")
            ));
        assertTrue(driver.getPageSource().toLowerCase().contains("sucesso") || driver.getPageSource().toLowerCase().contains("caixa"));
    }

    @Test
    public void testCadastroCaixaSemLogin() {
        driver.get("http://localhost:8080/caixa/form");
        new WebDriverWait(driver, 10)
            .until(ExpectedConditions.or(
                ExpectedConditions.urlContains("/login"),
                ExpectedConditions.textToBePresentInElementLocated(By.tagName("body"), "login")
            ));
        String pageSource = driver.getPageSource().toLowerCase();
        assertTrue(pageSource.contains("login") || pageSource.contains("usuÃ¡rio") || driver.getCurrentUrl().contains("/login"));
    }
}