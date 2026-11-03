package br.edu.oficinacampus.aplicacao.compartilhada;

import br.edu.oficinacampus.dominio.compartilhado.*;

/** Confere, antes de gravar, se o registro referenciado existe e está ativo. */
public final class ConferenciaVinculo {
    private ConferenciaVinculo() { }
    /**
     * @param ausente mensagem quando o registro não existe (400)
     * @param arquivado mensagem quando o registro está arquivado (409)
     */
    public static void exigirAtivo(Repositorio<?> repositorio, String id, String ausente, String arquivado) {
        Cadastro<?> registro = repositorio.buscar(id).orElseThrow(() -> ErroDominio.validacao(ausente));
        if (registro.controle().estado() != Estado.ATIVO) throw ErroDominio.conflito(arquivado);
    }
}
