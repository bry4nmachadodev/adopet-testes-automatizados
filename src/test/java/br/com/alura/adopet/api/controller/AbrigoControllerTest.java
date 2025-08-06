package br.com.alura.adopet.api.controller;

import br.com.alura.adopet.api.service.AbrigoService;
import br.com.alura.adopet.api.service.PetService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest
@AutoConfigureMockMvc
class AbrigoControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private AbrigoService abrigoService;

    @MockBean
    private PetService petService;

    @Test
    @DisplayName("deveria DEVOLVER código 200 para o GET -> LISTA DE ABRIGOS (DEVIDO AO JSON INVÁLIDO")
    void cenario01() throws Exception {
        //ACT
        var response = mvc.perform(
                        get("/abrigos")
                )
                .andReturn().getResponse();

        //ASSERT
        Assertions.assertEquals(200, response.getStatus());
    }
}