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
   
    @Test
    void testObjetosDiferentesNaoSaoIguais() {
        NotaFiscalFinalidade f1 = new NotaFiscalFinalidade();
        f1.setCodigo(1L);
        f1.setTipo(2);
        f1.setDescricao("Devolução");
        
        NotaFiscalFinalidade f2 = new NotaFiscalFinalidade();
        f2.setCodigo(2L);
        f2.setTipo(3);
        f2.setDescricao("Venda");
        
        
        assertNotEquals(f1.getCodigo(), f2.getCodigo());
        assertNotEquals(f1.getTipo(), f2.getTipo());
        assertNotEquals(f1.getDescricao(), f2.getDescricao());
    }
    @Test
    void testCamposNulos() {

        NotaFiscalFinalidade f = new NotaFiscalFinalidade();
        f.setCodigo(null);
        f.setDescricao(null);
        
        assertNull(f.getCodigo());
        assertNull(f.getDescricao());
        
        assertEquals(0, f.getTipo());
    }

    @Test
    void testOrdemDeUsoDosSetters() {

        NotaFiscalFinalidade f = new NotaFiscalFinalidade();
        f.setDescricao("Teste");
        f.setTipo(1);
        f.setCodigo(100L);
        assertAll(
            () -> assertEquals("Teste", f.getDescricao()),
            () -> assertEquals(1, f.getTipo())
            );
        }
         @Test
         void testDescricaoNaoAlteradaInesperadamente() {
            NotaFiscalFinalidade f = new NotaFiscalFinalidade();
            f.setDescricao("Teste");
            
            assertEquals("Teste", f.getDescricao());
        }
}

