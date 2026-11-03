package br.edu.oficinacampus.aplicacao.compartilhada;

import br.edu.oficinacampus.dominio.compartilhado.*;
import java.util.List;
import java.util.Optional;

/** Decorator: notifica depois do retorno do adaptador transacional. */
public final class RepositorioObservavel<T extends Cadastro<T>> implements Repositorio<T> {
    private final Repositorio<T> destino;
    private final Eventos eventos;
    private final String funcionalidade;
    public RepositorioObservavel(Repositorio<T> destino, Eventos eventos, String funcionalidade) {
        this.destino = destino; this.eventos = eventos; this.funcionalidade = funcionalidade;
    }
    public Optional<T> buscar(String id) { return destino.buscar(id); }
    public List<T> listar() { return destino.listar(); }
    public T salvar(T agregado, boolean novo) {
        T salvo = destino.salvar(agregado, novo);
        Controle c = salvo.controle();
        eventos.publicar(new Alteracao(funcionalidade, c.id(), c.estado().name(), c.versao(), c.atualizadoEm()));
        return salvo;
    }
}
