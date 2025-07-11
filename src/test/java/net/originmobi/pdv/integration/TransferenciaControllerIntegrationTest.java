package net.originmobi.pdv.integration;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class TransferenciaControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testCadastroTransferencia() throws Exception {
        mockMvc.perform(post("/transferencia")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("valor", "150.00")
                .param("observacao", "Transferência entre caixas")
                .param("data_transferencia", Timestamp.valueOf(LocalDateTime.now()).toString())
                .param("origem", "1")
                .param("destino", "2")
                .param("usuario", "1"))
                .andExpect(status().is3xxRedirection());
    }
}
