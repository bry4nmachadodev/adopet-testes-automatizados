package br.com.alura.adopet.api.controller;

import br.com.alura.adopet.api.dto.SolicitacaoAdocaoDto;
import br.com.alura.adopet.api.service.AdocaoService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.BDDMockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
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

    @Test
    @DisplayName("deveria DEVOLVER código 200 para a solicitacao de ADOÇÃO SEM erros")
    void cenario02() throws Exception {
        //ARRANGE
        String json = """
                {
                    "idPet": 1,
                    "idTutor": 1,
                    "motivo": "Motivo qualquer"
                }
                """;

        //ACT -> onde é feita a requisição para o controller (usando o MOCK MVC)
        var response = mvc.perform(
                        post("/adocoes")
                                .content(json)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andReturn().getResponse();

        //ASSERT -> pega o response e verifica seu status
        Assertions.assertEquals(200, response.getStatus());
    }

    @Test
    @DisplayName("se o SERVICE entendeu os dados do dto")
    void cenario03() throws Exception {
        //ARRANGE
        String json = """
            {
                "idPet": 1,
                "idTutor": 1,
                "motivo": "Motivo qualquer"
            }
            """;

        //ACT
        var response = mvc.perform(
                        post("/adocoes")
                                .content(json)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andReturn().getResponse();

        //ASSERT -> uso do captor para ver se o service leu os dados corretamente
        ArgumentCaptor<SolicitacaoAdocaoDto> captor = ArgumentCaptor.forClass(SolicitacaoAdocaoDto.class);
        verify(service).solicitar(captor.capture());

        var dtoCapturado = captor.getValue();
        assertEquals(1L, dtoCapturado.idPet());
        assertEquals(1L, dtoCapturado.idTutor());
        assertEquals("Motivo qualquer", dtoCapturado.motivo());
    }

}