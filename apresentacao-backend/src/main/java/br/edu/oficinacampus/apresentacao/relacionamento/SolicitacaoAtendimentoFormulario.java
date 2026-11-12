package br.edu.oficinacampus.apresentacao.relacionamento;

/** Contrato HTTP tipado; as invariantes são impostas pelo agregado. */
public record SolicitacaoAtendimentoFormulario(String assunto, String descricao, String categoria) { }
