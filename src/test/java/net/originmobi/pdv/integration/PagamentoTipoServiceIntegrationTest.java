package net.originmobi.pdv.integration;

import net.originmobi.pdv.model.PagamentoTipo;
import net.originmobi.pdv.repository.PagamentoTipoRespository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@SpringBootTest
@RunWith(SpringRunner.class)
public class PagamentoTipoServiceIntegrationTest {

    @Autowired
    private PagamentoTipoRespository pagamentoTipoRepository;

    @Test
    @Transactional
    public void testCadastrarPagamentoTipo() {
        PagamentoTipo tipo = new PagamentoTipo();
        tipo.setDescricao("À vista");
        tipo.setFormaPagamento("00");
        tipo.setData_cadastro(java.sql.Date.valueOf(java.time.LocalDate.now()));

        tipo = pagamentoTipoRepository.save(tipo);

        Assert.assertNotNull("Código não deveria ser nulo após salvar", tipo.getCodigo());
        Assert.assertEquals("Descrição deveria ser igual ao informado", "À vista", tipo.getDescricao());
    }

    @Test
    @Transactional
    public void testBuscaEPesquisaParcelas() {
        PagamentoTipo tipo = new PagamentoTipo();
        tipo.setDescricao("A prazo");
        tipo.setData_cadastro(java.sql.Date.valueOf(java.time.LocalDate.now()));
        tipo.setFormaPagamento("30");
        pagamentoTipoRepository.save(tipo);

        Optional<PagamentoTipo> encontrado = pagamentoTipoRepository.findAll().stream()
                .filter(pt -> "A prazo".equals(pt.getDescricao()))
                .findFirst();

        Assert.assertTrue("PagamentoTipo deveria ser encontrado", encontrado.isPresent());
        Assert.assertEquals("A descrição deveria ser 'A prazo'", "A prazo", encontrado.get().getDescricao());
    }

}
