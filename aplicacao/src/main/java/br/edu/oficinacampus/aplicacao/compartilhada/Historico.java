package br.edu.oficinacampus.aplicacao.compartilhada;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

/** Histórico da execução, limitado a 100 alterações; não é auditoria durável. */
public final class Historico {
    private final Deque<Alteracao> alteracoes = new ArrayDeque<>();
    public synchronized void registrar(Alteracao alteracao) {
        alteracoes.addFirst(alteracao);
        while (alteracoes.size() > 100) alteracoes.removeLast();
    }
    public synchronized List<Alteracao> listar() { return List.copyOf(alteracoes); }
}
