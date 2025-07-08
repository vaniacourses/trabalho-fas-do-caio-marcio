package net.originmobi.pdv.structural;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import net.originmobi.pdv.enumerado.EntradaSaida;
import net.originmobi.pdv.enumerado.produto.ProdutoControleEstoque;
import net.originmobi.pdv.enumerado.produto.ProdutoSubstTributaria;
import net.originmobi.pdv.filter.ProdutoFilter;
import net.originmobi.pdv.model.Produto;
import net.originmobi.pdv.repository.ProdutoRepository;
import net.originmobi.pdv.service.ProdutoService;
import net.originmobi.pdv.service.VendaProdutoService;

@RunWith(MockitoJUnitRunner.class)
public class ProdutoServiceStructuralTest {

    @Mock
    private ProdutoRepository produtos;

    @Mock
    private VendaProdutoService vendaProdutos;

    @InjectMocks
    private ProdutoService produtoService;

    private Produto produto;
    private List<Produto> listaProdutos;

    @Before
    public void setUp() {
        produto = new Produto();
        produto.setCodigo(1L);
        produto.setDescricao("Produto Teste");
        produto.setControla_estoque(ProdutoControleEstoque.SIM);

        listaProdutos = Arrays.asList(produto);
    }

    @Test
    public void testMergerInserirProdutoSucesso() {

        Long codprod = 0L;
        doNothing().when(produtos).insere(anyLong(), anyLong(), anyLong(), anyInt(), 
                                         anyString(), anyDouble(), anyDouble(), any(), 
                                         anyString(), anyString(), anyString(), anyInt(), 
                                         any(), anyString(), anyString(), anyLong(), 
                                         anyLong(), anyString());

        String resultado = produtoService.merger(codprod, 1L, 1L, 1L, 0, "Produto Teste",
                                                10.0, 15.0, new java.util.Date(), "SIM", "ATIVO",
                                                "UN", ProdutoSubstTributaria.NAO, "12345678", 
                                                "1234567", 1L, 1L, "SIM");

        assertEquals("Produdo cadastrado com sucesso", resultado);
        verify(produtos).insere(anyLong(), anyLong(), anyLong(), anyInt(), 
                               anyString(), anyDouble(), anyDouble(), any(), 
                               anyString(), anyString(), anyString(), anyInt(), 
                               any(), anyString(), anyString(), anyLong(), 
                               anyLong(), anyString());
        
    }

    @Test
    public void testMergerAtualizarProdutoSucesso() {
        
        Long codprod = 1L;
        doNothing().when(produtos).atualiza(anyLong(), anyLong(), anyLong(), anyLong(), anyInt(), 
                                           anyString(), anyDouble(), anyDouble(), any(), 
                                           anyString(), anyString(), anyString(), anyInt(), 
                                           anyString(), anyString(), anyLong(), anyLong(), anyString());

        String resultado = produtoService.merger(codprod, 1L, 1L, 1L, 0, "Produto Atualizado",
                                                10.0, 15.0, new java.util.Date(), "SIM", "ATIVO",
                                                "UN", ProdutoSubstTributaria.NAO, "12345678", 
                                                "1234567", 1L, 1L, "SIM");

        assertEquals("Produto atualizado com sucesso", resultado);
        verify(produtos).atualiza(anyLong(), anyLong(), anyLong(), anyLong(), anyInt(), 
                                 anyString(), anyDouble(), anyDouble(), any(), 
                                 anyString(), anyString(), anyString(), anyInt(), 
                                 anyString(), anyString(), anyLong(), anyLong(), anyString());
        
    }

