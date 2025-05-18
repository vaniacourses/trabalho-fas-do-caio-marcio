package net.originmobi.pdv.model;

import static org.junit.Assert.*;

import java.sql.Timestamp;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

@DisplayName("Classe para teste de Receber")
public class ReceberTest {

    private Receber receber;
    private Pessoa pessoa;
    private Venda venda;

    @Before
    public void setUp() {
        pessoa = new Pessoa();
        pessoa.setCodigo(1L);

        venda = new Venda();
        venda.setCodigo(100L);

        receber = new Receber(
                "Pagamento parcial",
                250.00,
                pessoa,
                new Timestamp(System.currentTimeMillis()),
                venda
        );
    }

    @Test
    @DisplayName("Teste do construtor com parâmetros")
    public void testConstrutorComParametros() {
        assertEquals("Pagamento parcial", receber.getObservacao());
        assertEquals(250.00, receber.getValor_total(), 0.001);
        assertEquals(pessoa, receber.getPessoa());
        assertNotNull(receber.getData_cadastro());
        assertEquals(venda, receber.getVenda());
    }

    @Test
    @DisplayName("Teste dos getters e setters")
    public void testSettersAndGetters() {
        Pessoa novaPessoa = new Pessoa();
        novaPessoa.setCodigo(2L);

        Venda novaVenda = new Venda();
        novaVenda.setCodigo(200L);

        Timestamp now = new Timestamp(System.currentTimeMillis());

        receber.setCodigo(5L);
        receber.setObservacao("Outro pagamento");
        receber.setValor_total(400.00);
        receber.setPessoa(novaPessoa);
        receber.setData_cadastro(now);
        receber.setVenda(novaVenda);

        assertEquals(Long.valueOf(5L), receber.getCodigo());
        assertEquals("Outro pagamento", receber.getObservacao());
        assertEquals(400.00, receber.getValor_total(), 0.001);
        assertEquals(novaPessoa, receber.getPessoa());
        assertEquals(now, receber.getData_cadastro());
        assertEquals(novaVenda, receber.getVenda());
    }

    @Test
    @DisplayName("Teste do construtor padrão")
    public void testConstrutorPadrao() {
        Receber novoReceber = new Receber();
        assertNotNull(novoReceber);
        assertNull(novoReceber.getCodigo());
        assertNull(novoReceber.getObservacao());
        assertNull(novoReceber.getPessoa());
        assertNull(novoReceber.getVenda());
    }
}

