package net.originmobi.pdv.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class TributacaoRegraTest {
    private TributacaoRegra regra;

    @BeforeEach
    void setUp() {
        regra = new TributacaoRegra();
    }

    @Test
    void testSetAndGetPis() {
        regra.setPis(1.5d);
        assertEquals(1.5d, regra.getPis(), 0.0001d);
    }

    @Test
    void testSetAndGetCofins() {
        regra.setCofins(2.5d);
        assertEquals(2.5d, regra.getCofins(), 0.0001d);
    }

    @Test
    void testSetAndGetTipo() {
        // Não é possível mockar enums com Mockito, usar valor real
        regra.setTipo(net.originmobi.pdv.enumerado.EntradaSaida.ENTRADA);
        assertEquals(net.originmobi.pdv.enumerado.EntradaSaida.ENTRADA, regra.getTipo());
    }

    @Test
    void testSetAndGetTributacao() {
        Tributacao tributacao = Mockito.mock(Tributacao.class);
        regra.setTributacao(tributacao);
        assertEquals(tributacao, regra.getTributacao());
    }
}
