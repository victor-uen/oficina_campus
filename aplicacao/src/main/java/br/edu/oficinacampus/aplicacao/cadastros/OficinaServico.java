package br.edu.oficinacampus.aplicacao.cadastros;

import br.edu.oficinacampus.dominio.cadastros.*;
import br.edu.oficinacampus.aplicacao.compartilhada.*;
import br.edu.oficinacampus.dominio.compartilhado.*;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

/**
 * Oficinas com data, espaço, instrutores e limite de participantes.
 * Confere a capacidade do espaço e evita conflito de agenda: duas oficinas ativas
 * não podem usar o mesmo espaço nem o mesmo instrutor em horários sobrepostos.
 */
public final class OficinaServico extends ServicoCadastro<Oficina> {
    private final OficinaRepositorio oficinas;
    private final EspacoRepositorio espacos;
    private final InstrutorRepositorio instrutores;
    private final Supplier<List<RegraOficina>> regras;
    public OficinaServico(OficinaRepositorio repositorio, Eventos eventos, EspacoRepositorio espacos, InstrutorRepositorio instrutores, Supplier<List<RegraOficina>> regras) {
        super(repositorio, eventos, "oficinas", new PoliticaCiclo.Padrao());
        this.oficinas = repositorio;
        this.espacos = espacos;
        this.instrutores = instrutores;
        this.regras = regras;
    }
    @Override
    protected void conferir(Oficina nova, Oficina anterior) {
        // Somente vínculos novos exigem registro ativo; os já existentes são preservados.
        if (!nova.espaco().isEmpty() && (anterior == null || !nova.espaco().equals(anterior.espaco())))
            ConferenciaVinculo.exigirAtivo(espacos, nova.espaco(), "Espaço não encontrado. Escolha um espaço do cadastro.",
                "O espaço escolhido está arquivado. Restaure o espaço ou escolha outro.");
        List<String> antes = anterior == null ? List.of() : anterior.instrutores();
        for (String instrutor : nova.instrutores())
            if (!antes.contains(instrutor))
                ConferenciaVinculo.exigirAtivo(instrutores, instrutor, "Instrutor não encontrado. Escolha instrutores do cadastro.",
                    "Um dos instrutores escolhidos está arquivado. Restaure o instrutor ou escolha outro.");
        conferirCapacidade(nova);
        if (nova.controle().estado() == Estado.ATIVO) conferirAgenda(nova);
    }
    @Override
    protected void conferirTransicao(Oficina nova, Oficina anterior) {
        if (nova.controle().estado() == Estado.ATIVO && anterior.controle().estado() != Estado.ATIVO) {
            conferirCapacidade(nova);
            conferirAgenda(nova);
        }
    }
    private void conferirCapacidade(Oficina nova) {
        if (!nova.usaEspaco()) return;
        espacos.buscar(nova.espaco()).map(Espaco::capacidade).filter(Objects::nonNull).ifPresent(capacidade -> {
            if (nova.limite() > capacidade)
                throw ErroDominio.validacao("O limite de participantes (" + nova.limite() + ") passa da capacidade do espaço (" + capacidade + ").");
        });
    }
    private void conferirAgenda(Oficina nova) {
        for (Oficina outra : oficinas.listar()) {
            if (outra.controle().id().equals(nova.controle().id()) || outra.controle().estado() != Estado.ATIVO || !outra.sobrepoe(nova)) continue;
            if (nova.usaEspaco() && outra.usaEspaco() && nova.espaco().equals(outra.espaco())) {
                String nome = espacos.buscar(nova.espaco()).map(Espaco::nome).orElse("escolhido");
                throw ErroDominio.conflito("Conflito de agenda: o espaço " + nome + " já está reservado para \"" + outra.titulo() + "\" nesse horário.");
            }
            for (String instrutor : nova.instrutores())
                if (outra.instrutores().contains(instrutor)) {
                    String nome = instrutores.buscar(instrutor).map(Instrutor::nome).orElse("escolhido");
                    throw ErroDominio.conflito("Conflito de agenda: " + nome + " já ministra \"" + outra.titulo() + "\" nesse horário.");
                }
        }
        for (RegraOficina regra : regras.get()) regra.conferir(nova);
    }
    protected List<String> valoresPesquisa(Oficina agregado) {
        return List.of(Objects.toString(agregado.titulo(), ""), Objects.toString(agregado.apresentacao(), ""), Objects.toString(agregado.modalidade(), ""), agregado.data());
    }
}
