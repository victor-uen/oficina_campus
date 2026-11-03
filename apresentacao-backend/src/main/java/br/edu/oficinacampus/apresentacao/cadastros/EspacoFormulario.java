package br.edu.oficinacampus.apresentacao.cadastros;

/** Contrato HTTP tipado; as invariantes são impostas pelo agregado. */
public record EspacoFormulario(String nome, String localizacao, String descricao, Integer capacidade) { }
