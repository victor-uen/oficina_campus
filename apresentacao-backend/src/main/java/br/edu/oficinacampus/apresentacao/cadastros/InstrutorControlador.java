package br.edu.oficinacampus.apresentacao.cadastros;

import br.edu.oficinacampus.dominio.cadastros.Instrutor;
import br.edu.oficinacampus.aplicacao.cadastros.InstrutorServico;
import br.edu.oficinacampus.dominio.compartilhado.Controle;
import br.edu.oficinacampus.apresentacao.compartilhada.ControladorCadastro;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/registros/instrutores")
public class InstrutorControlador extends ControladorCadastro<Instrutor, InstrutorFormulario> {
    public InstrutorControlador(InstrutorServico servico) { super("instrutores", servico); }
    protected Instrutor agregado(InstrutorFormulario dados, Controle controle) {
        return new Instrutor(controle, dados.nome(), dados.apresentacao(), dados.especialidade(), dados.contato());
    }
    protected InstrutorFormulario formulario(Instrutor agregado) { return new InstrutorFormulario(agregado.nome(), agregado.apresentacao(), agregado.especialidade(), agregado.contato()); }
}
