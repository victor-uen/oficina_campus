package br.edu.oficinacampus.apresentacao.metadados;

import java.util.List;
/**
 * Descrição visual; não valida nem substitui o modelo de domínio.
 * {@code ref} indica o cadastro referenciado por um campo do tipo "ref"; {@code requiredUnless}
 * ("campo=valor") torna o campo obrigatório exceto quando outro campo tem aquele valor;
 * {@code readonly} indica um campo exibido, mas definido pelo sistema.
 */
public record CampoFormulario(String key, String label, String kind, int min, int max, boolean required, List<String> options, String hint,
                              String ref, boolean multiple, String requiredUnless, boolean readonly) { }
