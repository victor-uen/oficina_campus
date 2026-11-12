package br.edu.oficinacampus.apresentacao.relacionamento;

/** Contrato HTTP tipado; as invariantes são impostas pelo agregado. */
public record AvaliacaoExperienciaFormulario(Integer nota, String aspecto, String comentario, String nome, String oficina) { }
