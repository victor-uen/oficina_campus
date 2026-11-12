package br.edu.oficinacampus.aplicacao.relacionamento;

import br.edu.oficinacampus.dominio.relacionamento.*;
import br.edu.oficinacampus.aplicacao.compartilhada.*;
import br.edu.oficinacampus.aplicacao.cadastros.ServicoVinculadoOficina;
import br.edu.oficinacampus.dominio.cadastros.*;
import br.edu.oficinacampus.dominio.compartilhado.*;
import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

/**
 * Manifestações de interesse enviadas pelo portal. Não há cadastro manual nem edição:
 * a organização aceita (e a pessoa vira participante da oficina) ou recusa.
 */
public final class ManifestacaoInteresseServico extends ServicoVinculadoOficina<ManifestacaoInteresse> {
    private final OficinaRepositorio oficinas;
    private final FichaParticipanteServico participantes;
    private final Clock relogio;
    public ManifestacaoInteresseServico(ManifestacaoInteresseRepositorio repositorio, Eventos eventos, OficinaRepositorio oficinas, FichaParticipanteServico participantes, Clock relogio) {
        super(repositorio, eventos, "interesses", new PoliticaCiclo.Padrao(), oficinas);
        this.oficinas = oficinas; this.participantes = participantes; this.relogio = relogio;
    }
    @Override
    protected void conferir(ManifestacaoInteresse nova, ManifestacaoInteresse anterior) {
        super.conferir(nova, anterior);
        if (!nova.pendente()) throw ErroDominio.validacao("Uma nova manifestação de interesse começa pendente.");
        Oficina oficina = oficinaDisponivel(nova.oficina());
        participantes.exigirVaga(oficina.controle().id(), "");
    }
    @Override
    public ManifestacaoInteresse editar(String id, Long versao, Function<Controle, ManifestacaoInteresse> fabrica) {
        throw ErroDominio.conflito("A manifestação de interesse não é editada: aceite ou recuse.");
    }
    /** Aceita o interesse e inclui a pessoa como participante da oficina. */
    public ManifestacaoInteresse aceitar(String id, Long versao) {
        return alterar(id, versao, atual -> {
            exigirPendente(atual);
            oficinaDisponivel(atual.oficina());
            participantes.criar(c -> new FichaParticipante(c, atual.nome(), atual.email(), atual.telefone(), atual.profissao(),
                "Incluído a partir da manifestação de interesse.", atual.oficina()));
            return atual.comSituacao(ManifestacaoInteresse.ACEITA).comControle(atual.controle().atualizar(atual.controle().estado()));
        });
    }
    public ManifestacaoInteresse recusar(String id, Long versao) {
        return alterar(id, versao, atual -> {
            exigirPendente(atual);
            return atual.comSituacao(ManifestacaoInteresse.RECUSADA).comControle(atual.controle().atualizar(atual.controle().estado()));
        });
    }
    private void exigirPendente(ManifestacaoInteresse atual) {
        if (atual.controle().estado() != Estado.ATIVO) throw ErroDominio.conflito("Restaure a manifestação antes de aceitar ou recusar.");
        if (!atual.pendente()) throw ErroDominio.conflito("Esta manifestação já foi " + atual.situacao().toLowerCase() + ".");
    }
    private Oficina oficinaDisponivel(String id) {
        Oficina oficina = oficinas.buscar(id).orElseThrow(() -> ErroDominio.validacao("Oficina não encontrada. Escolha uma oficina do cadastro."));
        if (oficina.controle().estado() != Estado.ATIVO) throw ErroDominio.conflito("A oficina está arquivada.");
        if (oficina.realizada(LocalDateTime.now(relogio))) throw ErroDominio.conflito("Esta oficina já aconteceu.");
        return oficina;
    }
    protected List<String> valoresPesquisa(ManifestacaoInteresse agregado) {
        return List.of(Objects.toString(agregado.nome(), ""), Objects.toString(agregado.email(), ""), Objects.toString(agregado.telefone(), ""),
            Objects.toString(agregado.profissao(), ""), Objects.toString(agregado.situacao(), ""));
    }
}
