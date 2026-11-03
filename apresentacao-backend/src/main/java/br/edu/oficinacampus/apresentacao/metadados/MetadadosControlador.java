package br.edu.oficinacampus.apresentacao.metadados;

import br.edu.oficinacampus.aplicacao.compartilhada.Historico;
import org.springframework.web.bind.annotation.*;
import java.time.Clock;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Map;

@RestController @RequestMapping("/api")
public class MetadadosControlador {
    private final CatalogoFormularios catalogo;
    private final Historico historico;
    private final Clock relogio;
    public MetadadosControlador(CatalogoFormularios catalogo, Historico historico, Clock relogio) { this.catalogo = catalogo; this.historico = historico; this.relogio = relogio; }
    /** Metadados das telas e a data atual do servidor, usada para marcar as oficinas realizadas. */
    @GetMapping("/meta") public Object meta() {
        return Map.of("modulos", catalogo.todos(), "persistencia", "relacional", "versao", "2.0.0",
            "agora", LocalDateTime.now(relogio).truncatedTo(ChronoUnit.MINUTES).toString());
    }
    @GetMapping("/historico") public Object historico() { return historico.listar(); }
}
