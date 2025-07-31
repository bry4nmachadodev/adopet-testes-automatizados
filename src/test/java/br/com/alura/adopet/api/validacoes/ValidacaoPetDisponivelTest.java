package br.com.alura.adopet.api.validacoes;

import br.com.alura.adopet.api.dto.SolicitacaoAdocaoDto;
import br.com.alura.adopet.api.model.Pet;
import br.com.alura.adopet.api.repository.PetRepository;
import org.hibernate.validator.constraints.ModCheck;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ValidacaoPetDisponivelTest {

    //anotação que indica que metodos dessa classe vao ser usados com mocks
    @InjectMocks
    private ValidacaoPetDisponivel validacao;

    @Mock
    private PetRepository petRepository;

    @Mock
    private Pet pet;

    @Mock
    private SolicitacaoAdocaoDto dto;

    @Test
    @DisplayName("Deveria Permitir A Solicitação De Adoção Do Pet")
    void cenario1(){
        //arrange
        SolicitacaoAdocaoDto dto = new SolicitacaoAdocaoDto(
                7l, 2l, "Motivo qualquer"
        );

        BDDMockito.given(petRepository.getReferenceById(id)).willReturn(pet);


        //assert + act
        Assertions.assertDoesNotThrow(() -> validacao.validar(dto));
    }

}