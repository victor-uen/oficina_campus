package br.edu.oficinacampus.bdd;
import br.edu.oficinacampus.CampusApplication;
import br.edu.oficinacampus.apresentacao.metadados.CatalogoFormularios;
import br.edu.oficinacampus.apresentacao.metadados.Formulario;
import io.cucumber.java.pt.*;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
@CucumberContextConfiguration
@SpringBootTest(classes=CampusApplication.class,webEnvironment=SpringBootTest.WebEnvironment.RANDOM_PORT,properties={"spring.datasource.url=jdbc:h2:mem:bdd;DB_CLOSE_DELAY=-1","spring.jpa.hibernate.ddl-auto=create-drop","campus.agora=2026-10-20T12:00"})
public class PassosCadastro {
 @Autowired TestRestTemplate http;
 @Autowired CatalogoFormularios catalogo;
 private Formulario modulo;
 private Map<String,Object> dados,registro,antes;
 private ResponseEntity<Map> resposta;
 private long quantidade;
 /** Os exemplos indicam outras fichas como "@modulo:N"; cada cenário cria a ficha correspondente. */
 private Map<String,Object> resolver(Map<String,Object> exemplo){
  var resolvido=new LinkedHashMap<String,Object>();
  exemplo.forEach((k,v)->resolvido.put(k,referencia(v)));
  return resolvido;
 }
 private Object referencia(Object valor){
  if(valor instanceof List<?> lista)return lista.stream().map(this::referencia).toList();
  if(!(valor instanceof String s)||!s.matches("@\\w+:\\d+"))return valor;
  String[] partes=s.substring(1).split(":");
  return criarExemplo(partes[0],Integer.parseInt(partes[1]));
 }
 private String criarExemplo(String modulo,int indice){
  Map criada=http.postForObject((catalogo.obter(modulo).create()?"/api/registros/":"/api/public/")+modulo,Map.of("dados",resolver(catalogo.obter(modulo).seeds().get(indice))),Map.class);
  return (String)criada.get("id");
 }
 private String novaOficina(int indice){return criarExemplo("oficinas",indice);}
 private void arquivar(String modulo,String id){
  Map atual=http.getForObject("/api/registros/"+modulo+"/"+id,Map.class);
  assertEquals(200,http.postForEntity("/api/registros/"+modulo+"/"+id+"/arquivar",Map.of("versao",atual.get("versao")),Map.class).getStatusCode().value());
 }
 private void arquivarOficina(String id){arquivar("oficinas",id);}
 @Dado("o cadastro de {string} com dados válidos")
 public void preparar(String nome){modulo=catalogo.obter(nome);dados=resolver(modulo.seeds().get(0));registro=null;antes=null;}
 private String url(){return "/api/registros/"+modulo.id();}
 private void guardar(){if(resposta.getStatusCode().is2xxSuccessful())registro=new LinkedHashMap<>(resposta.getBody());}
 @Quando("crio a ficha") public void criar(){resposta=http.postForEntity(url(),Map.of("dados",dados),Map.class);guardar();}
 @Quando("envio a ficha pelo portal da comunidade") public void enviarPeloPortal(){assertTrue(modulo.community(),modulo.id()+" não recebe fichas pelo portal");resposta=http.postForEntity("/api/public/"+modulo.id(),Map.of("dados",dados),Map.class);guardar();}
 @Entao("a resposta deve ter código {int}") public void codigo(int status){assertEquals(status,resposta.getStatusCode().value(),String.valueOf(resposta.getBody()));}
 @Entao("o estado deve ser {string}") public void estado(String esperado){assertEquals(esperado,registro.get("estado"));}
 @Entao("a ficha deve continuar disponível para consulta") public void consultar(){var consultada=http.getForEntity(url()+"/"+registro.get("id"),Map.class);assertEquals(200,consultada.getStatusCode().value());assertEquals(registro,consultada.getBody());}
 @Quando("altero um campo com informação válida") public void alterar(){var f=modulo.fields().get(0);dados.put(f.key(),f.kind().equals("number")?3:"Cadastro revisado para apresentação");resposta=http.exchange(url()+"/"+registro.get("id"),HttpMethod.PUT,new HttpEntity<>(Map.of("dados",dados,"versao",registro.get("versao"))),Map.class);guardar();}
 @Quando("solicito {string}") public void acao(String acao){resposta=http.postForEntity(url()+"/"+registro.get("id")+"/"+acao,Map.of("versao",registro.get("versao")),Map.class);guardar();}
 @Entao("os dados da ficha devem ser preservados") public void dadosPreservados(){assertEquals(dados,registro.get("dados"));}
 @Quando("deixo um campo obrigatório vazio") public void vazio(){var f=modulo.fields().stream().filter(x->x.required()).findFirst().orElseThrow();dados.put(f.key(),null);}
 @Dado("a quantidade de fichas antes da tentativa") public void contar(){quantidade=((Number)http.getForObject(url(),Map.class).get("total")).longValue();}
 @Entao("nenhuma ficha deve ser adicionada") public void semCriacao(){assertEquals(quantidade,((Number)http.getForObject(url(),Map.class).get("total")).longValue());}
 @Dado("uma cópia anterior da versão") public void copiar(){antes=new LinkedHashMap<>(registro);}
 @Quando("tento sobrescrever usando a versão anterior") public void antiga(){resposta=http.exchange(url()+"/"+registro.get("id"),HttpMethod.PUT,new HttpEntity<>(Map.of("dados",antes.get("dados"),"versao",antes.get("versao"))),Map.class);}
 @Entao("a ficha {string} aparecer na consulta pública") public void publico(String deve){var list=http.getForObject(modulo.id().equals("faq")?"/api/public/faq":"/api/public/conteudo",List.class);boolean existe=list.stream().anyMatch(x->((Map)x).get("id").equals(registro.get("id")));assertEquals(deve.equals("deve"),existe);}
 @Entao("a oficina {string} aparecer no portal da comunidade") public void oficinaNoPortal(String deve){var list=http.getForObject("/api/public/oficinas",List.class);boolean existe=list.stream().anyMatch(x->((Map)x).get("id").equals(registro.get("id")));assertEquals(deve.equals("deve"),existe);}
 @Quando("informo os componentes {string}") public void componentes(String tipo){dados.put("componentes",tipo.equals("vazios")?List.of():List.of("Papel","Caneta"));}
 @Quando("informo o campo {string} com {string}") public void campo(String campo,String valor){dados.put(campo,valor);}
 // Vínculo com a oficina do catálogo
 @Quando("deixo a oficina em branco") public void semOficina(){dados.put("oficina",null);}
 @Quando("informo uma oficina inexistente") public void oficinaInexistente(){dados.put("oficina","oficina-inexistente");}
 @Dado("que a oficina escolhida está arquivada") public void oficinaEscolhidaArquivada(){arquivarOficina((String)dados.get("oficina"));}
 @Quando("a oficina vinculada é arquivada") public void oficinaVinculadaArquivada(){arquivarOficina((String)((Map)registro.get("dados")).get("oficina"));}
 @Quando("vinculo a ficha a uma oficina arquivada") public void vincularArquivada(){String outra=novaOficina(1);arquivarOficina(outra);dados.put("oficina",outra);resposta=http.exchange(url()+"/"+registro.get("id"),HttpMethod.PUT,new HttpEntity<>(Map.of("dados",dados,"versao",registro.get("versao"))),Map.class);guardar();}
 // Oficina com espaço e instrutores do catálogo
 @Quando("informo um instrutor inexistente") public void instrutorInexistente(){dados.put("instrutores",List.of("instrutor-inexistente"));}
 @Dado("que o instrutor escolhido está arquivado") public void instrutorEscolhidoArquivado(){arquivar("instrutores",(String)((List)dados.get("instrutores")).get(0));}
 @Quando("o instrutor da oficina é arquivado") public void instrutorDaOficinaArquivado(){arquivar("instrutores",(String)((List)((Map)registro.get("dados")).get("instrutores")).get(0));}
 @Dado("que o espaço escolhido está arquivado") public void espacoEscolhidoArquivado(){arquivar("espacos",(String)dados.get("espaco"));}
 @Entao("a ficha deve aparecer na consulta da oficina") public void consultaDaOficina(){String oficina=(String)((Map)registro.get("dados")).get("oficina");Map pagina=http.getForObject(url()+"?oficina={o}",Map.class,oficina);List<Map> itens=(List<Map>)pagina.get("items");assertTrue(itens.stream().anyMatch(r->r.get("id").equals(registro.get("id"))));assertTrue(itens.stream().allMatch(r->oficina.equals(((Map)r.get("dados")).get("oficina"))));}
 // Regras de agenda, vagas e oficinas realizadas
 private Map dadosDe(Map ficha){return (Map)ficha.get("dados");}
 private Map consultarFicha(String modulo,String id){return http.getForObject("/api/registros/"+modulo+"/"+id,Map.class);}
 private void editarFicha(String modulo,Map ficha,Map novos){assertEquals(200,http.exchange("/api/registros/"+modulo+"/"+ficha.get("id"),HttpMethod.PUT,new HttpEntity<>(Map.of("dados",novos,"versao",ficha.get("versao"))),Map.class).getStatusCode().value());}
 @Quando("deixo o campo {string} vazio") public void campoVazio(String campo){dados.put(campo,null);}
 @Entao("a mensagem deve começar com {string}") public void mensagem(String inicio){String m=String.valueOf(resposta.getBody().get("mensagem"));assertTrue(m.startsWith(inicio),m);}
 @Quando("informo um limite acima da capacidade do espaço") public void acimaDaCapacidade(){Map espaco=consultarFicha("espacos",(String)dados.get("espaco"));dados.put("limite",((Number)dadosDe(espaco).get("capacidade")).intValue()+1);}
 @Dado("que já existe outra oficina {string} e horário") public void outraOficina(String igual){
  var outra=new LinkedHashMap<String,Object>(dados);
  if(igual.equals("no mesmo espaço"))outra.put("instrutores",List.of(criarExemplo("instrutores",1)));
  else outra.put("espaco",criarExemplo("espacos",0));
  outra.put("titulo","Outra oficina no mesmo horário");
  assertEquals(201,http.postForEntity(url(),Map.of("dados",outra),Map.class).getStatusCode().value());
 }
 @Quando("relaciono a ficha a {string}") public void relacionar(String caso){
  switch(caso){
   case "nenhuma oficina" -> dados.put("oficinas",List.of());
   case "oficinas em dias diferentes" -> dados.put("oficinas",List.of(novaOficina(0),novaOficina(2)));
   case "oficinas no mesmo horário" -> dados.put("oficinas",List.of(novaOficina(0),novaOficina(0)));
   case "uma oficina inexistente" -> dados.put("oficinas",List.of("oficina-inexistente"));
   case "uma oficina arquivada" -> {String id=novaOficina(1);arquivarOficina(id);dados.put("oficinas",List.of(id));}
   default -> throw new AssertionError(caso);
  }
 }
 @Entao("a ficha deve aparecer na consulta de cada oficina") public void consultaDeCadaOficina(){for(Object o:(List)dadosDe(registro).get("oficinas")){List<Map> itens=(List<Map>)http.getForObject(url()+"?oficina={o}",Map.class,o).get("items");assertTrue(itens.stream().anyMatch(r->r.get("id").equals(registro.get("id"))),String.valueOf(o));}}
 @Dado("que a oficina escolhida já está lotada") public void lotada(){
  String id=(String)dados.get("oficina");Map oficina=consultarFicha("oficinas",id);var novos=new LinkedHashMap<String,Object>(dadosDe(oficina));novos.put("limite",1);editarFicha("oficinas",oficina,novos);
  var p=new LinkedHashMap<String,Object>(resolver(catalogo.obter("participantes").seeds().get(0)));p.put("oficina",id);
  assertEquals(201,http.postForEntity("/api/registros/participantes",Map.of("dados",p),Map.class).getStatusCode().value());
 }
 @Dado("que a oficina escolhida já aconteceu") public void jaAconteceu(){dados.put("oficina",novaOficina(3));}
 @Dado("que a oficina escolhida ainda não aconteceu") public void aindaNaoAconteceu(){dados.put("oficina",novaOficina(0));}
 @Entao("a situação deve ser {string}") public void situacao(String esperada){assertEquals(esperada,dadosDe(registro).get("situacao"));}
 @Entao("a oficina deve ter {int} participantes") public void participantes(int n){List<Map> itens=(List<Map>)http.getForObject("/api/registros/participantes?oficina={o}",Map.class,dadosDe(registro).get("oficina")).get("items");assertEquals(n,itens.size());}
 @Entao("as vagas ocupadas da oficina devem ser {int}") public void vagas(int n){Object v=http.getForObject("/api/public/vagas",Map.class).get(dadosDe(registro).get("oficina"));assertEquals(n,v==null?0:((Number)v).intValue());}
}
