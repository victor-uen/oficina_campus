package br.edu.oficinacampus.apresentacao.recursos;

import br.edu.oficinacampus.dominio.recursos.RecursoAcessibilidade;
import br.edu.oficinacampus.aplicacao.recursos.RecursoAcessibilidadeServico;
import br.edu.oficinacampus.dominio.compartilhado.Controle;
import br.edu.oficinacampus.apresentacao.compartilhada.ControladorCadastro;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/registros/acessibilidade")
public class RecursoAcessibilidadeControlador extends ControladorCadastro<RecursoAcessibilidade, RecursoAcessibilidadeFormulario> {
    public RecursoAcessibilidadeControlador(RecursoAcessibilidadeServico servico) { super("acessibilidade", servico); }
    protected RecursoAcessibilidade agregado(RecursoAcessibilidadeFormulario dados, Controle controle) {
        return new RecursoAcessibilidade(controle, dados.nome(), dados.categoria(), dados.orientacao(), dados.oficina());
    }
    protected RecursoAcessibilidadeFormulario formulario(RecursoAcessibilidade agregado) { return new RecursoAcessibilidadeFormulario(agregado.nome(), agregado.categoria(), agregado.orientacao(), agregado.oficina()); }
}
