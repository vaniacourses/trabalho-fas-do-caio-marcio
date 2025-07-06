package net.originmobi.pdv.model;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Classe para teste do Pagar")
public class PagarTest {

    private Fornecedor fornecedor;
    private PagarTipo pagarTipo;
    private Pagar pagar;
    private LocalDate dataCadastro;

    @BeforeEach
    void setUp() {
        fornecedor = org.mockito.Mockito.mock(Fornecedor.class);
        pagarTipo = org.mockito.Mockito.mock(PagarTipo.class);
        dataCadastro = LocalDate.now();
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
        assertSame(fornecedor, pagar.getFornecedor());
        assertSame(pagarTipo, pagar.getTipo());
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
        Fornecedor novoFornecedor = org.mockito.Mockito.mock(Fornecedor.class);
        pagar.setFornecedor(novoFornecedor);
        assertSame(novoFornecedor, pagar.getFornecedor());
    }

    @DisplayName("Teste para verificar se os getters e setters do tipo de pagamento funcionam")
    @Test
    public void testGetSetTipo() {
        PagarTipo novoTipo = org.mockito.Mockito.mock(PagarTipo.class);
        pagar.setTipo(novoTipo);
        assertSame(novoTipo, pagar.getTipo());
    }

    @DisplayName("Teste para verificar as anotações de validação")
    @Test
    public void testValidacoes() {
        assertNotNull(pagar.getValor_total(), "Valor total não pode ser nulo");
        assertNotNull(pagar.getData_cadastro(), "Data de cadastro não pode ser nula");
        assertTrue(pagar.getObservacao() == null || pagar.getObservacao().length() <= 255,
            "Observação não pode ter mais de 255 caracteres");
    }
}