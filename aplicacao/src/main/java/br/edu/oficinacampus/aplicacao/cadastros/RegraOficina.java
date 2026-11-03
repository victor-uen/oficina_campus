package br.edu.oficinacampus.aplicacao.cadastros;

import br.edu.oficinacampus.dominio.cadastros.Oficina;

/**
 * Regra de outro contexto que precisa aprovar a agenda ou o limite de uma oficina
 * antes de ela ser gravada ou restaurada (por exemplo, equipamentos reservados e
 * participantes já incluídos). Cadastros publica a porta; os demais contextos a implementam.
 */
public interface RegraOficina {
    /** Lança {@code ErroDominio} quando a oficina, como ficará gravada, viola a regra. */
    void conferir(Oficina oficina);
}
