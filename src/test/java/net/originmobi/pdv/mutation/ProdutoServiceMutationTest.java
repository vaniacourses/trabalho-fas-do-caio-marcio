package net.originmobi.pdv.mutation;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
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
public class ProdutoServiceMutationTest {

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
    public void testMergerMutationCondicaoInsercao() {

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
        verify(produtos, never()).atualiza(anyLong(), anyLong(), anyLong(), anyLong(), anyInt(), 
                                          anyString(), anyDouble(), anyDouble(), any(), 
                                          anyString(), anyString(), anyString(), anyInt(), 
                                          anyString(), anyString(), anyLong(), anyLong(), anyString());
        
    }

    @Test
    public void testMergerMutationCondicaoAtualizacao() {
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
        verify(produtos, never()).insere(anyLong(), anyLong(), anyLong(), anyInt(), 
                                        anyString(), anyDouble(), anyDouble(), any(), 
                                        anyString(), anyString(), anyString(), anyInt(), 
                                        any(), anyString(), anyString(), anyLong(), 
                                        anyLong(), anyString());
        
    }

    @Test
    public void testMergerMutationRetornoInsercao() {

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

        assertEquals("Produdo cadastrado com sucesso", resultado); // Verifica o texto exato

    }

    @Test
    public void testMergerMutationRetornoAtualizacao() {
        Long codprod = 1L;
        doNothing().when(produtos).atualiza(anyLong(), anyLong(), anyLong(), anyLong(), anyInt(), 
                                           anyString(), anyDouble(), anyDouble(), any(), 
                                           anyString(), anyString(), anyString(), anyInt(), 
                                           anyString(), anyString(), anyLong(), anyLong(), anyString());

        String resultado = produtoService.merger(codprod, 1L, 1L, 1L, 0, "Produto Atualizado",
                                                10.0, 15.0, new java.util.Date(), "SIM", "ATIVO",
                                                "UN", ProdutoSubstTributaria.NAO, "12345678", 
                                                "1234567", 1L, 1L, "SIM");

        assertEquals("Produto atualizado com sucesso", resultado); // Verifica o texto exato
    }

    @Test
    public void testMovimentaEstoqueMutationControleEstoque() {
        Long codvenda = 1L;
        List<Object[]> resultado = new ArrayList<>();
        resultado.add(new Object[]{"1", "5"});
        
        Produto produtoComEstoque = new Produto();
        produtoComEstoque.setControla_estoque(ProdutoControleEstoque.SIM);
        
        when(vendaProdutos.buscaQtdProduto(codvenda)).thenReturn(resultado);
        when(produtos.findByCodigoIn(1L)).thenReturn(produtoComEstoque);
        when(produtos.saldoEstoque(1L)).thenReturn(10);
        doNothing().when(produtos).movimentaEstoque(anyLong(), anyString(), anyInt(), anyString(), any());

        produtoService.movimentaEstoque(codvenda, EntradaSaida.SAIDA);

        verify(produtos).movimentaEstoque(eq(1L), eq("SAIDA"), eq(5), eq("Venda 1"), any());;
    }

    @Test
    public void testMovimentaEstoqueMutationSemControleEstoque() {
        Long codvenda = 1L;
        List<Object[]> resultado = new ArrayList<>();
        resultado.add(new Object[]{"1", "5"});
        
        Produto produtoSemEstoque = new Produto();
        produtoSemEstoque.setControla_estoque(ProdutoControleEstoque.NAO);
        
        when(vendaProdutos.buscaQtdProduto(codvenda)).thenReturn(resultado);
        when(produtos.findByCodigoIn(1L)).thenReturn(produtoSemEstoque);

        produtoService.movimentaEstoque(codvenda, EntradaSaida.SAIDA);

        verify(produtos, never()).movimentaEstoque(anyLong(), anyString(), anyInt(), anyString(), any());
    }

    @Test
    public void testMovimentaEstoqueMutationEstoqueIgual() {

        Long codvenda = 1L;
        List<Object[]> resultado = new ArrayList<>();
        resultado.add(new Object[]{"1", "10"}); // qtd == estoque
        
        when(vendaProdutos.buscaQtdProduto(codvenda)).thenReturn(resultado);
        when(produtos.findByCodigoIn(1L)).thenReturn(produto);
        when(produtos.saldoEstoque(1L)).thenReturn(10); // qtd == estoque
        doNothing().when(produtos).movimentaEstoque(anyLong(), anyString(), anyInt(), anyString(), any());

        produtoService.movimentaEstoque(codvenda, EntradaSaida.SAIDA);

        verify(produtos).movimentaEstoque(eq(1L), eq("SAIDA"), eq(10), eq("Venda 1"), any());
        
    }

