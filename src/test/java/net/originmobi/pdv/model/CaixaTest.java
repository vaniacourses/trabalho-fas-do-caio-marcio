package net.originmobi.pdv.model;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import java.sql.Timestamp;
import java.util.Date;
import org.junit.jupiter.api.DisplayName;

import net.originmobi.pdv.enumerado.caixa.CaixaTipo;

@DisplayName("Classe para teste do caixa")
public class CaixaTest {

    private Caixa caixa;
    private Usuario usuario;

    @Before
    public void inicializa() {
        usuario = new Usuario();
        usuario.setCodigo(1L);
        usuario.setUser("TestUser");
        
        caixa = new Caixa();
        caixa.setCodigo(1L);
        caixa.setDescricao("Caixa Teste");
        caixa.setTipo(CaixaTipo.CAIXA);
        caixa.setValor_abertura(100.0);
        caixa.setValor_total(100.0);
        caixa.setUsuario(usuario);
        caixa.setData_cadastro(new Date());
    }

    @DisplayName("Teste para verificar se o caixa é um cofre")
    @Test
    public void testIsCofre() {
        assertFalse(caixa.isCofre());
        caixa.setTipo(CaixaTipo.COFRE);
        assertTrue(caixa.isCofre());
    }

    @DisplayName("Teste para verificar se o caixa é um banco")
    @Test
    public void testIsBanco() {
        assertFalse(caixa.isBanco());
        caixa.setTipo(CaixaTipo.BANCO);
        assertTrue(caixa.isBanco());
    }

    @DisplayName("Teste para verificar se o caixa está aberto")
    @Test
    public void testIsAberto() {
        assertTrue(caixa.isAberto());
        caixa.setData_fechamento(new Timestamp(System.currentTimeMillis()));
        assertFalse(caixa.isAberto());
    }

    @DisplayName("Teste para verificar se o construtor padrão funciona")
    @Test
    public void testConstrutorPadrao() {
        Caixa novoCaixa = new Caixa();
        assertNotNull(novoCaixa);
    }

    @DisplayName("Teste para verificar se o construtor com parâmetros funciona")
    @Test
    public void testConstrutorComParametros() {
        String descricao = "Caixa Teste";
        CaixaTipo tipo = CaixaTipo.CAIXA;
        Double valorAbertura = 100.0;
        Double valorTotal = 100.0;
        Double valorFechamento = 0.0;
        Date dataCadastro = new Date();
        Timestamp dataFechamento = null;

        Caixa novoCaixa = new Caixa(descricao, tipo, valorAbertura, valorTotal, valorFechamento, dataCadastro, dataFechamento, usuario);

        assertEquals(descricao, novoCaixa.getDescricao());
        assertEquals(tipo, novoCaixa.getTipo());
        assertEquals(valorAbertura, novoCaixa.getValor_abertura());
        assertEquals(valorTotal, novoCaixa.getValor_total());
        assertEquals(valorFechamento, novoCaixa.getValor_fechamento());
        assertEquals(dataCadastro, novoCaixa.getData_cadastro());
        assertEquals(dataFechamento, novoCaixa.getData_fechamento());
        assertEquals(usuario, novoCaixa.getUsuario());
    }

    @DisplayName("Teste para verificar se os setters e getters funcionam")
    @Test
    public void testSettersAndGetters() {
        String agencia = "1234";
        String conta = "56789";
        
        caixa.setAgencia(agencia);
        caixa.setConta(conta);
        
        assertEquals(agencia, caixa.getAgencia());
        assertEquals(conta, caixa.getConta());

        Double valorEntrada = 50.0;
        Double valorSaida = 30.0;
        
        caixa.setValor_entrada(valorEntrada);
        caixa.setValor_saida(valorSaida);
        
        assertEquals(valorEntrada, caixa.getValor_entrada());
        assertEquals(valorSaida, caixa.getValor_saida());
    }

    @DisplayName("Teste para verificar se o método toString funciona")
    @Test
    public void testToString() {
        String expected = "Caixa [codigo=" + caixa.getCodigo() + ", descricao=" + caixa.getDescricao() + "]";
        assertEquals(expected, caixa.toString());
    }
} 