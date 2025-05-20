package net.originmobi.pdv.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class EmpresaTest {

    @Test
    public void testGettersAndSetters() {
        Empresa empresa = new Empresa();

        empresa.setCodigo(10L);
        empresa.setNome("Empresa Exemplo");
        empresa.setNome_fantasia("Fantasia Exemplo");
        empresa.setCnpj("12.345.678/0001-99");
        empresa.setIe("123456789");
        
        RegimeTributario regime = new RegimeTributario();
        empresa.setRegime_tributario(regime);
        
        Endereco endereco = new Endereco();
        empresa.setEndereco(endereco);
        
        EmpresaParametro parametro = new EmpresaParametro();
        empresa.setParametro(parametro);

        assertEquals(10L, empresa.getCodigo());
        assertEquals("Empresa Exemplo", empresa.getNome());
        assertEquals("Fantasia Exemplo", empresa.getNome_fantasia());
        assertEquals("12.345.678/0001-99", empresa.getCnpj());
        assertEquals("123456789", empresa.getIe());
        assertEquals(regime, empresa.getRegime_tributario());
        assertEquals(endereco, empresa.getEndereco());
        assertEquals(parametro, empresa.getParametro());
    }
}
