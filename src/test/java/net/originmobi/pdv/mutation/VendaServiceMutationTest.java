package net.originmobi.pdv.mutation;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import net.originmobi.pdv.controller.TituloService;
import net.originmobi.pdv.enumerado.VendaSituacao;
import net.originmobi.pdv.filter.VendaFilter;
import net.originmobi.pdv.model.Pessoa;
import net.originmobi.pdv.model.Usuario;
import net.originmobi.pdv.model.Venda;
import net.originmobi.pdv.model.VendaProduto;
import net.originmobi.pdv.repository.VendaRepository;
import net.originmobi.pdv.service.CaixaLancamentoService;
import net.originmobi.pdv.service.CaixaService;
import net.originmobi.pdv.service.PagamentoTipoService;
import net.originmobi.pdv.service.ParcelaService;
import net.originmobi.pdv.service.ProdutoService;
import net.originmobi.pdv.service.ReceberService;
import net.originmobi.pdv.service.UsuarioService;
import net.originmobi.pdv.service.VendaProdutoService;
import net.originmobi.pdv.service.VendaService;
import net.originmobi.pdv.service.cartao.CartaoLancamentoService;

@RunWith(MockitoJUnitRunner.class)
public class VendaServiceMutationTest {
    @Mock private VendaRepository vendas;
    @Mock private UsuarioService usuarios;
    @Mock private VendaProdutoService vendaProdutos;
    @Mock private PagamentoTipoService formaPagamentos;
    @Mock private CaixaService caixas;
    @Mock private ReceberService receberServ;
    @Mock private ParcelaService parcelas;
    @Mock private CaixaLancamentoService lancamentos;
    @Mock private TituloService tituloService;
    @Mock private CartaoLancamentoService cartaoLancamento;
    @Mock private ProdutoService produtos;

    @InjectMocks private VendaService vendaService;

    private Venda venda;
    private List<Venda> listaVendas;

    @Before
    public void setUp() {
        venda = new Venda();
        venda.setCodigo(1L);
        venda.setSituacao(VendaSituacao.ABERTA);
        venda.setValor_produtos(100.0);
        venda.setPessoa(new Pessoa());
        venda.setUsuario(new Usuario());
        listaVendas = new ArrayList<>();
        listaVendas.add(venda);
    }

    @Test
    public void testAbreVendaMutationAtualizacao() {
        Venda vendaExistente = new Venda();
        vendaExistente.setCodigo(1L);
        vendaExistente.setPessoa(new Pessoa());
        vendaExistente.setObservacao("Teste");
        doNothing().when(vendas).updateDadosVenda(any(Pessoa.class), anyString(), anyLong());
        Long codigo = vendaService.abreVenda(vendaExistente);
        assertEquals(Long.valueOf(1L), codigo);
        verify(vendas).updateDadosVenda(eq(vendaExistente.getPessoa()), eq("Teste"), eq(1L));
    }

    @Test
    public void testBuscaMutationFiltroCodigoNulo() {
        VendaFilter filter = new VendaFilter();
        filter.setCodigo(null);
        Pageable pageable = PageRequest.of(0, 10);
        Page<Venda> page = new PageImpl<>(listaVendas);
        when(vendas.findBySituacaoEquals(any(VendaSituacao.class), eq(pageable))).thenReturn(page);
        Page<Venda> resultado = vendaService.busca(filter, "ABERTA", pageable);
        assertNotNull(resultado);
        assertEquals(1, resultado.getContent().size());
        verify(vendas).findBySituacaoEquals(eq(VendaSituacao.ABERTA), eq(pageable));
    }

    @Test
    public void testAddProdutoMutationVendaFechada() {
        when(vendas.verificaSituacao(1L)).thenReturn(VendaSituacao.FECHADA.toString());
        String resultado = vendaService.addProduto(1L, 1L, 10.0);
        assertEquals("Venda fechada", resultado);
    }

