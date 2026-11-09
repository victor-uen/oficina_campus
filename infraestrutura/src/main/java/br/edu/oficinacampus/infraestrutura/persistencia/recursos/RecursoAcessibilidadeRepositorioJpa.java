package br.edu.oficinacampus.infraestrutura.persistencia.recursos;

import br.edu.oficinacampus.dominio.recursos.*;
import br.edu.oficinacampus.infraestrutura.persistencia.RepositorioJpa;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;

@Repository
public class RecursoAcessibilidadeRepositorioJpa extends RepositorioJpa<RecursoAcessibilidade, RecursoAcessibilidadeJpa> implements RecursoAcessibilidadeRepositorio {
    public RecursoAcessibilidadeRepositorioJpa() { super(RecursoAcessibilidadeJpa.class, RecursoAcessibilidadeJpa::new); }
    protected RecursoAcessibilidade dominio(RecursoAcessibilidadeJpa row) { return new RecursoAcessibilidade(row.controle(), row.nome, row.categoria, row.orientacao, row.oficina); }
    protected void preencher(RecursoAcessibilidade agregado, RecursoAcessibilidadeJpa row) {
        row.nome = agregado.nome();
        row.categoria = agregado.categoria();
        row.orientacao = agregado.orientacao();
        row.oficina = agregado.oficina();
    }
}
