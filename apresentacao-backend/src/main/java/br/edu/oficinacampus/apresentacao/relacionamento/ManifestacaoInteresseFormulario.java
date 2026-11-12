package br.edu.oficinacampus.apresentacao.relacionamento;

/** Contrato HTTP tipado; as invariantes são impostas pelo agregado. */
public record ManifestacaoInteresseFormulario(String nome, String email, String telefone, String profissao, String oficina, String situacao) { }
