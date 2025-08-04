package br.com.alura.adopet.api.controller;

import br.com.alura.adopet.api.service.AdocaoService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
class AdocaoControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean //cria o mock e integra já como o spring deve usar
    private AdocaoService service;

    @Test
    @DisplayName("deveria DEVOLVER código 400 para a solicitacao de ADOÇÃO com erros")
    void cenario01() throws Exception {
        //ARRANGE
        String json = "{}";

        //ACT -> onde é feita a requisição para o controller (usando o MOCK MVC)
        var response = mvc.perform(
                post("/adocoes")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andReturn().getResponse();

        //ASSERT -> pega o response e verifica seu status
        Assertions.assertEquals(400, response.getStatus());
    }
}