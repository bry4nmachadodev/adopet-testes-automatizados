package br.com.alura.adopet.api.validacoes;

import br.com.alura.adopet.api.dto.SolicitacaoAdocaoDto;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.StatusAdocao;
import br.com.alura.adopet.api.repository.AdocaoRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ValidacaoPetComAdocaoEmAndamentoTest {
    @Mock
    private AdocaoRepository adocaoRepository;

    @InjectMocks
    private ValidacaoPetComAdocaoEmAndamento validacao;

    @Mock
    private SolicitacaoAdocaoDto dto;

    @Test
    @DisplayName("Deveria retornar uma Validação Exception devido a adoção do pet já estar com andamento")
    void cenario01(){
        //arrange
        BDDMockito.given(dto.idPet()).willReturn(1L);
        BDDMockito.given(adocaoRepository
                        .existsByPetIdAndStatus(dto.idPet(), StatusAdocao.AGUARDANDO_AVALIACAO))
                .willReturn(true);

        //assert + act
        Assertions.assertThrows(ValidacaoException.class, () -> validacao.validar(dto));
    }

    @Test
    @DisplayName("Deveria passar e NÃO retornar uma exception")
    void cenario02(){
        //arrange
        BDDMockito.given(dto.idPet()).willReturn(1L);
        BDDMockito.given(adocaoRepository
                        .existsByPetIdAndStatus(dto.idPet(), StatusAdocao.AGUARDANDO_AVALIACAO))
                .willReturn(false);

        //assert + act
        Assertions.assertDoesNotThrow(() -> validacao.validar(dto));
    }
}