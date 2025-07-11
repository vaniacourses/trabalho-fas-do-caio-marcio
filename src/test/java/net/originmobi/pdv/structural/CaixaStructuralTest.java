package net.originmobi.pdv.structural;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import java.sql.Timestamp;
import java.util.Date;

import net.originmobi.pdv.enumerado.caixa.CaixaTipo;
import net.originmobi.pdv.model.Caixa;

public class CaixaStructuralTest {

    private Caixa caixa;

    @Before
    public void setUp() {
        caixa = new Caixa();
    }

    @Test
    public void testIsCofreRetornaTrue() {
        caixa.setTipo(CaixaTipo.COFRE);
        assertTrue(caixa.isCofre());
    }

    @Test
    public void testIsCofreRetornaFalse() {
        caixa.setTipo(CaixaTipo.BANCO);
        assertFalse(caixa.isCofre());
    }

    @Test
    public void testIsBancoRetornaTrue() {
        caixa.setTipo(CaixaTipo.BANCO);
        assertTrue(caixa.isBanco());
    }

    @Test
    public void testIsBancoRetornaFalse() {
        caixa.setTipo(CaixaTipo.COFRE);
        assertFalse(caixa.isBanco());
    }

    @Test
    public void testIsAbertoRetornaTrue() {
        caixa.setData_fechamento(null);
        assertTrue(caixa.isAberto());
    }

    @Test
    public void testIsAbertoRetornaFalse() {
        caixa.setData_fechamento(new Timestamp(System.currentTimeMillis()));
        assertFalse(caixa.isAberto());
    }
}
