package br.edu.oficinacampus.apresentacao.compartilhada;

import br.edu.oficinacampus.aplicacao.compartilhada.*;
import br.edu.oficinacampus.dominio.compartilhado.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.time.Instant;
import java.util.Set;

/** Adaptador REST reutilizável com formulários e agregados concretos. */
public abstract class ControladorCadastro<T extends Cadastro<T>, D> {
    public record Entrada<D>(D dados, Long versao) { }
    public record Saida<D>(String id, String modulo, D dados, String estado, long versao, Instant criadoEm, Instant atualizadoEm) { }
    private final String funcionalidade;
    private final ServicoCadastro<T> servico;
    protected ControladorCadastro(String funcionalidade, ServicoCadastro<T> servico) { this.funcionalidade = funcionalidade; this.servico = servico; }
    protected abstract T agregado(D dados, Controle controle);
    protected abstract D formulario(T agregado);
    protected Saida<D> saida(T agregado) {
        Controle c = agregado.controle();
        return new Saida<>(c.id(), funcionalidade, formulario(agregado), c.estado().name(), c.versao(), c.criadoEm(), c.atualizadoEm());
    }
    private void exigirDados(Entrada<D> entrada) { if (entrada.dados() == null) throw ErroDominio.validacao("Informe os dados do cadastro."); }
    @GetMapping
    public Pagina<Saida<D>> listar(@RequestParam(defaultValue="") String q, @RequestParam(defaultValue="TODOS") String estado, @RequestParam(defaultValue="") String oficina, @RequestParam(defaultValue="0") int offset, @RequestParam(defaultValue="100") int limite) {
        Pagina<T> pagina = servico.pesquisar(q, estado, oficina, offset, limite);
        return new Pagina<>(pagina.items().stream().map(this::saida).toList(), pagina.total(), pagina.offset(), pagina.limite());
    }
    @GetMapping("/{id}") public Saida<D> consultar(@PathVariable String id) { return saida(servico.consultar(id)); }
    @PostMapping
    public ResponseEntity<Saida<D>> criar(@RequestBody Entrada<D> entrada) {
        exigirDados(entrada);
        Saida<D> salvo = saida(servico.criar(c -> agregado(entrada.dados(), c)));
        return ResponseEntity.created(URI.create("/api/registros/" + funcionalidade + "/" + salvo.id())).body(salvo);
    }
    @PutMapping("/{id}")
    public Saida<D> editar(@PathVariable String id, @RequestBody Entrada<D> entrada) {
        exigirDados(entrada);
        return saida(servico.editar(id, entrada.versao(), c -> agregado(entrada.dados(), c)));
    }
    @PostMapping("/{id}/{acao}")
    public Saida<D> transitar(@PathVariable String id, @PathVariable String acao, @RequestBody Entrada<D> entrada) {
        if (!Set.of("arquivar", "restaurar", "publicar", "ocultar").contains(acao)) throw ErroDominio.ausente();
        return saida(servico.transitar(id, entrada.versao(), acao));
    }
}
