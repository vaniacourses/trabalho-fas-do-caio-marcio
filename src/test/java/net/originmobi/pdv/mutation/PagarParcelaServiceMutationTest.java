package net.originmobi.pdv.mutation;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Optional;

import net.originmobi.pdv.filter.PagarParcelaFilter;
import net.originmobi.pdv.model.Pagar;
import net.originmobi.pdv.model.PagarParcela;
import net.originmobi.pdv.repository.PagarParcelaRespository;
import net.originmobi.pdv.service.PagarParcelaService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.Collections;

@RunWith(MockitoJUnitRunner.class)
public class PagarParcelaServiceMutationTest {

    @Mock
    private PagarParcelaRespository parcelas;

    @InjectMocks
    private PagarParcelaService service;

    private PagarParcela parcela;

    @Before
    public void setUp() {
        parcela = new PagarParcela();
        parcela.setCodigo(1L);
    }

    @Test
    public void testCadastrarDeveChamarRepository() {
        doNothing().when(parcelas).geraParcela(anyDouble(), anyDouble(), anyDouble(), anyDouble(), anyDouble(), anyInt(), any(), any(), any());
        service.cadastrar(100.0, 50.0, 0, Timestamp.valueOf("2023-01-01 00:00:00"), LocalDate.now(), new Pagar());
        verify(parcelas).geraParcela(anyDouble(), anyDouble(), anyDouble(), anyDouble(), anyDouble(), anyInt(), any(), any(), any());
    }

    @Test(expected = RuntimeException.class)
    public void testCadastrarLancaExcecao() {
        doThrow(new RuntimeException("Erro simulado")).when(parcelas).geraParcela(anyDouble(), anyDouble(), anyDouble(), anyDouble(), anyDouble(), anyInt(), any(), any(), any());
        service.cadastrar(100.0, 50.0, 0, Timestamp.valueOf("2023-01-01 00:00:00"), LocalDate.now(), new Pagar());
    }

    @Test
    public void testMergerDeveSalvarParcela() {
        when(parcelas.save(any())).thenReturn(parcela);
        PagarParcela result = service.merger(parcela);
        assertNotNull(result);
        assertEquals(parcela.getCodigo(), result.getCodigo());
        verify(parcelas).save(eq(parcela));
    }

    @Test
    public void testListaSemNomeDeveListarTudo() {
        PageRequest pageable = PageRequest.of(0, 10);
        when(parcelas.listaOrdenada(eq(pageable))).thenReturn(new PageImpl<>(Collections.singletonList(parcela)));

        PagarParcelaFilter filter = new PagarParcelaFilter();
        filter.setNome(null); // ou filter.setNome("");

        Page<PagarParcela> result = service.lista(filter, pageable);

        assertEquals(1, result.getContent().size());
        verify(parcelas).listaOrdenada(eq(pageable));
    }

    @Test
    public void testListaComNomeDeveFiltrar() {
        PageRequest pageable = PageRequest.of(0, 10);
        when(parcelas.listaOrdenada(eq("exemplo"), eq(pageable))).thenReturn(new PageImpl<>(Collections.singletonList(parcela)));

        PagarParcelaFilter filter = new PagarParcelaFilter();
        filter.setNome("exemplo");

        Page<PagarParcela> result = service.lista(filter, pageable);

        assertEquals(1, result.getContent().size());
        verify(parcelas).listaOrdenada(eq("exemplo"), eq(pageable));
    }

    @Test
    public void testBuscaPorCodigo() {
        when(parcelas.findById(1L)).thenReturn(Optional.of(parcela));
        Optional<PagarParcela> result = service.busca(1L);
        assertTrue(result.isPresent());
        assertEquals(parcela.getCodigo(), result.get().getCodigo());
        verify(parcelas).findById(1L);
    }

    @Test
    public void testTotalParagarAberto() {
        when(parcelas.valorDespesasAbertas()).thenReturn("1000.00");
        String result = service.totalParagarAberto();
        assertEquals("1000.00", result);
        verify(parcelas).valorDespesasAbertas();
    }
}
