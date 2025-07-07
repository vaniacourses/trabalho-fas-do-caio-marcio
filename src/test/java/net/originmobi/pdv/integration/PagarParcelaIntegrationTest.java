package net.originmobi.pdv.integration;

import java.sql.Timestamp;
import java.time.LocalDate;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import net.originmobi.pdv.model.Pagar;
import net.originmobi.pdv.repository.PagarParcelaRespository;
import net.originmobi.pdv.repository.PagarRepository;

@SpringBootTest
@RunWith(SpringRunner.class)
public class PagarParcelaIntegrationTest {
    @Autowired
    private PagarParcelaRespository pagarParcelaRespository;
    @Autowired
    private PagarRepository pagarRepository;

    @Test
    @Transactional
    public void testLancarContaPagar() {
        Pagar pagar = new Pagar();
        pagar.setObservacao("Conta Teste");
        pagar.setValor_total(100.0);
        pagar.setData_cadastro(LocalDate.now());
        pagar = pagarRepository.save(pagar);

        pagarParcelaRespository.geraParcela(100.0, 100.0, 0.0, 0.0, 0.0, 0, new Timestamp(System.currentTimeMillis()), LocalDate.now().plusDays(30), pagar);
        Assert.assertTrue(pagarParcelaRespository.listaOrdenada(null).getContent().size() > 0);
    }
}