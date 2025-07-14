package net.originmobi.pdv.mutation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import net.originmobi.pdv.enumerado.EntradaSaida;
import net.originmobi.pdv.enumerado.ajuste.AjusteStatus;
import net.originmobi.pdv.filter.AjusteFilter;
import net.originmobi.pdv.model.Ajuste;
import net.originmobi.pdv.model.AjusteProduto;
import net.originmobi.pdv.model.Produto;
import net.originmobi.pdv.repository.AjusteRepository;
import net.originmobi.pdv.service.AjusteService;
import net.originmobi.pdv.service.ProdutoService;

@RunWith(MockitoJUnitRunner.class)
public class AjusteServiceMutationTest {

    @Mock
    private AjusteRepository ajustes;

    @Mock
    private ProdutoService produtos;

    @InjectMocks
    private AjusteService service;

    private Ajuste ajuste;

    @Before
    public void setUp() {
        ajuste = new Ajuste();
        ajuste.setCodigo(10L);
        ajuste.setStatus(AjusteStatus.APROCESSAR);

        Produto produtoMock1 = mock(Produto.class);
        when(produtoMock1.getCodigo()).thenReturn(100L);
        AjusteProduto ajusteProdutoMock1 = mock(AjusteProduto.class);
        when(ajusteProdutoMock1.getProduto()).thenReturn(produtoMock1);
        when(ajusteProdutoMock1.getQtd_alteracao()).thenReturn(5);

        Produto produtoMock2 = mock(Produto.class);
        when(produtoMock2.getCodigo()).thenReturn(200L);
        AjusteProduto ajusteProdutoMock2 = mock(AjusteProduto.class);
        when(ajusteProdutoMock2.getProduto()).thenReturn(produtoMock2);
        when(ajusteProdutoMock2.getQtd_alteracao()).thenReturn(-2);

        List<AjusteProduto> listaProdutos = new ArrayList<>();
        listaProdutos.add(ajusteProdutoMock1);
        listaProdutos.add(ajusteProdutoMock2);

        ajuste.setProdutos(listaProdutos);
    }

    @Test
    public void testBuscaExistente() {
        when(ajustes.findById(10L)).thenReturn(Optional.of(ajuste));
        Optional<Ajuste> resultado = service.busca(10L);
        assertTrue(resultado.isPresent());
        verify(ajustes).findById(10L);
    }

    @Test
    public void testBuscaInexistente() {
        when(ajustes.findById(anyLong())).thenReturn(Optional.empty());
        Optional<Ajuste> resultado = service.busca(99L);
        assertFalse(resultado.isPresent());
    }

    @Test
    public void testRemoverAjusteValido() {
        ajuste.setStatus(AjusteStatus.APROCESSAR);
        doNothing().when(ajustes).deleteById(10L);
        service.remover(ajuste);
        verify(ajustes).deleteById(10L);
    }

    @Test
    public void testRemoverAjusteProcessadoDeveLancarExcecao() {
        ajuste.setStatus(AjusteStatus.PROCESSADO);
        try {
            service.remover(ajuste);
            fail("Deveria ter lançado uma RuntimeException");
        } catch (RuntimeException e) {
            assertEquals("O ajuste já esta processado", e.getMessage());
        }
        verify(ajustes, never()).deleteById(anyLong());
    }

    @Test
    public void testProcessarComSucesso() {
        when(ajustes.findById(10L)).thenReturn(Optional.of(ajuste));
        String resultado = service.processar(10L, "Teste de Mutacao");
        assertEquals("Ajuste realizado com sucesso", resultado);

        ArgumentCaptor<Ajuste> ajusteCaptor = ArgumentCaptor.forClass(Ajuste.class);
        verify(ajustes).save(ajusteCaptor.capture());
        assertEquals(AjusteStatus.PROCESSADO, ajusteCaptor.getValue().getStatus());
        assertEquals("Teste de Mutacao", ajusteCaptor.getValue().getObservacao());
        assertNotNull(ajusteCaptor.getValue().getData_processamento());

        verify(produtos, times(2)).ajusteEstoque(anyLong(), anyInt(), any(EntradaSaida.class), anyString(),
                any(Date.class));
    }

    @Test
    public void testProcessarAjusteJaProcessado() {
        ajuste.setStatus(AjusteStatus.PROCESSADO);
        when(ajustes.findById(10L)).thenReturn(Optional.of(ajuste));

        try {
            service.processar(10L, "Teste");
            fail("Deveria ter lançado uma RuntimeException");
        } catch (RuntimeException e) {
            assertEquals("Ajuste já processado", e.getMessage());
        }

        verify(ajustes, never()).save(any(Ajuste.class));
    }

    @Test
    public void testProcessarComFalhaNoAjusteEstoque() {
        when(ajustes.findById(10L)).thenReturn(Optional.of(ajuste));
        doThrow(new RuntimeException("Falha no estoque")).when(produtos).ajusteEstoque(anyLong(), anyInt(),
                any(EntradaSaida.class), anyString(), any(Date.class));

        try {
            service.processar(10L, "Teste");
            fail("Deveria ter lançado uma RuntimeException");
        } catch (RuntimeException e) {
            assertEquals("Erro ao tentar processar o ajuste, chame o suporte", e.getMessage());
        }
    }

    @Test
    public void testListaComFiltro() {
        Pageable pageable = Pageable.unpaged();
        AjusteFilter filter = new AjusteFilter();
        filter.setCodigo(1L);

        when(ajustes.lista(1L, pageable)).thenReturn(new PageImpl<>(Collections.singletonList(ajuste)));

        Page<Ajuste> resultado = service.lista(pageable, filter);

        assertNotNull(resultado);
        assertTrue(resultado.hasContent());

        verify(ajustes, times(1)).lista(1L, pageable);
        verify(ajustes, never()).lista(pageable);
    }

    @Test
    public void testListaSemFiltro() {
        Pageable pageable = Pageable.unpaged();
        AjusteFilter filter = new AjusteFilter();

        when(ajustes.lista(pageable)).thenReturn(new PageImpl<>(Collections.singletonList(ajuste)));

        Page<Ajuste> resultado = service.lista(pageable, filter);

        assertNotNull(resultado);
        assertTrue(resultado.hasContent());

        verify(ajustes, times(1)).lista(pageable);
        verify(ajustes, never()).lista(anyLong(), any(Pageable.class));
    }

    @Test
    public void testRemoverComFalhaNoRepositorio() {
        ajuste.setStatus(AjusteStatus.APROCESSAR);
        doThrow(new RuntimeException("Falha de DB")).when(ajustes).deleteById(10L);

        try {
            service.remover(ajuste);
            fail("Deveria ter lançado uma RuntimeException");
        } catch (RuntimeException e) {
            assertEquals("Erro ao tentar cancelar o ajuste", e.getMessage());
        }
        verify(ajustes, times(1)).deleteById(10L);
    }
}