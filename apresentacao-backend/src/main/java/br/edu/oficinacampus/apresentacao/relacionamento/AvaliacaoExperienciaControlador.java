package br.edu.oficinacampus.apresentacao.relacionamento;

import br.edu.oficinacampus.dominio.relacionamento.AvaliacaoExperiencia;
import br.edu.oficinacampus.aplicacao.relacionamento.AvaliacaoExperienciaServico;
import br.edu.oficinacampus.dominio.compartilhado.Controle;
import br.edu.oficinacampus.apresentacao.compartilhada.ControladorCadastro;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/registros/avaliacoes")
public class AvaliacaoExperienciaControlador extends ControladorCadastro<AvaliacaoExperiencia, AvaliacaoExperienciaFormulario> {
    public AvaliacaoExperienciaControlador(AvaliacaoExperienciaServico servico) { super("avaliacoes", servico); }
    protected AvaliacaoExperiencia agregado(AvaliacaoExperienciaFormulario dados, Controle controle) {
        return new AvaliacaoExperiencia(controle, dados.nota(), dados.aspecto(), dados.comentario(), dados.nome(), dados.oficina());
    }
    protected AvaliacaoExperienciaFormulario formulario(AvaliacaoExperiencia agregado) { return new AvaliacaoExperienciaFormulario(agregado.nota(), agregado.aspecto(), agregado.comentario(), agregado.nome(), agregado.oficina()); }
}
