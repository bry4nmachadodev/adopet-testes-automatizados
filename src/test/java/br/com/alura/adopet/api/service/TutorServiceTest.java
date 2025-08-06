package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.dto.AtualizacaoTutorDto;
import br.com.alura.adopet.api.dto.CadastroTutorDto;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.Pet;
import br.com.alura.adopet.api.model.Tutor;
import br.com.alura.adopet.api.repository.TutorRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class TutorServiceTest {

    @InjectMocks
    private TutorService service;

    @Mock
    private TutorRepository tutorRepository;

    private CadastroTutorDto tutorDto;

    private AtualizacaoTutorDto atualizacaoTutorDto;

    @Captor
    private ArgumentCaptor<Tutor> tutorCaptor;

    @Test
    @DisplayName("Cadastrar TUTOR e SALVAR no banco")
    void cenario01(){
        //ARRAGE
        CadastroTutorDto tutorCriado = new CadastroTutorDto("Bryan", "22997071492", "teste@teste.com");
        BDDMockito.given(tutorRepository.existsByTelefoneOrEmail(tutorCriado.telefone(), tutorCriado.email())).willReturn(false);

        //ACT
        service.cadastrar(tutorCriado);

        //ASSERT
        BDDMockito.then(tutorRepository).should().save(tutorCaptor.capture());
        Tutor tutorSalvo = tutorCaptor.getValue();
        Assertions.assertEquals(tutorCriado.nome(), tutorSalvo.getNome());
        Assertions.assertEquals(tutorCriado.telefone(), tutorSalvo.getTelefone());
        Assertions.assertEquals(tutorCriado.email(), tutorSalvo.getEmail());
    }

    @Test
    @DisplayName("Cadastro FALHA devido a EXCEÇÃO (ValidacaoException)")
    void cenario02(){
        //ARRAGE
        CadastroTutorDto tutorCriado = new CadastroTutorDto("Bryan", "22997071492", "teste@teste.com");
        BDDMockito.given(tutorRepository.existsByTelefoneOrEmail(tutorCriado.telefone(), tutorCriado.email())).willReturn(true);

        //ASSERT + ACT
        Assertions.assertThrows(ValidacaoException.class, () -> service.cadastrar(tutorCriado));
    }

    @Test
    @DisplayName("Atualiza os dados e verifica se realmente mudou")
    void cenario01Atualizar(){
        //ARRANGE
        CadastroTutorDto tutorCriado = new CadastroTutorDto("Bryan", "22997071492", "teste@teste.com");
        Tutor tutor1 = new Tutor(tutorCriado);
        BDDMockito.given(tutorRepository.getReferenceById(99L)).willReturn(tutor1);

        AtualizacaoTutorDto atualizacaoTutor1 = new AtualizacaoTutorDto(99L, "Bryan", "22999999999", "modificado@teste.com");

        //ACT
        service.atualizar(atualizacaoTutor1);

        //ASSERT
        Assertions.assertEquals(atualizacaoTutor1.nome(), tutor1.getNome());
        Assertions.assertEquals(atualizacaoTutor1.telefone(), tutor1.getTelefone());
        Assertions.assertEquals(atualizacaoTutor1.email(), tutor1.getEmail());
    }
}