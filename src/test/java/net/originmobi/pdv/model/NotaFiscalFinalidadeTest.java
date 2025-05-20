package net.originmobi.pdv.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class NotaFiscalFinalidadeTest {

    @Test
    void testConstructorAndGettersSetters() {
        NotaFiscalFinalidade finalidade = new NotaFiscalFinalidade();

        Long codigo = 1L;
        int tipo = 2;
        String descricao = "Devolução de mercadoria";

        finalidade.setCodigo(codigo);
        finalidade.setTipo(tipo);
        finalidade.setDescricao(descricao);

        assertEquals(codigo, finalidade.getCodigo());
        assertEquals(tipo, finalidade.getTipo());
        assertEquals(descricao, finalidade.getDescricao());
    }

    @Test
    void testDefaultConstructor() {
        NotaFiscalFinalidade finalidade = new NotaFiscalFinalidade();
        assertNotNull(finalidade);
    }
}
