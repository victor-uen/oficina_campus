package br.edu.oficinacampus.aplicacao.cadastros;

import br.edu.oficinacampus.dominio.cadastros.*;
import br.edu.oficinacampus.aplicacao.compartilhada.*;
import br.edu.oficinacampus.dominio.compartilhado.PoliticaCiclo;
import java.util.List;
import java.util.Objects;

/** Instrutores: cadastro sem vínculo com outros agregados. */
public final class InstrutorServico extends ServicoCadastro<Instrutor> {
    public InstrutorServico(InstrutorRepositorio repositorio, Eventos eventos) {
        super(repositorio, eventos, "instrutores", new PoliticaCiclo.Padrao());
    }
    protected List<String> valoresPesquisa(Instrutor agregado) {
        return List.of(Objects.toString(agregado.nome(), ""), Objects.toString(agregado.apresentacao(), ""), Objects.toString(agregado.especialidade(), ""), Objects.toString(agregado.contato(), ""));
    }
}
