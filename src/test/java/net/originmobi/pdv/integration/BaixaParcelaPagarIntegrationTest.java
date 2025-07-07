package net.originmobi.pdv.integration;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import net.originmobi.pdv.model.Pagar;
import net.originmobi.pdv.model.PagarParcela;
import net.originmobi.pdv.repository.PagarParcelaRespository;
import net.originmobi.pdv.repository.PagarRepository;

@SpringBootTest
@RunWith(SpringRunner.class)
public class BaixaParcelaPagarIntegrationTest {
    @Autowired
    private PagarParcelaRespository pagarParcelaRespository;
    @Autowired
    private PagarRepository pagarRepository;

    @Test
    @Transactional
    public void testBaixaParcelaPagar() {
        Pagar pagar = new Pagar();
        pagar.setObservacao("Conta Baixa");
        pagar.setValor_total(200.0);
        pagar.setData_cadastro(LocalDate.now());
        pagar = pagarRepository.save(pagar);

        pagarParcelaRespository.geraParcela(200.0, 200.0, 0.0, 0.0, 0.0, 0, new Timestamp(System.currentTimeMillis()), LocalDate.now().plusDays(30), pagar);
        List<PagarParcela> parcelas = pagarParcelaRespository.listaOrdenada(null).getContent();
        Assert.assertFalse(parcelas.isEmpty());
        PagarParcela parcela = parcelas.get(0);
        parcela.setQuitado(1);
        pagarParcelaRespository.save(parcela);
        PagarParcela quitada = pagarParcelaRespository.findById(parcela.getCodigo()).orElse(null);
        Assert.assertNotNull(quitada);
        Assert.assertEquals(1, quitada.getQuitado());
    }
}
