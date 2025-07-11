package net.originmobi.pdv.integration;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.sql.Date;
import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class AjusteControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testCadastroAjuste() throws Exception {
        mockMvc.perform(post("/ajuste") 
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("status", "ABERTURA") 
                .param("usuario", "admin")
                .param("data_cadastro", Date.valueOf(LocalDate.now()).toString())
                .param("observacao", "Teste de integração com MockMvc"))
                .andExpect(status().is3xxRedirection()); 
    }
}
