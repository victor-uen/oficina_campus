package br.edu.oficinacampus.infraestrutura.configuracao;

import br.edu.oficinacampus.dominio.relacionamento.*;
import br.edu.oficinacampus.aplicacao.relacionamento.*;
import br.edu.oficinacampus.aplicacao.compartilhada.Eventos;
import br.edu.oficinacampus.dominio.cadastros.OficinaRepositorio;
import br.edu.oficinacampus.aplicacao.cadastros.RegraOficina;
import java.time.Clock;
import org.springframework.context.annotation.*;

/** Monta os serviços do contexto Relacionamento. */
@Configuration
public class RelacionamentoConfiguracao {
    @Bean public FichaParticipanteServico participantesServico(FichaParticipanteRepositorio repositorio, Eventos eventos, OficinaRepositorio oficinas) { return new FichaParticipanteServico(repositorio, eventos, oficinas); }
    @Bean public ManifestacaoInteresseServico interessesServico(ManifestacaoInteresseRepositorio repositorio, Eventos eventos, OficinaRepositorio oficinas, FichaParticipanteServico participantes, Clock relogio) { return new ManifestacaoInteresseServico(repositorio, eventos, oficinas, participantes, relogio); }
    @Bean public AvaliacaoExperienciaServico avaliacoesServico(AvaliacaoExperienciaRepositorio repositorio, Eventos eventos, OficinaRepositorio oficinas, Clock relogio) { return new AvaliacaoExperienciaServico(repositorio, eventos, oficinas, relogio); }
    @Bean public SolicitacaoAtendimentoServico atendimentosServico(SolicitacaoAtendimentoRepositorio repositorio, Eventos eventos) { return new SolicitacaoAtendimentoServico(repositorio, eventos); }
    @Bean public RegraOficina limiteDeParticipantes(FichaParticipanteRepositorio participantes) { return new LimiteParticipantes(participantes); }
}
