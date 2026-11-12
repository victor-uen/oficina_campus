package br.edu.oficinacampus.aplicacao.relacionamento;

import br.edu.oficinacampus.dominio.relacionamento.*;
import br.edu.oficinacampus.aplicacao.compartilhada.*;
import br.edu.oficinacampus.aplicacao.cadastros.ServicoVinculadoOficina;
import br.edu.oficinacampus.dominio.cadastros.*;
import br.edu.oficinacampus.dominio.compartilhado.*;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Participantes de cada oficina. As vagas ocupadas são os participantes ativos;
 * arquivar um participante libera a vaga.
 */
public final class FichaParticipanteServico extends ServicoVinculadoOficina<FichaParticipante> {
    private final OficinaRepositorio oficinas;
    public FichaParticipanteServico(FichaParticipanteRepositorio repositorio, Eventos eventos, OficinaRepositorio oficinas) {
        super(repositorio, eventos, "participantes", new PoliticaCiclo.Padrao(), oficinas);
        this.oficinas = oficinas;
    }
    @Override
    protected void conferir(FichaParticipante novo, FichaParticipante anterior) {
        super.conferir(novo, anterior);
        if (anterior == null || !novo.oficina().equals(anterior.oficina())) exigirVaga(novo.oficina(), novo.controle().id());
    }
    @Override
    protected void conferirTransicao(FichaParticipante novo, FichaParticipante anterior) {
        if (novo.controle().estado() == Estado.ATIVO && anterior.controle().estado() != Estado.ATIVO) exigirVaga(novo.oficina(), novo.controle().id());
    }
    /** Vagas ocupadas por oficina (participantes ativos). */
    public Map<String, Long> ocupadas() {
        return listar().stream().filter(p -> p.controle().estado() == Estado.ATIVO)
            .collect(Collectors.groupingBy(FichaParticipante::oficina, Collectors.counting()));
    }
    /** Lança conflito quando a oficina não tem mais vagas. */
    public void exigirVaga(String oficina, String ignorar) {
        Oficina o = oficinas.buscar(oficina).orElseThrow(() -> ErroDominio.validacao("Oficina não encontrada. Escolha uma oficina do cadastro."));
        long ocupadas = listar().stream().filter(p -> p.controle().estado() == Estado.ATIVO && oficina.equals(p.oficina()) && !p.controle().id().equals(ignorar)).count();
        if (ocupadas >= o.limite()) throw ErroDominio.conflito("Vagas esgotadas: a oficina \"" + o.titulo() + "\" já tem " + ocupadas + " de " + o.limite() + " participantes.");
    }
    protected List<String> valoresPesquisa(FichaParticipante agregado) {
        return List.of(Objects.toString(agregado.nome(), ""), Objects.toString(agregado.email(), ""), Objects.toString(agregado.telefone(), ""),
            Objects.toString(agregado.profissao(), ""), Objects.toString(agregado.observacoes(), ""));
    }
}
