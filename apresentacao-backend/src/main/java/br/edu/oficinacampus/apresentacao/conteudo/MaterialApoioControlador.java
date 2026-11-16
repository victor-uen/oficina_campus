package br.edu.oficinacampus.apresentacao.conteudo;

import br.edu.oficinacampus.dominio.conteudo.MaterialApoio;
import br.edu.oficinacampus.aplicacao.conteudo.MaterialApoioServico;
import br.edu.oficinacampus.dominio.compartilhado.Controle;
import br.edu.oficinacampus.apresentacao.compartilhada.ControladorCadastro;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/registros/materiais")
public class MaterialApoioControlador extends ControladorCadastro<MaterialApoio, MaterialApoioFormulario> {
    public MaterialApoioControlador(MaterialApoioServico servico) { super("materiais", servico); }
    protected MaterialApoio agregado(MaterialApoioFormulario dados, Controle controle) {
        return new MaterialApoio(controle, dados.titulo(), dados.endereco(), dados.descricao(), dados.oficina());
    }
    protected MaterialApoioFormulario formulario(MaterialApoio agregado) { return new MaterialApoioFormulario(agregado.titulo(), agregado.endereco(), agregado.descricao(), agregado.oficina()); }
}
