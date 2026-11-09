package br.edu.oficinacampus.apresentacao.recursos;

/** Contrato HTTP tipado; as invariantes são impostas pelo agregado. */
public record RecursoAcessibilidadeFormulario(String nome, String categoria, String orientacao, String oficina) { }
