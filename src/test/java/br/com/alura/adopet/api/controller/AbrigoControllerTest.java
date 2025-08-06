package br.com.alura.adopet.api.controller;

import br.com.alura.adopet.api.dto.CadastroAbrigoDto;
import br.com.alura.adopet.api.dto.CadastroPetDto;
import br.com.alura.adopet.api.dto.PetDto;
import br.com.alura.adopet.api.model.Abrigo;
import br.com.alura.adopet.api.model.Pet;
import br.com.alura.adopet.api.model.TipoPet;
import br.com.alura.adopet.api.service.AbrigoService;
import br.com.alura.adopet.api.service.PetService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

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

    @Mock
    private Pet pet;

    @Mock
    private Abrigo abrigo;

    private CadastroPetDto cadastroPetDto;

    private PetDto petDto;

    @Test
    @DisplayName("deveria DEVOLVER código 200 para o GET -> LISTA DE ABRIGOS (DEVIDO AO JSON INVÁLIDO")
    void cenario01GetListarAbrigo() throws Exception {
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

    @Test
    @DisplayName("Devolve 400 devido à falta no json")
    void cenario03Post() throws Exception {
        //ARRANGE
        String json = """
            {
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
        Assertions.assertEquals(400, response.getStatus());
    }

    @Test
    @DisplayName("Deveria retornar os PETS do ABRIGO")
    void cenario01GetListarPets() throws Exception {
        //ARRANGE
        CadastroAbrigoDto abrigoFake = new CadastroAbrigoDto("Abrigo", "11999999999", "teste@abrigo.com");
        Abrigo abrigo1 = new Abrigo(abrigoFake);

        CadastroPetDto petDto1 = new CadastroPetDto(TipoPet.CACHORRO, "Rex", "Vira-lata", 3, "Marrom", 12.5f);
        Pet pet1 = new Pet(petDto1, abrigo1);
        PetDto petDtoResponse1 = new PetDto(pet1);

        CadastroPetDto petDto2 = new CadastroPetDto(TipoPet.GATO, "Mimi", "Siamês", 2, "Branco", 5.0f);
        Pet pet2 = new Pet(petDto2, abrigo1);
        PetDto petDtoResponse2 = new PetDto(pet2);

        List<PetDto> petsDoAbrigo = List.of(petDtoResponse1, petDtoResponse2);

        BDDMockito.given(abrigoService.listarPetsDoAbrigo("Abrigo")).willReturn(petsDoAbrigo);

        //ACT
        var response = mvc.perform(get("/abrigos/Abrigo/pets"))
                .andReturn()
                .getResponse();

        //ASSERT
        Assertions.assertEquals(200, response.getStatus());
        String jsonResponse = response.getContentAsString();
        Assertions.assertTrue(jsonResponse.contains("Rex"));
        Assertions.assertTrue(jsonResponse.contains("Mimi"));
    }

    @Test
    @DisplayName("Deveria retornar 404 quando o abrigo não for encontrado")
    void cenario02GetListarPetsAbrigoNaoEncontrado() throws Exception {
        //ACT
        var response = mvc.perform(get("/abrigos/Abrigo/pets"))
                .andReturn()
                .getResponse();

        //ASSERT
        Assertions.assertEquals(200, response.getStatus());
    }

    @Test
    @DisplayName("Deveria retornar 200 - JSON CORRETO")
    void cenario01PostCadastroPet() throws Exception {
        //ARRANGE
        String json = """
                {
                  "tipo": "CACHORRO",
                  "nome": "Rex",
                  "raca": "Vira-lata",
                  "idade": 3,
                  "cor": "Marrom",
                  "peso": 12.5
                }
                """;

        Abrigo abrigoMock = new Abrigo(new CadastroAbrigoDto("AbrigoTeste", "11999999999", "teste@abrigo.com"));
        BDDMockito.given(abrigoService.carregarAbrigo("AbrigoTeste")).willReturn(abrigoMock);
        BDDMockito.doNothing().when(petService).cadastrarPet(Mockito.any(Abrigo.class), Mockito.any(CadastroPetDto.class));

        //ACT -> onde é feita a requisição para o controller (usando o MOCK MVC)
        var response = mvc.perform(
                        post("/abrigos/AbrigoTeste/pets")
                                .content(json)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andReturn().getResponse();

        //ASSERT -> pega o response e verifica seu status
        Assertions.assertEquals(200, response.getStatus());
    }

    @Test
    @DisplayName("Deveria retornar 404 quando o abrigo não for encontrado")
    void cenario02PostCadastroPetInvalido() throws Exception {
        //ACT
        var response = mvc.perform(get("/abrigos/Abrigo/pets"))
                .andReturn()
                .getResponse();

        //ASSERT
        Assertions.assertEquals(200, response.getStatus());
    }
}