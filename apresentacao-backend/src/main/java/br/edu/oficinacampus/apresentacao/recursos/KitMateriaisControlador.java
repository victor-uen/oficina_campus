package br.edu.oficinacampus.apresentacao.recursos;

import br.edu.oficinacampus.dominio.recursos.KitMateriais;
import br.edu.oficinacampus.aplicacao.recursos.KitMateriaisServico;
import br.edu.oficinacampus.dominio.compartilhado.Controle;
import br.edu.oficinacampus.apresentacao.compartilhada.ControladorCadastro;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/registros/kits")
public class KitMateriaisControlador extends ControladorCadastro<KitMateriais, KitMateriaisFormulario> {
    public KitMateriaisControlador(KitMateriaisServico servico) { super("kits", servico); }
    protected KitMateriais agregado(KitMateriaisFormulario dados, Controle controle) {
        return new KitMateriais(controle, dados.nome(), dados.finalidade(), dados.componentes(), dados.oficina());
    }
    protected KitMateriaisFormulario formulario(KitMateriais agregado) { return new KitMateriaisFormulario(agregado.nome(), agregado.finalidade(), agregado.componentes(), agregado.oficina()); }
}