    @Test
    public void testRemoveProdutoMutationVendaFechada() {
        Venda vendaFechada = new Venda();
        vendaFechada.setCodigo(1L);
        vendaFechada.setSituacao(VendaSituacao.FECHADA);
        when(vendas.findByCodigoEquals(1L)).thenReturn(vendaFechada);
        String resultado = vendaService.removeProduto(1L, 1L);
        assertEquals("Venda fechada", resultado);
    }

    @Test
    public void testListaMutationRetorno() {
        when(vendas.findAll()).thenReturn(listaVendas);
        List<Venda> resultado = vendaService.lista();
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(venda, resultado.get(0));
        verify(vendas).findAll();
    }

    @Test(expected = RuntimeException.class)
    public void testFechaVendaMutationValorZero() {
        Venda vendaAberta = new Venda();
        vendaAberta.setCodigo(1L);
        vendaAberta.setSituacao(VendaSituacao.ABERTA);
        when(vendas.findByCodigoEquals(1L)).thenReturn(vendaAberta);
        vendaService.fechaVenda(1L, 1L, 0.0, 0.0, 0.0, new String[]{"0.0"}, new String[]{"1"});
    }

    @Test(expected = RuntimeException.class)
    public void testFechaVendaMutationValorNegativo() {
        Venda vendaAberta = new Venda();
        vendaAberta.setCodigo(1L);
        vendaAberta.setSituacao(VendaSituacao.ABERTA);
        when(vendas.findByCodigoEquals(1L)).thenReturn(vendaAberta);
        vendaService.fechaVenda(1L, 1L, -10.0, 0.0, 0.0, new String[]{"-10.0"}, new String[]{"1"});
    }

    @Test
    public void testQtdAbertosMutationRetorno() {
        when(vendas.qtdVendasEmAberto()).thenReturn(2);
        int qtd = vendaService.qtdAbertos();
        assertEquals(2, qtd);
        verify(vendas).qtdVendasEmAberto();
    }

    @Test
    public void testBuscaMutationFiltroCodigoNaoNulo() {
        VendaFilter filter = new VendaFilter();
        filter.setCodigo(1L);
        Pageable pageable = PageRequest.of(0, 10);
        Page<Venda> page = new PageImpl<>(listaVendas);
        when(vendas.findByCodigoIn(eq(1L), eq(pageable))).thenReturn(page);
        Page<Venda> resultado = vendaService.busca(filter, "ABERTA", pageable);
        assertNotNull(resultado);
        assertEquals(1, resultado.getContent().size());
        verify(vendas).findByCodigoIn(eq(1L), eq(pageable));
    }

    @Test
    public void testBuscaMutationSituacaoFechada() {
        VendaFilter filter = new VendaFilter();
        filter.setCodigo(null);
        Pageable pageable = PageRequest.of(0, 10);
        Page<Venda> page = new PageImpl<>(listaVendas);
        when(vendas.findBySituacaoEquals(eq(VendaSituacao.FECHADA), eq(pageable))).thenReturn(page);
        Page<Venda> resultado = vendaService.busca(filter, "FECHADA", pageable);
        assertNotNull(resultado);
        assertEquals(1, resultado.getContent().size());
        verify(vendas).findBySituacaoEquals(eq(VendaSituacao.FECHADA), eq(pageable));
    }

    @Test
    public void testAddProdutoMutationRetornoOk() {
        when(vendas.verificaSituacao(1L)).thenReturn(VendaSituacao.ABERTA.toString());
        doNothing().when(vendaProdutos).salvar(any(VendaProduto.class));
        String resultado = vendaService.addProduto(1L, 1L, 10.0);
        assertEquals("ok", resultado);
        verify(vendaProdutos).salvar(any(VendaProduto.class));
    }

    @Test
    public void testRemoveProdutoMutationRetornoOk() {
        Venda vendaAberta = new Venda();
        vendaAberta.setCodigo(1L);
        vendaAberta.setSituacao(VendaSituacao.ABERTA);
        when(vendas.findByCodigoEquals(1L)).thenReturn(vendaAberta);
        doNothing().when(vendaProdutos).removeProduto(1L);
        String resultado = vendaService.removeProduto(1L, 1L);
        assertEquals("ok", resultado);
        verify(vendaProdutos).removeProduto(1L);
    }
} 