package br.com.alura.adopet.api.controller;

import br.com.alura.adopet.api.dto.AtualizacaoTutorDto;
import br.com.alura.adopet.api.dto.CadastroTutorDto;
import br.com.alura.adopet.api.model.Tutor;
import br.com.alura.adopet.api.repository.TutorRepository;
import br.com.alura.adopet.api.service.TutorService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

@SpringBootTest
@AutoConfigureMockMvc
class TutorControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private TutorService service;

    private CadastroTutorDto tutorDto;

    private AtualizacaoTutorDto atualizacaoTutorDto;

    @Test
    @DisplayName("Dever retornar 200-OK ( CADASTRO REALIZADO COM SUCESSO )")
    void cenario01() throws Exception {
        //ARRANGE
        String json = "{\n" +
                "  \"nome\": \"Bryan\",\n" +
                "  \"telefone\": \"22997071492\",\n" +
                "  \"email\": \"teste@teste.com\"\n" +
                "}\n"
        ;
        //ACT
        var response = mvc.perform(
                post("/tutores")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        //ASSERT
        Assertions.assertEquals(200, response.getStatus());
    }

    @Test
    @DisplayName("Dever retornar 400 ( CADASTRO NÃO REALIZADO COM SUCESSO )")
    void cenario02() throws Exception {
        //ARRANGE
        String json = "{\n" +
                "  \"telefone\": \"22997071492\",\n" +
                "  \"email\": \"teste@teste.com\"\n" +
                "}\n"
                ;
        //ACT
        var response = mvc.perform(
                post("/tutores")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        //ASSERT
        Assertions.assertEquals(400, response.getStatus());
    }

    @Test
    @DisplayName("Deve atualizar e retornar 200 - OK")
    void cenario01Atualizar() throws Exception {
        //ARRANGE
        String json = """
                {
                  "id": 99,
                  "nome": "Bryan",
                  "telefone": "88888888888",
                  "email": "modificado@teste.com"
                }
                """;
        BDDMockito.doNothing().when(service).atualizar(Mockito.any());

        //ACT
        var response = mvc.perform(
                put("/tutores")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        //ASSERT
        Assertions.assertEquals(200, response.getStatus());
    }
}