package br.edu.oficinacampus.apresentacao.conteudo;

/** Contrato HTTP tipado; as invariantes são impostas pelo agregado. */
public record LeituraComplementarFormulario(String titulo, String autor, String tipo, String trecho, String obrigatoriedade, Integer ordem, String oficina) { }
