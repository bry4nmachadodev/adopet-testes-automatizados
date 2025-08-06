package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.dto.CadastroAbrigoDto;
import br.com.alura.adopet.api.dto.CadastroPetDto;
import br.com.alura.adopet.api.dto.PetDto;
import br.com.alura.adopet.api.model.Abrigo;
import br.com.alura.adopet.api.model.Pet;
import br.com.alura.adopet.api.model.TipoPet;
import br.com.alura.adopet.api.repository.PetRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.never;

@ExtendWith(MockitoExtension.class)
class PetServiceTest {

    @InjectMocks
    private PetService service;

    @Mock
    private PetRepository petRepository;

    @Mock
    private Abrigo abrigo;

    private PetDto petDto;

    private CadastroPetDto cadastroPetDto;

    private CadastroAbrigoDto abrigoDto;

    @Captor
    private ArgumentCaptor<Pet> petCaptor;

    @Test
    @DisplayName("Cadastrar Pet - Salvar no banco de dados")
    void cenario01(){
        //ARRANGE
        CadastroAbrigoDto cadastroAbrigo1 = new CadastroAbrigoDto("Casinha Feliz", "1134567890", "casinhafeliz@teste.com");
        Abrigo abrigo1 = new Abrigo(cadastroAbrigo1);

        CadastroPetDto cadastroPet1 = new CadastroPetDto(
                TipoPet.CACHORRO,
                "Rex",
                "Labrador",
                3,
                "Marrom",
                25.0f
        );

        //ACT
        service.cadastrarPet(abrigo1,cadastroPet1);

        //ASSERT
        BDDMockito.then(petRepository).should().save(petCaptor.capture());
        Pet petCadastrado = petCaptor.getValue();

        Assertions.assertAll(
                () -> Assertions.assertEquals(cadastroPet1.nome(), petCadastrado.getNome()),
                () -> Assertions.assertEquals(cadastroPet1.raca(), petCadastrado.getRaca()),
                () -> Assertions.assertEquals(cadastroPet1.idade(), petCadastrado.getIdade()),
                () -> Assertions.assertEquals(cadastroPet1.cor(), petCadastrado.getCor()),
                () -> Assertions.assertEquals(cadastroPet1.tipo(), petCadastrado.getTipo()),
                () -> Assertions.assertEquals(cadastroPet1.peso(), petCadastrado.getPeso()),
                () -> Assertions.assertEquals(abrigo1, petCadastrado.getAbrigo())
        );
    }

    @Test
    @DisplayName("Buscar pets disponíveis retorna lista correta")
    void cenario01Listar(){
        //ARRANGE
        CadastroAbrigoDto abrigoFake = new CadastroAbrigoDto("Abrigo Teste", "11999999999", "teste@abrigo.com");
        Abrigo abrigo1 = new Abrigo(abrigoFake);

        CadastroPetDto petDto1 = new CadastroPetDto(TipoPet.CACHORRO, "Rex", "Labrador", 3, "Marrom", 25.0f);
        CadastroPetDto petDto2 = new CadastroPetDto(TipoPet.GATO, "Mimi", "Siamês", 2, "Branco", 5.0f);

        Pet pet1 = new Pet(petDto1, abrigo1);
        Pet pet2 = new Pet(petDto2, abrigo1);

        List<Pet> petsFake = List.of(pet1, pet2);

        BDDMockito.given(petRepository.findAllByAdotadoFalse()).willReturn(petsFake);

        //ACT
        List<PetDto> resultado = service.buscarPetsDisponiveis();

        //ASSERT
        Assertions.assertEquals(2, petsFake.size());
        Assertions.assertEquals("Rex", resultado.get(0).nome());
        Assertions.assertEquals("Labrador", resultado.get(0).raca());
        Assertions.assertEquals(TipoPet.CACHORRO, resultado.get(0).tipo());
        Assertions.assertEquals(3, resultado.get(0).idade());

        Assertions.assertEquals("Mimi", resultado.get(1).nome());
        Assertions.assertEquals("Siamês", resultado.get(1).raca());
        Assertions.assertEquals(TipoPet.GATO, resultado.get(1).tipo());
        Assertions.assertEquals(2, resultado.get(1).idade());
    }

}