package br.edu.oficinacampus.apresentacao.relacionamento;

import br.edu.oficinacampus.dominio.relacionamento.FichaParticipante;
import br.edu.oficinacampus.aplicacao.relacionamento.FichaParticipanteServico;
import br.edu.oficinacampus.dominio.compartilhado.Controle;
import br.edu.oficinacampus.apresentacao.compartilhada.ControladorCadastro;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/registros/participantes")
public class FichaParticipanteControlador extends ControladorCadastro<FichaParticipante, FichaParticipanteFormulario> {
    public FichaParticipanteControlador(FichaParticipanteServico servico) { super("participantes", servico); }
    protected FichaParticipante agregado(FichaParticipanteFormulario dados, Controle controle) {
        return new FichaParticipante(controle, dados.nome(), dados.email(), dados.telefone(), dados.profissao(), dados.observacoes(), dados.oficina());
    }
    protected FichaParticipanteFormulario formulario(FichaParticipante agregado) { return new FichaParticipanteFormulario(agregado.nome(), agregado.email(), agregado.telefone(), agregado.profissao(), agregado.observacoes(), agregado.oficina()); }
}
