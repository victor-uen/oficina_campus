package br.edu.oficinacampus.apresentacao.cadastros;

import br.edu.oficinacampus.apresentacao.compartilhada.ControladorCadastro.Saida;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/** Portal da comunidade: oficinas ativas para consulta. */
@RestController
@RequestMapping("/api/public")
public class ComunidadeCadastrosControlador {
    private final OficinaControlador oficinas;
    public ComunidadeCadastrosControlador(OficinaControlador oficinas) { this.oficinas = oficinas; }
    @GetMapping("/oficinas")
    public List<Saida<OficinaFormulario>> oficinasAtivas() { return oficinas.listar("", "ATIVO", "", 0, 100).items(); }
}
