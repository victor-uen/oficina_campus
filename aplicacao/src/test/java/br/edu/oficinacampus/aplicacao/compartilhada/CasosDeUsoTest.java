package br.edu.oficinacampus.aplicacao.compartilhada;

import br.edu.oficinacampus.dominio.compartilhado.*;
import org.junit.jupiter.api.Test;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import static org.junit.jupiter.api.Assertions.*;

class CasosDeUsoTest {
    record FichaTeste(Controle controle, String titulo) implements Cadastro<FichaTeste> {
        public FichaTeste comControle(Controle c) { return new FichaTeste(c, titulo); }
    }
    static class Memoria implements Repositorio<FichaTeste> {
        final Map<String,FichaTeste> dados = new HashMap<>();
        boolean falhar;
        public Optional<FichaTeste> buscar(String id) { return Optional.ofNullable(dados.get(id)); }
        public List<FichaTeste> listar() { return List.copyOf(dados.values()); }
        public FichaTeste salvar(FichaTeste ficha, boolean novo) {
            if (falhar) throw new IllegalStateException("Falha simulada antes do commit");
            var c = ficha.controle();
            var salvo = ficha.comControle(new Controle(c.id(), c.estado(), novo ? 0 : c.versao()+1, c.criadoEm(), c.atualizadoEm()));
            dados.put(c.id(), salvo); return salvo;
        }
    }
    static class ServicoTeste extends ServicoCadastro<FichaTeste> {
        ServicoTeste(Memoria repositorio, Eventos eventos) { super(repositorio, eventos, "teste", new PoliticaCiclo.Padrao()); }
        protected List<String> valoresPesquisa(FichaTeste ficha) { return List.of(ficha.titulo()); }
    }
    @Test void templateProtegeVersaoEEstadoEDecoratorNotificaAposSalvar() {
        var memoria = new Memoria(); var eventos = new Eventos(); var historico = new Historico();
        var contador = new AtomicInteger(); eventos.observar(historico::registrar); eventos.observar(e -> contador.incrementAndGet());
        var servico = new ServicoTeste(memoria, eventos);
        var criado = servico.criar(c -> new FichaTeste(c, "Título"));
        assertEquals(1, contador.get());
        var editado = servico.editar(criado.controle().id(), 0L, c -> new FichaTeste(c, "Revisado"));
        assertEquals(1, editado.controle().versao());
        assertThrows(ErroDominio.class, () -> servico.editar(criado.controle().id(), 0L, c -> new FichaTeste(c, "Perdido")));
        assertEquals("Revisado", servico.consultar(criado.controle().id()).titulo());
        assertEquals(2, contador.get());
        var arquivado = servico.transitar(criado.controle().id(), 1L, "arquivar");
        assertThrows(ErroDominio.class, () -> servico.editar(criado.controle().id(), arquivado.controle().versao(), c -> new FichaTeste(c, "Inválido")));
        assertEquals(3, historico.listar().size());
        assertEquals(Estado.ARQUIVADO.name(), historico.listar().get(0).estado());
    }
    @Test void decoratorNaoPublicaFalhaDaPersistencia() {
        var memoria = new Memoria(); memoria.falhar = true;
        var eventos = new Eventos(); var historico = new Historico(); eventos.observar(historico::registrar);
        var servico = new ServicoTeste(memoria, eventos);
        assertThrows(IllegalStateException.class, () -> servico.criar(c -> new FichaTeste(c, "Título")));
        assertTrue(historico.listar().isEmpty()); assertTrue(memoria.dados.isEmpty());
    }
    @Test void consultaAplicaBuscaEstadoELimite() {
        var servico = new ServicoTeste(new Memoria(), new Eventos());
        var a = servico.criar(c -> new FichaTeste(c, "Oficina de Java"));
        servico.criar(c -> new FichaTeste(c, "Oficina de Dados"));
        servico.transitar(a.controle().id(), 0L, "arquivar");
        assertEquals(1, servico.pesquisar("JAVA", "ARQUIVADO", 0, 10).total());
        assertEquals(0, servico.pesquisar("Java", "ATIVO", 0, 10).total());
        assertEquals(1, servico.pesquisar("Oficina", "TODOS", 1, 1).items().size());
        assertThrows(ErroDominio.class, () -> servico.pesquisar("", "ERRO", 0, 10));
        assertThrows(ErroDominio.class, () -> servico.pesquisar("", "TODOS", -1, 10));
    }
}
