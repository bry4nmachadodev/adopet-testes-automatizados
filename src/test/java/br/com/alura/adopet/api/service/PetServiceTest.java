package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.dto.CadastroAbrigoDto;
import br.com.alura.adopet.api.dto.CadastroPetDto;
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

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PetServiceTest {

    @InjectMocks
    private PetService service;

    @Mock
    private PetRepository petRepository;

    @Mock
    private Abrigo abrigo;

    private CadastroPetDto petDto;

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
}