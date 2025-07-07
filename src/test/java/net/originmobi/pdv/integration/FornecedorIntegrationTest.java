package net.originmobi.pdv.integration;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import net.originmobi.pdv.model.Fornecedor;
import net.originmobi.pdv.repository.FornecedorRepository;

@SpringBootTest
@RunWith(SpringRunner.class)
public class FornecedorIntegrationTest {
    @Autowired
    private FornecedorRepository fornecedorRepository;

    @Test
    @Transactional
    public void testCadastroFornecedor() {
        Fornecedor fornecedor = new Fornecedor();
        fornecedor.setNome("Fornecedor Teste");
        fornecedor.setCnpj("12345678000199");
        Fornecedor salvo = fornecedorRepository.save(fornecedor);
        Assert.assertNotNull(salvo.getCodigo());
        Assert.assertEquals("Fornecedor Teste", salvo.getNome());
        Assert.assertEquals("12345678000199", salvo.getCnpj());
    }
}