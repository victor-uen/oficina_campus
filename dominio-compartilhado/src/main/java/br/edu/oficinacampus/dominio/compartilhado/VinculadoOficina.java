package br.edu.oficinacampus.dominio.compartilhado;

/**
 * Agregado que pertence a uma oficina do catálogo.
 * A referência guarda somente a identidade da oficina; a existência e o estado
 * da oficina são conferidos pela aplicação, fora da fronteira do agregado.
 */
public interface VinculadoOficina {
    /** Identidade da oficina vinculada ou texto vazio quando o vínculo é opcional e não foi informado. */
    String oficina();
}
