package net.originmobi.pdv.model;

import java.sql.Date;
import java.util.Calendar;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import net.originmobi.pdv.enumerado.Ativo;
import net.originmobi.pdv.enumerado.produto.ProdutoBalanca;
import net.originmobi.pdv.enumerado.produto.ProdutoControleEstoque;
import net.originmobi.pdv.enumerado.produto.ProdutoVendavel;

@DisplayName("Classe para teste de Produto")
public class ProdutoTest {

    private Produto produto;

    // Inicializa o objeto antes de cada teste para evitar repetição
    @BeforeEach
    void setup() {
        produto = new Produto();
    }

    @Test
    @DisplayName("Teste para verificar se o construtor padrão funciona")
    void testConstrutorPadrao() {
        assertNotNull(produto, "Produto não deve ser nulo após instanciado");
    }

    @Test
    @DisplayName("Teste dos getters e setters do código")
    void testCodigo() {
        Long codigo = 123L;
        produto.setCodigo(codigo);
        assertEquals(codigo, produto.getCodigo(), "O código deve ser igual ao setado");
    }

    @Test
    @DisplayName("Teste dos getters e setters da descrição")
    void testDescricao() {
        String descricao = "Produto Teste";
        produto.setDescricao(descricao);
        assertEquals(descricao, produto.getDescricao(), "A descrição deve ser igual ao setada");
    }

    @Test
    @DisplayName("Teste dos getters e setters do valor de custo")
    void testValorCusto() {
        Double valor = 50.75;
        produto.setValor_custo(valor);
        assertEquals(valor, produto.getValor_custo(), "O valor de custo deve ser igual ao setado");
    }

    @Test
    @DisplayName("Teste dos getters e setters do valor de venda")
    void testValorVenda() {
        Double valor = 80.99;
        produto.setValor_venda(valor);
        assertEquals(valor, produto.getValor_venda(), "O valor de venda deve ser igual ao setado");
    }

    @Test
    @DisplayName("Teste dos getters e setters do valor da balança")
    void testValorBalanca() {
        Double valor = 10.50;
        produto.setValor_balanca(valor);
        assertEquals(valor, produto.getValor_balanca(), "O valor da balança deve ser igual ao setado");
    }

    @Test
    @DisplayName("Teste dos getters e setters da data de validade")
    void testDataValidade() {
        java.util.Date data = Calendar.getInstance().getTime();
        produto.setData_validade(data);
        assertEquals(data, produto.getData_validade(), "A data de validade deve ser igual à setada");
    }

    @Test
    @DisplayName("Teste dos getters e setters da data de cadastro")
    void testDataCadastro() {
        Date data = new Date(System.currentTimeMillis());
        produto.setData_cadastro(data);
        assertEquals(data, produto.getData_cadastro(), "A data de cadastro deve ser igual à setada");
    }

    @Test
    @DisplayName("Teste dos getters e setters do fornecedor")
    void testFornecedor() {
        Fornecedor fornecedor = org.mockito.Mockito.mock(Fornecedor.class);
        produto.setFornecedor(fornecedor);
        assertSame(fornecedor, produto.getFornecedor(), "O fornecedor deve ser igual ao setado");
    }

    @Test
    @DisplayName("Teste dos getters e setters do grupo")
    void testGrupo() {
        Grupo grupo = org.mockito.Mockito.mock(Grupo.class);
        produto.setGrupo(grupo);
        assertSame(grupo, produto.getGrupo(), "O grupo deve ser igual ao setado");
    }

    @Test
    @DisplayName("Teste dos getters e setters da categoria")
    void testCategoria() {
        Categoria categoria = org.mockito.Mockito.mock(Categoria.class);
        produto.setCategoria(categoria);
        assertSame(categoria, produto.getCategoria(), "A categoria deve ser igual à setada");
    }

    @Test
    @DisplayName("Teste dos getters e setters da balança")
    void testBalanca() {
        produto.setBalanca(ProdutoBalanca.SIM);
        assertEquals(ProdutoBalanca.SIM, produto.getBalanca(), "A balança deve ser igual ao setado");
    }

    @Test
    @DisplayName("Teste dos getters e setters da unidade")
    void testUnidade() {
        String unidade = "kg";
        produto.setUnidade(unidade);
        assertEquals(unidade, produto.getUnidade(), "A unidade deve ser igual à setada");
    }

    @Test
    @DisplayName("Teste dos getters e setters do NCM")
    void testNcm() {
        String ncm = "12345678";
        produto.setNcm(ncm);
        assertEquals(ncm, produto.getNcm(), "O NCM deve ser igual ao setado");
    }

    @Test
    @DisplayName("Teste dos getters e setters do CEST")
    void testCest() {
        String cest = "1234567";
        produto.setCest(cest);
        assertEquals(cest, produto.getCest(), "O CEST deve ser igual ao setado");
    }

    @Test
    @DisplayName("Teste dos getters e setters da tributação")
    void testTributacao() {
        Tributacao tributacao = org.mockito.Mockito.mock(Tributacao.class);
        produto.setTributacao(tributacao);
        assertSame(tributacao, produto.getTributacao(), "A tributação deve ser igual à setada");
    }

    @Test
    @DisplayName("Teste dos getters e setters do modBcIcms")
    void testModBcIcms() {
        ModBcIcms modBcIcms = org.mockito.Mockito.mock(ModBcIcms.class);
        produto.setModBcIcms(modBcIcms);
        assertSame(modBcIcms, produto.getModBcIcms(), "O modBcIcms deve ser igual ao setado");
    }

    @Test
    @DisplayName("Teste dos getters e setters do vendável")
    void testVendavel() {
        produto.setVendavel(ProdutoVendavel.SIM);
        assertEquals(ProdutoVendavel.SIM, produto.getVendavel(), "O vendável deve ser igual ao setado");
    }

    @Test
    @DisplayName("Teste dos getters e setters do estoque")
    void testEstoque() {
        ProdutoEstoque estoque = org.mockito.Mockito.mock(ProdutoEstoque.class);
        produto.setEstoque(estoque);
        assertSame(estoque, produto.getEstoque(), "O estoque deve ser igual ao setado");
    }

    @Test
    @DisplayName("Teste dos getters e setters do controle de estoque")
    void testControlaEstoque() {
        produto.setControla_estoque(ProdutoControleEstoque.SIM);
        assertEquals(ProdutoControleEstoque.SIM, produto.getControla_estoque(), "O controle de estoque deve ser igual ao setado");
    }

    @Test
    @DisplayName("Teste dos getters e setters do status ativo")
    void testAtivo() {
        produto.setAtivo(Ativo.ATIVO);
        assertEquals(Ativo.ATIVO, produto.getAtivo(), "O status ativo deve ser igual ao setado");
    }
}