    @Test
    public void testMergerInserirProdutoErro() {

        Long codprod = 0L;
        doThrow(new RuntimeException("Erro de banco")).when(produtos).insere(anyLong(), anyLong(), 
                                                                             anyLong(), anyInt(), anyString(), 
                                                                             anyDouble(), anyDouble(), any(), 
                                                                             anyString(), anyString(), anyString(), 
                                                                             anyInt(), any(), anyString(), 
                                                                             anyString(), anyLong(), anyLong(), 
                                                                             anyString());

        String resultado = produtoService.merger(codprod, 1L, 1L, 1L, 0, "Produto Teste",
                                                10.0, 15.0, new java.util.Date(), "SIM", "ATIVO",
                                                "UN", ProdutoSubstTributaria.NAO, "12345678", 
                                                "1234567", 1L, 1L, "SIM");

        assertEquals("Erro a cadastrar produto, chame o suporte", resultado);
    }

    @Test
    public void testMergerAtualizarProdutoErro() {
        
        Long codprod = 1L;
        doThrow(new RuntimeException("Erro de banco")).when(produtos).atualiza(anyLong(), anyLong(), 
                                                                               anyLong(), anyLong(), anyInt(), 
                                                                               anyString(), anyDouble(), anyDouble(), 
                                                                               any(), anyString(), anyString(), 
                                                                               anyString(), anyInt(), anyString(), 
                                                                               anyString(), anyLong(), anyLong(), 
                                                                               anyString());

        String resultado = produtoService.merger(codprod, 1L, 1L, 1L, 0, "Produto Atualizado",
                                                10.0, 15.0, new java.util.Date(), "SIM", "ATIVO",
                                                "UN", ProdutoSubstTributaria.NAO, "12345678", 
                                                "1234567", 1L, 1L, "SIM");

        assertEquals("Erro a atualizar produto, chame o suporte", resultado);
    }

    @Test
    public void testMovimentaEstoqueComEstoqueSuficiente() {
        
        Long codvenda = 1L;
        List<Object[]> resultado = new ArrayList<>();
        resultado.add(new Object[]{"1", "5"}); // codprod, qtd
        
        when(vendaProdutos.buscaQtdProduto(codvenda)).thenReturn(resultado);
        when(produtos.findByCodigoIn(1L)).thenReturn(produto);
        when(produtos.saldoEstoque(1L)).thenReturn(10); // Estoque suficiente
        doNothing().when(produtos).movimentaEstoque(anyLong(), anyString(), anyInt(), anyString(), any());

        produtoService.movimentaEstoque(codvenda, EntradaSaida.SAIDA);

        verify(produtos).movimentaEstoque(eq(1L), eq("SAIDA"), eq(5), eq("Venda 1"), any());
    }

    @Test
    public void testMovimentaEstoqueProdutoNaoControlaEstoque() {
        
        Long codvenda = 1L;
        Produto produtoSemEstoque = new Produto();
        produtoSemEstoque.setControla_estoque(ProdutoControleEstoque.NAO);
        
        List<Object[]> resultado = new ArrayList<>();
        resultado.add(new Object[]{"1", "5"});
        
        when(vendaProdutos.buscaQtdProduto(codvenda)).thenReturn(resultado);
        when(produtos.findByCodigoIn(1L)).thenReturn(produtoSemEstoque);

        produtoService.movimentaEstoque(codvenda, EntradaSaida.SAIDA);

        verify(produtos, never()).movimentaEstoque(anyLong(), anyString(), anyInt(), anyString(), any());

    }

    @Test(expected = RuntimeException.class)
    public void testMovimentaEstoqueEstoqueInsuficiente() {
        
        Long codvenda = 1L;
        List<Object[]> resultado = new ArrayList<>();
        resultado.add(new Object[]{"1", "15"}); // qtd > estoque
        
        when(vendaProdutos.buscaQtdProduto(codvenda)).thenReturn(resultado);
        when(produtos.findByCodigoIn(1L)).thenReturn(produto);
        when(produtos.saldoEstoque(1L)).thenReturn(10); // Estoque insuficiente

        produtoService.movimentaEstoque(codvenda, EntradaSaida.SAIDA);
    }

