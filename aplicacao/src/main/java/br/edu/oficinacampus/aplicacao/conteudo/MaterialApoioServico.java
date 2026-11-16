package br.edu.oficinacampus.aplicacao.conteudo;

import br.edu.oficinacampus.dominio.conteudo.*;
import br.edu.oficinacampus.aplicacao.compartilhada.*;
import br.edu.oficinacampus.dominio.compartilhado.PoliticaCiclo;
import br.edu.oficinacampus.dominio.cadastros.OficinaRepositorio;
import br.edu.oficinacampus.aplicacao.cadastros.ServicoVinculadoOficina;
import java.util.List;
import java.util.Objects;

/** Materiais de apoio: cada ficha pertence a uma oficina ativa. */
public final class MaterialApoioServico extends ServicoVinculadoOficina<MaterialApoio> {
    public MaterialApoioServico(MaterialApoioRepositorio repositorio, Eventos eventos, OficinaRepositorio oficinas) {
        super(repositorio, eventos, "materiais", new PoliticaCiclo.Conteudo(), oficinas);
    }
    protected List<String> valoresPesquisa(MaterialApoio agregado) {
        return List.of(Objects.toString(agregado.titulo(), ""), Objects.toString(agregado.endereco(), ""), Objects.toString(agregado.descricao(), ""));
    }
}
