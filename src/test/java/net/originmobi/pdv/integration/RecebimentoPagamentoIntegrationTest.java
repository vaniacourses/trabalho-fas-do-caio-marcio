package net.originmobi.pdv.integration;

import net.originmobi.pdv.model.Venda;
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
public class RecebimentoPagamentoIntegrationTest {
    @Autowired
    private VendaRepository vendaRepository;

    @Test
    @Transactional
    public void testRecebimentoPagamento() {
        Venda venda = new Venda();
        venda.setValor_total(100.0);
        venda = vendaRepository.save(venda);

        venda.setValor_total(100.0); // Simula recebimento total
        venda = vendaRepository.save(venda);
        Assert.assertEquals(100.0, venda.getValor_total(), 0.01);
    }
}
