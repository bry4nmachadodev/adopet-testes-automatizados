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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

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
    void cenario01Get() throws Exception {
        //ACT
        var response = mvc.perform(
                        get("/abrigos")
                )
                .andReturn().getResponse();

        //ASSERT
        Assertions.assertEquals(200, response.getStatus());
    }

    @Test
    @DisplayName("Devolve 400 devido ao json inválido")
    void cenario01Post() throws Exception {
        //ARRANGE
        String json = "{}";

        //ACT -> onde é feita a requisição para o controller (usando o MOCK MVC)
        var response = mvc.perform(
                        post("/abrigos")
                                .content(json)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andReturn().getResponse();

        //ASSERT -> pega o response e verifica seu status
        Assertions.assertEquals(400, response.getStatus());
    }

    @Test
    @DisplayName("Devolve 200 devido ao json VÁLIDO")
    void cenario02Post() throws Exception {
        //ARRANGE
        String json = """
            {
          "nome": "Abrigo Feliz",
          "telefone": "(12)91234-5678",
          "email": "contato@abrigofeliz.com"
            }
            """;


        //ACT -> onde é feita a requisição para o controller (usando o MOCK MVC)
        var response = mvc.perform(
                        post("/abrigos")
                                .content(json)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andReturn().getResponse();

        //ASSERT -> pega o response e verifica seu status
        Assertions.assertEquals(200, response.getStatus());
    }
}