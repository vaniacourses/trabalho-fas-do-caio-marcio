package net.originmobi.pdv.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class NotaFiscalTotaisTest {
    private NotaFiscalTotais totais;

    @BeforeEach
    void setUp() {
        totais = new NotaFiscalTotais();
    }

    @Test
    void testSetAndGetVProd() {
        totais.setV_prod(123.45);
        assertEquals(123.45, totais.getV_prod(), 0.0001);
    }

    @Test
    void testSetAndGetVDesc() {
        totais.setV_desc(10.0);
        assertEquals(10.0, totais.getV_desc(), 0.0001);
    }

    @Test
    void testSetAndGetVFrete() {
        totais.setV_frete(5.5);
        assertEquals(5.5, totais.getV_frete(), 0.0001);
    }

    @Test
    void testSetAndGetVSeg() {
        totais.setV_seg(2.0);
        assertEquals(2.0, totais.getV_seg(), 0.0001);
    }
}
