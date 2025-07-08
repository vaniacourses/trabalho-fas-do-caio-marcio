package net.originmobi.pdv.functional;

import org.junit.Test;
import static org.junit.Assert.*;

public class VendaFunctionalTest {

    private static final double VALOR_MINIMO = 0.0;
    private static final int OBSERVACAO_MAX_LENGTH = 255;
    
    @Test
    public void testParticionamentoClassesEquivalenciaValorTotal() {
        Double valorTotalValido = 150.50;
        assertTrue("Valor total válido deve ser aceito", 
            validarValorTotal(valorTotalValido));
        Double valorTotalZero = 0.0;
        assertTrue("Valor total zero deve ser aceito", 
            validarValorTotal(valorTotalZero));
        Double valorTotalNegativo = -50.0;
        assertFalse("Valor total negativo deve ser rejeitado", 
            validarValorTotal(valorTotalNegativo));
        Double valorTotalNulo = null;
        assertFalse("Valor total nulo deve ser rejeitado", 
            validarValorTotal(valorTotalNulo));
        Double valorTotalAlto = 999999.99;
        assertTrue("Valor total alto deve ser aceito", 
            validarValorTotal(valorTotalAlto));
    }
    
    @Test
    public void testAnaliseValorLimiteObservacao() {
        String observacaoExato = String.join("", java.util.Collections.nCopies(255, "A"));
        assertTrue("Observação com 255 caracteres deve ser aceita", 
            validarObservacao(observacaoExato));
        String observacaoAbaixo = String.join("", java.util.Collections.nCopies(254, "A"));
        assertTrue("Observação com 254 caracteres deve ser aceita", 
            validarObservacao(observacaoAbaixo));
        String observacaoAcima = String.join("", java.util.Collections.nCopies(256, "A"));
        assertFalse("Observação com 256 caracteres deve ser rejeitada", 
            validarObservacao(observacaoAcima));
        String observacaoMinimo = "A";
        assertTrue("Observação com 1 caractere deve ser aceita", 
            validarObservacao(observacaoMinimo));
        String observacaoVazia = "";
        assertTrue("Observação vazia deve ser aceita (campo opcional)", 
            validarObservacao(observacaoVazia));
        String observacaoNula = null;
        assertTrue("Observação nula deve ser aceita (campo opcional)", 
            validarObservacao(observacaoNula));
    }
    
    @Test
    public void testGrafoCausaEfeitoValoresVenda() {

        // CAUSA: Desconto menor que valor dos produtos
        // EFEITO: Deve ser aceito (desconto válido)
        Double valorProdutos1 = 100.0;
        Double valorDesconto1 = 20.0;
        Double valorTotal1 = 80.0;
        assertTrue("Desconto menor que valor dos produtos deve ser aceito", 
            validarRelacaoValoresVenda(valorProdutos1, valorDesconto1, valorTotal1));

        // CAUSA: Desconto igual ao valor dos produtos
        // EFEITO: Deve ser aceito (desconto total)
        Double valorProdutos2 = 100.0;
        Double valorDesconto2 = 100.0;
        Double valorTotal2 = 0.0;
        assertTrue("Desconto igual ao valor dos produtos deve ser aceito", 
            validarRelacaoValoresVenda(valorProdutos2, valorDesconto2, valorTotal2));

        // CAUSA: Desconto maior que valor dos produtos
        // EFEITO: Deve ser rejeitado (desconto inválido)
        Double valorProdutos3 = 100.0;
        Double valorDesconto3 = 120.0;
        Double valorTotal3 = -20.0;
        assertFalse("Desconto maior que valor dos produtos deve ser rejeitado", 
            validarRelacaoValoresVenda(valorProdutos3, valorDesconto3, valorTotal3));

        // CAUSA: Valor total calculado incorretamente
        // EFEITO: Deve ser rejeitado (cálculo incorreto)
        Double valorProdutos4 = 100.0;
        Double valorDesconto4 = 20.0;
        Double valorTotal4 = 90.0;
        assertFalse("Valor total calculado incorretamente deve ser rejeitado", 
            validarRelacaoValoresVenda(valorProdutos4, valorDesconto4, valorTotal4));

        // CAUSA: Valores negativos
        // EFEITO: Deve ser rejeitado (valores inválidos)
        Double valorProdutos5 = -50.0;
        Double valorDesconto5 = 20.0;
        Double valorTotal5 = -70.0;
        assertFalse("Valores negativos devem ser rejeitados", 
            validarRelacaoValoresVenda(valorProdutos5, valorDesconto5, valorTotal5));
            
        // CAUSA: Venda com acréscimo
        // EFEITO: Deve ser aceito (acréscimo válido)
        Double valorProdutos6 = 100.0;
        Double valorDesconto6 = 0.0;
        Double valorAcrescimo6 = 10.0;
        Double valorTotal6 = 110.0;
        assertTrue("Venda com acréscimo deve ser aceita", 
            validarRelacaoValoresVendaComAcrescimo(valorProdutos6, valorDesconto6, valorAcrescimo6, valorTotal6));
    }

    private boolean validarValorTotal(Double valorTotal) {
        if (valorTotal == null) {
            return false;
        }
        return valorTotal >= VALOR_MINIMO;
    }
    
    private boolean validarObservacao(String observacao) {
        if (observacao == null || observacao.trim().isEmpty()) {
            return true; 
        }
        return observacao.length() <= OBSERVACAO_MAX_LENGTH;
    }

    private boolean validarRelacaoValoresVenda(Double valorProdutos, Double valorDesconto, Double valorTotal) {
        if (valorProdutos == null || valorDesconto == null || valorTotal == null) {
            return false;
        }
        if (valorProdutos < 0 || valorDesconto < 0 || valorTotal < 0) {
            return false;
        }
        if (valorDesconto > valorProdutos) {
            return false;
        }
        double valorCalculado = valorProdutos - valorDesconto;
        return Math.abs(valorTotal - valorCalculado) < 0.01;
    }
    
    private boolean validarRelacaoValoresVendaComAcrescimo(Double valorProdutos, Double valorDesconto, 
                                                          Double valorAcrescimo, Double valorTotal) {
        if (valorProdutos == null || valorDesconto == null || valorAcrescimo == null || valorTotal == null) {
            return false; 
        }
        if (valorProdutos < 0 || valorDesconto < 0 || valorAcrescimo < 0 || valorTotal < 0) {
            return false; 
        }
        if (valorDesconto > valorProdutos) {
            return false;
        }
        double valorCalculado = valorProdutos - valorDesconto + valorAcrescimo;
        return Math.abs(valorTotal - valorCalculado) < 0.01;
    }

    public static void main(String[] args) {
        VendaFunctionalTest testes = new VendaFunctionalTest();
        try {
            testes.testParticionamentoClassesEquivalenciaValorTotal();
            testes.testAnaliseValorLimiteObservacao();
            testes.testGrafoCausaEfeitoValoresVenda();
        } catch (Exception e) {
            System.err.println("ERRO NA EXECUÇÃO DOS TESTES: " + e.getMessage());
            e.printStackTrace();
        }
    }
} 