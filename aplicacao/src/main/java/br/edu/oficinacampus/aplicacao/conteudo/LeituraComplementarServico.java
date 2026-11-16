package br.edu.oficinacampus.aplicacao.conteudo;

import br.edu.oficinacampus.dominio.conteudo.*;
import br.edu.oficinacampus.aplicacao.compartilhada.*;
import br.edu.oficinacampus.dominio.compartilhado.PoliticaCiclo;
import br.edu.oficinacampus.dominio.cadastros.OficinaRepositorio;
import br.edu.oficinacampus.aplicacao.cadastros.ServicoVinculadoOficina;
import java.util.List;
import java.util.Objects;

/** Leituras complementares: cada ficha pertence a uma oficina ativa. */
public final class LeituraComplementarServico extends ServicoVinculadoOficina<LeituraComplementar> {
    public LeituraComplementarServico(LeituraComplementarRepositorio repositorio, Eventos eventos, OficinaRepositorio oficinas) {
        super(repositorio, eventos, "leituras", new PoliticaCiclo.Conteudo(), oficinas);
    }
    protected List<String> valoresPesquisa(LeituraComplementar agregado) {
        return List.of(Objects.toString(agregado.titulo(), ""), Objects.toString(agregado.autor(), ""), Objects.toString(agregado.tipo(), ""), Objects.toString(agregado.trecho(), ""), Objects.toString(agregado.obrigatoriedade(), ""), Objects.toString(agregado.ordem(), ""));
    }
}
