package net.originmobi.pdv.structural;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
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
public class AjusteServiceStructuralTest {

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
        ajuste.setStatus(AjusteStatus.APROCESSAR);
        ajuste.setUsuario("usuario_teste"); // Usando String, como descoberto
        ajuste.setData_cadastro(Date.valueOf(LocalDate.now()));
        ajuste.setCodigo(10L);

        Produto produtoExemplo = mock(Produto.class);
        when(produtoExemplo.getCodigo()).thenReturn(100L);

        AjusteProduto ajusteProdutoMock = mock(AjusteProduto.class);

        when(ajusteProdutoMock.getProduto()).thenReturn(produtoExemplo);
        when(ajusteProdutoMock.getQtd_alteracao()).thenReturn(5);

        List<AjusteProduto> listaProdutos = new ArrayList<>();
        listaProdutos.add(ajusteProdutoMock);

        ajuste.setProdutos(listaProdutos);
    }

    @Test
    public void busca_deveRetornarAjuste_quandoEncontrado() {
        when(ajustes.findById(10L)).thenReturn(Optional.of(ajuste));
        Optional<Ajuste> resultado = service.busca(10L);
        assertTrue(resultado.isPresent());
        assertEquals(ajuste.getCodigo(), resultado.get().getCodigo());
        verify(ajustes).findById(10L);
    }

    @Test
    public void remover_deveChamarDeleteById_quandoAjusteNaoProcessado() {
        ajuste.setStatus(AjusteStatus.APROCESSAR);
        doNothing().when(ajustes).deleteById(ajuste.getCodigo());

        service.remover(ajuste);

        verify(ajustes, times(1)).deleteById(ajuste.getCodigo());
    }

    @Test(expected = RuntimeException.class)
    public void remover_deveLancarExcecao_quandoAjusteProcessado() {
        ajuste.setStatus(AjusteStatus.PROCESSADO);
        try {
            service.remover(ajuste);
        } catch (RuntimeException e) {
            assertEquals("O ajuste já esta processado", e.getMessage());
            verify(ajustes, never()).deleteById(anyLong());
            throw e;
        }
    }

    @Test
    public void processar_deveFuncionarCorretamente_emCasoDeSucesso() {
        when(ajustes.findById(10L)).thenReturn(Optional.of(ajuste));
        doNothing().when(produtos).ajusteEstoque(anyLong(), anyInt(), any(EntradaSaida.class), anyString(),
                any(Date.class));
        when(ajustes.save(any(Ajuste.class))).thenReturn(ajuste);

        String resultado = service.processar(10L, "Observacao Teste");

        assertEquals("Ajuste realizado com sucesso", resultado);

        // verifica se o método foi chamado com os valores definido no mock
        verify(produtos, times(1)).ajusteEstoque(eq(100L), eq(5), eq(EntradaSaida.ENTRADA), anyString(),
                any(Date.class));

        ArgumentCaptor<Ajuste> ajusteCaptor = ArgumentCaptor.forClass(Ajuste.class);
        verify(ajustes).save(ajusteCaptor.capture());
        assertEquals(AjusteStatus.PROCESSADO, ajusteCaptor.getValue().getStatus());
        assertEquals("Observacao Teste", ajusteCaptor.getValue().getObservacao());
    }

    @Test(expected = RuntimeException.class)
    public void processar_deveLancarExcecao_seAjusteJaProcessado() {
        ajuste.setStatus(AjusteStatus.PROCESSADO);
        when(ajustes.findById(10L)).thenReturn(Optional.of(ajuste));

        try {
            service.processar(10L, "Obs");
        } catch (RuntimeException e) {
            assertEquals("Ajuste já processado", e.getMessage());
            verify(produtos, never()).ajusteEstoque(anyLong(), anyInt(), any(EntradaSaida.class), anyString(),
                    any(Date.class));
            throw e;
        }
    }

    @Test(expected = RuntimeException.class)
    public void processar_deveLancarExcecao_seProdutoServiceFalhar() {
        when(ajustes.findById(10L)).thenReturn(Optional.of(ajuste));
        doThrow(new RuntimeException("Erro de estoque")).when(produtos).ajusteEstoque(anyLong(), anyInt(),
                any(EntradaSaida.class), anyString(), any(Date.class));

        try {
            service.processar(10L, "Obs");
        } catch (RuntimeException e) {
            assertEquals("Erro ao tentar processar o ajuste, chame o suporte", e.getMessage());
            verify(ajustes, never()).save(any(Ajuste.class));
            throw e;
        }
    }

    @Test(expected = RuntimeException.class)
    public void processar_deveLancarExcecao_seFalharAoSalvarFinal() {
        when(ajustes.findById(10L)).thenReturn(Optional.of(ajuste));
        doNothing().when(produtos).ajusteEstoque(anyLong(), anyInt(), any(EntradaSaida.class), anyString(),
                any(Date.class));

        when(ajustes.save(any(Ajuste.class))).thenThrow(new RuntimeException("Erro de banco de dados"));

        try {
            service.processar(10L, "Obs");
        } catch (RuntimeException e) {
            assertEquals("Erro ao tentar processar o ajuste, chame o suporte", e.getMessage());
            verify(ajustes, times(1)).save(any(Ajuste.class));
            throw e;
        }
    }

    @Test
    public void lista_deveChamarMetodoComFiltro_quandoFiltroCodigoInformado() {
        AjusteFilter filter = new AjusteFilter();
        filter.setCodigo(123L);
        Pageable pageable = Pageable.unpaged();

        when(ajustes.lista(123L, pageable)).thenReturn(Page.empty());

        service.lista(pageable, filter);

        verify(ajustes).lista(123L, pageable);
        verify(ajustes, never()).lista(pageable);
    }

    @Test
    public void lista_deveChamarMetodoSemFiltro_quandoFiltroCodigoNulo() {
        AjusteFilter filter = new AjusteFilter();
        Pageable pageable = Pageable.unpaged();

        when(ajustes.lista(pageable)).thenReturn(Page.empty());

        service.lista(pageable, filter);

        verify(ajustes).lista(pageable);
        verify(ajustes, never()).lista(anyLong(), any(Pageable.class));
    }
}