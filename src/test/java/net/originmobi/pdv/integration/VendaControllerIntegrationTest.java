package net.originmobi.pdv.integration;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class VendaControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testCadastroVenda() throws Exception {
        mockMvc.perform(post("/venda")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("valor_total", "100.00")) // ajuste os parâmetros conforme o controller
                .andExpect(status().is3xxRedirection());
    }
}
