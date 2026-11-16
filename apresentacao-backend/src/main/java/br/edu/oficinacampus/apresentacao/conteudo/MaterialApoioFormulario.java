package br.edu.oficinacampus.apresentacao.conteudo;

/** Contrato HTTP tipado; as invariantes são impostas pelo agregado. */
public record MaterialApoioFormulario(String titulo, String endereco, String descricao, String oficina) { }
