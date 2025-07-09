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

public class CadastroPessoaTest {
    private WebDriver driver;

    @Before
    public void setUp() {
        // System.setProperty("webdriver.chrome.driver", "/usr/lib/chromium-browser/chromedriver"); //linux
        System.setProperty("webdriver.chrome.driver", "chromedriver.exe"); //windows
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
                        ExpectedConditions.urlContains("/login")));
    }

    @Test
    public void testCadastroPessoa() {
        realizarLogin();

        driver.get("http://localhost:8080/pessoa/form");

        WebDriverWait wait = new WebDriverWait(driver, 10);

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("nome")));
        driver.findElement(By.name("nome")).sendKeys("João Silva");
        driver.findElement(By.name("apelido")).sendKeys("Joãozinho");
        driver.findElement(By.name("cpfcnpj")).sendKeys("12345678901");
        driver.findElement(By.name("data_nascimento")).sendKeys("01/01/1990");
        driver.findElement(By.name("observacao")).sendKeys("Cadastro feito via Selenium");

        driver.findElement(By.cssSelector("a[href='#menu1']")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("rua")));

        driver.findElement(By.name("cidade")).sendKeys("789");
        driver.findElement(By.name("rua")).sendKeys("Rua das Flores");
        driver.findElement(By.name("bairro")).sendKeys("Jardim");
        driver.findElement(By.name("numero")).sendKeys("1234");
        driver.findElement(By.name("cep")).sendKeys("12345000");
        driver.findElement(By.name("referencia")).sendKeys("Perto do parque");

        driver.findElement(By.cssSelector("a[href='#menu2']")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("fone")));

        driver.findElement(By.name("fone")).sendKeys("999999999");
        driver.findElement(By.name("tipo")).sendKeys("Cliente");

        driver.findElement(By.cssSelector("input[type='submit'][value='Salvar']")).click();

        wait.until(ExpectedConditions.or(
                ExpectedConditions.textToBePresentInElementLocated(By.tagName("body"),
                        "Pessoa cadastrada com sucesso"),
                ExpectedConditions.urlContains("/pessoa")));

        assertTrue(
                driver.getPageSource().toLowerCase().contains("sucesso") ||
                        driver.getCurrentUrl().contains("/pessoa"));
    }

}
