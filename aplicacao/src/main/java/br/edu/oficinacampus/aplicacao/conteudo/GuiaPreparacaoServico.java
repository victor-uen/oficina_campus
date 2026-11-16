package br.edu.oficinacampus.aplicacao.conteudo;

import br.edu.oficinacampus.dominio.conteudo.*;
import br.edu.oficinacampus.aplicacao.compartilhada.*;
import br.edu.oficinacampus.dominio.compartilhado.PoliticaCiclo;
import br.edu.oficinacampus.dominio.cadastros.OficinaRepositorio;
import br.edu.oficinacampus.aplicacao.cadastros.ServicoVinculadoOficina;
import java.util.List;
import java.util.Objects;

/** Guias de preparação: cada ficha pertence a uma oficina ativa. */
public final class GuiaPreparacaoServico extends ServicoVinculadoOficina<GuiaPreparacao> {
    public GuiaPreparacaoServico(GuiaPreparacaoRepositorio repositorio, Eventos eventos, OficinaRepositorio oficinas) {
        super(repositorio, eventos, "guias", new PoliticaCiclo.Conteudo(), oficinas);
    }
    protected List<String> valoresPesquisa(GuiaPreparacao agregado) {
        return List.of(Objects.toString(agregado.titulo(), ""), Objects.toString(agregado.resumo(), ""), Objects.toString(agregado.instrucoes(), ""));
    }
}
