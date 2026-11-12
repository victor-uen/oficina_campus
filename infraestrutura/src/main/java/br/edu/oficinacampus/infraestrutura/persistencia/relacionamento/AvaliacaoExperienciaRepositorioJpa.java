package br.edu.oficinacampus.infraestrutura.persistencia.relacionamento;

import br.edu.oficinacampus.dominio.relacionamento.*;
import br.edu.oficinacampus.infraestrutura.persistencia.RepositorioJpa;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;

@Repository
public class AvaliacaoExperienciaRepositorioJpa extends RepositorioJpa<AvaliacaoExperiencia, AvaliacaoExperienciaJpa> implements AvaliacaoExperienciaRepositorio {
    public AvaliacaoExperienciaRepositorioJpa() { super(AvaliacaoExperienciaJpa.class, AvaliacaoExperienciaJpa::new); }
    protected AvaliacaoExperiencia dominio(AvaliacaoExperienciaJpa row) { return new AvaliacaoExperiencia(row.controle(), row.nota, row.aspecto, row.comentario, row.nome, row.oficina); }
    protected void preencher(AvaliacaoExperiencia agregado, AvaliacaoExperienciaJpa row) {
        row.nota = agregado.nota();
        row.aspecto = agregado.aspecto();
        row.comentario = agregado.comentario();
        row.nome = agregado.nome();
        row.oficina = agregado.oficina();
    }
}
