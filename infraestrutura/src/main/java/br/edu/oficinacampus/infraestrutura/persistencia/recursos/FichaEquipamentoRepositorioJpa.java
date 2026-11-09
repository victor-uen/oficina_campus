package br.edu.oficinacampus.infraestrutura.persistencia.recursos;

import br.edu.oficinacampus.dominio.recursos.*;
import br.edu.oficinacampus.infraestrutura.persistencia.RepositorioJpa;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;

@Repository
public class FichaEquipamentoRepositorioJpa extends RepositorioJpa<FichaEquipamento, FichaEquipamentoJpa> implements FichaEquipamentoRepositorio {
    public FichaEquipamentoRepositorioJpa() { super(FichaEquipamentoJpa.class, FichaEquipamentoJpa::new); }
    protected FichaEquipamento dominio(FichaEquipamentoJpa row) { return new FichaEquipamento(row.controle(), row.nome, row.tipo, row.instrucoes, row.oficinas); }
    protected void preencher(FichaEquipamento agregado, FichaEquipamentoJpa row) {
        row.nome = agregado.nome();
        row.tipo = agregado.tipo();
        row.instrucoes = agregado.instrucoes();
        row.oficinas = new ArrayList<>(agregado.oficinas());
    }
}
