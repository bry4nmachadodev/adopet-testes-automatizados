package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.dto.CadastroAbrigoDto;
import br.com.alura.adopet.api.dto.PetDto;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.Abrigo;
import br.com.alura.adopet.api.model.Pet;
import br.com.alura.adopet.api.repository.AbrigoRepository;
import br.com.alura.adopet.api.repository.PetRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AbrigoServiceTest {

    @Spy
    @InjectMocks
    private AbrigoService service;

    @Mock
    private AbrigoRepository abrigoRepository;

    @Mock
    private PetRepository petRepository;

    @Mock
    private Pet pet;

    @Mock
    private Abrigo abrigo;

    private CadastroAbrigoDto cadastroDto;

    @Captor
    private ArgumentCaptor<Abrigo> abrigoCaptor;

    @Test
    @DisplayName("deve dar ValidacaoException devido a DADOS JÁ CADASTRADOS")
    void cenario01(){
        //ARRANGE
        this.cadastroDto = new CadastroAbrigoDto("Casinha Feliz", "1134567890", "casinhafeliz@teste.com");
        BDDMockito.given(
                abrigoRepository.existsByNomeOrTelefoneOrEmail(
                        cadastroDto.nome(),
                        cadastroDto.telefone(),
                        cadastroDto.email())
                )
        .willReturn(true);

        //ASSERT + ACT
        assertThrows(ValidacaoException.class, () -> service.cadastrar(cadastroDto));
    }

    @Test
    @DisplayName("deve SALVAR no banco")
    void cenario02(){
        //ARRANGE
        CadastroAbrigoDto dto = new CadastroAbrigoDto("Casinha Feliz", "1134567890", "casinhafeliz@teste.com");
        BDDMockito.given(
                        abrigoRepository.existsByNomeOrTelefoneOrEmail(
                                dto.nome(),
                                dto.telefone(),
                                dto.email())
                )
                .willReturn(false);

        //ACT
        service.cadastrar(dto);

        //ASSERT
        BDDMockito.then(abrigoRepository).should().save(abrigoCaptor.capture());
        Abrigo abrigoSalvo = abrigoCaptor.getValue();

        Assertions.assertEquals(dto.nome(), abrigoSalvo.getNome());
        Assertions.assertEquals(dto.telefone(), abrigoSalvo.getTelefone());
        Assertions.assertEquals(dto.email(), abrigoSalvo.getEmail());
    }

    @Test
    @DisplayName(" carregarAbrigo deve buscar por ID quando entrada for número")
    void cenario01CarregarAbrigo() {
        //ARRANGE
        Abrigo abrigoEsperado = Mockito.mock(Abrigo.class);
        Long id = 13L;
        BDDMockito.given(abrigoRepository.findById(id)).willReturn(Optional.of(abrigoEsperado));

        //ACT
        Abrigo resultado = service.carregarAbrigo("13");

        //ASSERT
        Assertions.assertEquals(abrigoEsperado, resultado);
    }

    @Test
    @DisplayName("carregarAbrigo deve lançar exceção se o ID não for encontrado")
    void cenario02CarregarAbrigo() {
        // Arrange
        Long id = 99L;
        BDDMockito.given(abrigoRepository.findById(id)).willReturn(Optional.empty());

        // Act + Assert
        Assertions.assertThrows(ValidacaoException.class, () -> {
            service.carregarAbrigo("99");
        });
    }

    @Test
    @DisplayName("deve listar pets do abrigo a partir do ID ou nome")
    void cenario01ListarPetsDoAbrigo(){
        //ARRANGE
        Abrigo abrigo = Mockito.mock(Abrigo.class);
        List<Pet> listaDePetsMock = new ArrayList<>();
        listaDePetsMock.add(Mockito.mock(Pet.class));
        listaDePetsMock.add(Mockito.mock(Pet.class));
        BDDMockito.doReturn(abrigo).when(service).carregarAbrigo("123");
        BDDMockito.when(petRepository.findByAbrigo(abrigo)).thenReturn(listaDePetsMock);

        //ACT
        List<PetDto> resultado = service.listarPetsDoAbrigo("123");

        //ASSERT
        Assertions.assertEquals(listaDePetsMock.size(), resultado.size());
    }
}