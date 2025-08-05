package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.dto.CadastroAbrigoDto;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.repository.AbrigoRepository;
import br.com.alura.adopet.api.repository.PetRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
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

    private CadastroAbrigoDto cadastroDto;

    @Test
    @DisplayName("deve dar ValidacaoException devido a DADOS JÁ CADASTRADOS")
    void cenario01(){
        //ARRANGE
        this.cadastroDto = new CadastroAbrigoDto("Casinha Feliz", "1134567890", "casinhafeliz@teste.com");
        BDDMockito.given(abrigoRepository.existsByNomeOrTelefoneOrEmail(cadastroDto.nome(), cadastroDto.telefone(), cadastroDto.email())).willReturn(true);

        //ASSERT + ACT
        assertThrows(ValidacaoException.class, () -> service.cadastrar(cadastroDto));
    }

    @Test
    @DisplayName("deve SALVAR no banco")
    void cenario02(){
        //ARRANGE

        //ACT

        //ASSERT

    }
}