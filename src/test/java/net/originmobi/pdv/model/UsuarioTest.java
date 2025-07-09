package net.originmobi.pdv.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Classe para teste da classe Usuario")
public class UsuarioTest {

    private Usuario usuario;

    private Pessoa pessoaMock;
    private List<GrupoUsuario> grupoUsuarioMock;
    private List<Permissoes> permissoesMock;
    private Date dataCadastro;

    @BeforeEach
    void setUp() {
        pessoaMock = Mockito.mock(Pessoa.class);

        grupoUsuarioMock = new ArrayList<>();
        grupoUsuarioMock.add(Mockito.mock(GrupoUsuario.class));
        grupoUsuarioMock.add(Mockito.mock(GrupoUsuario.class));

        permissoesMock = new ArrayList<>();
        permissoesMock.add(Mockito.mock(Permissoes.class));

        dataCadastro = new Date(System.currentTimeMillis());

        usuario = new Usuario();
        usuario.setCodigo(1L);
        usuario.setUser("usuarioTeste");
        usuario.setSenha("senha123");
        usuario.setData_cadastro(dataCadastro);
        usuario.setPessoa(pessoaMock);
        usuario.setGrupoUsuario(grupoUsuarioMock);
        usuario.setPermissoes(permissoesMock);
    }

    @Test
    @DisplayName("Teste dos getters e setters da classe Usuario")
    void testGettersAndSetters() {
        assertEquals("usuarioTeste", usuario.getUser());
        assertEquals("senha123", usuario.getSenha());
        assertEquals(dataCadastro, usuario.getData_cadastro());
        assertSame(pessoaMock, usuario.getPessoa());
        assertEquals(grupoUsuarioMock, usuario.getGrupoUsuario());
        assertEquals(permissoesMock, usuario.getPermissoes());
    }

    @Test
    @DisplayName("Teste do construtor padrão")
    void testConstrutorPadrao() {
        Usuario u = new Usuario();
        assertNotNull(u);
        assertNull(u.getCodigo());
        assertNull(u.getUser());
        assertNull(u.getSenha());
        assertNull(u.getData_cadastro());
        assertNull(u.getPessoa());
        assertNull(u.getGrupoUsuario());
        assertNull(u.getPermissoes());
    }
}
