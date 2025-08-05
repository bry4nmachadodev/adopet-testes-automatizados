package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.repository.AbrigoRepository;
import br.com.alura.adopet.api.repository.PetRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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

    @Test
    @DisplayName("deve dar ValidacaoException devido a DADOS JÁ CADASTRADOS")
    void cenario01(){
    //ARRANGE

    //ACT

    //ASSERT

    }

    @Test
    @DisplayName("deve SALVAR no banco")
    void cenario02(){
        //ARRANGE

        //ACT

        //ASSERT

    }
}