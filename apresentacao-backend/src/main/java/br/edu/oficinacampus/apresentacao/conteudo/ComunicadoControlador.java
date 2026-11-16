package br.edu.oficinacampus.apresentacao.conteudo;

import br.edu.oficinacampus.dominio.conteudo.Comunicado;
import br.edu.oficinacampus.aplicacao.conteudo.ComunicadoServico;
import br.edu.oficinacampus.dominio.compartilhado.Controle;
import br.edu.oficinacampus.apresentacao.compartilhada.ControladorCadastro;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/registros/comunicados")
public class ComunicadoControlador extends ControladorCadastro<Comunicado, ComunicadoFormulario> {
    public ComunicadoControlador(ComunicadoServico servico) { super("comunicados", servico); }
    protected Comunicado agregado(ComunicadoFormulario dados, Controle controle) {
        return new Comunicado(controle, dados.titulo(), dados.mensagem(), dados.etiqueta());
    }
    protected ComunicadoFormulario formulario(Comunicado agregado) { return new ComunicadoFormulario(agregado.titulo(), agregado.mensagem(), agregado.etiqueta()); }
}
