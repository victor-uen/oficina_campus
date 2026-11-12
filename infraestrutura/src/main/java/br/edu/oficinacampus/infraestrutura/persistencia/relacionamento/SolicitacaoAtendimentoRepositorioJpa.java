package br.edu.oficinacampus.infraestrutura.persistencia.relacionamento;

import br.edu.oficinacampus.dominio.relacionamento.*;
import br.edu.oficinacampus.infraestrutura.persistencia.RepositorioJpa;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;

@Repository
public class SolicitacaoAtendimentoRepositorioJpa extends RepositorioJpa<SolicitacaoAtendimento, SolicitacaoAtendimentoJpa> implements SolicitacaoAtendimentoRepositorio {
    public SolicitacaoAtendimentoRepositorioJpa() { super(SolicitacaoAtendimentoJpa.class, SolicitacaoAtendimentoJpa::new); }
    protected SolicitacaoAtendimento dominio(SolicitacaoAtendimentoJpa row) { return new SolicitacaoAtendimento(row.controle(), row.assunto, row.descricao, row.categoria); }
    protected void preencher(SolicitacaoAtendimento agregado, SolicitacaoAtendimentoJpa row) {
        row.assunto = agregado.assunto();
        row.descricao = agregado.descricao();
        row.categoria = agregado.categoria();
    }
}
