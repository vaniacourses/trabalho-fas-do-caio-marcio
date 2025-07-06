package net.originmobi.pdv.model;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Timestamp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Classe para teste de Receber")
public class ReceberTest {

    private Receber receber;
    private Pessoa pessoa;
    private Venda venda;

    @BeforeEach
    void setUp() {
        pessoa = org.mockito.Mockito.mock(Pessoa.class);
        venda = org.mockito.Mockito.mock(Venda.class);
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
        assertSame(pessoa, receber.getPessoa());
        assertNotNull(receber.getData_cadastro());
        assertSame(venda, receber.getVenda());
    }

    @Test
    @DisplayName("Teste dos getters e setters")
    public void testSettersAndGetters() {
        Pessoa novaPessoa = org.mockito.Mockito.mock(Pessoa.class);
        Venda novaVenda = org.mockito.Mockito.mock(Venda.class);
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
        assertSame(novaPessoa, receber.getPessoa());
        assertEquals(now, receber.getData_cadastro());
        assertSame(novaVenda, receber.getVenda());
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

