package br.edu.oficinacampus.infraestrutura.persistencia.conteudo;

import br.edu.oficinacampus.dominio.conteudo.*;
import br.edu.oficinacampus.infraestrutura.persistencia.RepositorioJpa;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;

@Repository
public class MaterialApoioRepositorioJpa extends RepositorioJpa<MaterialApoio, MaterialApoioJpa> implements MaterialApoioRepositorio {
    public MaterialApoioRepositorioJpa() { super(MaterialApoioJpa.class, MaterialApoioJpa::new); }
    protected MaterialApoio dominio(MaterialApoioJpa row) { return new MaterialApoio(row.controle(), row.titulo, row.endereco, row.descricao, row.oficina); }
    protected void preencher(MaterialApoio agregado, MaterialApoioJpa row) {
        row.titulo = agregado.titulo();
        row.endereco = agregado.endereco();
        row.descricao = agregado.descricao();
        row.oficina = agregado.oficina();
    }
}
