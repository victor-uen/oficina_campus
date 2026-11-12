package br.edu.oficinacampus.aplicacao.relacionamento;

import br.edu.oficinacampus.dominio.relacionamento.*;
import br.edu.oficinacampus.aplicacao.compartilhada.*;
import br.edu.oficinacampus.dominio.compartilhado.PoliticaCiclo;
import java.util.List;
import java.util.Objects;

/** Atendimentos: cadastro sem vínculo com outros agregados. */
public final class SolicitacaoAtendimentoServico extends ServicoCadastro<SolicitacaoAtendimento> {
    public SolicitacaoAtendimentoServico(SolicitacaoAtendimentoRepositorio repositorio, Eventos eventos) {
        super(repositorio, eventos, "atendimentos", new PoliticaCiclo.Padrao());
    }
    protected List<String> valoresPesquisa(SolicitacaoAtendimento agregado) {
        return List.of(Objects.toString(agregado.assunto(), ""), Objects.toString(agregado.descricao(), ""), Objects.toString(agregado.categoria(), ""));
    }
}
