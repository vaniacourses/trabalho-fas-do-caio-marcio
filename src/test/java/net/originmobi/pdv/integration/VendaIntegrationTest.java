package net.originmobi.pdv.integration;

import net.originmobi.pdv.model.Produto;
import net.originmobi.pdv.model.Venda;
import net.originmobi.pdv.repository.ProdutoRepository;
import net.originmobi.pdv.repository.VendaRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@RunWith(SpringRunner.class)
public class VendaIntegrationTest {
    @Autowired
    private VendaRepository vendaRepository;
    @Autowired
    private ProdutoRepository produtoRepository;

    @Test
    @Transactional
    public void testRealizacaoVenda() {
        Produto produto = new Produto();
        produto.setDescricao("Produto Venda");
        produto.setValor_venda(20.0);
        produto = produtoRepository.save(produto);

        Venda venda = new Venda();
        venda.setValor_total(20.0);
        venda.setProduto(java.util.Collections.singletonList(produto));
        Venda salva = vendaRepository.save(venda);
        Assert.assertNotNull(salva.getCodigo());
        Assert.assertEquals(20.0, salva.getValor_total(), 0.01);
        Assert.assertTrue(salva.getProduto().contains(produto));
    }
}
