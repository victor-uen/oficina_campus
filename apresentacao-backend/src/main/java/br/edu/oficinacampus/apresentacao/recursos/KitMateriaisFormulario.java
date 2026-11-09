package br.edu.oficinacampus.apresentacao.recursos;

/** Contrato HTTP tipado; as invariantes são impostas pelo agregado. */
public record KitMateriaisFormulario(String nome, String finalidade, java.util.List<String> componentes, String oficina) { }
