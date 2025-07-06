package net.originmobi.pdv.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;

class AjusteProdutoTest {
    private AjusteProduto ajusteProduto;

    @BeforeEach
    void setUp() {
        ajusteProduto = new AjusteProduto();
    }

    @Test
    void testSetAndGetEstoqueAtual() {
        ajusteProduto.setEstoque_atual(10);
        assertEquals(10, ajusteProduto.getEstoque_atual());
    }

    @Test
    void testSetAndGetQtdAlteracao() {
        ajusteProduto.setQtd_alteracao(5);
        assertEquals(5, ajusteProduto.getQtd_alteracao());
    }

    @Test
    void testSetAndGetQtdNova() {
        ajusteProduto.setQtd_nova(15);
        assertEquals(15, ajusteProduto.getQtd_nova());
    }

    @Test
    void testSetAndGetProduto() {
        Produto produto = mock(Produto.class);
        ajusteProduto.setProduto(produto);
        assertEquals(produto, ajusteProduto.getProduto());
    }

    @Test
    void testSetAndGetAjuste() {
        Ajuste ajuste = mock(Ajuste.class);
        ajusteProduto.setAjuste(ajuste);
        assertEquals(ajuste, ajusteProduto.getAjuste());
    }
}
