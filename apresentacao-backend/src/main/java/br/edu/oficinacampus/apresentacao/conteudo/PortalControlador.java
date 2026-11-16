package br.edu.oficinacampus.apresentacao.conteudo;

import org.springframework.web.bind.annotation.*;
import java.util.*;

/** Portal da comunidade: somente os conteúdos publicados (visíveis). */
@RestController
public class PortalControlador {
    private final MaterialApoioControlador materiais;
    private final GuiaPreparacaoControlador guias;
    private final LeituraComplementarControlador leituras;
    private final ComunicadoControlador comunicados;
    public PortalControlador(MaterialApoioControlador materiais, GuiaPreparacaoControlador guias, LeituraComplementarControlador leituras, ComunicadoControlador comunicados) {
        this.materiais = materiais; this.guias = guias; this.leituras = leituras; this.comunicados = comunicados;
    }
    @GetMapping("/api/public/conteudo") public List<Object> consultar() {
        List<Object> visiveis = new ArrayList<>();
        visiveis.addAll(materiais.listar("", "VISIVEL", "", 0, 100).items());
        visiveis.addAll(guias.listar("", "VISIVEL", "", 0, 100).items());
        visiveis.addAll(leituras.listar("", "VISIVEL", "", 0, 100).items());
        visiveis.addAll(comunicados.listar("", "VISIVEL", "", 0, 100).items());
        return List.copyOf(visiveis);
    }
}
