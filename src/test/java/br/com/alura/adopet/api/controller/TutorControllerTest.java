package br.com.alura.adopet.api.controller;

import br.com.alura.adopet.api.dto.AtualizacaoTutorDto;
import br.com.alura.adopet.api.dto.CadastroTutorDto;
import br.com.alura.adopet.api.service.TutorService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

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
}