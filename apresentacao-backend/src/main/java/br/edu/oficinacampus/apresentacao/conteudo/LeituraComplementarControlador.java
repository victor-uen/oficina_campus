package br.edu.oficinacampus.apresentacao.conteudo;

import br.edu.oficinacampus.dominio.conteudo.LeituraComplementar;
import br.edu.oficinacampus.aplicacao.conteudo.LeituraComplementarServico;
import br.edu.oficinacampus.dominio.compartilhado.Controle;
import br.edu.oficinacampus.apresentacao.compartilhada.ControladorCadastro;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/registros/leituras")
public class LeituraComplementarControlador extends ControladorCadastro<LeituraComplementar, LeituraComplementarFormulario> {
    public LeituraComplementarControlador(LeituraComplementarServico servico) { super("leituras", servico); }
    protected LeituraComplementar agregado(LeituraComplementarFormulario dados, Controle controle) {
        return new LeituraComplementar(controle, dados.titulo(), dados.autor(), dados.tipo(), dados.trecho(), dados.obrigatoriedade(), dados.ordem(), dados.oficina());
    }
    protected LeituraComplementarFormulario formulario(LeituraComplementar agregado) { return new LeituraComplementarFormulario(agregado.titulo(), agregado.autor(), agregado.tipo(), agregado.trecho(), agregado.obrigatoriedade(), agregado.ordem(), agregado.oficina()); }
}
