package net.originmobi.pdv.model;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

@DisplayName("Classe para teste do regime tributário")
public class RegimeTributarioTest {

    private RegimeTributario regimeTributario;

    @Before
    public void inicializa() {
        regimeTributario = new RegimeTributario();
        regimeTributario.setCodigo(1L);
        regimeTributario.setDescricao("Simples Nacional");
        regimeTributario.setTipoRegime(1);
    }

    @DisplayName("Teste para verificar se o construtor padrão funciona")
    @Test
    public void testConstrutorPadrao() {
        RegimeTributario novoRegime = new RegimeTributario();
        assertNotNull(novoRegime);
    }

    @DisplayName("Teste para verificar se os getters e setters do código funcionam")
    @Test
    public void testGetSetCodigo() {
        Long novoCodigo = 2L;
        regimeTributario.setCodigo(novoCodigo);
        assertEquals(novoCodigo, regimeTributario.getCodigo());
    }

    @DisplayName("Teste para verificar se os getters e setters da descrição funcionam")
    @Test
    public void testGetSetDescricao() {
        String novaDescricao = "Lucro Presumido";
        regimeTributario.setDescricao(novaDescricao);
        assertEquals(novaDescricao, regimeTributario.getDescricao());
    }

    @DisplayName("Teste para verificar se os getters e setters do tipo de regime funcionam")
    @Test
    public void testGetSetTipoRegime() {
        int novoTipoRegime = 2;
        regimeTributario.setTipoRegime(novoTipoRegime);
        assertEquals(novoTipoRegime, regimeTributario.getTipoRegime());
    }

    @DisplayName("Teste para verificar se a descrição não pode ser nula")
    @Test
    public void testDescricaoNotNull() {
        assertNotNull(regimeTributario.getDescricao());
    }

    @DisplayName("Teste para verificar se o tipo de regime é inicializado corretamente")
    @Test
    public void testTipoRegimeInitialization() {
        RegimeTributario novoRegime = new RegimeTributario();
        assertEquals(0, novoRegime.getTipoRegime()); // Valor padrão para int é 0
    }
} 