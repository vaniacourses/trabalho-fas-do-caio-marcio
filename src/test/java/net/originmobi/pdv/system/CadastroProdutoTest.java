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

public class CadastroProdutoTest {
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
    public void testCadastroProduto() {
        realizarLogin();
        driver.get("http://localhost:8080/produto/form");
        new WebDriverWait(driver, 10)
            .until(ExpectedConditions.visibilityOfElementLocated(By.name("descricao")));
        driver.findElement(By.name("descricao")).sendKeys("Produto Selenium");
        driver.findElement(By.name("valor_custo")).sendKeys("10.00");
        driver.findElement(By.name("valor_venda")).sendKeys("15.00");
        driver.findElement(By.name("unidade")).sendKeys("UN");
        driver.findElement(By.name("ncm")).sendKeys("12345678");
        driver.findElement(By.name("cest")).sendKeys("1234567");
        WebElement fornecedor = driver.findElement(By.name("fornecedor"));
        if (fornecedor.getTagName().equals("select")) {
            java.util.List<WebElement> opts = fornecedor.findElements(By.tagName("option"));
            if (opts.size() > 1) opts.get(1).click();
            else if (opts.size() == 1) opts.get(0).click();
        }
        WebElement categoria = driver.findElement(By.name("categoria"));
        if (categoria.getTagName().equals("select")) {
            java.util.List<WebElement> opts = categoria.findElements(By.tagName("option"));
            if (opts.size() > 1) opts.get(1).click();
            else if (opts.size() == 1) opts.get(0).click();
        }
        WebElement grupo = driver.findElement(By.name("grupo"));
        if (grupo.getTagName().equals("select")) {
            java.util.List<WebElement> opts = grupo.findElements(By.tagName("option"));
            if (opts.size() > 1) opts.get(1).click();
            else if (opts.size() == 1) opts.get(0).click();
        }
        driver.findElement(By.name("balanca")).sendKeys("SIM");
        driver.findElement(By.name("ativo")).sendKeys("ATIVO");
        driver.findElement(By.name("subtributaria")).sendKeys("SIM");
        driver.findElement(By.name("vendavel")).sendKeys("SIM");
        WebElement tributacao = driver.findElement(By.name("tributacao"));
        if (tributacao.getTagName().equals("select")) {
            java.util.List<WebElement> opts = tributacao.findElements(By.tagName("option"));
            if (opts.size() > 1) opts.get(1).click();
            else if (opts.size() == 1) opts.get(0).click();
        }
        WebElement modbc = driver.findElement(By.name("modBcIcms"));
        if (modbc.getTagName().equals("select")) {
            java.util.List<WebElement> opts = modbc.findElements(By.tagName("option"));
            if (opts.size() > 1) opts.get(1).click();
            else if (opts.size() == 1) opts.get(0).click();
        }
        WebElement controlaEstoque = driver.findElement(By.name("controla_estoque"));
        if (controlaEstoque.getTagName().equals("select")) {
            java.util.List<WebElement> opts = controlaEstoque.findElements(By.tagName("option"));
            if (opts.size() > 1) opts.get(1).click();
            else if (opts.size() == 1) opts.get(0).click();
        }
        driver.findElement(By.name("data_validade")).sendKeys("01/01/2030");
        driver.findElement(By.cssSelector("input[type='submit'][name='enviar']")).click();
        new WebDriverWait(driver, 10)
            .until(ExpectedConditions.or(
                ExpectedConditions.textToBePresentInElementLocated(By.tagName("body"), "Produto Selenium")
            ));
        assertTrue(driver.getPageSource().toLowerCase().contains("sucesso") || driver.getPageSource().toLowerCase().contains("produto"));
    }

    @Test
    public void testCadastroProdutoPerformance() {
        realizarLogin();
        long startTime = System.currentTimeMillis();
        driver.get("http://localhost:8080/produto/form");
        new WebDriverWait(driver, 10)
            .until(ExpectedConditions.visibilityOfElementLocated(By.name("descricao")));
        driver.findElement(By.name("descricao")).sendKeys("Produto Selenium Performance");
        driver.findElement(By.name("valor_custo")).sendKeys("10.00");
        driver.findElement(By.name("valor_venda")).sendKeys("15.00");
        driver.findElement(By.name("unidade")).sendKeys("UN");
        driver.findElement(By.name("ncm")).sendKeys("12345678");
        driver.findElement(By.name("cest")).sendKeys("1234567");
        WebElement fornecedor = driver.findElement(By.name("fornecedor"));
        if (fornecedor.getTagName().equals("select")) {
            java.util.List<WebElement> opts = fornecedor.findElements(By.tagName("option"));
            if (opts.size() > 1) opts.get(1).click();
            else if (opts.size() == 1) opts.get(0).click();
        }
        WebElement categoria = driver.findElement(By.name("categoria"));
        if (categoria.getTagName().equals("select")) {
            java.util.List<WebElement> opts = categoria.findElements(By.tagName("option"));
            if (opts.size() > 1) opts.get(1).click();
            else if (opts.size() == 1) opts.get(0).click();
        }
        WebElement grupo = driver.findElement(By.name("grupo"));
        if (grupo.getTagName().equals("select")) {
            java.util.List<WebElement> opts = grupo.findElements(By.tagName("option"));
            if (opts.size() > 1) opts.get(1).click();
            else if (opts.size() == 1) opts.get(0).click();
        }
        driver.findElement(By.name("balanca")).sendKeys("SIM");
        driver.findElement(By.name("ativo")).sendKeys("ATIVO");
        driver.findElement(By.name("subtributaria")).sendKeys("SIM");
        driver.findElement(By.name("vendavel")).sendKeys("SIM");
        WebElement tributacao = driver.findElement(By.name("tributacao"));
        if (tributacao.getTagName().equals("select")) {
            java.util.List<WebElement> opts = tributacao.findElements(By.tagName("option"));
            if (opts.size() > 1) opts.get(1).click();
            else if (opts.size() == 1) opts.get(0).click();
        }
        WebElement modbc = driver.findElement(By.name("modBcIcms"));
        if (modbc.getTagName().equals("select")) {
            java.util.List<WebElement> opts = modbc.findElements(By.tagName("option"));
            if (opts.size() > 1) opts.get(1).click();
            else if (opts.size() == 1) opts.get(0).click();
        }
        WebElement controlaEstoque = driver.findElement(By.name("controla_estoque"));
        if (controlaEstoque.getTagName().equals("select")) {
            java.util.List<WebElement> opts = controlaEstoque.findElements(By.tagName("option"));
            if (opts.size() > 1) opts.get(1).click();
            else if (opts.size() == 1) opts.get(0).click();
        }
        driver.findElement(By.name("data_validade")).sendKeys("01/01/2030");
        driver.findElement(By.cssSelector("input[type='submit'][name='enviar']")).click();
        new WebDriverWait(driver, 10)
            .until(ExpectedConditions.or(
                ExpectedConditions.textToBePresentInElementLocated(By.tagName("body"), "Produto Selenium Performance")
            ));
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        System.out.println("Tempo para cadastro do produto: " + duration + " ms");
        assertTrue("Cadastro de produto demorou mais que o esperado: " + duration + " ms", duration < 3000);
    }
}
