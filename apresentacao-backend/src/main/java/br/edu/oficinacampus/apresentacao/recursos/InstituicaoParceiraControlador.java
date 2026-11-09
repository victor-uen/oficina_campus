package br.edu.oficinacampus.apresentacao.recursos;

import br.edu.oficinacampus.dominio.recursos.InstituicaoParceira;
import br.edu.oficinacampus.aplicacao.recursos.InstituicaoParceiraServico;
import br.edu.oficinacampus.dominio.compartilhado.Controle;
import br.edu.oficinacampus.apresentacao.compartilhada.ControladorCadastro;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/registros/parceiras")
public class InstituicaoParceiraControlador extends ControladorCadastro<InstituicaoParceira, InstituicaoParceiraFormulario> {
    public InstituicaoParceiraControlador(InstituicaoParceiraServico servico) { super("parceiras", servico); }
    protected InstituicaoParceira agregado(InstituicaoParceiraFormulario dados, Controle controle) {
        return new InstituicaoParceira(controle, dados.nome(), dados.modalidade(), dados.contato(), dados.oficinas());
    }
    protected InstituicaoParceiraFormulario formulario(InstituicaoParceira agregado) { return new InstituicaoParceiraFormulario(agregado.nome(), agregado.modalidade(), agregado.contato(), agregado.oficinas()); }
}
