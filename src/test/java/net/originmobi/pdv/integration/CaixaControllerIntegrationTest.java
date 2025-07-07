package net.originmobi.pdv.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class CaixaControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testCadastroCaixa() throws Exception {
        Map<String, String> caixaRequest = new HashMap<>();
        caixaRequest.put("descricao", "Caixa Teste");
        caixaRequest.put("tipo", "CAIXA"); // ou outro valor válido do enum CaixaTipo
        caixaRequest.put("valor_abertura", "100.00");
        caixaRequest.put("agencia", "1234");
        caixaRequest.put("conta", "56789");

        mockMvc.perform(post("/caixa")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("descricao", caixaRequest.get("descricao"))
                .param("tipo", caixaRequest.get("tipo"))
                .param("valor_abertura", caixaRequest.get("valor_abertura"))
                .param("agencia", caixaRequest.get("agencia"))
                .param("conta", caixaRequest.get("conta")))
                .andExpect(status().is3xxRedirection());
    }
}