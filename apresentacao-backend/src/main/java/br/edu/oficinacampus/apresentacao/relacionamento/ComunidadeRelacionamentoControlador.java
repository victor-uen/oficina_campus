package br.edu.oficinacampus.apresentacao.relacionamento;

import br.edu.oficinacampus.aplicacao.relacionamento.FichaParticipanteServico;
import br.edu.oficinacampus.apresentacao.compartilhada.ControladorCadastro.Entrada;
import br.edu.oficinacampus.apresentacao.compartilhada.ControladorCadastro.Saida;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

/** Portal da comunidade: vagas ocupadas, manifestação de interesse, avaliação das oficinas realizadas e atendimento. */
@RestController
@RequestMapping("/api/public")
public class ComunidadeRelacionamentoControlador {
    private final ManifestacaoInteresseControlador interesses;
    private final AvaliacaoExperienciaControlador avaliacoes;
    private final SolicitacaoAtendimentoControlador atendimentos;
    private final FichaParticipanteServico participantes;
    public ComunidadeRelacionamentoControlador(ManifestacaoInteresseControlador interesses, AvaliacaoExperienciaControlador avaliacoes, SolicitacaoAtendimentoControlador atendimentos, FichaParticipanteServico participantes) {
        this.interesses = interesses; this.avaliacoes = avaliacoes; this.atendimentos = atendimentos; this.participantes = participantes;
    }
    /** Vagas ocupadas por oficina; oficinas sem participantes não aparecem. */
    @GetMapping("/vagas") public Map<String, Long> vagas() { return participantes.ocupadas(); }
    @PostMapping("/interesses")
    public ResponseEntity<Saida<ManifestacaoInteresseFormulario>> manifestarInteresse(@RequestBody Entrada<ManifestacaoInteresseFormulario> entrada) { return interesses.registrar(entrada); }
    @PostMapping("/avaliacoes")
    public ResponseEntity<Saida<AvaliacaoExperienciaFormulario>> avaliar(@RequestBody Entrada<AvaliacaoExperienciaFormulario> entrada) { return avaliacoes.criar(entrada); }
    @PostMapping("/atendimentos")
    public ResponseEntity<Saida<SolicitacaoAtendimentoFormulario>> pedirAtendimento(@RequestBody Entrada<SolicitacaoAtendimentoFormulario> entrada) { return atendimentos.registrar(entrada); }
}
