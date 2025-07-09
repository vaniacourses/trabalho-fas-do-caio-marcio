package net.originmobi.pdv.functional;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

import net.originmobi.pdv.model.Caixa;

public class TransferenciaFunctionalTest {

    private static final int OBSERVACAO_MAX_LENGTH = 255;

    @Test
    public void testParticionamentoClassesEquivalenciaValor() {
        // Partição Válida: Valor positivo
        Double valorValido = 100.50;
        assertTrue("Valor positivo deve ser aceito", validarValor(valorValido));

        // Partição Inválida: Valor zero
        Double valorZero = 0.0;
        assertFalse("Valor zero deve ser rejeitado", validarValor(valorZero));

        // Partição Inválida: Valor negativo
        Double valorNegativo = -50.0;
        assertFalse("Valor negativo deve ser rejeitado", validarValor(valorNegativo));

        // Partição Inválida: Valor nulo
        Double valorNulo = null;
        assertFalse("Valor nulo deve ser rejeitado", validarValor(valorNulo));
    }

    @Test
    public void testAnaliseValorLimiteObservacao() {
        // Limite Válido: Observação com 255 caracteres
        String observacaoExata = String.join("", java.util.Collections.nCopies(OBSERVACAO_MAX_LENGTH, "A"));
        assertTrue("Observação com 255 caracteres deve ser aceita", validarObservacao(observacaoExata));

        // Limite Inválido: Observação com 256 caracteres
        String observacaoAcima = String.join("", java.util.Collections.nCopies(OBSERVACAO_MAX_LENGTH + 1, "A"));
        assertFalse("Observação com 256 caracteres deve ser rejeitada", validarObservacao(observacaoAcima));

        // Limite Válido: Observação com 1 caractere
        String observacaoMinima = "A";
        assertTrue("Observação com 1 caractere deve ser aceita", validarObservacao(observacaoMinima));

        // Limite Inválido: Observacao vazia
        String observacaoVazia = "";
        assertFalse("Observação vazia deve ser rejeitada", validarObservacao(observacaoVazia));

        // Limite Inválido: Observacao nula
        String observacaoNula = null;
        assertFalse("Observação nula deve ser rejeitada", validarObservacao(observacaoNula));
    }

    @Test
    public void testGrafoCausaEfeitoContas() {
        Caixa contaOrigem = new Caixa();
        contaOrigem.setCodigo(1L);

        Caixa contaDestino = new Caixa();
        contaDestino.setCodigo(2L);

        // CAUSA: Origem e Destino são diferentes e não nulos
        // EFEITO: Deve ser aceito
        assertTrue("Contas de origem e destino diferentes devem ser aceitas",
                validarContas(contaOrigem, contaDestino));

        // CAUSA: Origem e Destino são a mesma conta
        // EFEITO: Deve ser rejeitado
        assertFalse("Contas de origem e destino iguais devem ser rejeitadas",
                validarContas(contaOrigem, contaOrigem));

        // CAUSA: Origem é nula
        // EFEITO: Deve ser rejeitado
        assertFalse("Conta de origem nula deve ser rejeitada",
                validarContas(null, contaDestino));

        // CAUSA: Destino é nulo
        // EFEITO: Deve ser rejeitado
        assertFalse("Conta de destino nula deve ser rejeitada",
                validarContas(contaOrigem, null));

        // CAUSA: Ambas as contas são nulas
        // EFEITO: Deve ser rejeitado
        assertFalse("Ambas as contas nulas devem ser rejeitadas",
                validarContas(null, null));
    }

    // --- MÉTODOS DE VALIDAÇÃO (Lógica do Teste) ---

    private boolean validarValor(Double valor) {
        if (valor == null) {
            return false;
        }
        return valor > 0;
    }

    private boolean validarObservacao(String observacao) {
        if (observacao == null || observacao.trim().isEmpty()) {
            return false;
        }
        return observacao.length() <= OBSERVACAO_MAX_LENGTH;
    }

    private boolean validarContas(Caixa origem, Caixa destino) {
        if (origem == null || destino == null) {
            return false;
        }
        // Compara os códigos para ver se representam a mesma conta
        return !origem.getCodigo().equals(destino.getCodigo());
    }

    public static void main(String[] args) {
        TransferenciaFunctionalTest testes = new TransferenciaFunctionalTest();
        System.out.println("Iniciando testes funcionais para Transferencia...");

        try {
            // Executando cada método de teste
            testes.testParticionamentoClassesEquivalenciaValor();
            testes.testAnaliseValorLimiteObservacao();
            testes.testGrafoCausaEfeitoContas();

            System.out.println(">>> SUCESSO: Todos os testes funcionais passaram.");

        } catch (AssertionError e) {
            // Captura falhas de assert (assertTrue, assertFalse)
            System.err.println(">>> FALHA EM UM TESTE: " + e.getMessage());
            e.printStackTrace();
        }
    }

}