    @Test
    public void testMovimentaEstoqueVendaSemProdutos() {
        
        Long codvenda = 1L;
        List<Object[]> resultado = new ArrayList<>(); // Lista vazia
        
        when(vendaProdutos.buscaQtdProduto(codvenda)).thenReturn(resultado);

        produtoService.movimentaEstoque(codvenda, EntradaSaida.SAIDA);

        verify(produtos, never()).findByCodigoIn(anyLong());
        verify(produtos, never()).movimentaEstoque(anyLong(), anyString(), anyInt(), anyString(), any());

    }

    @Test(expected = RuntimeException.class)
    public void testAjusteEstoqueProdutoNaoControlaEstoque() {

        Long codprod = 1L;
        Produto produtoSemEstoque = new Produto();
        produtoSemEstoque.setControla_estoque(ProdutoControleEstoque.NAO);
        
        when(produtos.findByCodigoIn(codprod)).thenReturn(produtoSemEstoque);

        produtoService.ajusteEstoque(codprod, 5, EntradaSaida.ENTRADA, "Ajuste", Date.valueOf(LocalDate.now()));
        
    }

    @Test
    public void testAjusteEstoqueProdutoControlaEstoque() {
        
        Long codprod = 1L;
        when(produtos.findByCodigoIn(codprod)).thenReturn(produto);
        doNothing().when(produtos).movimentaEstoque(anyLong(), anyString(), anyInt(), anyString(), any());

        produtoService.ajusteEstoque(codprod, 5, EntradaSaida.ENTRADA, "Ajuste", Date.valueOf(LocalDate.now()));

        verify(produtos).movimentaEstoque(eq(codprod), eq("ENTRADA"), eq(5), eq("Ajuste"), any());
        
    }

    @Test
    public void testFilterDescricaoNula() {
        
        ProdutoFilter filter = new ProdutoFilter();
        filter.setDescricao(null); // Ramo verdadeiro do if
        Pageable pageable = PageRequest.of(0, 10);
        Page<Produto> page = new PageImpl<>(listaProdutos);
        
        when(produtos.findByDescricaoContaining("%", pageable)).thenReturn(page);

        Page<Produto> resultado = produtoService.filter(filter, pageable);

        assertNotNull(resultado);
        assertEquals(1, resultado.getContent().size());
        verify(produtos).findByDescricaoContaining("%", pageable);
        
    }

    @Test
    public void testFilterDescricaoNaoNula() {

        ProdutoFilter filter = new ProdutoFilter();
        filter.setDescricao("Produto"); // Ramo falso do if
        Pageable pageable = PageRequest.of(0, 10);
        Page<Produto> page = new PageImpl<>(listaProdutos);
        
        when(produtos.findByDescricaoContaining("Produto", pageable)).thenReturn(page);

        Page<Produto> resultado = produtoService.filter(filter, pageable);

        assertNotNull(resultado);
        assertEquals(1, resultado.getContent().size());
        verify(produtos).findByDescricaoContaining("Produto", pageable);

    }

    @Test
    public void testListar() {

        when(produtos.findAll()).thenReturn(listaProdutos);

        List<Produto> resultado = produtoService.listar();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        verify(produtos).findAll();
        
    }

    @Test
    public void testListaProdutosVendaveis() {

        when(produtos.produtosVendaveis()).thenReturn(listaProdutos);

        List<Produto> resultado = produtoService.listaProdutosVendaveis();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        verify(produtos).produtosVendaveis();
        
    }

    @Test
    public void testBusca() {

        Long codigo = 1L;
        when(produtos.findByCodigoIn(codigo)).thenReturn(produto);

        Produto resultado = produtoService.busca(codigo);

        assertNotNull(resultado);
        assertEquals(codigo, resultado.getCodigo());
        verify(produtos).findByCodigoIn(codigo);
        
    }

    @Test
    public void testBuscaProduto() {
        
        Long codigo = 1L;
        when(produtos.findById(codigo)).thenReturn(Optional.of(produto));

        Optional<Produto> resultado = produtoService.buscaProduto(codigo);

        assertTrue(resultado.isPresent());
        assertEquals(codigo, resultado.get().getCodigo());
        verify(produtos).findById(codigo);
    }
} 