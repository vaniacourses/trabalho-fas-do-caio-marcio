package net.originmobi.pdv.structural;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import net.originmobi.pdv.enumerado.cartao.CartaoSituacao;
import net.originmobi.pdv.model.cartao.CartaoLancamento;

public class CartaoLancamentoStructuralTest {

    private CartaoLancamento lancamento;

    @Before
    public void setUp() {
        lancamento = new CartaoLancamento();
    }

    @Test
    public void testIsProcessadoTrue() {
        lancamento.setSituacao(CartaoSituacao.PROCESSADO);
        assertTrue(lancamento.isProcessado());
    }

    @Test
    public void testIsProcessadoFalse() {
        lancamento.setSituacao(CartaoSituacao.ANTECIPADO);
        assertFalse(lancamento.isProcessado());
    }

    @Test
    public void testIsAntecipadoTrue() {
        lancamento.setSituacao(CartaoSituacao.ANTECIPADO);
        assertTrue(lancamento.isAntecipado());
    }

    @Test
    public void testIsAntecipadoFalse() {
        lancamento.setSituacao(CartaoSituacao.PROCESSADO);
        assertFalse(lancamento.isAntecipado());
    }
}
