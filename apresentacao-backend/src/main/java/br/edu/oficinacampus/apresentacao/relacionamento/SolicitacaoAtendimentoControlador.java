package br.edu.oficinacampus.apresentacao.relacionamento;

import br.edu.oficinacampus.dominio.relacionamento.SolicitacaoAtendimento;
import br.edu.oficinacampus.aplicacao.relacionamento.SolicitacaoAtendimentoServico;
import br.edu.oficinacampus.dominio.compartilhado.*;
import br.edu.oficinacampus.apresentacao.compartilhada.ControladorCadastro;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/** Atendimentos: chegam pelo portal da comunidade. A organização somente consulta; não cadastra, não edita e não arquiva. */
@RestController
@RequestMapping("/api/registros/atendimentos")
public class SolicitacaoAtendimentoControlador extends ControladorCadastro<SolicitacaoAtendimento, SolicitacaoAtendimentoFormulario> {
    private static final String SOMENTE_CONSULTA = "Atendimentos são enviados pelo portal da comunidade; a organização apenas consulta.";
    public SolicitacaoAtendimentoControlador(SolicitacaoAtendimentoServico servico) { super("atendimentos", servico); }
    protected SolicitacaoAtendimento agregado(SolicitacaoAtendimentoFormulario dados, Controle controle) {
        return new SolicitacaoAtendimento(controle, dados.assunto(), dados.descricao(), dados.categoria());
    }
    protected SolicitacaoAtendimentoFormulario formulario(SolicitacaoAtendimento agregado) { return new SolicitacaoAtendimentoFormulario(agregado.assunto(), agregado.descricao(), agregado.categoria()); }
    @Override public ResponseEntity<Saida<SolicitacaoAtendimentoFormulario>> criar(@RequestBody Entrada<SolicitacaoAtendimentoFormulario> entrada) { throw ErroDominio.conflito(SOMENTE_CONSULTA); }
    @Override public Saida<SolicitacaoAtendimentoFormulario> editar(@PathVariable String id, @RequestBody Entrada<SolicitacaoAtendimentoFormulario> entrada) { throw ErroDominio.conflito(SOMENTE_CONSULTA); }
    @Override public Saida<SolicitacaoAtendimentoFormulario> transitar(@PathVariable String id, @PathVariable String acao, @RequestBody Entrada<SolicitacaoAtendimentoFormulario> entrada) { throw ErroDominio.conflito(SOMENTE_CONSULTA); }
    /** Envio pelo portal da comunidade. */
    public ResponseEntity<Saida<SolicitacaoAtendimentoFormulario>> registrar(Entrada<SolicitacaoAtendimentoFormulario> entrada) { return super.criar(entrada); }
}
