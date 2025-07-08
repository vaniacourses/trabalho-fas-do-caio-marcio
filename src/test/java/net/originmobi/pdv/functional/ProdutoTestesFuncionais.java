package net.originmobi.pdv.functional;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;


public class ProdutoTestesFuncionais {
    
    private static final int DESCRICAO_MIN_LENGTH = 3;
    private static final int DESCRICAO_MAX_LENGTH = 50;
    private static final int NCM_MAX_LENGTH = 8;
    
    @Test
    public void testParticionamentoClassesEquivalenciaDescricao() {
        
        String descricaoValida = "Produto Teste Válido";
        assertTrue("Descrição válida deve ser aceita", 
            validarDescricao(descricaoValida));
        
        String descricaoVazia = "";
        assertFalse("Descrição vazia deve ser rejeitada", 
            validarDescricao(descricaoVazia));
        
        String descricaoNula = null;
        assertFalse("Descrição nula deve ser rejeitada", 
            validarDescricao(descricaoNula));
        
        String descricaoCurta = "AB";
        assertFalse("Descrição muito curta deve ser rejeitada", 
            validarDescricao(descricaoCurta));
        
        String descricaoLonga = String.join("", java.util.Collections.nCopies(51, "A"));
        assertFalse("Descrição muito longa deve ser rejeitada", 
            validarDescricao(descricaoLonga));
    }
    
    
    @Test
    public void testAnaliseValorLimiteNCM() {
        String ncmExato = "12345678";
        assertTrue("NCM com 8 caracteres deve ser aceito", 
            validarNCM(ncmExato));
        
        String ncmAbaixo = "1234567";
        assertTrue("NCM com 7 caracteres deve ser aceito", 
            validarNCM(ncmAbaixo));
        
        String ncmAcima = "123456789";
        assertFalse("NCM com 9 caracteres deve ser rejeitado", 
            validarNCM(ncmAcima));
        
        String ncmMinimo = "1";
        assertTrue("NCM com 1 caractere deve ser aceito", 
            validarNCM(ncmMinimo));
        
        String ncmMaximo = "1234567890";
        assertFalse("NCM com 10 caracteres deve ser rejeitado", 
            validarNCM(ncmMaximo));
        
        String ncmVazio = "";
        assertTrue("NCM vazio deve ser aceito (campo opcional)", 
            validarNCM(ncmVazio));
    }
    
    @Test
    public void testGrafoCausaEfeitoValoresProduto() {
        
        // CAUSA: Valor de venda maior que valor de custo
        // EFEITO: Deve ser aceito
        Double valorCusto1 = 10.0;
        Double valorVenda1 = 15.0;
        assertTrue("Valor de venda maior que custo deve ser aceito", 
            validarRelacaoValores(valorCusto1, valorVenda1));
        
        // CAUSA: Valor de venda igual ao valor de custo
        // EFEITO: Deve ser aceito
        Double valorCusto2 = 15.0;
        Double valorVenda2 = 15.0;
        assertTrue("Valor de venda igual ao custo deve ser aceito", 
            validarRelacaoValores(valorCusto2, valorVenda2));
        
        // CAUSA: Valor de venda menor que valor de custo
        // EFEITO: Deve ser rejeitado
        Double valorCusto3 = 20.0;
        Double valorVenda3 = 15.0;
        assertFalse("Valor de venda menor que custo deve ser rejeitado", 
            validarRelacaoValores(valorCusto3, valorVenda3));
        
        // CAUSA: Valor de custo zero e valor de venda positivo
        // EFEITO: Deve ser aceito
        Double valorCusto4 = 0.0;
        Double valorVenda4 = 10.0;
        assertTrue("Valor de custo zero com venda positiva deve ser aceito", 
            validarRelacaoValores(valorCusto4, valorVenda4));
        
        // CAUSA: Valores negativos
        // EFEITO: Deve ser rejeitado
        Double valorCusto5 = -10.0;
        Double valorVenda5 = 15.0;
        assertFalse("Valor de custo negativo deve ser rejeitado", 
            validarRelacaoValores(valorCusto5, valorVenda5));
    }
    
    private boolean validarDescricao(String descricao) {
        if (descricao == null || descricao.trim().isEmpty()) {
            return false; 
        }
        
        return descricao.length() >= DESCRICAO_MIN_LENGTH && 
               descricao.length() <= DESCRICAO_MAX_LENGTH;
    }
    
    private boolean validarNCM(String ncm) {
        if (ncm == null || ncm.trim().isEmpty()) {
            return true;
        }
        
        return ncm.length() <= NCM_MAX_LENGTH;
    }
    
    private boolean validarRelacaoValores(Double valorCusto, Double valorVenda) {
        if (valorCusto == null || valorVenda == null) {
            return false;
        }
        
        if (valorCusto < 0 || valorVenda < 0) {
            return false;
        }
        return valorVenda >= valorCusto;
    }

    
    public static void main(String[] args) {
        
        ProdutoTestesFuncionais testes = new ProdutoTestesFuncionais();
        
        try {
            testes.testParticionamentoClassesEquivalenciaDescricao();
            testes.testAnaliseValorLimiteNCM();
            testes.testGrafoCausaEfeitoValoresProduto();
            
            
        } catch (Exception e) {
            System.err.println("ERRO NA EXECUÇÃO DOS TESTES: " + e.getMessage());
            e.printStackTrace();
        }
    }
} 