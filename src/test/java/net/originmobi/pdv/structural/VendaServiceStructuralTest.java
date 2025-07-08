package net.originmobi.pdv.structural;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import net.originmobi.pdv.enumerado.VendaSituacao;
import net.originmobi.pdv.filter.VendaFilter;
import net.originmobi.pdv.model.Caixa;
import net.originmobi.pdv.model.PagamentoTipo;
import net.originmobi.pdv.model.Pessoa;
import net.originmobi.pdv.model.Titulo;
import net.originmobi.pdv.model.Usuario;
import net.originmobi.pdv.model.Venda;
import net.originmobi.pdv.model.VendaProduto;
import net.originmobi.pdv.repository.VendaRepository;
import net.originmobi.pdv.service.CaixaService;
import net.originmobi.pdv.service.CaixaLancamentoService;
import net.originmobi.pdv.service.PagamentoTipoService;
import net.originmobi.pdv.service.ParcelaService;
import net.originmobi.pdv.service.ProdutoService;
import net.originmobi.pdv.service.ReceberService;
import net.originmobi.pdv.service.UsuarioService;
import net.originmobi.pdv.service.VendaProdutoService;
import net.originmobi.pdv.service.VendaService;
import net.originmobi.pdv.service.cartao.CartaoLancamentoService;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class VendaServiceStructuralTest {

    @Mock
    private VendaRepository vendas;

    @Mock
    private UsuarioService usuarios;

    @Mock
    private VendaProdutoService vendaProdutos;

    @Mock
    private PagamentoTipoService formaPagamentos;

    @Mock
    private CaixaService caixas;

    @Mock
    private ReceberService receberServ;

    @Mock
    private ParcelaService parcelas;

    @Mock
    private CaixaLancamentoService lancamentos;

    @Mock
    private CartaoLancamentoService cartaoLancamento;

    @Mock
    private ProdutoService produtos;



    @InjectMocks
    private VendaService vendaService;

    private Venda venda;
    private List<Venda> listaVendas;
    private Usuario usuario;
    private Pessoa pessoa;
    private PagamentoTipo pagamentoTipo;
    private Titulo titulo;
    private Caixa caixa;

    @Before
    public void setUp() {
        usuario = new Usuario();
        usuario.setCodigo(1L);
        usuario.setUser("usuario_teste");
        pessoa = new Pessoa();
        pessoa.setCodigo(1L);
        pessoa.setNome("Cliente Teste");
        venda = new Venda();
        venda.setCodigo(1L);
        venda.setSituacao(VendaSituacao.ABERTA);
        venda.setUsuario(usuario);
        venda.setPessoa(pessoa);
        venda.setValor_produtos(100.0);
        listaVendas = Arrays.asList(venda);
        pagamentoTipo = new PagamentoTipo();
        pagamentoTipo.setCodigo(1L);
        pagamentoTipo.setFormaPagamento("00");
        titulo = new Titulo();
        titulo.setCodigo(1L);
        caixa = new Caixa();
        caixa.setCodigo(1L);
    }

    @Test
    public void testAbreVendaAtualizarVendaSucesso() {
        Venda vendaExistente = new Venda();
        vendaExistente.setCodigo(1L);
        vendaExistente.setPessoa(pessoa);
        vendaExistente.setObservacao("Observação teste");
        doNothing().when(vendas).updateDadosVenda(any(Pessoa.class), anyString(), anyLong());
        Long resultado = vendaService.abreVenda(vendaExistente);
        assertEquals(Long.valueOf(1L), resultado);
        verify(vendas).updateDadosVenda(any(Pessoa.class), anyString(), anyLong());
    }

    @Test
    public void testAbreVendaAtualizarVendaErro() {
        Venda vendaExistente = new Venda();
        vendaExistente.setCodigo(1L);
        vendaExistente.setPessoa(pessoa);
        vendaExistente.setObservacao("Observação teste");
        doThrow(new RuntimeException("Erro de banco")).when(vendas).updateDadosVenda(any(Pessoa.class), anyString(), anyLong());
        Long resultado = vendaService.abreVenda(vendaExistente);
        assertEquals(Long.valueOf(1L), resultado);
    }

    @Test
    public void testBuscaSituacaoAberta() {
        VendaFilter filter = new VendaFilter();
        String situacao = "ABERTA"; 
        Pageable pageable = PageRequest.of(0, 10);
        Page<Venda> page = new PageImpl<>(listaVendas);
        when(vendas.findBySituacaoEquals(VendaSituacao.ABERTA, pageable)).thenReturn(page);
        Page<Venda> resultado = vendaService.busca(filter, situacao, pageable);
        assertNotNull(resultado);
        assertEquals(1, resultado.getContent().size());
        verify(vendas).findBySituacaoEquals(VendaSituacao.ABERTA, pageable);
    }

    @Test
    public void testBuscaSituacaoFechada() {
        VendaFilter filter = new VendaFilter();
        String situacao = "FECHADA"; 
        Pageable pageable = PageRequest.of(0, 10);
        Page<Venda> page = new PageImpl<>(listaVendas);
        when(vendas.findBySituacaoEquals(VendaSituacao.FECHADA, pageable)).thenReturn(page);
        Page<Venda> resultado = vendaService.busca(filter, situacao, pageable);
        assertNotNull(resultado);
        assertEquals(1, resultado.getContent().size());
        verify(vendas).findBySituacaoEquals(VendaSituacao.FECHADA, pageable);
    }

    @Test
    public void testBuscaComCodigo() {
        VendaFilter filter = new VendaFilter();
        filter.setCodigo(1L); 
        String situacao = "ABERTA";
        Pageable pageable = PageRequest.of(0, 10);
        Page<Venda> page = new PageImpl<>(listaVendas);
        when(vendas.findByCodigoIn(1L, pageable)).thenReturn(page);
        Page<Venda> resultado = vendaService.busca(filter, situacao, pageable);
        assertNotNull(resultado);
        assertEquals(1, resultado.getContent().size());
        verify(vendas).findByCodigoIn(1L, pageable);
    }
    @Test
    public void testBuscaSemCodigo() {
        VendaFilter filter = new VendaFilter();
        filter.setCodigo(null); 
        String situacao = "ABERTA";
        Pageable pageable = PageRequest.of(0, 10);
        Page<Venda> page = new PageImpl<>(listaVendas);
        when(vendas.findBySituacaoEquals(VendaSituacao.ABERTA, pageable)).thenReturn(page);
        Page<Venda> resultado = vendaService.busca(filter, situacao, pageable);
        assertNotNull(resultado);
        assertEquals(1, resultado.getContent().size());
        verify(vendas).findBySituacaoEquals(VendaSituacao.ABERTA, pageable);
    }

    @Test
    public void testAddProdutoVendaAberta() {
        Long codVen = 1L;
        Long codPro = 1L;
        Double vlBalanca = 10.0;
        when(vendas.verificaSituacao(codVen)).thenReturn(VendaSituacao.ABERTA.toString()); 
        doNothing().when(vendaProdutos).salvar(any(VendaProduto.class));
        String resultado = vendaService.addProduto(codVen, codPro, vlBalanca);
        assertEquals("ok", resultado);
        verify(vendaProdutos).salvar(any(VendaProduto.class));
    }

    @Test
    public void testAddProdutoVendaFechada() {
        Long codVen = 1L;
        Long codPro = 1L;
        Double vlBalanca = 10.0;
        when(vendas.verificaSituacao(codVen)).thenReturn(VendaSituacao.FECHADA.toString()); 
        String resultado = vendaService.addProduto(codVen, codPro, vlBalanca);
        assertEquals("Venda fechada", resultado);
        verify(vendaProdutos, never()).salvar(any(VendaProduto.class));
    }

    @Test
    public void testAddProdutoErro() {
        Long codVen = 1L;
        Long codPro = 1L;
        Double vlBalanca = 10.0;
        when(vendas.verificaSituacao(codVen)).thenReturn(VendaSituacao.ABERTA.toString());
        doThrow(new RuntimeException("Erro de banco")).when(vendaProdutos).salvar(any(VendaProduto.class));
        String resultado = vendaService.addProduto(codVen, codPro, vlBalanca);
        assertEquals("ok", resultado);
    }

    @Test
    public void testRemoveProdutoVendaAberta() {
        Long posicaoProd = 1L;
        Long codVenda = 1L;
        when(vendas.findByCodigoEquals(codVenda)).thenReturn(venda);
        doNothing().when(vendaProdutos).removeProduto(posicaoProd);
        String resultado = vendaService.removeProduto(posicaoProd, codVenda);
        assertEquals("ok", resultado);
        verify(vendaProdutos).removeProduto(posicaoProd);
    }

    @Test
    public void testRemoveProdutoVendaFechada() {
        Long posicaoProd = 1L;
        Long codVenda = 1L;
        
        Venda vendaFechada = new Venda();
        vendaFechada.setSituacao(VendaSituacao.FECHADA);
        
        when(vendas.findByCodigoEquals(codVenda)).thenReturn(vendaFechada);
        String resultado = vendaService.removeProduto(posicaoProd, codVenda);
        assertEquals("Venda fechada", resultado);
        verify(vendaProdutos, never()).removeProduto(anyLong());
    }

    @Test
    public void testRemoveProdutoErro() {
        Long posicaoProd = 1L;
        Long codVenda = 1L;
        when(vendas.findByCodigoEquals(codVenda)).thenReturn(venda);
        doThrow(new RuntimeException("Erro de banco")).when(vendaProdutos).removeProduto(posicaoProd);
        String resultado = vendaService.removeProduto(posicaoProd, codVenda);
        assertEquals("ok", resultado);
    }

    @Test
    public void testLista() {
        when(vendas.findAll()).thenReturn(listaVendas);
        List<Venda> resultado = vendaService.lista();
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        verify(vendas).findAll();
    }

    @Test
    public void testQtdAbertos() {
        when(vendas.qtdVendasEmAberto()).thenReturn(5);
        int resultado = vendaService.qtdAbertos();
        assertEquals(5, resultado);
        verify(vendas).qtdVendasEmAberto();
    }
} 