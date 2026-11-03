package br.edu.oficinacampus.dominio.compartilhado;

import java.util.List;

/**
 * Agregado geral da instituição que se relaciona com várias oficinas
 * (equipamento reservado, instituição que apoia). Guarda somente as identidades.
 */
public interface VinculadoOficinas {
    /** Identidades das oficinas relacionadas, sem repetição. */
    List<String> oficinas();
}
