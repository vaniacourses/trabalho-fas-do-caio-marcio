package net.originmobi.pdv.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class CaixaLancamentoTest {
    private CaixaLancamento lancamento;

    @BeforeEach
    void setUp() {
        lancamento = new CaixaLancamento();
    }

    @Test
    void testSetAndGetValor() {
        lancamento.setValor(100.0);
        assertEquals(100.0, lancamento.getValor(), 0.0001);
    }

    @Test
    void testSetAndGetObservacao() {
        lancamento.setObservacao("Teste");
        assertEquals("Teste", lancamento.getObservacao());
    }

    @Test
    void testSetAndGetCaixa() {
        Caixa caixa = Mockito.mock(Caixa.class);
        lancamento.setCaixa(caixa);
        assertTrue(lancamento.getCaixa().isPresent());
        assertEquals(caixa, lancamento.getCaixa().get());
    }

    @Test
    void testSetAndGetUsuario() {
        Usuario usuario = Mockito.mock(Usuario.class);
        lancamento.setUsuario(usuario);
        assertEquals(usuario, lancamento.getUsuario());
    }

    @Test
    void testSetAndGetDataCadastro() {
        java.sql.Timestamp now = Mockito.mock(java.sql.Timestamp.class);
        lancamento.setData_cadastro(now);
        assertEquals(now, lancamento.getData_cadastro());
    }
}
