package br.edu.oficinacampus.apresentacao.recursos;

/** Contrato HTTP tipado; as invariantes são impostas pelo agregado. */
public record FichaEquipamentoFormulario(String nome, String tipo, String instrucoes, java.util.List<String> oficinas) { }
