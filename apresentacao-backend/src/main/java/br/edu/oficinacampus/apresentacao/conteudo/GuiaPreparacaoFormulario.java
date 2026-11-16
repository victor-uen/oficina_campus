package br.edu.oficinacampus.apresentacao.conteudo;

/** Contrato HTTP tipado; as invariantes são impostas pelo agregado. */
public record GuiaPreparacaoFormulario(String titulo, String resumo, String instrucoes, String oficina) { }
