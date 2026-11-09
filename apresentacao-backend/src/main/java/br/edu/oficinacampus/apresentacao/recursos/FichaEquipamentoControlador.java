package br.edu.oficinacampus.apresentacao.recursos;

import br.edu.oficinacampus.dominio.recursos.FichaEquipamento;
import br.edu.oficinacampus.aplicacao.recursos.FichaEquipamentoServico;
import br.edu.oficinacampus.dominio.compartilhado.Controle;
import br.edu.oficinacampus.apresentacao.compartilhada.ControladorCadastro;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/registros/equipamentos")
public class FichaEquipamentoControlador extends ControladorCadastro<FichaEquipamento, FichaEquipamentoFormulario> {
    public FichaEquipamentoControlador(FichaEquipamentoServico servico) { super("equipamentos", servico); }
    protected FichaEquipamento agregado(FichaEquipamentoFormulario dados, Controle controle) {
        return new FichaEquipamento(controle, dados.nome(), dados.tipo(), dados.instrucoes(), dados.oficinas());
    }
    protected FichaEquipamentoFormulario formulario(FichaEquipamento agregado) { return new FichaEquipamentoFormulario(agregado.nome(), agregado.tipo(), agregado.instrucoes(), agregado.oficinas()); }
}
