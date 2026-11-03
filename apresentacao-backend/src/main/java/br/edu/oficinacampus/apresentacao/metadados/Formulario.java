package br.edu.oficinacampus.apresentacao.metadados;

import java.util.List;
import java.util.Map;
/**
 * Metadados exclusivos da apresentação e exemplos fictícios.
 * {@code persona} indica quem opera a tela; {@code profiles}, em quais perfis ela aparece;
 * {@code menu}, em quais perfis ela tem item próprio no menu; {@code tab}, a aba da página da
 * oficina em que aparece; {@code community}, se a pessoa interessada envia a ficha pelo portal;
 * {@code create}/{@code edit}/{@code archive}, se a organização cadastra, edita e arquiva; {@code actions}, ações próprias.
 */
public record Formulario(String id, String title, String singular, int owner, String group, String summary, List<CampoFormulario> fields, List<Map<String,Object>> seeds, String story, String activity, String policy,
                         String persona, List<String> profiles, boolean community, String communityStory, String tab, boolean create, boolean edit, List<String> actions, List<String> menu, boolean archive) { }
