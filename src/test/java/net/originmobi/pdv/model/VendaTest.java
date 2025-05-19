package net.originmobi.pdv.model;

import net.originmobi.pdv.enumerado.VendaSituacao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Classe para teste da Venda")
public class VendaTest {

    private Venda venda;
    private Timestamp agora;
    private Pessoa pessoa;
    private Usuario usuario;

    @BeforeEach
    void inicializa() {
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

    @DisplayName("Teste para verificar se os valores do construtor estão corretos")
    @Test
    void testConstrutorEGetters() {
        assertEquals("Teste de observação", venda.getObservacao());
        assertEquals(100.00, venda.getValor_produtos(), 0.001);
        assertEquals(10.00, venda.getValor_desconto(), 0.001);
        assertEquals(5.00, venda.getValor_acrescimo(), 0.001);
        assertEquals(95.00, venda.getValor_total(), 0.001);
        assertEquals(VendaSituacao.ABERTA, venda.getSituacao());
        assertEquals(agora, venda.getData_cadastro());
        assertNull(venda.getData_finalizado());
        assertNull(venda.getData_cancelado());
        assertEquals(pessoa, venda.getPessoa());
        assertEquals(usuario, venda.getUsuario());
    }

    @DisplayName("Teste para verificar se os setters funcionam corretamente")
    @Test
    void testSetters() {
        venda.setObservacao("Nova observação");
        venda.setValor_produtos(200.0);
        venda.setValor_desconto(20.0);
        venda.setValor_acrescimo(10.0);
        venda.setValor_total(190.0);
        venda.setSituacao(VendaSituacao.CANCELADA);

        assertEquals("Nova observação", venda.getObservacao());
        assertEquals(200.0, venda.getValor_produtos(), 0.001);
        assertEquals(20.0, venda.getValor_desconto(), 0.001);
        assertEquals(10.0, venda.getValor_acrescimo(), 0.001);
        assertEquals(190.0, venda.getValor_total(), 0.001);
        assertEquals(VendaSituacao.CANCELADA, venda.getSituacao());
    }

    @DisplayName("Teste para verificar se a venda está aberta")
    @Test
    void testIsAberta() {
        venda.setSituacao(VendaSituacao.ABERTA);
        assertTrue(venda.isAberta());

        venda.setSituacao(VendaSituacao.CANCELADA);
        assertFalse(venda.isAberta());
    }

    @DisplayName("Teste para verificar associação com produtos")
    @Test
    void testAssociacaoComProdutos() {
        Produto produto = new Produto();
        produto.setDescricao("Produto Teste");
        venda.setProduto(Collections.singletonList(produto));

        assertNotNull(venda.getProduto());
        assertEquals(1, venda.getProduto().size());
        assertEquals("Produto Teste", venda.getProduto().get(0).getDescricao());
    }

    @DisplayName("Teste para verificar associação com tipo de pagamento")
    @Test
    void testSetGetPagamentoTipo() {
        PagamentoTipo pagamentoTipo = new PagamentoTipo();
        venda.setPagamentotipo(pagamentoTipo);

        assertEquals(pagamentoTipo, venda.getPagamentotipo());
    }
}