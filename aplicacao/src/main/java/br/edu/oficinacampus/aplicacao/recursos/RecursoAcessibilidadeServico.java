package br.edu.oficinacampus.aplicacao.recursos;

import br.edu.oficinacampus.dominio.recursos.*;
import br.edu.oficinacampus.aplicacao.compartilhada.*;
import br.edu.oficinacampus.dominio.compartilhado.PoliticaCiclo;
import br.edu.oficinacampus.dominio.cadastros.OficinaRepositorio;
import br.edu.oficinacampus.aplicacao.cadastros.ServicoVinculadoOficina;
import java.util.List;
import java.util.Objects;

/** Acessibilidade: cada ficha pertence a uma oficina ativa. */
public final class RecursoAcessibilidadeServico extends ServicoVinculadoOficina<RecursoAcessibilidade> {
    public RecursoAcessibilidadeServico(RecursoAcessibilidadeRepositorio repositorio, Eventos eventos, OficinaRepositorio oficinas) {
        super(repositorio, eventos, "acessibilidade", new PoliticaCiclo.Padrao(), oficinas);
    }
    protected List<String> valoresPesquisa(RecursoAcessibilidade agregado) {
        return List.of(Objects.toString(agregado.nome(), ""), Objects.toString(agregado.categoria(), ""), Objects.toString(agregado.orientacao(), ""));
    }
}
