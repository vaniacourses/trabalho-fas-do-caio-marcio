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
public class CategoriaControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testCadastroCategoria() throws Exception {
        mockMvc.perform(post("/categoria")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("nome", "Categoria Teste")) 
                .andExpect(status().is3xxRedirection());
    }
}