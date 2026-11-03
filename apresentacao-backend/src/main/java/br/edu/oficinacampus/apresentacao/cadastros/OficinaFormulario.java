package br.edu.oficinacampus.apresentacao.cadastros;

/** Contrato HTTP tipado; as invariantes são impostas pelo agregado. */
public record OficinaFormulario(String titulo, String apresentacao, String modalidade, Integer duracao, Integer limite, String data, String espaco, java.util.List<String> instrutores) { }
