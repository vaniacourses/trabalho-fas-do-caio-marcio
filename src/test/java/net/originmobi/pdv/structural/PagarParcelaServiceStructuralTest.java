package net.originmobi.pdv.structural;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Optional;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.*;

import net.originmobi.pdv.filter.PagarParcelaFilter;
import net.originmobi.pdv.model.Pagar;
import net.originmobi.pdv.model.PagarParcela;
import net.originmobi.pdv.repository.PagarParcelaRespository;
import net.originmobi.pdv.service.PagarParcelaService;

@RunWith(MockitoJUnitRunner.class)
public class PagarParcelaServiceStructuralTest {

    @Mock
    private PagarParcelaRespository parcelas;

    @InjectMocks
    private PagarParcelaService service;

    private Double vltotal;
    private Double vlrestante;
    private int quitado;
    private Timestamp cadastro;
    private LocalDate vencimento;
    private Pagar pagar;

    @Before
    public void setUp() {
        vltotal = 100.0;
        vlrestante = 50.0;
        quitado = 0;
        cadastro = new Timestamp(System.currentTimeMillis());
        vencimento = LocalDate.now().plusDays(10);
        pagar = mock(Pagar.class);
    }

    // ---- cadastrar() ----

    @Test
    public void cadastrar_deveChamarGeraParcela_comSucesso() {
        service.cadastrar(vltotal, vlrestante, quitado, cadastro, vencimento, pagar);

        verify(parcelas, times(1)).geraParcela(
                eq(vltotal), eq(vlrestante), eq(0.0), eq(0.0), eq(0.0),
                eq(quitado), eq(cadastro), eq(vencimento), eq(pagar));
    }

    @Test
    public void cadastrar_deveLancarRuntimeException_quandoGeraParcelaLancarExcecao() {
        doThrow(new RuntimeException("Erro ao gerar parcela")).when(parcelas).geraParcela(
                anyDouble(), anyDouble(), anyDouble(), anyDouble(), anyDouble(),
                anyInt(), any(Timestamp.class), any(LocalDate.class), any(Pagar.class));

        try {
            service.cadastrar(vltotal, vlrestante, quitado, cadastro, vencimento, pagar);
            fail("Esperava RuntimeException");
        } catch (RuntimeException e) {
            assertEquals(RuntimeException.class, e.getClass());
        }

        verify(parcelas).geraParcela(
                eq(vltotal), eq(vlrestante), eq(0.0), eq(0.0), eq(0.0),
                eq(quitado), eq(cadastro), eq(vencimento), eq(pagar));
    }

    // ---- merger() ----

    @Test
    public void merger_deveRetornarParcelaSalva() {
        PagarParcela parcela = new PagarParcela();
        parcela.setCodigo(1L);

        when(parcelas.save(parcela)).thenReturn(parcela);

        PagarParcela resultado = service.merger(parcela);

        assertNotNull(resultado);
        assertEquals(Long.valueOf(1L), resultado.getCodigo());
        verify(parcelas).save(parcela);
    }

    // ---- lista() ----

    @Test
    public void lista_deveChamarListaOrdenada_quandoFiltroNomeNulo() {
        PagarParcelaFilter filter = new PagarParcelaFilter(); // nome == null
        Pageable pageable = PageRequest.of(0, 10);
        Page<PagarParcela> page = new PageImpl<>(Arrays.asList());

        when(parcelas.listaOrdenada(pageable)).thenReturn(page);

        Page<PagarParcela> resultado = service.lista(filter, pageable);

        assertNotNull(resultado);
        verify(parcelas).listaOrdenada(pageable);
    }

    @Test
    public void lista_deveChamarListaOrdenadaComFiltro_quandoNomeInformado() {
        PagarParcelaFilter filter = new PagarParcelaFilter();
        filter.setNome("fornecedor");
        Pageable pageable = PageRequest.of(0, 10);
        Page<PagarParcela> page = new PageImpl<>(Arrays.asList());

        when(parcelas.listaOrdenada("fornecedor", pageable)).thenReturn(page);

        Page<PagarParcela> resultado = service.lista(filter, pageable);

        assertNotNull(resultado);
        verify(parcelas).listaOrdenada("fornecedor", pageable);
    }

    // ---- busca() ----

    @Test
    public void busca_deveRetornarParcela_quandoEncontrado() {
        Long codigo = 1L;
        PagarParcela parcela = new PagarParcela();
        parcela.setCodigo(codigo);

        when(parcelas.findById(codigo)).thenReturn(Optional.of(parcela));

        Optional<PagarParcela> resultado = service.busca(codigo);

        assertTrue(resultado.isPresent());
        assertEquals(codigo, resultado.get().getCodigo());
        verify(parcelas).findById(codigo);
    }

    @Test
    public void busca_deveRetornarOptionalVazio_quandoNaoEncontrado() {
        Long codigo = 1L;
        when(parcelas.findById(codigo)).thenReturn(Optional.empty());

        Optional<PagarParcela> resultado = service.busca(codigo);

        assertFalse(resultado.isPresent());
        verify(parcelas).findById(codigo);
    }

    // ---- totalParagarAberto() ----

    @Test
    public void totalParagarAberto_deveRetornarValorEsperado() {
        when(parcelas.valorDespesasAbertas()).thenReturn("150.00");

        String resultado = service.totalParagarAberto();

        assertEquals("150.00", resultado);
        verify(parcelas).valorDespesasAbertas();
    }
}
