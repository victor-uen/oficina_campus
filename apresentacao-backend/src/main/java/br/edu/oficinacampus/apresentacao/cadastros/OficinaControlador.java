package br.edu.oficinacampus.apresentacao.cadastros;

import br.edu.oficinacampus.dominio.cadastros.Oficina;
import br.edu.oficinacampus.aplicacao.cadastros.OficinaServico;
import br.edu.oficinacampus.dominio.compartilhado.Controle;
import br.edu.oficinacampus.apresentacao.compartilhada.ControladorCadastro;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/registros/oficinas")
public class OficinaControlador extends ControladorCadastro<Oficina, OficinaFormulario> {
    public OficinaControlador(OficinaServico servico) { super("oficinas", servico); }
    protected Oficina agregado(OficinaFormulario dados, Controle controle) {
        return new Oficina(controle, dados.titulo(), dados.apresentacao(), dados.modalidade(), dados.duracao(), dados.limite(), dados.data(), dados.espaco(), dados.instrutores());
    }
    protected OficinaFormulario formulario(Oficina agregado) { return new OficinaFormulario(agregado.titulo(), agregado.apresentacao(), agregado.modalidade(), agregado.duracao(), agregado.limite(), agregado.data(), agregado.espaco(), agregado.instrutores()); }
}
