package br.edu.oficinacampus.infraestrutura.persistencia.cadastros;

import br.edu.oficinacampus.dominio.cadastros.*;
import br.edu.oficinacampus.infraestrutura.persistencia.RepositorioJpa;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;

@Repository
public class EspacoRepositorioJpa extends RepositorioJpa<Espaco, EspacoJpa> implements EspacoRepositorio {
    public EspacoRepositorioJpa() { super(EspacoJpa.class, EspacoJpa::new); }
    protected Espaco dominio(EspacoJpa row) { return new Espaco(row.controle(), row.nome, row.localizacao, row.descricao, row.capacidade); }
    protected void preencher(Espaco agregado, EspacoJpa row) {
        row.nome = agregado.nome();
        row.localizacao = agregado.localizacao();
        row.descricao = agregado.descricao();
        row.capacidade = agregado.capacidade();
    }
}
