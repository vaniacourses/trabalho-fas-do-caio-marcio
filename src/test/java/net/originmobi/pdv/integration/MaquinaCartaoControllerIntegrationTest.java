package net.originmobi.pdv.integration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class MaquinaCartaoControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    /**
     * Testa se o cadastro de uma máquina de cartão redireciona corretamente após o
     * sucesso.
     */

    @Test
    public void testCadastroMaquinaCartao() throws Exception {
        mockMvc.perform(post("/maquinacartao")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)

                // Define os parâmetros do formulário que seriam submetidos pelo usuário.
                .param("descricao", "Maquininha Teste")
                .param("taxa", "2.5")
        // Exemplo de parâmetro de relacionamento, caso o caixa seja obrigatório.
        // .param("caixa.codigo", "1")
        )

                // Verifica se a resposta HTTP tem o status de redirecionamento (código 3xx).
                .andExpect(status().is3xxRedirection());
    }
}