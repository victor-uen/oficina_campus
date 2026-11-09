package br.edu.oficinacampus.infraestrutura.persistencia.recursos;

import br.edu.oficinacampus.dominio.recursos.*;
import br.edu.oficinacampus.infraestrutura.persistencia.RepositorioJpa;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;

@Repository
public class KitMateriaisRepositorioJpa extends RepositorioJpa<KitMateriais, KitMateriaisJpa> implements KitMateriaisRepositorio {
    public KitMateriaisRepositorioJpa() { super(KitMateriaisJpa.class, KitMateriaisJpa::new); }
    protected KitMateriais dominio(KitMateriaisJpa row) { return new KitMateriais(row.controle(), row.nome, row.finalidade, row.componentes, row.oficina); }
    protected void preencher(KitMateriais agregado, KitMateriaisJpa row) {
        row.nome = agregado.nome();
        row.finalidade = agregado.finalidade();
        row.componentes = new ArrayList<>(agregado.componentes());
        row.oficina = agregado.oficina();
    }
}
