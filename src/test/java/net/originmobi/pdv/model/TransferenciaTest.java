package net.originmobi.pdv.model;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Timestamp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Classe para teste de Transferencia")
public class TransferenciaTest {

    private Transferencia transferencia;
    private Caixa origem;
    private Caixa destino;
    private Usuario usuario;

    @BeforeEach
    void setUp() {
        origem = new Caixa();
        origem.setCodigo(1L);

        destino = new Caixa();
        destino.setCodigo(2L);

        usuario = new Usuario();
        usuario.setCodigo(10L);

        transferencia = new Transferencia(
                150.75,
                new Timestamp(System.currentTimeMillis()),
                origem,
                destino,
                usuario,
                "Transferência teste"
        );
    }

    @Test
    @DisplayName("Teste do construtor com parâmetros")
    void testConstrutorComParametros() {
        assertEquals(150.75, transferencia.getValor(), 0.001);
        assertNotNull(transferencia.getData_transferencia());
        assertEquals(origem, transferencia.getOrigem());
        assertEquals(destino, transferencia.getDestino());
        assertEquals(usuario, transferencia.getUsuario());
        assertEquals("Transferência teste", transferencia.getObservacao());
    }

    @Test
    @DisplayName("Teste do construtor padrão")
    void testConstrutorPadrao() {
        Transferencia t = new Transferencia();
        assertNotNull(t);
        assertNull(t.getCodigo());
        assertNull(t.getValor());
        assertNull(t.getData_transferencia());
        assertNull(t.getOrigem());
        assertNull(t.getDestino());
        assertNull(t.getUsuario());
        assertNull(t.getObservacao());
    }

    @Test
    @DisplayName("Teste dos getters e setters")
    void testSettersAndGetters() {
        Caixa novaOrigem = new Caixa();
        novaOrigem.setCodigo(3L);

        Caixa novoDestino = new Caixa();
        novoDestino.setCodigo(4L);

        Usuario novoUsuario = new Usuario();
        novoUsuario.setCodigo(20L);

        Timestamp now = new Timestamp(System.currentTimeMillis());

        transferencia.setCodigo(5L);
        transferencia.setValor(300.00);
        transferencia.setData_transferencia(now);
        transferencia.setOrigem(novaOrigem);
        transferencia.setDestino(novoDestino);
        transferencia.setUsuario(novoUsuario);
        transferencia.setObservacao("Alteração na transferência");

        assertEquals(Long.valueOf(5L), transferencia.getCodigo());
        assertEquals(300.00, transferencia.getValor(), 0.001);
        assertEquals(now, transferencia.getData_transferencia());
        assertEquals(novaOrigem, transferencia.getOrigem());
        assertEquals(novoDestino, transferencia.getDestino());
        assertEquals(novoUsuario, transferencia.getUsuario());
        assertEquals("Alteração na transferência", transferencia.getObservacao());
    }
}
