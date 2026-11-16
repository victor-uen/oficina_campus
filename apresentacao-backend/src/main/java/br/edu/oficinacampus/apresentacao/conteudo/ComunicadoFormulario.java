package br.edu.oficinacampus.apresentacao.conteudo;

/** Contrato HTTP tipado; as invariantes são impostas pelo agregado. */
public record ComunicadoFormulario(String titulo, String mensagem, String etiqueta) { }
