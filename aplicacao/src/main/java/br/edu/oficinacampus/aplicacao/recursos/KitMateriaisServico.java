package br.edu.oficinacampus.aplicacao.recursos;

import br.edu.oficinacampus.dominio.recursos.*;
import br.edu.oficinacampus.aplicacao.compartilhada.*;
import br.edu.oficinacampus.dominio.compartilhado.PoliticaCiclo;
import br.edu.oficinacampus.dominio.cadastros.OficinaRepositorio;
import br.edu.oficinacampus.aplicacao.cadastros.ServicoVinculadoOficina;
import java.util.List;
import java.util.Objects;

/** Kits de materiais: cada ficha pertence a uma oficina ativa. */
public final class KitMateriaisServico extends ServicoVinculadoOficina<KitMateriais> {
    public KitMateriaisServico(KitMateriaisRepositorio repositorio, Eventos eventos, OficinaRepositorio oficinas) {
        super(repositorio, eventos, "kits", new PoliticaCiclo.Padrao(), oficinas);
    }
    protected List<String> valoresPesquisa(KitMateriais agregado) {
        return List.of(Objects.toString(agregado.nome(), ""), Objects.toString(agregado.finalidade(), ""), Objects.toString(agregado.componentes(), ""));
    }
}
