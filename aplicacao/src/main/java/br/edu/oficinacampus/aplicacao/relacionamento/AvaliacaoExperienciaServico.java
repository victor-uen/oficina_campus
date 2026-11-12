package br.edu.oficinacampus.aplicacao.relacionamento;

import br.edu.oficinacampus.dominio.relacionamento.*;
import br.edu.oficinacampus.aplicacao.compartilhada.*;
import br.edu.oficinacampus.aplicacao.cadastros.ServicoVinculadoOficina;
import br.edu.oficinacampus.dominio.cadastros.OficinaRepositorio;
import br.edu.oficinacampus.dominio.compartilhado.*;
import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/** Avaliações: só são aceitas depois que a oficina acontece. */
public final class AvaliacaoExperienciaServico extends ServicoVinculadoOficina<AvaliacaoExperiencia> {
    private final OficinaRepositorio oficinas;
    private final Clock relogio;
    public AvaliacaoExperienciaServico(AvaliacaoExperienciaRepositorio repositorio, Eventos eventos, OficinaRepositorio oficinas, Clock relogio) {
        super(repositorio, eventos, "avaliacoes", new PoliticaCiclo.Padrao(), oficinas);
        this.oficinas = oficinas; this.relogio = relogio;
    }
    @Override
    protected void conferir(AvaliacaoExperiencia nova, AvaliacaoExperiencia anterior) {
        super.conferir(nova, anterior);
        if (anterior != null && nova.oficina().equals(anterior.oficina())) return;
        LocalDateTime agora = LocalDateTime.now(relogio);
        oficinas.buscar(nova.oficina()).filter(o -> !o.realizada(agora)).ifPresent(o -> {
            throw ErroDominio.conflito("A oficina \"" + o.titulo() + "\" ainda não aconteceu. A avaliação fica disponível depois do término.");
        });
    }
    protected List<String> valoresPesquisa(AvaliacaoExperiencia agregado) {
        return List.of(Objects.toString(agregado.nota(), ""), Objects.toString(agregado.aspecto(), ""), Objects.toString(agregado.comentario(), ""), Objects.toString(agregado.nome(), ""));
    }
}
