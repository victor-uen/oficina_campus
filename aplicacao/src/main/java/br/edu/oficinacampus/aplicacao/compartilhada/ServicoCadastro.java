package br.edu.oficinacampus.aplicacao.compartilhada;

import br.edu.oficinacampus.dominio.compartilhado.*;
import java.util.List;
import java.util.Locale;
import java.util.function.Function;

/** Coordenação comum de casos de uso sobre agregados tipados. */
public abstract class ServicoCadastro<T extends Cadastro<T>> {
    private final Repositorio<T> repositorio;
    private final PoliticaCiclo ciclo;
    protected ServicoCadastro(Repositorio<T> repositorio, Eventos eventos, String funcionalidade, PoliticaCiclo ciclo) {
        this.repositorio = new RepositorioObservavel<>(repositorio, eventos, funcionalidade);
        this.ciclo = ciclo;
    }
    public T criar(Function<Controle,T> fabrica) {
        T novo = fabrica.apply(Controle.novo(ciclo.inicial()));
        conferir(novo, null);
        return repositorio.salvar(novo, true);
    }
    public T consultar(String id) { return repositorio.buscar(id).orElseThrow(ErroDominio::ausente); }
    public List<T> listar() { return repositorio.listar(); }
    public T editar(String id, Long versao, Function<Controle,T> fabrica) {
        return new MutacaoCadastro<T>(repositorio) {
            protected T transformar(T atual) {
                if (!ciclo.podeEditar(atual.controle().estado())) throw ErroDominio.conflito("Restaure a ficha antes de editar.");
                T novo = fabrica.apply(atual.controle().atualizar(atual.controle().estado()));
                conferir(novo, atual);
                return novo;
            }
        }.executar(id, versao);
    }
    public T transitar(String id, Long versao, String acao) {
        return new MutacaoCadastro<T>(repositorio) {
            protected T transformar(T atual) {
                T novo = atual.comControle(atual.controle().atualizar(ciclo.transitar(atual.controle().estado(), acao)));
                conferirTransicao(novo, atual);
                return novo;
            }
        }.executar(id, versao);
    }
    public Pagina<T> pesquisar(String q, String estado, int offset, int limite) { return pesquisar(q, estado, "", offset, limite); }
    /** Busca textual, estado e, para fichas vinculadas, a oficina a que pertencem. */
    public Pagina<T> pesquisar(String q, String estado, String oficina, int offset, int limite) {
        if (offset < 0 || limite < 1 || limite > 100) throw ErroDominio.validacao("Paginação inválida.");
        if (!"TODOS".equals(estado)) {
            try { Estado.valueOf(estado); } catch (IllegalArgumentException e) { throw ErroDominio.validacao("Estado inválido."); }
        }
        String consulta = q.trim().toLowerCase(Locale.ROOT);
        String vinculo = oficina == null ? "" : oficina.trim();
        List<T> filtrados = listar().stream()
            .filter(t -> "TODOS".equals(estado) || t.controle().estado().name().equals(estado))
            .filter(t -> vinculo.isEmpty() || t instanceof VinculadoOficina v && vinculo.equals(v.oficina())
                || t instanceof VinculadoOficinas vs && vs.oficinas().contains(vinculo))
            .filter(t -> valoresPesquisa(t).stream().anyMatch(v -> v.toLowerCase(Locale.ROOT).contains(consulta))).toList();
        return new Pagina<>(filtrados.stream().skip(offset).limit(limite).toList(), filtrados.size(), offset, limite);
    }
    /** Ponto de extensão para regras que dependem de outros agregados; {@code anterior} é nulo na criação. */
    protected void conferir(T novo, T anterior) { }
    /** Ponto de extensão para regras ao mudar de estado (por exemplo, ao restaurar uma ficha arquivada). */
    protected void conferirTransicao(T novo, T anterior) { }
    /** Salva uma nova versão produzida pelo próprio serviço, conferindo a versão informada. */
    protected T alterar(String id, Long versao, java.util.function.UnaryOperator<T> mudanca) {
        return new MutacaoCadastro<T>(repositorio) {
            protected T transformar(T atual) { return mudanca.apply(atual); }
        }.executar(id, versao);
    }
    protected abstract List<String> valoresPesquisa(T agregado);
}