    @Test(expected = RuntimeException.class)
    public void testMovimentaEstoqueMutationEstoqueInsuficiente() {
        Long codvenda = 1L;
        List<Object[]> resultado = new ArrayList<>();
        resultado.add(new Object[]{"1", "15"}); // qtd > estoque
        
        when(vendaProdutos.buscaQtdProduto(codvenda)).thenReturn(resultado);
        when(produtos.findByCodigoIn(1L)).thenReturn(produto);
        when(produtos.saldoEstoque(1L)).thenReturn(10); // qtd > estoque

        produtoService.movimentaEstoque(codvenda, EntradaSaida.SAIDA);
        
    }

    @Test(expected = RuntimeException.class)
    public void testAjusteEstoqueMutationSemControle() {        
        Long codprod = 1L;
        Produto produtoSemEstoque = new Produto();
        produtoSemEstoque.setControla_estoque(ProdutoControleEstoque.NAO);
        
        when(produtos.findByCodigoIn(codprod)).thenReturn(produtoSemEstoque);

        produtoService.ajusteEstoque(codprod, 5, EntradaSaida.ENTRADA, "Ajuste", Date.valueOf(LocalDate.now()));
    }

    @Test
    public void testAjusteEstoqueMutationComControle() {
        Long codprod = 1L;
        when(produtos.findByCodigoIn(codprod)).thenReturn(produto);
        doNothing().when(produtos).movimentaEstoque(anyLong(), anyString(), anyInt(), anyString(), any());

        produtoService.ajusteEstoque(codprod, 5, EntradaSaida.ENTRADA, "Ajuste", Date.valueOf(LocalDate.now()));

        verify(produtos).movimentaEstoque(eq(codprod), eq("ENTRADA"), eq(5), eq("Ajuste"), any());

    }

    @Test
    public void testFilterMutationDescricaoNula() {

        ProdutoFilter filter = new ProdutoFilter();
        filter.setDescricao(null); // Deve usar "%"
        Pageable pageable = PageRequest.of(0, 10);
        Page<Produto> page = new PageImpl<>(listaProdutos);
        
        when(produtos.findByDescricaoContaining(eq("%"), eq(pageable))).thenReturn(page);

        Page<Produto> resultado = produtoService.filter(filter, pageable);

        assertNotNull(resultado);
        assertEquals(1, resultado.getContent().size());
        verify(produtos).findByDescricaoContaining(eq("%"), eq(pageable));
    }

    @Test
    public void testFilterMutationDescricaoNaoNula() {
        ProdutoFilter filter = new ProdutoFilter();
        filter.setDescricao("Produto"); // Deve usar o valor fornecido
        Pageable pageable = PageRequest.of(0, 10);
        Page<Produto> page = new PageImpl<>(listaProdutos);
        
        when(produtos.findByDescricaoContaining(eq("Produto"), eq(pageable))).thenReturn(page);

        Page<Produto> resultado = produtoService.filter(filter, pageable);

        assertNotNull(resultado);
        assertEquals(1, resultado.getContent().size());
        verify(produtos).findByDescricaoContaining(eq("Produto"), eq(pageable));
    }

    @Test
    public void testListarMutationRetorno() {
        when(produtos.findAll()).thenReturn(listaProdutos);

        List<Produto> resultado = produtoService.listar();
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(produto, resultado.get(0));
        verify(produtos).findAll();
    }

    @Test
    public void testListaProdutosVendaveisMutationRetorno() {
        when(produtos.produtosVendaveis()).thenReturn(listaProdutos);

        List<Produto> resultado = produtoService.listaProdutosVendaveis();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(produto, resultado.get(0));
        verify(produtos).produtosVendaveis();
    }

    @Test
    public void testBuscaMutationRetorno() {
        Long codigo = 1L;
        when(produtos.findByCodigoIn(codigo)).thenReturn(produto);

        Produto resultado = produtoService.busca(codigo);

        assertNotNull(resultado);
        assertEquals(codigo, resultado.getCodigo());
        assertEquals("Produto Teste", resultado.getDescricao());
        verify(produtos).findByCodigoIn(codigo);
    }

    @Test
    public void testBuscaProdutoMutationRetorno() {
        Long codigo = 1L;
        when(produtos.findById(codigo)).thenReturn(Optional.of(produto));

        Optional<Produto> resultado = produtoService.buscaProduto(codigo);

        assertTrue(resultado.isPresent());
        assertEquals(codigo, resultado.get().getCodigo());
        assertEquals("Produto Teste", resultado.get().getDescricao());
        verify(produtos).findById(codigo);
    }

    @Test
    public void testBuscaProdutoMutationRetornoVazio() {
        Long codigo = 999L;
        when(produtos.findById(codigo)).thenReturn(Optional.empty());

        Optional<Produto> resultado = produtoService.buscaProduto(codigo);

        assertFalse(resultado.isPresent());
        verify(produtos).findById(codigo);
    }
} 