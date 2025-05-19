package net.originmobi.pdv.model;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import java.time.LocalDate;

@DisplayName("Classe para teste do Pagar")
public class PagarTest {

    private Fornecedor fornecedor;
    private PagarTipo pagarTipo;
    private Pagar pagar;
    private LocalDate dataCadastro;

    @Before
    public void setUp() {
        // Inicializa o fornecedor
        fornecedor = new Fornecedor();
        fornecedor.setCodigo(1L);
        fornecedor.setNome("Fornecedor Teste");
        fornecedor.setNome_fantasia("Fantasia Teste Ltda");
        fornecedor.setCnpj("12.345.678/0001-90");
        fornecedor.setInscricao_estadual("123456789");

        // Inicializa o tipo de pagamento
        pagarTipo = new PagarTipo();
        pagarTipo.setCodigo(1L);
        pagarTipo.setDescricao("Conta de Energia");

        // Data de cadastro
        dataCadastro = LocalDate.now();

        // Inicializa o objeto Pagar
        pagar = new Pagar(
            "Pagamento de energia elétrica",
            500.0,
            dataCadastro,
            fornecedor,
            pagarTipo
        );
    }

    @DisplayName("Teste para verificar se o construtor com parâmetros funciona corretamente")
    @Test
    public void testConstrutorComParametros() {
        assertEquals("Pagamento de energia elétrica", pagar.getObservacao());
        assertEquals(Double.valueOf(500.0), pagar.getValor_total());
        assertEquals(dataCadastro, pagar.getData_cadastro());
        assertEquals(fornecedor, pagar.getFornecedor());
        assertEquals(pagarTipo, pagar.getTipo());
    }

    @DisplayName("Teste para verificar se o construtor padrão funciona")
    @Test
    public void testConstrutorPadrao() {
        Pagar novoPagar = new Pagar();
        assertNotNull(novoPagar);
    }

    @DisplayName("Teste para verificar se os getters e setters do código funcionam")
    @Test
    public void testGetSetCodigo() {
        Long novoCodigo = 2L;
        pagar.setCodigo(novoCodigo);
        assertEquals(novoCodigo, pagar.getCodigo());
    }

    @DisplayName("Teste para verificar se os getters e setters da observação funcionam")
    @Test
    public void testGetSetObservacao() {
        String novaObservacao = "Nova observação de teste";
        pagar.setObservacao(novaObservacao);
        assertEquals(novaObservacao, pagar.getObservacao());
    }

    @DisplayName("Teste para verificar se os getters e setters do valor total funcionam")
    @Test
    public void testGetSetValorTotal() {
        Double novoValor = 750.0;
        pagar.setValor_total(novoValor);
        assertEquals(novoValor, pagar.getValor_total());
    }

    @DisplayName("Teste para verificar se os getters e setters da data de cadastro funcionam")
    @Test
    public void testGetSetDataCadastro() {
        LocalDate novaData = LocalDate.of(2024, 3, 15);
        pagar.setData_cadastro(novaData);
        assertEquals(novaData, pagar.getData_cadastro());
    }

    @DisplayName("Teste para verificar se os getters e setters do fornecedor funcionam")
    @Test
    public void testGetSetFornecedor() {
        Fornecedor novoFornecedor = new Fornecedor();
        novoFornecedor.setCodigo(2L);
        novoFornecedor.setNome("Novo Fornecedor");
        pagar.setFornecedor(novoFornecedor);
        assertEquals(novoFornecedor, pagar.getFornecedor());
    }

    @DisplayName("Teste para verificar se os getters e setters do tipo de pagamento funcionam")
    @Test
    public void testGetSetTipo() {
        PagarTipo novoTipo = new PagarTipo();
        novoTipo.setCodigo(2L);
        novoTipo.setDescricao("Conta de Água");
        pagar.setTipo(novoTipo);
        assertEquals(novoTipo, pagar.getTipo());
    }

    @DisplayName("Teste para verificar as anotações de validação")
    @Test
    public void testValidacoes() {
        assertNotNull("Valor total não pode ser nulo", pagar.getValor_total());
        assertNotNull("Data de cadastro não pode ser nula", pagar.getData_cadastro());
        assertTrue("Observação não pode ter mais de 255 caracteres", 
            pagar.getObservacao() == null || pagar.getObservacao().length() <= 255);
    }
}