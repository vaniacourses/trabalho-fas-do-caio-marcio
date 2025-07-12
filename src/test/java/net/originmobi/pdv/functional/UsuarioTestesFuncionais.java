package net.originmobi.pdv.functional;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class UsuarioTestesFuncionais {

    private static final int USER_MIN_LENGTH = 3;
    private static final int USER_MAX_LENGTH = 20;

    @Test
    public void testParticionamentoClassesEquivalenciaUsuario() {
        String userValido = "usuarioValido";
        assertTrue(validarUser(userValido));

        String userVazio = "";
        assertFalse(validarUser(userVazio));

        String userNulo = null;
        assertFalse(validarUser(userNulo));

        String userCurto = "AB";
        assertFalse(validarUser(userCurto));

        String userLongo = String.join("", java.util.Collections.nCopies(21, "A"));
        assertFalse(validarUser(userLongo));
    }

    @Test
    public void testValorLimiteSenha() {
        String senhaValida = "senha123";
        assertTrue(validarSenha(senhaValida));

        String senhaVazia = "";
        assertFalse(validarSenha(senhaVazia));

        String senhaNula = null;
        assertFalse(validarSenha(senhaNula));
    }

    @Test
    public void testGrafoCausaEfeitoUsuarioSenha() {
        String user = "admin";
        String senha = "1234";
        assertTrue(validarLogin(user, senha));

        String userVazio = "";
        assertFalse(validarLogin(userVazio, senha));

        String senhaNula = null;
        assertFalse(validarLogin(user, senhaNula));

        String userNulo = null;
        String senhaVazia = "";
        assertFalse(validarLogin(userNulo, senhaVazia));
    }

    private boolean validarUser(String user) {
        if (user == null || user.trim().isEmpty()) {
            return false;
        }
        return user.length() >= USER_MIN_LENGTH && user.length() <= USER_MAX_LENGTH;
    }

    private boolean validarSenha(String senha) {
        return senha != null && !senha.trim().isEmpty();
    }

    private boolean validarLogin(String user, String senha) {
        return validarUser(user) && validarSenha(senha);
    }

    public static void main(String[] args) {
        UsuarioTestesFuncionais testes = new UsuarioTestesFuncionais();

        try {
            testes.testParticionamentoClassesEquivalenciaUsuario();
            testes.testValorLimiteSenha();
            testes.testGrafoCausaEfeitoUsuarioSenha();
            System.out.println(">>> SUCESSO: Todos os testes funcionais passaram.");
        } catch (AssertionError e) {
            System.err.println(">>> FALHA EM UM TESTE: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println(">>> ERRO NA EXECUÇÃO DOS TESTES: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
