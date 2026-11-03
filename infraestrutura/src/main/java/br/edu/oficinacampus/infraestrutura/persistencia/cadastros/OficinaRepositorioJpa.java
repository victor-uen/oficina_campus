package br.edu.oficinacampus.infraestrutura.persistencia.cadastros;

import br.edu.oficinacampus.dominio.cadastros.*;
import br.edu.oficinacampus.infraestrutura.persistencia.RepositorioJpa;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;

@Repository
public class OficinaRepositorioJpa extends RepositorioJpa<Oficina, OficinaJpa> implements OficinaRepositorio {
    public OficinaRepositorioJpa() { super(OficinaJpa.class, OficinaJpa::new); }
    protected Oficina dominio(OficinaJpa row) { return new Oficina(row.controle(), row.titulo, row.apresentacao, row.modalidade, row.duracao, row.limite, row.data, row.espaco, row.instrutores); }
    protected void preencher(Oficina agregado, OficinaJpa row) {
        row.titulo = agregado.titulo();
        row.apresentacao = agregado.apresentacao();
        row.modalidade = agregado.modalidade();
        row.duracao = agregado.duracao();
        row.limite = agregado.limite();
        row.data = agregado.data();
        row.espaco = agregado.espaco();
        row.instrutores = new ArrayList<>(agregado.instrutores());
    }
}
