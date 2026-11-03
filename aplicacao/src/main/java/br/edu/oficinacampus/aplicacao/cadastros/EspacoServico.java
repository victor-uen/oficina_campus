package br.edu.oficinacampus.aplicacao.cadastros;

import br.edu.oficinacampus.dominio.cadastros.*;
import br.edu.oficinacampus.aplicacao.compartilhada.*;
import br.edu.oficinacampus.dominio.compartilhado.PoliticaCiclo;
import java.util.List;
import java.util.Objects;

/** Espaços: cadastro sem vínculo com outros agregados. */
public final class EspacoServico extends ServicoCadastro<Espaco> {
    public EspacoServico(EspacoRepositorio repositorio, Eventos eventos) {
        super(repositorio, eventos, "espacos", new PoliticaCiclo.Padrao());
    }
    protected List<String> valoresPesquisa(Espaco agregado) {
        return List.of(Objects.toString(agregado.nome(), ""), Objects.toString(agregado.localizacao(), ""), Objects.toString(agregado.descricao(), ""), Objects.toString(agregado.capacidade(), ""));
    }
}
