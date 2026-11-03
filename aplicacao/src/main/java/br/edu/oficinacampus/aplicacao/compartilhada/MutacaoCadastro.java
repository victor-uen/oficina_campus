package br.edu.oficinacampus.aplicacao.compartilhada;

import br.edu.oficinacampus.dominio.compartilhado.*;

/** Template Method: consultar, conferir a versão, transformar e persistir. */
public abstract class MutacaoCadastro<T extends Cadastro<T>> {
    private final Repositorio<T> repositorio;
    protected MutacaoCadastro(Repositorio<T> repositorio) { this.repositorio = repositorio; }
    public final T executar(String id, Long versao) {
        if (versao == null || versao < 0) throw ErroDominio.validacao("Informe a versão atual da ficha.");
        T atual = repositorio.buscar(id).orElseThrow(ErroDominio::ausente);
        if (atual.controle().versao() != versao) throw ErroDominio.conflito("Esta ficha mudou. Recarregue e revise antes de salvar.");
        return repositorio.salvar(transformar(atual), false);
    }
    protected abstract T transformar(T atual);
}
