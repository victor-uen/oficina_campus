package br.edu.oficinacampus.apresentacao.conteudo;

import br.edu.oficinacampus.dominio.conteudo.GuiaPreparacao;
import br.edu.oficinacampus.aplicacao.conteudo.GuiaPreparacaoServico;
import br.edu.oficinacampus.dominio.compartilhado.Controle;
import br.edu.oficinacampus.apresentacao.compartilhada.ControladorCadastro;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/registros/guias")
public class GuiaPreparacaoControlador extends ControladorCadastro<GuiaPreparacao, GuiaPreparacaoFormulario> {
    public GuiaPreparacaoControlador(GuiaPreparacaoServico servico) { super("guias", servico); }
    protected GuiaPreparacao agregado(GuiaPreparacaoFormulario dados, Controle controle) {
        return new GuiaPreparacao(controle, dados.titulo(), dados.resumo(), dados.instrucoes(), dados.oficina());
    }
    protected GuiaPreparacaoFormulario formulario(GuiaPreparacao agregado) { return new GuiaPreparacaoFormulario(agregado.titulo(), agregado.resumo(), agregado.instrucoes(), agregado.oficina()); }
}
