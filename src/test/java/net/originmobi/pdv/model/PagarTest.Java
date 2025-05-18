package net.originmobi.pdv.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class PagarTest {

    private Fornecedor fornecedor;
    private PagarTipo pagarTipo;
    private Pagar pagar;

    @BeforeEach
    void setUp() {
        fornecedor = new Fornecedor();
        fornecedor.setCodigo(1L);
        fornecedor.setNome("Fornecedor Exemplo");
        fornecedor.setNome_fantasia("Fantasia Ltda");
        fornecedor.setCnpj("12.345.678/0001-95");
        fornecedor.setInscricao_estadual("12345678");
        fornecedor.setAtivo(1);
        fornecedor.setData_cadastro(Date.valueOf("2024-01-01"));
        fornecedor.setEndereco(null);
        fornecedor.setTelefone(Collections.emptyList());

        pagarTipo = new PagarTipo();
        pagarTipo.setCodigo(1L);
        pagarTipo.setDescricao("Conta de Luz");
        pagarTipo.setData_cadastro(Timestamp.valueOf("2024-01-01 10:00:00"));

        pagar = new Pagar(
            "Pagamento referente a janeiro",
            500.0,
            LocalDate.of(2024, 2, 1),
            fornecedor,
            pagarTipo
        );
    }

    @Test
    void testPagarNotNull() {
        assertNotNull(pagar);
        assertEquals("Pagamento referente a janeiro", pagar.getObservacao());
        assertEquals(500.0, pagar.getValor_total());
        assertEquals(LocalDate.of(2024, 2, 1), pagar.getData_cadastro());
        assertEquals(fornecedor, pagar.getFornecedor());
        assertEquals(pagarTipo, pagar.getTipo());
    }

    @Test
    void testSetAndGetCodigo() {
        pagar.setCodigo(10L);
        assertEquals(10L, pagar.getCodigo());
    }

    @Test
    void testSetObservacao() {
        pagar.setObservacao("Nova observação");
        assertEquals("Nova observação", pagar.getObservacao());
    }

    @Test
    void testSetValorTotal() {
        pagar.setValor_total(999.99);
        assertEquals(999.99, pagar.getValor_total());
    }

    @Test
    void testSetDataCadastro() {
        LocalDate novaData = LocalDate.of(2025, 1, 1);
        pagar.setData_cadastro(novaData);
        assertEquals(novaData, pagar.getData_cadastro());
    }

    @Test
    void testSetFornecedor() {
        Fornecedor novoFornecedor = new Fornecedor();
        novoFornecedor.setNome("Outro Fornecedor");
        pagar.setFornecedor(novoFornecedor);
        assertEquals("Outro Fornecedor", pagar.getFornecedor().getNome());
    }

    @Test
    void testSetTipo() {
        PagarTipo outroTipo = new PagarTipo();
        outroTipo.setDescricao("Aluguel");
        pagar.setTipo(outroTipo);
        assertEquals("Aluguel", pagar.getTipo().getDescricao());
    }
}
