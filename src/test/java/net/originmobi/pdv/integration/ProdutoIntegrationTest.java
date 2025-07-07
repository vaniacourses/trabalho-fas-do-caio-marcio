package net.originmobi.pdv.integration;

import net.originmobi.pdv.model.Produto;
import net.originmobi.pdv.repository.ProdutoRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ProdutoIntegrationTest {
    @Autowired
    private ProdutoRepository produtoRepository;

    @Test
    @Transactional
    public void testCadastroProduto() {
        Produto produto = new Produto();
        produto.setDescricao("Produto Teste");
        produto.setValor_custo(5.0);
        produto.setValor_venda(10.0);
        Produto salvo = produtoRepository.save(produto);
        Assert.assertNotNull(salvo.getCodigo());
        Assert.assertEquals("Produto Teste", salvo.getDescricao());
        Assert.assertEquals(5.0, salvo.getValor_custo(), 0.01);
        Assert.assertEquals(10.0, salvo.getValor_venda(), 0.01);
    }
}
