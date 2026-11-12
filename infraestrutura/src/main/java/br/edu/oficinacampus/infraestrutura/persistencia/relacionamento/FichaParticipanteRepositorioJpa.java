package br.edu.oficinacampus.infraestrutura.persistencia.relacionamento;

import br.edu.oficinacampus.dominio.relacionamento.*;
import br.edu.oficinacampus.infraestrutura.persistencia.RepositorioJpa;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;

@Repository
public class FichaParticipanteRepositorioJpa extends RepositorioJpa<FichaParticipante, FichaParticipanteJpa> implements FichaParticipanteRepositorio {
    public FichaParticipanteRepositorioJpa() { super(FichaParticipanteJpa.class, FichaParticipanteJpa::new); }
    protected FichaParticipante dominio(FichaParticipanteJpa row) { return new FichaParticipante(row.controle(), row.nome, row.email, row.telefone, row.profissao, row.observacoes, row.oficina); }
    protected void preencher(FichaParticipante agregado, FichaParticipanteJpa row) {
        row.nome = agregado.nome();
        row.email = agregado.email();
        row.telefone = agregado.telefone();
        row.profissao = agregado.profissao();
        row.observacoes = agregado.observacoes();
        row.oficina = agregado.oficina();
    }
}
