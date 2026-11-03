package br.edu.oficinacampus.infraestrutura.persistencia.cadastros;

import br.edu.oficinacampus.dominio.cadastros.*;
import br.edu.oficinacampus.infraestrutura.persistencia.RepositorioJpa;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;

@Repository
public class InstrutorRepositorioJpa extends RepositorioJpa<Instrutor, InstrutorJpa> implements InstrutorRepositorio {
    public InstrutorRepositorioJpa() { super(InstrutorJpa.class, InstrutorJpa::new); }
    protected Instrutor dominio(InstrutorJpa row) { return new Instrutor(row.controle(), row.nome, row.apresentacao, row.especialidade, row.contato); }
    protected void preencher(Instrutor agregado, InstrutorJpa row) {
        row.nome = agregado.nome();
        row.apresentacao = agregado.apresentacao();
        row.especialidade = agregado.especialidade();
        row.contato = agregado.contato();
    }
}
