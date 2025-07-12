package net.originmobi.pdv.mutation;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.*;

import java.util.*;

import net.originmobi.pdv.model.*;
import net.originmobi.pdv.service.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;

@RunWith(MockitoJUnitRunner.class)
public class UsuarioControllerMutationTest {

    @InjectMocks
    private net.originmobi.pdv.controller.UsuarioController controller;

    @Mock
    private UsuarioService usuarios;

    @Mock
    private PessoaService pessoas;

    @Mock
    private GrupoUsuarioService gruposUsuario;

    @Mock
    private Errors errors;

    private Usuario usuario;
    private List<Usuario> listaUsuarios;
    private List<Pessoa> listaPessoas;
    private List<GrupoUsuario> listaGrupos;

    @Before
    public void setUp() {
        usuario = new Usuario();
        usuario.setCodigo(1L);
        listaUsuarios = Collections.singletonList(usuario);
        listaPessoas = Collections.emptyList();
        listaGrupos = Collections.emptyList();
    }

    @Test
    public void testForm() {
        ModelAndView mv = controller.form();
        assertEquals("usuario/form", mv.getViewName());
        assertNotNull(mv.getModel().get("usuario"));
    }

    @Test
    public void testLista() {
        when(usuarios.lista()).thenReturn(listaUsuarios);

        ModelAndView mv = controller.lista();
        assertEquals("usuario/list", mv.getViewName());
        assertEquals(listaUsuarios, mv.getModel().get("usuarios"));
        verify(usuarios).lista();
    }

    @Test
    public void testCadastrarComErroRetornaForm() {
        when(errors.hasErrors()).thenReturn(true);
        String result = controller.cadastrar(usuario, errors, mock(org.springframework.web.servlet.mvc.support.RedirectAttributes.class));
        assertEquals("usuario/form", result);
    }


    @Test
    public void testEditar() {
        when(gruposUsuario.buscaGrupos(usuario)).thenReturn(listaGrupos);

        ModelAndView mv = controller.editar(usuario);
        assertEquals("usuario/form", mv.getViewName());
        assertEquals(usuario, mv.getModel().get("usuario"));
        assertEquals(listaGrupos, mv.getModel().get("grupos"));
        verify(gruposUsuario).buscaGrupos(usuario);
    }

    @Test
    public void testAddGrupoComCodigoVazio() {
        Map<String, String> request = new HashMap<>();
        request.put("codigoGru", "");
        String result = controller.addGrupo(request);
        assertEquals("grupo vazio", result);
    }

    @Test
    public void testAddGrupoComValoresValidos() {
        Map<String, String> request = new HashMap<>();
        request.put("codigoGru", "2");
        request.put("codigoUsu", "1");

        when(usuarios.addGrupo(1L, 2L)).thenReturn("sucesso");

        String result = controller.addGrupo(request);
        assertEquals("sucesso", result);
        verify(usuarios).addGrupo(1L, 2L);
    }

    @Test
    public void testRemoveGrupo() {
        Map<String, String> request = new HashMap<>();
        request.put("codigoGru", "2");
        request.put("codigoUsu", "1");

        when(usuarios.removeGrupo(1L, 2L)).thenReturn("removido");

        String result = controller.removeGrupo(request);
        assertEquals("removido", result);
        verify(usuarios).removeGrupo(1L, 2L);
    }

    @Test
    public void testTesteRetornaOk() {
        String result = controller.teste();
        assertEquals("tudo ok", result);
    }

    @Test
    public void testPessoasModelAttribute() {
        when(pessoas.lista()).thenReturn(listaPessoas);
        List<Pessoa> result = controller.pessoas();
        assertEquals(listaPessoas, result);
        verify(pessoas).lista();
    }

    @Test
    public void testTodosGruposModelAttribute() {
        when(gruposUsuario.lista()).thenReturn(listaGrupos);
        List<GrupoUsuario> result = controller.todosGrupos();
        assertEquals(listaGrupos, result);
        verify(gruposUsuario).lista();
    }
}
