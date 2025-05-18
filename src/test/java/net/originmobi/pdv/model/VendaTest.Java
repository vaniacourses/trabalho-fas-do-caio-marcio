package net.originmobi.pdv.model;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import net.originmobi.pdv.enumerado.VendaSituacao;

class VendaTest {

    private Venda venda;
    private Timestamp agora;
    private Pessoa pessoa;
    private Usuario usuario;

    @BeforeEach
    void setUp() {
        agora = new Timestamp(System.currentTimeMillis());
        pessoa = new Pessoa();
        usuario = new Usuario();

        venda = new Venda(
                "Teste de observação",
                100.00,
                10.00,
                5.00,
                95.00,
                VendaSituacao.ABERTA,
                agora,
                null,
                null,
                pessoa,
                usuario
        );
    }

    @Test
    void testConstrutorEGetters() {
        assertEquals("Teste de observação", venda.getObservacao());
        assertEquals(100.00, venda.getValor_produtos());
        assertEquals(10.00, venda.getValor_desconto());
        assertEquals(5.00, venda.getValor_acrescimo());
        assertEquals(95.00, venda.getValor_total());
        assertEquals(VendaSituacao.ABERTA, venda.getSituacao());
        assertEquals(agora, venda.getData_cadastro());
        assertNull(venda.getData_finalizado());
        assertNull(venda.getData_cancelado());
        assertEquals(pessoa, venda.getPessoa());
        assertEquals(usuario, venda.getUsuario());
    }

    @Test
    void testSetters() {
        venda.setObservacao("Nova observação");
        venda.setValor_produtos(200.0);
        venda.setValor_desconto(20.0);
        venda.setValor_acrescimo(10.0);
        venda.setValor_total(190.0);
        venda.setSituacao(VendaSituacao.CANCELADA);

        assertEquals("Nova observação", venda.getObservacao());
        assertEquals(200.0, venda.getValor_produtos());
        assertEquals(20.0, venda.getValor_desconto());
        assertEquals(10.0, venda.getValor_acrescimo());
        assertEquals(190.0, venda.getValor_total());
        assertEquals(VendaSituacao.CANCELADA, venda.getSituacao());
    }

    @Test
    void testIsAberta() {
        venda.setSituacao(VendaSituacao.ABERTA);
        assertTrue(venda.isAberta());

        venda.setSituacao(VendaSituacao.FINALIZADA);
        assertFalse(venda.isAberta());
    }

    @Test
    void testAssociacaoComProdutos() {
        Produto produto = new Produto();
        produto.setDescricao("Produto Teste");
        venda.setProduto(Collections.singletonList(produto));

        assertNotNull(venda.getProduto());
        assertEquals(1, venda.getProduto().size());
        assertEquals("Produto Teste", venda.getProduto().get(0).getDescricao());
    }

    @Test
    void testSetGetPagamentoTipo() {
        PagamentoTipo pagamentoTipo = new PagamentoTipo();
        venda.setPagamentotipo(pagamentoTipo);

        assertEquals(pagamentoTipo, venda.getPagamentotipo());
    }
}
