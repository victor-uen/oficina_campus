package br.edu.oficinacampus.infraestrutura.persistencia.recursos;

import br.edu.oficinacampus.dominio.recursos.*;
import br.edu.oficinacampus.infraestrutura.persistencia.RepositorioJpa;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;

@Repository
public class InstituicaoParceiraRepositorioJpa extends RepositorioJpa<InstituicaoParceira, InstituicaoParceiraJpa> implements InstituicaoParceiraRepositorio {
    public InstituicaoParceiraRepositorioJpa() { super(InstituicaoParceiraJpa.class, InstituicaoParceiraJpa::new); }
    protected InstituicaoParceira dominio(InstituicaoParceiraJpa row) { return new InstituicaoParceira(row.controle(), row.nome, row.modalidade, row.contato, row.oficinas); }
    protected void preencher(InstituicaoParceira agregado, InstituicaoParceiraJpa row) {
        row.nome = agregado.nome();
        row.modalidade = agregado.modalidade();
        row.contato = agregado.contato();
        row.oficinas = new ArrayList<>(agregado.oficinas());
    }
}
