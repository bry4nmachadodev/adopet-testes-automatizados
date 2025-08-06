package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.dto.AtualizacaoTutorDto;
import br.com.alura.adopet.api.dto.CadastroTutorDto;
import br.com.alura.adopet.api.model.Pet;
import br.com.alura.adopet.api.model.Tutor;
import br.com.alura.adopet.api.repository.TutorRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

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
}