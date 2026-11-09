package br.edu.oficinacampus.apresentacao.recursos;

/** Contrato HTTP tipado; as invariantes são impostas pelo agregado. */
public record InstituicaoParceiraFormulario(String nome, String modalidade, String contato, java.util.List<String> oficinas) { }
