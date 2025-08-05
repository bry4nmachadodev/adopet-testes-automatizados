package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.dto.CadastroAbrigoDto;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.Abrigo;
import br.com.alura.adopet.api.model.Adocao;
import br.com.alura.adopet.api.repository.AbrigoRepository;
import br.com.alura.adopet.api.repository.PetRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AbrigoServiceTest {

    @InjectMocks
    private AbrigoService service;

    @Mock
    private AbrigoRepository abrigoRepository;

    @Mock
    private PetRepository petRepository;

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
}